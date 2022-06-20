import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Villain subclass of 
 * Puts down a bomb and can get arrested by police
 * 
 * @author Jerry Zhu
 * @version 1
 */

public class Villain extends Pedestrian
{
    // Instance variables
    private boolean hasBomb;
    private int putY;
    // Constructor
    public Villain()
    {
        // figure out own width (related to checking if at world's edge)
        GreenfootImage g = this.getImage();
        myWidth = g.getWidth();
        startSpeed = 2;
        // Set current healthy status to true (alive and moving)
        healthy = true;
        // Set initial speed
        speed = startSpeed;
        hasBomb = true;
        putY = CrossingWorld.getYPosition(Greenfoot.getRandomNumber(6));
    }

    // act() method - called by Greenfoot at every
    // "act" or step of execution
    public void act() 
    {
        // move upwards
        setLocation (getX(), getY() - speed);
        putBomb();
        // check if I'm at the edge of the world,
        // and if so, remove myself
        if (atWorldEdge())
        {
            getWorld().removeObject(this);
        }
    }    
    /**
     * Method that puts a bomb when the Villain passes a certain Y value
     */
    private void putBomb(){
        if (hasBomb){
            if (getY() <= putY){
                Bomb b = new Bomb();
                getWorld().addObject(b, getX(), getY());
                hasBomb = false;
            }
        }
    }
    
    public boolean hasBomb(){
        return hasBomb;
    }
}
