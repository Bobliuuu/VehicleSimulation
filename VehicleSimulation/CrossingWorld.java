import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * CrossingWorld functions as the "Stage" for this Greenfoot
 * project which helps students understand inheritance
 * 
 * INSERT YOUR WRITTEN TASK HERE:
 * Scenario Name: Free For All
 * Author: Jerry Zhu
 * 
 * Credit:
 * Art: Gimp, MSPaint, Google Images (Pinterest, ShutterStock, ClipartMax), 
 * Andrew Qiao (github.com/qiaoandrew/ParadiseTowerDefense/tree/main/ParadiseTowerDefense/images)
 * Music: www.suonoelettronico.com, www.zapsplat.com, mixkit.co/free-sound-effects, www.soundjay.com
 * Code: Mr.Cohen's in class examples (Files credited with author name(s))
 * 
 * Reflection:
 * 1. My scenario has interactions between vehicles, projectiles, pedestrians, and other standalone objects. 
 * Ambulances, buses, cars, limos, police cars, and tanks (vehicles) drive on the streets
 * Bullets are shot and bombs are placed (projectiles) which can hurt pedestrians and vehicles
 * Rich, poor, villain, and soldier (pedestrians) move perpendicular to the streets and can be hit by by projectiles and vehicles
 * Objects like planes and sitting tanks also enter and exit the World
 * Effects like police slowdown, blizzards, and the night also affect objects in the World
 * Sounds include traffic ambient sounds, projectile/vehicle/pedestrian sounds, special action sounds, and interaction sounds
 * 2. OOP helped to refactor my code to make it more efficient. Inheritance allowed me to make superclasses like Projectile, 
 * Pedestrian, and Vehicle, and make methods and variables that could be easily inherited by any subclasses. By defining and 
 * organizing my classes in this way, I could quickly find out how an interaction worked, and which classes would achieve this 
 * effect. This made it much easier to add new classes and debug any errors. 
 * While coding, I was able to reuse methods and refactor my code to create modular and reusable code. One prominent example is the
 * drive() method, where I could reuse for all of my Vehicles and not have to worry about copy pasting or errors. Abstract methods
 * helped me organize my code into a readable format, and make sure that I implemented all the methods I was supposed to. For example, 
 * I was easily able to implement the SmoothMover superclass, which helped out substantially with the object movement. 
 * My end product was also much more sophisticated as a result. I was able to add objects into the World easily, improve interactions
 * between classes rather than worrying about functional constraints, and create more interesting characters rather than being 
 * stuck with rewriting code. 
 * 
 * Checklist: 
 * Car changes lane when a slower vehicle is ahead via drive() method override: DONE
 * Ambulance and Bus interactions implemented: DONE
 * An additional Vehicle class has been implemented with its own interactions: DONE
 * Pedestrian has been turned into an abstract superclass with two subclasses with unique behavior: DONE
 * Rich, Poor, Soldier, Villain
 * Added an effect that temporarily changes the speed of all Vehicles in the simulation through use of ArrayList and iterative loop: DONE
 * Blizzard effect
 * Added an effect that temporarily changes the speed of some Vehicles in the simulation through use of ArrayList and iterative loop, via collision detection or radius search
 * Police siren effect, explosion effect
 * Added (6) sound effects, as described: DONE
 * Air raid, Ambulance, blizzard, bus, carhonk, carscreech, explosion, gunshot, policesiren, shovel, splat, traffic ambient sounds
 * 
 * @author Jordan Cohen, Jerry Zhu
 * @version 1
 */
