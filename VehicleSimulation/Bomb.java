import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Bomb that can blow up on contact with a Vehicle
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class Bomb extends Actor
{
    /**
     * Act - do whatever the Bomb wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkHitVehicle();
    }
    /**
     * Method that checks if a bomb has hit a vehicle or not
     * If it has, remove all objects in the range of the Bomb
     */
    private void checkHitVehicle(){
        Vehicle v = (Vehicle) getOneIntersectingObject(Vehicle.class);
        if (v != null){
            ArrayList <Vehicle> arrV = (ArrayList <Vehicle>) getObjectsInRange(60, Vehicle.class);
            ArrayList <Pedestrian> arrP = (ArrayList <Pedestrian>) getObjectsInRange(60, Pedestrian.class);
            for (Vehicle p : arrV){
                p.explode();
            }
            for (Pedestrian p : arrP){
                ((CrossingWorld)getWorld()).playSplat();
                p.explode();
            }
            getWorld().removeObject(this);
        }
    }
}
