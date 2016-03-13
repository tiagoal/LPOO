/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

/**
 *
 * @author Tiago
 */
public class Hero extends Actor{
    
    private boolean isDead;
    private boolean hasSword;

    public Hero(int PosX, int PosY) {
        super(PosX, PosY);
        isDead = false;
        hasSword = false;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public boolean hasSword() {
        return hasSword;
    }
    
    public void setHasSword() {
        hasSword = true;
    }
    
    public void updatePosition(String direction) {
        
        switch(direction) {
            case "w":
                position.setY(position.getY() - 1);
                break;
            case "s":
                position.setY(position.getY() + 1);
                break;
            case "d":
                position.setX(position.getX() + 1);
                break;
            case "a":
                position.setX(position.getX() - 1);
                break;
            default:
                break;
        }
    }
    
    public void kill() {
        this.isDead = true;
    }
}
