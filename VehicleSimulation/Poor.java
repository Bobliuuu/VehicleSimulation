import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Poor class that is a subclass of pedestrian that moves slowly
 * Can pick up Bombs
 * 
 * @author Jerry Zhu
 * @version 1
 */

public class Poor extends Pedestrian
{
    private GreenfootSound shovel = new GreenfootSound("shovel.wav");
    // Constructor
    public Poor()
    {
        // figure out own width (related to checking if at world's edge)
        GreenfootImage g = this.getImage();
        myWidth = g.getWidth();
        startSpeed = 1.5;
        // Set current healthy status to true (alive and moving)
        healthy = true;
        // Set initial speed
        speed = startSpeed;
    }

    // act() method - called by Greenfoot at every
    // "act" or step of execution
    public void act() 
    {
        // move upwards
        setLocation (getX(), getY() - speed);
        if (isHealthy()){
            checkBomb();
        }
        // check if I'm at the edge of the world,
        // and if so, remove myself
        if (atWorldEdge())
        {
            getWorld().removeObject(this);
        }
    }    
    
    private void checkBomb(){
        Bomb b = (Bomb) getOneIntersectingObject(Bomb.class);
        if (b != null){
            shovel.play();
            getWorld().removeObject(b);
        }
    }
}
