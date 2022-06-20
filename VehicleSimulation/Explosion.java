import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A 25-frame explosion class that is called every time something in the World explodes
 * 
 * @author Jerry Zhu
 * Credit to Andrew Qiao for the images 
 * @version 1
 */
public class Explosion extends Actor
{
    private GreenfootImage[] explosionImages = {
        new GreenfootImage("frame1.png"), 
        new GreenfootImage("frame2.png"), 
        new GreenfootImage("frame3.png"), 
        new GreenfootImage("frame4.png"), 
        new GreenfootImage("frame5.png"), 
        new GreenfootImage("frame6.png"), 
        new GreenfootImage("frame7.png"), 
        new GreenfootImage("frame8.png"), 
        new GreenfootImage("frame9.png"), 
        new GreenfootImage("frame10.png"), 
        new GreenfootImage("frame11.png"), 
        new GreenfootImage("frame12.png"), 
        new GreenfootImage("frame13.png"), 
        new GreenfootImage("frame14.png"), 
        new GreenfootImage("frame15.png"), 
        new GreenfootImage("frame16.png"), 
        new GreenfootImage("frame17.png"), 
        new GreenfootImage("frame18.png"), 
        new GreenfootImage("frame19.png"), 
        new GreenfootImage("frame20.png"), 
        new GreenfootImage("frame21.png"), 
        new GreenfootImage("frame22.png"), 
        new GreenfootImage("frame23.png"), 
        new GreenfootImage("frame24.png"), 
        new GreenfootImage("frame25.png")
    };
    
    private int explosionIndex;
    private int animationCount;
    private GreenfootSound explosion;
    
    /**
     * Constructor
     */
    public Explosion()
    {
        explosionIndex = 1; 
        animationCount = 1; 
        setImage(explosionImages[0]); 
        explosion = new GreenfootSound("explosion.wav");
        //explosion.setVolume(60);
        explosion.play();
    }
    
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
    */
    public void act()
    {
        if (explosionIndex == 25) {
            getWorld().removeObject(this);
        } 
        else if (explosionIndex < 25 && animationCount % 4 == 0) { 
            setImage(explosionImages[explosionIndex]); 
            explosionIndex++; 
        }
        animationCount++; 
    }
}