/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

import static maze.logic.Direction.*;

/**
 *
 * @author Tiago
 */
public class Dragon extends Actor{
    
    private boolean isDead;
    private boolean isSleeping;
    
    public Dragon(int PosX, int PosY) {
        super(PosX, PosY);
        isDead = false;
        isSleeping = false;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void kill() {
        this.isDead = true;
    }
    
    public boolean isSleeping() {
        return isSleeping;
    }
    
    public void setNotSleeping() {
        isSleeping = false;
    }
    
    public void setSleeping() {
        isSleeping = true;
    }
    
    public void updatePosition(Direction direction) {
        
        switch(direction) {
            case LEFT:
                position.setX(position.getX() - 1);
                break;
            case RIGHT:
                position.setX(position.getX() + 1);
                break;
            case UP:
                position.setY(position.getY() - 1);
                break;
            case DOWN:
                position.setY(position.getY() + 1);
                break;
            default:
                break;
        }
            
    }
}