public class CrossingWorld extends World
{
    // Instance variables
    private int randomize;
    private int bulletDelay;
    private static Color[] swatch;
    private GreenfootImage background = new GreenfootImage("background.png");
    private GreenfootImage backgroundDark = new GreenfootImage("backgrounddark.png");
    private GreenfootImage image;
    private GreenfootImage snowyImage;
    private GreenfootSound traffic;
    private GreenfootSound blizzard;
    private boolean hasEffect;
    private boolean isDay;
    private boolean shootSideways;
    private boolean isBlizzard;
    private boolean isFlying;
    /**
     * Spawn Rates:
     * Lower number means more spawns
     * 3:spawnRate chance per act of spawning a random Vehicle
     * 1:pedSpawn chance per act of spawning a Pedestrian
     */
    private int spawnRate = 200; // must be higher than 3 ... should be higher than 30
    private int pedSpawn = 200; 
    private int planeRate = 250;
    private int weatherCountdown;
    private int dayNightCountdown;
    private final int BULLETDELAY = 150;
    private final int BETWEENBULLETS = 10;
    // LaneSpawnChecks are transparent rectangles the size of vehicles to determine if a 
    // Vehicle exists in a given post. These ones are used to determine if a lane is available
    // for spawning a new Vehicle, so that a new Vehicle can be safely spawned without overlap.
    private LaneSpawnCheck[] laneChecks;
    // Array for fast sounds
    private GreenfootSound[] bulletSounds;
    private int bulletSoundsIndex;
    private int bulletWait;
    private boolean shootingSecond;
    private GreenfootSound[] splatSounds;
    private int splatSoundsIndex;
    /**
     * Constructor for objects of class CrossingWorld.
     */
    public CrossingWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 
        swatch = loadSwatch();
        setBackground(background);
        image = background;
        weatherCountdown = 0;
        laneChecks = new LaneSpawnCheck [6];
        for (int i = 0; i < 6; i++){
            laneChecks[i] = new LaneSpawnCheck();
            addObject (laneChecks[i], 40, getYPosition (i));
        }
        addObject (new SunMoon(), -40, 10);
        isDay = true;
        dayNightCountdown = 300;
        bulletDelay = BULLETDELAY;
        traffic = new GreenfootSound("traffic.wav");
        blizzard = new GreenfootSound("blizzard.wav");
        addObject(new SittingTank(true), 30, 365);
        addObject(new SittingTank(false), 570, 365);
        shootSideways = true;
        isFlying = false;
        bulletSoundsIndex = 0;
        bulletSounds = new GreenfootSound[20];
        for (int i = 0; i < bulletSounds.length; i++){
            bulletSounds[i] = new GreenfootSound("gunshot.wav");
        }
        bulletWait = 0;
        shootingSecond = false;
        
