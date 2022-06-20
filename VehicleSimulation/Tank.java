import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Tank subclass of Vehicle
 * Slow vehicle that shoots bullets that hit Pedestrians
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Tank extends Vehicle
{
    // Instance variables
    private int offset;
    private Pedestrian p;
    private static boolean isSound;
    private GreenfootSound policeSiren = new GreenfootSound("policesiren.wav");
    
    // Constructor
    public Tank()
    {
        maxSpeed = 1;
        speed = maxSpeed;
    }
    
    // act() method - called by Greenfoot at every "act" or step of execution
    public void act()
    {
        checkHitPedestrian(); 
        drive();
        shootBullet();
        checkEdges();
    }

    // Remove this Tank object from the World
    public void removeSelf(){
        getWorld().removeObject(this);
    }
    
    /**
     * Check if the Tank has hit a Pedestrian
     */
    public void checkHitPedestrian ()
    {
        ArrayList <Pedestrian> arr = (ArrayList <Pedestrian>) getIntersectingObjects(Pedestrian.class);
        for (Pedestrian p : arr){
            p.knockMeOver();
        }
    }
    /**
     * Shoot a bullet from the Tank at randomized intervals
     */
    private void shootBullet(){
        if (Greenfoot.getRandomNumber(150) == 1){
            Bullet b = new Bullet(0);
            GreenfootSound sound = new GreenfootSound("gunshot.wav");
            sound.play();
            getWorld().addObject(b, getX(), getY());
        }
    }
}
