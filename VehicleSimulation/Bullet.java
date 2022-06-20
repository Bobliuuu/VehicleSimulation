import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Bullet subclass of Projectile
 * Travels fast and can hit Pedestrians
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Bullet extends Projectile
{
    // Constructor
    public Bullet (int angle){
        setRotation (angle);
    }   
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        move (5);
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
        else {
            checkHitPedestrian();  
        }
    }
    /**
     * Check if the bullet is at the edges, and remove if it is. 
     */
    private void checkEdges(){
        if (isAtEdge()){
            getWorld().removeObject(this);
        }
    }
    /**
     * Check if the bullet has hit a pedestrian
     */
    private void checkHitPedestrian(){
        Pedestrian p = (Pedestrian) getOneIntersectingObject(Pedestrian.class);
        if (p != null && p.isHealthy() && !(p instanceof Soldier)){
            ((CrossingWorld)getWorld()).playSplat();
            getWorld().removeObject(this);
            p.knockMeOver();
        }
    }
}
