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
public class Sword extends Actor{
    
    private boolean isOnTheFloor;
    
    public Sword(int PosX, int PosY) {
        super(PosX, PosY);
        isOnTheFloor = true;
    }

}
