import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * SunMoon class that mimicks the passing of days
 * Changes the background based on the time of day (getY())
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class SunMoon extends Actor
{
    // Instance variables
    private GreenfootImage sun = new GreenfootImage("sun.png");
    private GreenfootImage moon = new GreenfootImage("moon.png");
    private GreenfootImage background = new GreenfootImage("background.png");
    private GreenfootImage backgroundDark = new GreenfootImage("backgrounddark.png");
    private int speed;
    // Constructor
    public SunMoon(){
        setImage(sun);
        speed = 2;
    }
    /**
     * Act - do whatever the SunMoon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        moveSunMoon();
        checkEdges();
    }
    // Move the sun/moon
    public void moveSunMoon(){
        move(speed);
    }
    /**
     * Method to check if the sun/moon has reached the edge of the World
     * Also checks for switches between images (day to night and night to day)
     */
    public void checkEdges(){ 
        if (getX() == 300){
            setImage(moon);
            getWorld().setBackground(backgroundDark);
            ((CrossingWorld)getWorld()).setImageTo(backgroundDark);
        }
        if (getX() < -getImage().getWidth() || getX() > getWorld().getBackground().getWidth() + getImage().getWidth()){
            setLocation(-40, 10);
            setImage(sun);
            getWorld().setBackground(background);
            ((CrossingWorld)getWorld()).setImageTo(background);
        }
    }
}
