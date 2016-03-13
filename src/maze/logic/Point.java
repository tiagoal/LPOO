/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.logic;

/**
 *
 * @author José Carlos
 */
public class Point {
    private int posX, posY;
    
    public Point() {
        posX = 0;
        posY = 0;
    }
    
    public Point(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public int getX() {
        return posX;
    }
    
    public int getY() {
        return posY;
    }
    
    public void setX(int x) {
        posX = x;
    }
    
    public void setY(int y) {
        posY = y;
    }
    
    @Override
    public boolean equals(Object object) {
        
       if (!(object instanceof Point))
            return false;

        Point rhs = (Point) object;
        
        return posX == rhs.posX && posY == rhs.posY;
    } 
}
