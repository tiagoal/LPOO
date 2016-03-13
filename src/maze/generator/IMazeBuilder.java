/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.generator;

/**
 *
 * @author Tiago Almeida
 */
public interface IMazeBuilder {
    public char[][] buildMaze(int size) throws IllegalArgumentException;
}
