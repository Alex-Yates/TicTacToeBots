/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;


/**
 *
 * @author Alex
 */
public class Game {

    private iPlayer _crossPlayer = new GeneBot();
    private iPlayer _noughtPlayer = new GeneBot();
    private int _gameState = TTT.NEWBOARD;
    private iPlayer _currentPlayer = _crossPlayer;

    private void nextPlayer() {
        if (_currentPlayer == _crossPlayer) 
        {
            _currentPlayer = _noughtPlayer;
        } 
        else 
        {
            _currentPlayer = _crossPlayer;
        }
    }

    private boolean isValidMove(int square) {
        // Checking the square is between 0 and 8 
        if (square > 8 || square < 0) 
        {
            System.out.println("Square " + square + " does notr exist! Must be 0-8.");
            return false;
        }

        // Checking the square is empty
        if ((_gameState & TTT.getMask(square)) != TTT.getEmpty(square)) 
        {
            System.out.println("Square " + square + " is not empty!");
            return false;
        }

        // If neither of the above are true the move is legal
        return true;
    }

    private boolean gameEnd() {
        for (int i = 0; i < 8; i++)
        {
            if ((TTT.getCrossVictory(i) & _gameState) == 0)
            {
                System.out.println("Crosses have won!");
                return true;
            }
        }
        for (int i = 0; i < 8; i++)
        {
            if ((TTT.getNoughtVictory(i) & _gameState) == 0)
            {
                System.out.println("Noughts have won!");
                return true;
            }
        }
        return false;
    }

    private void playMove(iPlayer bot) 
    {
        int square = _currentPlayer.takeTurn(_gameState);
        System.out.println(" I am chosing square " + square);
        if (isValidMove(square)) 
        {
            _gameState &= ~TTT.getMask(square);
            
            if (_currentPlayer == _crossPlayer) 
            {
                _gameState = (_gameState + TTT.getCross(square));
            }
            else
            {
                _gameState = (_gameState + TTT.getNought(square));
            }     
        }
    }

    private void playGame() 
    {
        for (int i = 0; i < 9; i++) 
        {
            playMove(_currentPlayer);
            
            Utils.printBoard(_gameState);
            System.out.println(Utils.toString(_gameState));
            
            if (gameEnd()) 
            {
                break;
            }
            nextPlayer();
        }

    }
    
    public static void main(String[] args)
    {
        Game g = new Game();
  
        g.playGame();
    }

}