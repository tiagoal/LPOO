package maze.cli;

import java.util.Scanner;
import maze.generator.MazeBuilder;
import maze.logic.GameMode;
import static maze.logic.GameMode.*;
import maze.logic.GameStatus;

public class Game {
        
    
    public static void main(String[] args) {
        
        GameStatus game = new GameStatus();
        String input;
        boolean correctInput = false;
        GameMode gameMode = STILL;
        Scanner reader = new Scanner(System.in);
        MazeBuilder mazeBuilder = new MazeBuilder(); //why?
        char[][] maze;

        System.out.print("Introduza o tamanho do tabuleiro(Numero impar): ");
        do {
            try {
                input = reader.nextLine();
                int size = Integer.parseInt(input);
                maze = mazeBuilder.buildMaze(size);
                
                game.initializeBoard(maze);
                correctInput = true;
                
            } catch(NumberFormatException NotANumber) {
                System.out.print("Por favor, introduza um numero: ");
            } catch(IllegalArgumentException InvalidArgument) {
                System.out.print("O numero tem de ser impar. Volte a introduzir um numero: ");
            }
        }while(!correctInput);
        
        correctInput = false;
        System.out.printf("Escolha o mode de jogo:\n 1 - Dragao parado;\n 2 - Dragao a move-se\n 3 - Dragao move-se"
                    + " e pode adormecer: ");
        do {            
            try {
                input = reader.nextLine();
                int mode = Integer.parseInt(input);
                
                if(mode < 1 || mode > 3) {
                    System.out.print("Por favor, introduza um numero valido: ");
                    continue;
                }
                else 
                    correctInput = true;
                
                switch (mode) {
                    case 1:
                        gameMode = STILL;
                        break;
                    case 2:
                        gameMode = MOVING;
                        break;
                    case 3:
                        gameMode = MOVINGANDSLEEPING;
                        break;
                    default:
                        break;
                }     
            } catch(NumberFormatException NotANumber) {
                System.out.print("Por favor, introduza um numero: ");
            }
        }while(!correctInput);
        
        game.getBoard().setGameMode(gameMode);
        
        System.out.printf("\n\n");
        System.out.print("Intruçoes de jogo:\n");
        System.out.print("Para cima: w " + "\n" + "Para baixo: s" + "\n"
            + "Para a esquerda: a" + "\n" + "Para a direita: d" + "\n");
        System.out.println();
        
        while(!(game.gameOver())){
            
            System.out.println(game.getBoard());

            do {
                System.out.print("Introduza a direcao: ");
                input = reader.nextLine();
                System.out.println();
            } while (!(input.equals("d")) && !(input.equals("w")) && !(input.equals("a")) && !(input.equals("s"))
                && !(input.equals("D")) && !(input.equals("W")) && !(input.equals("A")) && !(input.equals("S")));
        
            game.updateGameStatus(input);
        }
        
        System.out.println(game.getBoard());

        
        if(game.getBoard().getDragon().isDead())
            System.out.println("Parabens!!! Ganhou o jogo!");
        else
            System.out.println("Que ganda nabo. Perdeste!");
    }
 }
