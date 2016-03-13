/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.generator;

import java.util.*;
import maze.logic.*;
import static maze.logic.Direction.*;

/**
 *
 * @author Tiago Almeida
 */
public class MazeBuilder implements IMazeBuilder {
    
    private char[][] maze;
    private boolean[][] visitedCells;
    Stack<Point> path = new Stack<>();
    Random random = new Random();
    
    public MazeBuilder() {
        
    }
    
    @Override
    public char[][] buildMaze(int size) throws IllegalArgumentException {
        
        if(size % 2 == 0) 
            throw new IllegalArgumentException();
        
        int sizeVisited = (size - 1) / 2;
        maze = new char[size][size];
        visitedCells = new boolean[sizeVisited][sizeVisited];
        
        
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                maze[i][j] = 'X';
            }
        }
        
        for(int i = 0; i < sizeVisited; i++) {
            for(int j = 0; j < sizeVisited; j++)
                visitedCells[i][j] = false;
        }
        
        for(int i = 1; i < size; i+=2) {
            for(int j = 1; j < size; j+=2)
                maze[i][j] = ' ';
        }
        
        int randomExit = random.nextInt(4);
        int randomGuideCell = random.nextInt((size - 1) / 2);
        
        switch(randomExit) {
            case 0:
                //Exit on the top line
                //Pseudo-Random n para achar a celula de partida
                path.push(new Point(randomGuideCell * 2 + 1, 1));
                maze[0][randomGuideCell * 2 + 1] = 'S';
                visitedCells[0][randomGuideCell] = true;
                break;
            case 1:
                //Exit on bottom line
                path.push(new Point(randomGuideCell * 2 + 1, size - 2));
                maze[size - 1][randomGuideCell * 2 + 1] = 'S';
                visitedCells[(size - 3) / 2][randomGuideCell] = true;
                break;
            case 2:
                //Exit on left side
                path.push(new Point(1, randomGuideCell * 2 + 1));
                maze[randomGuideCell * 2 + 1][0] = 'S';
                visitedCells[randomGuideCell][0] = true;
                break;
            case 3:
                //Exit on right side
                path.push(new Point(size - 2, randomGuideCell * 2 + 1));
                maze[randomGuideCell * 2 + 1][size - 1] = 'S';
                visitedCells[randomGuideCell][(size - 3) / 2] = true;
                break;
            default:
                break;
        }     
        
        Direction directionGuideCell;
        
        do {
            
            for (char[] line : maze) {
                for(char element : line)
                    System.out.print(element + " ");
                System.out.println();
            }
            System.out.println();
            
            for(int i = 0; i < visitedCells.length; i++){
                for(int j = 0; j < visitedCells[i].length; j++) {
                    if(visitedCells[i][j] == true)
                        System.out.print("+");
                    else 
                        System.out.print(".");
                    System.out.print(" ");
                }
                System.out.println();
            }
            
            do {
                directionGuideCell = randomDirection(size);  
                if(directionGuideCell == CANTMOVE) path.pop();
                
            }while(directionGuideCell == CANTMOVE && !path.empty());
            
            if(path.empty()) continue;
            
            switch(directionGuideCell) {
                case UP:
                    maze[path.lastElement().getY() - 1][path.lastElement().getX()] = ' ';
                    path.push(new Point(path.lastElement().getX(), path.lastElement().getY() - 2));
                    visitedCells[(path.lastElement().getY() - 1) / 2][(path.lastElement().getX() - 1) / 2] = true;
                    break;
                case DOWN:
                    maze[path.lastElement().getY() + 1][path.lastElement().getX()] = ' ';
                    path.push(new Point(path.lastElement().getX(), path.lastElement().getY() + 2));
                    visitedCells[(path.lastElement().getY() - 1) / 2][(path.lastElement().getX() - 1) / 2] = true;
                    break;
                case RIGHT:
                    maze[path.lastElement().getY()][path.lastElement().getX() + 1] = ' ';
                    path.push(new Point(path.lastElement().getX() + 2,path.lastElement().getY()));
                    visitedCells[(path.lastElement().getY() - 1) / 2][(path.lastElement().getX() - 1) / 2] = true;
                    break;
                case LEFT:
                    maze[path.lastElement().getY()][path.lastElement().getX() - 1] = ' ';
                    path.push(new Point(path.lastElement().getX() - 2,path.lastElement().getY()));
                    visitedCells[(path.lastElement().getY() - 1) / 2][(path.lastElement().getX() - 1) / 2] = true;
                    break;
                default:
                    break;  
            }        
        }while(!path.empty());
        
        int randomActorPositionX, randomActorPositionY;
        
        //Hero random Position
        do {
            randomActorPositionX = random.nextInt(size);
            randomActorPositionY = random.nextInt(size);
            
            if(maze[randomActorPositionY][randomActorPositionX] == ' ') {
                maze[randomActorPositionY][randomActorPositionX] = 'H';
                break;
            }
            
        }while(true);
        
        //Dragon random Position
        do {
            randomActorPositionX = random.nextInt(size);
            randomActorPositionY = random.nextInt(size);
            
            if(maze[randomActorPositionY][randomActorPositionX] == ' ' &&
                    maze[randomActorPositionY - 1][randomActorPositionX] != 'H' &&
                    maze[randomActorPositionY + 1][randomActorPositionX] != 'H' &&
                    maze[randomActorPositionY][randomActorPositionX - 1] != 'H' &&
                    maze[randomActorPositionY][randomActorPositionX + 1] != 'H') {
                maze[randomActorPositionY][randomActorPositionX] = 'D';
                break;
            }
            
        }while(true);
        
        //Hero random Position
        do {
            randomActorPositionX = random.nextInt(size);
            randomActorPositionY = random.nextInt(size);
            
            if(maze[randomActorPositionY][randomActorPositionX] == ' ') {
                maze[randomActorPositionY][randomActorPositionX] = 'E';
                break;
            }
            
        }while(true);
        
        
        
        
        return maze;
    }
    
    private Direction randomDirection(int size) {
        Point guideCell = new Point((path.lastElement().getX() - 1) / 2,(path.lastElement().getY() - 1) / 2);
        Stack<Direction> possibleDirections = new Stack<>();
        
        if((guideCell.getY() - 1 >= 0) && !visitedCells[guideCell.getY() - 1][guideCell.getX()])
            possibleDirections.push(UP);
        
        if((guideCell.getY() + 1 < visitedCells.length) && !visitedCells[guideCell.getY() + 1][guideCell.getX()])
            possibleDirections.push(DOWN);
        
        if((guideCell.getX() - 1 >= 0) && !visitedCells[guideCell.getY()][guideCell.getX() - 1])
            possibleDirections.push(LEFT);
        
        if((guideCell.getX() + 1 < visitedCells.length) && !visitedCells[guideCell.getY()][guideCell.getX() + 1])
            possibleDirections.push(RIGHT);
        
        if(possibleDirections.empty()) return CANTMOVE;
        if(possibleDirections.size() == 1) return possibleDirections.lastElement();
        
        int randomDirection = random.nextInt(possibleDirections.size());
        
        for(int i = 0; i < randomDirection; i++)
            possibleDirections.pop();
        
        return possibleDirections.lastElement();
    }
    
}
