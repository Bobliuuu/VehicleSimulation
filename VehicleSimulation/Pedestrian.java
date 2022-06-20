import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Pedestrian Abstract Class
 * 
 * Moves mindlessly up the road, reacting to cars
 * that hit it, ambulances that heal it, and potentially
 * buses that pick it up.
 * 
 * @author Jordan Cohen, Jerry Zhu
 * @version 2
 * 
 */

public abstract class Pedestrian extends SmoothMover
{
    // Instance variables
    protected int myWidth;
    protected boolean healthy;
    protected double startSpeed;
    protected double speed;
    
    /**
     * Method that removes the pedestrian from the World
     */
    public void removeSelf(){
        getWorld().removeObject(this);
    }
    
    /**
     * Method that causes this Pedestrian to stop moving and appear to fall over
     */
    public void knockMeOver ()
    {
        speed = 0;
        setRotation (90);
        healthy = false;
    }
    /**
     * Method that causes this pedestrian to "heal" - regain upright position and start moving again
     */
    public void healMe ()
    {
        speed = startSpeed;
        setRotation (0);
        healthy = true;
    }
    
    /**
     * Method that causes the pedestrian to get picked up by the bus - not yet implemented
     */
    public void pickMeUp()
    {
        //getImage().setTransparency(0);
        getWorld().removeObject(this);
    }
    
    /**
     * Handy method that checks if this object is at the edge
     * of the World
     * 
     * @return boolean true if at or past the edge of the World, otherwise false
     */
    public boolean atWorldEdge()
    {
        if (getX() < -(myWidth / 2) || getX() > getWorld().getWidth() + (myWidth / 2))
            return true;
        else if (getY() < -(myWidth / 2) || getY () > getWorld().getHeight() + (myWidth / 2))
            return true;
        else
            return false;
    }
    
    /**
     * Method that checks if the pedestrian is healthy
     */
    public boolean isHealthy(){
        return healthy;
    }
    
    /**
     * Replaces the pedestrian with an explosion
     * Used when a pedestrian is in range of an exploding Bomb
     */
    public void explode(){
        Explosion e = new Explosion();
        getWorld().addObject(e, getX(), getY());
        healthy = false;
        removeSelf();
    }
}
