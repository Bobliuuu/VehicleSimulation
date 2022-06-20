import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Ambulance subclass of Vehicle
 * Heals passengers and makes a siren sound
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Ambulance extends Vehicle
{
    // Instance variables
    private GreenfootSound ambulanceSound = new GreenfootSound("ambulance.wav");
    // Constructor
    public Ambulance ()
    {
        maxSpeed = 3;
        speed = maxSpeed;
        canChangeLanes = true;
        targetLane = -1;
        ambulanceSound.playLoop();
    }
    
    // act() method - called by Greenfoot at every "act" or step of execution
    public void act ()
    {
        checkHitPedestrian ();  // not implemented 
        drive();
        checkEdges();
    }
    
    /**
     * Play the ambulance sound
     */
    public void playSound(){
        ambulanceSound.playLoop();
    }
    
    /**
     * Stop the ambulance sound
     */
    public void stopSound(){
        ambulanceSound.stop();
    }
    
    /**
     * Method that checks if the Ambulance has reached the edge of the World
     */
    public void checkEdges(){
        if (getX() < -getImage().getWidth() || getX() > getWorld().getBackground().getWidth() + getImage().getWidth()){
            stopSound();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Remove this ambulance from the World
     */
    public void removeSelf(){
        stopSound();
        getWorld().removeObject(this);
    }
    
    /**
     * Check if the ambulance has hit a Pedestrian
     */
    public void checkHitPedestrian ()
    {
        ArrayList <Pedestrian> arr = (ArrayList <Pedestrian>) getIntersectingObjects(Pedestrian.class);
        for (Pedestrian p : arr){
            p.healMe();
        }
    }
}
