import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Limo subclass of Vehicle
 * Bus-like vehicle that picks up rich passengers
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Limo extends Vehicle
{
    // Instance variables
    private int offset;
    private Pedestrian p;
    private GreenfootImage first = new GreenfootImage("limo.png");
    private GreenfootImage second = new GreenfootImage("limo2.png");
    //Constructor
    public Limo ()
    {
        maxSpeed = 3;
        speed = maxSpeed;
        canChangeLanes = true;
        targetLane = -1;
        setImage(first);
    }
    
    // act() method - called by Greenfoot at every "act" or step of execution
    public void act()
    {
        checkHitPedestrian ();  
        drive();
        checkEdges();
    }
    
    /**
     * Method that removes this Limo from the WOrld
     */
    public void removeSelf(){
        getWorld().removeObject(this);
    }
    
    /**
     * Method that checks if a Limo should pick up or knock over a Pedestrian
     * Limos only pick up Rich Pedestrians
     */
    public void checkHitPedestrian ()
    {
        ArrayList <Pedestrian> arr = (ArrayList <Pedestrian>) getIntersectingObjects(Pedestrian.class);
        for (Pedestrian p : arr){
            if (p instanceof Rich && p.isHealthy()){
                getWorld().removeObject(p);
                setImage(second);
            }
            else {
                p.knockMeOver();
            }
        }
    }
}
