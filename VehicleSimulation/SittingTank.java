import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A tank that does not move and shoots bullets at different angles
 * 
 * @author Jerry Zhu
 * @version 1
 */
public class SittingTank extends Actor
{
    // Instance variables
    private boolean leftTank;
    // Constructor
    public SittingTank(boolean leftTank){
        if (leftTank){
            getImage().mirrorHorizontally();
        }
        this.leftTank = leftTank;
    }
    /**
     * Turns the tank to shoot sideways
     */
    public void moveSideways(boolean left){
        if (left == leftTank){
            if (left){
                setRotation(-30);
            }
            else {
                setRotation(30);
            }
        }
    }
    /**
     * Turns the tank to shoot forwards
     */
    public void moveStraight(){
        setRotation(0);
    }
}
