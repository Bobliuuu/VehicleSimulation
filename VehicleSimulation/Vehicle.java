import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Abstract class for all Vehicles
 * 
 * @author Jerry Zhu
 * @version 1
 */

public abstract class Vehicle extends SmoothMover
{   
    /**
     * Note: for each new vehicle, initialize these variables: 
     *  canChangeLanes = true;
        targetLane = -1;
     */
    // Variables in abstract classes should be implemented  here
    protected double speed;
    protected double maxSpeed;
    protected int wantLanePos;
    protected int targetLane;
    protected boolean snowy;
    protected boolean switchLeftLane;
    protected boolean switchRightLane;
    protected boolean canChangeLanes;
    protected boolean slowDown;
    protected boolean canHonk;
    protected boolean canScreech;
    protected boolean honkPlayed;
    protected Pedestrian p;
    protected GreenfootSound carHonk = new GreenfootSound("carhonk.wav");
    protected GreenfootSound carScreech = new GreenfootSound("carscreech.wav");
    /**
     * Abstract method declarations: 
     * This means that all Vehicles must have a
     * move() and checkHitPedestrian() method with the same signature:
     * E.g: public void move ();
     * 
     * HINT:
     * However, in this current implementation, the drive()
     * method for all three subclasses is the same. Perhaps
     * one of these methods will change if you implement something
     * interesting that requires different drive methods. If not,
     * it would be more efficient to actually write the method
     * here, making it inherited by not abstract.
     */

    // This is a promise - all Vehicles MUST contain this method:
    public abstract void checkHitPedestrian();
    
    // Promise that all Vehicles must remove themselves in some way
    public abstract void removeSelf();
    
