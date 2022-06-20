import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Bus subclass of Vehicle
 * Picks up passengers
 * 
 * @author Jerry Zhu
 * @version 1
 */

public class Bus extends Vehicle
{
    // Instance variables 
    private GreenfootImage rideBus = new GreenfootImage("bus2.png");
    private GreenfootSound busSound = new GreenfootSound("bus.wav");
    private boolean hasPassengers;
    private int delay;
    private boolean lastDrive;
    // Constructor
    public Bus ()
    {
        maxSpeed = 2;
        speed = maxSpeed;
        hasPassengers = false;
        delay = 0;
        lastDrive = false;
        canChangeLanes = true;
        targetLane = -1;
    }

    // act() method - called by Greenfoot at every "act" or step of execution
    public void act()
    {
        checkHitPedestrian ();  // not implemented 
        if (delay > 0 && --delay > 0){ // Decrements in the comparison operator
            return;
        }
        else {
            drive();
        }
        checkEdges();
    }
    
    /**
     * Remove this bus from the World
     */
    public void removeSelf(){
        getWorld().removeObject(this);
    }
    
    /**
     * Check if the bus has hit a Pedestrian
     * Uses an array of offset detections along the bottom and right side of the bus
     */
    public void checkHitPedestrian ()
    {
        int width = getImage().getWidth();
        int height = getImage().getHeight();
        int offset = width / 2 + 1;
        for (int i = -(height/2); i < (height/2); i += 5){  
            p = (Pedestrian)getOneObjectAtOffset(offset, i, Pedestrian.class);
            // DEBUG: TURN ON MARKERS
            //Marker m = new Marker();
            //getWorld().addObject(m, getX() + offset, getY() + i);
            if (p != null && p.isHealthy() == true){
                if (hasPassengers == false){
                    //busSound.setVolume(60);
                    busSound.play();
                    p.pickMeUp();
                    hasPassengers = true;
                    delay = 60;
                    speed = 0;
                    setImage(rideBus);
                }
                else {
                    p.knockMeOver();
                }
            }
        }
        
        offset = height / 2;
        for (int i = -(width/2); i < (width/2); i += 5){  
            p = (Pedestrian)getOneObjectAtOffset(i, offset, Pedestrian.class);
            // DEBUG: TURN ON MARKERS
            //Marker m = new Marker();
            //getWorld().addObject(m, getX() + i, getY() + offset);
            if (p != null && p.isHealthy() == true){
                if (hasPassengers == false){
                    p.pickMeUp();
                    hasPassengers = true;
                    delay = 60;
                    speed = 0;
                    setImage(rideBus);
                }
                else {
                    p.knockMeOver();
                }
            }
        }
    }
}
