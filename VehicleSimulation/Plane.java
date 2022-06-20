import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Plane class that spawns a plane which moves from bottom left to top right
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Plane extends Actor
{
    private GreenfootSound airHorn = new GreenfootSound("airraid.wav");
    /**
     * Act - do whatever the Plane wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX() + 5, getY() - 5);
        if (isAtEdge()){
            ((CrossingWorld)getWorld()).setFlying(false);
            airStrike();
            getWorld().removeObject(this); // Must be last line
        }
    }
    
    /**
     * Method that starts the air strike
     * Three random bombs at different locations
     */
    public void airStrike(){
        airHorn.play();
        int randX;
        int randY;
        for (int i = 0; i < 3; i++){
            randY = Greenfoot.getRandomNumber(300) + 50;
            randX = Greenfoot.getRandomNumber(500) + 50;
            getWorld().addObject(new Bomb(), randX, randY);
        }
    }
    
}
