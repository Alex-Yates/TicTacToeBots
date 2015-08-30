/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import ttt.alex.RandomPlayer;

/**
 *
 * @author Alex
 */
public class Game {
    private iPlayer _crossPlayer = new RandomPlayer();
    private iPlayer _noughtPlayer = new RandomPlayer();
    private int _gameState = TTT.NEWBOARD;
    private iPlayer _currentPlayer = _crossPlayer;
    
    private void nextPlayer()
    {
        if (_currentPlayer == _crossPlayer)
        {
            _currentPlayer = _noughtPlayer;
        }
        else
        {
            _currentPlayer = _crossPlayer;
        }
    }
    
    private boolean isValidMove(int square)
    {
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
    
    private boolean gameEnd()
    {
        // todo: make this logic work!
        return true;
    }
    
    private void playMove(iPlayer bot)
    {
        int square = _currentPlayer.takeTurn(_gameState);
        // if square.isValid()
        
    }
    
    private void playGame()
    {
        for (int i = 0; i <9; i++)
        {
            playMove(_currentPlayer);
            if (gameEnd())
            {
                break;
            }
            nextPlayer();
        }

    }
    
}
