import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Car subclass of Vehicle
 * Fast vehicle that knocks over Pedestrians
 * 
 * @author Jerry Zhu
 * @version 1
 */

public class Car extends Vehicle
{
    // Instance variables
    private int offset;
    private Pedestrian p;
    // Constructor
    public Car ()
    {
        maxSpeed = 4;
        speed = maxSpeed;
        canChangeLanes = true;
        targetLane = -1;
    }
    
    // act() method - called by Greenfoot at every "act" or step of execution
    public void act()
    {
        checkHitPedestrian ();  
        drive();
        checkEdges();
    }
    
    /**
     * Method that removes this Car from the WOrld
     */
    public void removeSelf(){
        getWorld().removeObject(this);
    }
    
    /**
     * Check if this car has hit a pedestrian
     */
    public void checkHitPedestrian ()
    {
        ArrayList <Pedestrian> arr = (ArrayList <Pedestrian>) getIntersectingObjects(Pedestrian.class);
        for (Pedestrian p : arr){
            p.knockMeOver();
        }
    }
}
