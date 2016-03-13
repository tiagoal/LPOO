/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

/**
 *
 * @author Tiago Almeida
 */
public class Actor {
    protected Point position;
    
    public Actor(int posX, int posY) {
        this.position = new Point(posX, posY);
    }
    
    public Point getPoint() {
        return position;
    }
}