        splatSoundsIndex = 0;
        splatSounds = new GreenfootSound[20];
        for (int i = 0; i < splatSounds.length; i++){
            splatSounds[i] = new GreenfootSound("splat.wav");
        }
    }
    
    /**
     * Act - do whatever the CrossingWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act ()
    {
        // Run methods to see if any pedestrians or vehicles are going to be spawned this act:
        spawnPedestrians();
        spawnVehicles();
        spawnEvents();
        spawnBullet();
        spawnAirStrike();
    }
    
    /**
     * Method to set image to a specific Greenfoot image
     */
    public void setImageTo(GreenfootImage image){
        this.image = image;
    }
    
    /**
     * Method that is called when Greenfoot starts up or play button is pressed
     */
    public void started(){
        ArrayList <Police> arrP = (ArrayList <Police>) getObjects(Police.class);
        for (Police p : arrP){
            p.playSound();
        }
        ArrayList <Ambulance> arrA = (ArrayList <Ambulance>) getObjects(Ambulance.class);
        for (Ambulance p : arrA){
            p .playSound();
        }
        traffic.playLoop();
        if (isBlizzard){
            blizzard.playLoop();
        }
    }
    
    /**
     * Method that is called when Greenfoot stops or pause button is pressed
     */
    public void stopped(){
        ArrayList <Police> arrP = (ArrayList <Police>) getObjects(Police.class);
        for (Police p : arrP){
            p.stopSound();
        }
        ArrayList <Ambulance> arrA = (ArrayList <Ambulance>) getObjects(Ambulance.class);
        for (Ambulance p : arrA){
            p.stopSound();
        }
        traffic.pause();
        blizzard.pause();
    }
    
    /**
     * Spawn an air strike in the World
     */
    public void spawnAirStrike(){
        int rand = Greenfoot.getRandomNumber(planeRate);
        if (rand == 1 && !isFlying){
            isFlying = true;
            Plane p = new Plane();
            addObject(p, 0, 400);
        }
    }
    
    /**
     * Set the flying variable (if the plane is flying)
     */
    public void setFlying(boolean x){
        isFlying = x;
    }
    
    /**
     * Method that plays the splat sound when a pedestrian is hit
     */
    public void playSplat(){
        splatSounds[splatSoundsIndex].play();
        splatSoundsIndex++;
        if (splatSoundsIndex > splatSounds.length - 1){
            splatSoundsIndex = 0;
        }
    }
    
    /**
     * Method that spawns a bullet on a timer
     */
    private void spawnBullet(){
        if (bulletDelay != 0){
            bulletDelay--;
        }
        if (bulletDelay == 0){
            if (shootSideways){
                if (bulletWait == 0 && !shootingSecond){
                    addObject(new Bullet(-45), 50, 368);
                    shootSound();
                    bulletWait = 10;
                    shootingSecond = true;
                    ArrayList <SittingTank> arr = (ArrayList <SittingTank>) getObjects(SittingTank.class);
                    for (SittingTank p : arr){
                        p.moveSideways(true);
                    }
                }
                else {
                    bulletWait--;
                    if (bulletWait == 0){
                        addObject(new Bullet(-135), 550, 368);
                        shootSound();
                        ArrayList <SittingTank> arr = (ArrayList <SittingTank>) getObjects(SittingTank.class);
                        for (SittingTank p : arr){
                            p.moveSideways(false);
                        }
                        bulletDelay = BULLETDELAY;
                        shootSideways = false;
                        shootingSecond = false;
                    }
                }
            }
            else {
                if (bulletWait == 0 && !shootingSecond){
                    addObject(new Bullet(0), 50, 368);
                    shootSound();
                    bulletWait = 10;
                    shootingSecond = true;
                    ArrayList <SittingTank> arr = (ArrayList <SittingTank>) getObjects(SittingTank.class);
                    for (SittingTank p : arr){
                        p.moveStraight();
                    }
                }
                else {
                    bulletWait--;
                    if (bulletWait == 0){
                        addObject(new Bullet(180), 550, 368);
                        shootSound();
                        ArrayList <SittingTank> arr = (ArrayList <SittingTank>) getObjects(SittingTank.class);
                        for (SittingTank p : arr){
                            p.moveStraight();
                        }
                        bulletDelay = BULLETDELAY;
                        shootSideways = true;
                        shootingSecond = false;
                    }
                }
            }
        }
    }
    
    /**
     * Play the shoot sound once a bullet is shot
     */
    private void shootSound(){
        bulletSounds[bulletSoundsIndex].play();
        bulletSoundsIndex++;
        if (bulletSoundsIndex > bulletSounds.length - 1){
            bulletSoundsIndex = 0;
        }
    }
    
    /**
     * Spawns a blizzard at a randomized time interval
     */
    private void spawnEvents(){
        randomize = Greenfoot.getRandomNumber(300);
        if (randomize == 1 && weatherCountdown == 0){
            blizzard.playLoop();
            isBlizzard = true;
            snowyImage = new GreenfootImage(image);
            snowyImage.drawImage(drawSnow(600, 400, 50, false), 0, 0);
            setBackground(snowyImage);
            weatherCountdown = 120;
        }
        if (weatherCountdown > 0 && --weatherCountdown > 0){ // If timer is not 0
            ArrayList <Vehicle> vehicles = (ArrayList <Vehicle>) getObjects(Vehicle.class);
            for (Vehicle v : vehicles){
                v.startSnowing();
            }
        }
        else { // Timer is 0
            setBackground(image);
            ArrayList <Vehicle> vehicles = (ArrayList <Vehicle>) getObjects(Vehicle.class);
            for (Vehicle v : vehicles){
                v.stopSnowing();
            }
            blizzard.stop();
            isBlizzard = false;
        }
    }
    
    /**
     * Method to spawn vehicles with a random element
     */
    private void spawnVehicles()
    {
        // Generate a random number to add a random element
        // to Vehicle spawning
        randomize = Greenfoot.getRandomNumber(spawnRate);

        // Chose a random lane in case a vehicle spawns
        int lane = Greenfoot.getRandomNumber (6);
        
        // Check if randomize is a low number (not just 3, to leave room to add vehicles) and then
        // check if there is already a vehicle present (in which case, don't spawn anything, which will
        // affect the spawn rate, but that's okay).
        if (randomize < 10 && laneChecks[lane].vehiclePresent() == false){
            // determine the Y position of the desired lane
            int spawnY = getYPosition (lane);
            if (randomize == 1)
            {
                // spawn a Car
                addObject (new Car(), 10, spawnY);
            }
            else if (randomize == 2)
            {
                // spawn a Bus
                addObject (new Bus(), 10, spawnY);
            }

            else if (randomize == 3)
            {
                // spawn an Ambuluance
                addObject (new Ambulance(), 10, spawnY);
            }
            
            else if (randomize == 4)
            {
                // spawn a Police car
                addObject (new Police(), 10, spawnY);
            }
            
            else if (randomize == 5)
            {
                // spawn a Limo
                addObject (new Limo(), 10, spawnY);
            }
            else if (randomize == 6)
            {
                // spawn a Tank
                addObject (new Tank(), 10, spawnY);
            }
        }  

    }
    
    /**
     * Method to spawn Pedestrians to the World
     */
    private void spawnPedestrians()
    {
        int random = Greenfoot.getRandomNumber(pedSpawn);
        int place = Greenfoot.getRandomNumber(550) + 50;
        // spawn pedestrians
        if (random == 1)
        {
            addObject (new Poor(), place, 395);
        }
        if (random == 2)
        {
            addObject (new Rich(), place, 395);
        }
        if (random == 3)
        {
            addObject (new Villain(), place, 395);
        }
        if (random == 4)
        {
            addObject (new Soldier(), place, 395);
        }
    }

    /**
     * Returns the appropriate y coordinate for a given lane
     */
    public static int getYPosition (int inLane)
    {
        // Manually input values based on the background graphic
        switch (inLane)
        {
            case 0: 
            return 79;

            case 1:
            return 127;

            case 2:
            return 175;

            case 3:
            return 222;

            case 4:
            return 272;

            case 5: 
            return 320;

        }  
        // In case an invalid value is passed in
        return -1;
    }
     /**
     * An inverse of the getYPosition method! This will be useful to 
     * figure out what lane your Car is in for the purpose of 
     * changing lanes (and may be useful for other reasons, too).
     */
    public static int getLane (int yPosition){
        // Manually input values based on the background graphic
        switch (yPosition)
        {
            case 79: 
            return 0;


            case 127:
            return 1;


            case 175:
            return 2;


            case 222:
            return 3;


            case 272:
            return 4;


            case 320: 
            return 5;


        }  
        // In case an invalid value is passed in
        return -1;
    }
    
    /**
     * Calculate the distance between 2 lanes
     * Must be 1 lane apart
     */
    public static int laneDistance(int firstLane, int secondLane){ 
        return Math.abs(getLane(secondLane) - getLane(firstLane));
    }
    
    /**
     * Draw the blizzard effect to the World
     */
    private static GreenfootImage drawSnow(int width, int height, int density, boolean fill){
        GreenfootImage temp = new GreenfootImage(width, height);
        if (fill){
            temp.setColor(Color.BLACK);
            temp.fill();
        }
        for (int i = 0; i < density; i++){
            for (int j = 0; j < 100; j++){
                int randSize = Greenfoot.getRandomNumber(4) + 1;
                int randColor = Greenfoot.getRandomNumber(swatch.length);
                temp.setColor(swatch[randColor]);
                int randX = Greenfoot.getRandomNumber(width);
                int randY = Greenfoot.getRandomNumber(height);
                temp.fillOval(randX, randY, randSize, randSize);
            }
        }
        return temp;
    }
    
    /**
     * Load a swatch of colors to the World
     */
    private static Color[] loadSwatch(){
        Color[] swatch = new Color[64];
        int red = 128;
        int blue = 192;
        for (int i = 0; i < swatch.length / 2; i++){ 
            swatch[i] = new Color(red, 240, 255);
            red += 2;
        }
        for (int i = swatch.length/2; i < swatch.length; i++){ 
            swatch[i] = new Color(255, 255, blue);
            blue++;
        }
        return swatch;
    }
}

