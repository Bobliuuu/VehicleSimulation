import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A blank image that checks if a Vehicle can switch lanes
 * 
 * @author Jerry Zhu
 * @version 1
 */

public class LaneSpawnCheck extends Actor
{
    // Instance variables
    private GreenfootImage blank;
    // Constructor
    public LaneSpawnCheck (){
        blank = new GreenfootImage (80, 50);
        // DEBUG: See the LaneSpawnCheck in action
        //blank.setColor(Color.BLACK);
        //blank.fill();
        setImage(blank);
    }
    
    // Overloaded constructor with width, height, and offset
    public LaneSpawnCheck(int width, int height, int offset){
        blank = new GreenfootImage (width + offset, height);
        // DEBUG: See the LaneSpawnCheck in action
        //blank.setColor(Color.BLACK);
        //blank.fill();
        setImage(blank); 
    }

    /**
     * Returns if a Vehicle is touching the LaneSpawnCheck
     */
    public boolean vehiclePresent () {
        return isTouching (Vehicle.class);
    }
    
}
