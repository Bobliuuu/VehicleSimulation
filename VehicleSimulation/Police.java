import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Car subclass of Vehicle
 * Vehicle that arrests Pedestrians and slows down vehicles around it
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Police extends Vehicle
{
    // Instance variables
    private int offset;
    private Pedestrian p;
    private boolean isPlaying;
    private GreenfootSound policeSiren = new GreenfootSound("policesiren.wav");
    // Constructor
    public Police()
    {
        maxSpeed = 4;
        speed = maxSpeed;
        canChangeLanes = true;
        targetLane = -1;
        policeSiren.playLoop();
    }
    
    // act() method - called by Greenfoot at every "act" or step of execution
    public void act()
    {
        checkHitPedestrian(); 
        checkVehicles();
        drive();
        checkEdges();
    }
    
    /**
     * Check if the police car has reached the edge of the World
     */
    public void checkEdges(){
        if (getX() < -getImage().getWidth() || getX() > getWorld().getBackground().getWidth() + getImage().getWidth()){
            policeSiren.stop();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Slow down all vehicles 
     */
    public void checkVehicles()
    {
        ArrayList <Vehicle> vehicles = (ArrayList <Vehicle>) getObjectsInRange(200, Vehicle.class);
        for (Vehicle v : vehicles){
            if (v != this){
                v.slowDown();
            }
        }
    }
    
    // Play the police siren
    public void playSound(){
        policeSiren.playLoop();
    }
    
    // Stop playing the police siren
    public void stopSound(){
        policeSiren.stop();
    }
    
    // Debug method to check if siren is playing
    public boolean isPlayingSound(){
        return policeSiren.isPlaying();
    }
    
    // Remove the Police object from the World
    public void removeSelf(){
        stopSound();
        getWorld().removeObject(this);
    }
    
    // 
    /**
     * Check to see if the Police car has hit a Pedestrian
     * Arrest the Pedestrian if it is a Villain
     */
    public void checkHitPedestrian ()
    {
        ArrayList <Pedestrian> arr = (ArrayList <Pedestrian>) getIntersectingObjects(Pedestrian.class);
        for (Pedestrian p : arr){
            if (p instanceof Villain && p.isHealthy()){
                p.removeSelf();
            }
            else {
                p.knockMeOver();
            }
        }
    }
}
