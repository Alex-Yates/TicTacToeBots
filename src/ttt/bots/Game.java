/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import java.util.Random;


/**
 *
 * @author Alex
 */
public class Game {

    private iPlayer _crossPlayer;
    private iPlayer _noughtPlayer;
    private int _crossPlayerScore = 0;
    private int _noughtPlayerScore = 0;
    private int _totalLegalMoves;
    private int _gameState;
    private iPlayer _currentPlayer;// = _crossPlayer;
    private Random _random = new Random();

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
            System.out.println("Square " + square + " does not exist! Must be 0-8.");
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


    
    private boolean crossWin()
        {
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
    
    private boolean noughtWin()
        {
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
    
//    private boolean gameEnd() {
//        for (int i = 0; i < 8; i++)
//        {
//            if ((TTT.getCrossVictory(i) & _gameState) == 0)
//            {
//                System.out.println("Crosses have won!");
//                return true;
//            }
//        }
//        for (int i = 0; i < 8; i++)
//        {
//            if ((TTT.getNoughtVictory(i) & _gameState) == 0)
//            {
//                System.out.println("Noughts have won!");
//                return true;
//            }
//        }
//        return false;
//    }

    private void playMove(iPlayer bot) 
    {
        int square = _currentPlayer.takeTurn(_gameState);
        System.out.println(" I am chosing square " + square);
        if (isValidMove(square)) 
        {
            _gameState &= ~TTT.getMask(square);
            
            if (bot == _crossPlayer) 
            {
                _gameState = (_gameState + TTT.getCross(square));
                _crossPlayerScore++;
            }
            else
            {
                _gameState = (_gameState + TTT.getNought(square));
                _noughtPlayerScore++;
            }     
        }
    }
    
    private void playLastMove(iPlayer bot) 
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


    private iPlayer playGame(iPlayer crossPlayer, iPlayer noughtPlayer) 
    {
        _gameState = TTT.NEWBOARD;
        _crossPlayer = crossPlayer;
        _noughtPlayer = noughtPlayer;
        _currentPlayer = crossPlayer;
        _totalLegalMoves = _totalLegalMoves + _crossPlayerScore + _noughtPlayerScore;
        _crossPlayerScore = 0;
        _noughtPlayerScore = 0;
        
        // first turn
        playMove(crossPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));
        
        // second turn
        playMove(noughtPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));        
        
        // third turn
        playMove(crossPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));
        
        // fourth turn
        playMove(noughtPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));        
        
        // fifth turn
        playMove(crossPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));
        if (crossWin())
        {
            return crossPlayer;
        }
        
        // sixth turn
        playMove(noughtPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));        
        if (noughtWin())
        {
            return noughtPlayer;
        }
        
        // seventh turn
        playMove(crossPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));
        if (crossWin())
        {
            return crossPlayer;
        }
        
        // eigth turn
        playMove(noughtPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));        
        if (noughtWin())
        {
            return noughtPlayer;
        }
        
        // ninth turn
        playLastMove(crossPlayer);
        Utils.printBoard(_gameState);
        System.out.println(Utils.toString(_gameState));
        if (crossWin())
        {
            return crossPlayer;
        }
        
        // if no winner return player with most legal moves
        if (_crossPlayerScore > _noughtPlayerScore)
        {
            return crossPlayer;
        }
        
        if (_crossPlayerScore < _noughtPlayerScore)
        {
            return noughtPlayer;
        }
        
        // if no winner return random player
        return _random.nextBoolean() ? crossPlayer : noughtPlayer;
    }    
    
    public static void main(String[] args)
    {
        Game g = new Game();
  
        iPlayer crossPlayer = new GeneBot();
        iPlayer noughtPlayer = new GeneBot();
        
        iPlayer winner = g.playGame(crossPlayer, noughtPlayer);
    }

}