    // These two methods are inherited by any children who do not overwrite them:
    // Remove me if I've gone fully off the edge
    protected void checkEdges(){
        if (getX() < -getImage().getWidth() || getX() > getWorld().getBackground().getWidth() + getImage().getWidth()){
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Method that starts the snowing effect in the scenario
     */
    public void startSnowing(){
        snowy = true;
    }
    
    /**
     * Method that stops the snowing effect in the scenario
     */
    public void stopSnowing(){
        snowy = false;
    }
    
    /**
     * Method that slows down the Vehicles around a Vehicle
     */
    public void slowDown(){
        slowDown = true;
    }
    
    /**
     * Method that causes a Vehicle to explode
     */
    public void explode(){
        Explosion e = new Explosion();
        getWorld().addObject(e, getX(), getY());
        removeSelf();
    }
    
    /**
     * Method that deals with movement. Speed can be set by individual subclasses in their constructors
     */
    public void drive() 
    {
        // Ahead is a generic vehicle - we don't know what type BUT
        // since every Vehicle "promises" to have a getSpeed() method,
        // we can call that on any vehicle to find out it's speed
        Vehicle ahead = (Vehicle) getOneObjectAtOffset (getImage().getWidth()/2 + (int)speed + 4, 0, Vehicle.class);
        if (this instanceof Tank){
            if (ahead == null){
                speed = maxSpeed;
            }
            else {
                speed = 0;
            }
            move(speed);
            return;
        }
        if (ahead == null && !snowy) // no vehicle ahead
        {
            speed = maxSpeed;
            canHonk = true;
            canScreech = true;
        } 
        else if (ahead == null && snowy){
            speed = maxSpeed / 2;
            canHonk = true;
            canScreech = true;
        }
        else {
            speed = ahead.getSpeed();
            if (canHonk){
                carHonk.play();
                canHonk = false;
                honkPlayed = true;
            }
            moveLanes();
        }
        if (slowDown && speed > 0){
            speed--;
            slowDown = false;
        }
        move(speed);
        if (switchRightLane){
            if (canScreech && honkPlayed){
                carScreech.play();
                canScreech = false;
                honkPlayed = false;
            }
            double change;
            if (getY() + speed >= wantLanePos){
                change = wantLanePos;
                switchRightLane = false;
                canChangeLanes = true;
                targetLane = -1;
            }
            else {
                change = getY() + speed;
            }
            setLocation(getX(), change);
        }
        else if (switchLeftLane){
            if (canScreech){
                carScreech.play();
                canScreech = false;
            }
            double change;
            if (getY() - speed <= wantLanePos){
                change = wantLanePos;
                switchLeftLane = false;
                canChangeLanes = false;
                targetLane = -1;
            }
            else {
                change = getY() - speed;
            }
            setLocation(getX(), change);
        }
    }
    
    /*
     * Previous collision detection using bounding boxes
    public void tryLanes(){ 
        int lane = CrossingWorld.getLane(this.getY());
        if (lane != 0){ // If not leftmost lane
            int offset = 20;
            LaneSpawnCheck checkMinus = new LaneSpawnCheck(getImage().getWidth(), getImage().getHeight(), offset);
            getWorld().addObject(checkMinus, this.getX() - offset, CrossingWorld.getYPosition(lane - 1));
            Vehicle v = (Vehicle)getOneIntersectingObject(Vehicle.class);
            if (v == null){
                setLocation(this.getX(), CrossingWorld.getYPosition(lane - 1));
            }
            getWorld().removeObject(checkMinus);
        }
        if (lane != 5){ // If not rightmost lane
            LaneSpawnCheck checkPlus = new LaneSpawnCheck(getImage().getWidth(), getImage().getHeight(), 0);
            getWorld().addObject(checkPlus, this.getX(), CrossingWorld.getYPosition(lane + 1));
            Vehicle v = (Vehicle)getOneIntersectingObject(Vehicle.class);
            if (v == null){
                setLocation(this.getX(), CrossingWorld.getYPosition(lane + 1));
            }
            getWorld().removeObject(checkPlus);
        }
    }
    */
    
    /**
     * Newest version of collision detection using image physics
     */
    public void moveLanes(){
        if (!canChangeLanes){
            return;
        }
        
        ArrayList <Vehicle> vehicles = (ArrayList <Vehicle>) getWorld().getObjects(Vehicle.class);
        int myLane = CrossingWorld.getLane(this.getY()); 
        boolean moveLeft = true;
        boolean moveRight = true;
        
        for (Vehicle v : vehicles){
            int otherLane = CrossingWorld.getLane(v.getY());
            if (otherLane == myLane + 1 || otherLane == myLane - 1){
                int laneDis = CrossingWorld.laneDistance(myLane, otherLane);
                int firstX = this.getX() + laneDis; // Assuming 45 degrees
                double distance = ((laneDis * 1.0) / this.getSpeed()) * v.getSpeed();
                int secondX = v.getX() + (int)distance;
                int margin = 5;
                if (Math.abs(secondX - firstX) <= ((this.getImage().getWidth() + v.getImage().getWidth()) / 2) + margin){
                    if (otherLane == myLane + 1){
                        moveRight = false;
                    }
                    else {
                        moveLeft = false;
                    }
                }
            }
        }
        
        for (Vehicle v : vehicles){
            int targetLane = v.getTargetLane();
            if (targetLane == myLane + 1){
                moveRight = false;
            }
            else if (targetLane == myLane - 1){
                moveLeft = false;
            }
        }
        
        if (moveRight == true && myLane < 5){
            switchRightLane = true;
            canChangeLanes = false;
            wantLanePos = CrossingWorld.getYPosition(myLane + 1);
            targetLane = myLane + 1;
        }
        else if (moveLeft == true && myLane > 0){
            switchLeftLane = true;
            canChangeLanes = false;
            wantLanePos = CrossingWorld.getYPosition(myLane - 1);
            targetLane = myLane - 1;
        }
    }
    
    /**
     * Getter method that returns the speed of the Vehicle
     */
    public double getSpeed(){
        return speed;
    }
    
    /**
     * Getter method that returns the current target lane of the Vehicle
     */
    public int getTargetLane(){
        return targetLane;
    }
}



