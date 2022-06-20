import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Soldier subclass of Pedestrian
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Soldier extends Pedestrian
{
    private int numBullets;
    // Constructor
    public Soldier()
    {
        // figure out own width (related to checking if at world's edge)
        GreenfootImage g = this.getImage();
        myWidth = g.getWidth();
        startSpeed = 2;
        // Set current healthy status to true (alive and moving)
        healthy = true;
        // Set initial speed
        speed = startSpeed;
        numBullets = Greenfoot.getRandomNumber(5) + 1;
    }

    // act() method - called by Greenfoot at every
    // "act" or step of execution
    public void act() 
    {
        // move upwards
        setLocation (getX(), getY() - speed);
        shootBullet();
        // check if I'm at the edge of the world,
        // and if so, remove myself
        if (atWorldEdge())
        {
            getWorld().removeObject(this);
        }
    }
    /**
     * Method that shoots a bullet from the soldier
     * Only shoots if bullets are left and soldier is alive
     */
    private void shootBullet(){
        if (getWorld() != null){
            if (Greenfoot.getRandomNumber(100) == 1 && numBullets != 0 && isHealthy()){
            Bullet b = new Bullet(270);
            GreenfootSound sound = new GreenfootSound("gunshot.wav");
            sound.play();
            getWorld().addObject(b, getX(), getY());
            }
        }
    }
}
