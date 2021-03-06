/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import java.util.Random;


/**
 * Handles the execution of specific tic tac toe matches
 * @author Alex
 */
public class Game {

    // member variables
    private iPlayer _crossPlayer;
    private iPlayer _noughtPlayer;
    private int _crossPlayerScore = 0;
    private int _noughtPlayerScore = 0;
    private int _totalLegalMoves;
    private int _totalFailedMoves;
    private int _totalCrossWins;
    private int _totalNoughtWins;
    private int _gameState;
    private iPlayer _currentPlayer;// = _crossPlayer;
    private Random _random = new Random();

    
    public int getTotalLegalMoves()
    {
        return _totalLegalMoves;
    }
    
    public int getTotalFailedMoves()
    {
        return _totalFailedMoves;
    }
        
    public int getTotalCrossWins()
    {
        return _totalCrossWins;
    }
            
    public int getTotalNoughtWins()
    {
        return _totalNoughtWins;
    }
    
    /**
     * Toggles the current [player between _noughtPlayer and _crossPlayer
     */
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

    /**
     * Tests if a particular int represents a legal move. For example, is the
     * int within the correct range (0-8) and is the square free?
     * @param square the square being tested
     * @return true or false depending on whether square is free
     */
    private boolean isValidMove(int square) {
        // Checking the square is between 0 and 8 
        if (square > 8 || square < 0) 
        {
            //System.out.println("Square " + square + " does not exist! Must be 0-8.");
            _totalFailedMoves++;
            return false;
        }

        // Checking the square is empty
        if ((_gameState & TTT.getMask(square)) != TTT.getEmpty(square)) 
        {
            //System.out.println("Square " + square + " is not empty!");
            _totalFailedMoves++;
            return false;
        }

        // If neither of the above are true the move is legal
        return true;
    }

    /**
     * @return true if _crossPlayer has won
     */
    private boolean crossWin()
        {
            for (int i = 0; i < 8; i++)
            {
                if ((TTT.getCrossVictory(i) & _gameState) == 0)
                {
                    System.out.println("Crosses have won!");
                    Utils.printBoard(_gameState);
                    System.out.println(Utils.toString(_gameState));
                    _totalCrossWins++;
                    return true;
                }
            }
            return false;
        }
    
    /**
     * @return true if _noughtPlayer has won
     */
    private boolean noughtWin()
        {
            for (int i = 0; i < 8; i++)
            {
                if ((TTT.getNoughtVictory(i) & _gameState) == 0)
                {
                    System.out.println("Noughts have won!");
                    Utils.printBoard(_gameState);
                    System.out.println(Utils.toString(_gameState));
                    _totalNoughtWins++;
                    
                    return true;
                }
            }
            return false;
        }

    /**
     * Asks a particular bot where it would like to go. If the bot responds
     * with a legal move this is recorded
     * @param bot A geneBot that will be asked where to move
     */
    private void playMove(iPlayer bot) 
    {
        int square = _currentPlayer.takeTurn(_gameState);
        //System.out.println(" I am chosing square " + square);
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
    
    /**
     * The same as playMove(), except this method is used specifically
     * for the last turn. (No score is given to the bot if they make a legal
     * move as there should only be one move available. This reduces bias 
     * toward the first bot to play winning as each bot will get 4 opportunities
     * to make legal moves.)
     * @param bot 
     */
    private void playLastMove(iPlayer bot) 
    {
        int square = _currentPlayer.takeTurn(_gameState);
        //System.out.println(" I am chosing square " + square);
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

    /**
     * Plays a full game of tic tac toe
     * @param crossPlayer An iPlayer (probably a GeneBot) to play as crosses
     * @param noughtPlayer An iPlayer (probably a GeneBot) to play as noughts
     * @return the winner (or a random player if a draw)
     */
    public iPlayer playGame(iPlayer crossPlayer, iPlayer noughtPlayer) 
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
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));
        
        // second turn
        playMove(noughtPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));        
        
        // third turn
        playMove(crossPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));
        
        // fourth turn
        playMove(noughtPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));        
        
        // fifth turn
        playMove(crossPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));
        if (crossWin())
        {
            return crossPlayer;
        }
        
        // sixth turn
        playMove(noughtPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));        
        if (noughtWin())
        {
            return noughtPlayer;
        }
        
        // seventh turn
        playMove(crossPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));
        if (crossWin())
        {
            return crossPlayer;
        }
        
        // eigth turn
        playMove(noughtPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));        
        if (noughtWin())
        {
            return noughtPlayer;
        }
        
        // ninth turn
        playLastMove(crossPlayer);
        // Utils.printBoard(_gameState);
        // System.out.println(Utils.toString(_gameState));
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
    
    /**
     * Just used for testing
     * @param args 
     */
    public static void main(String[] args)
    {
        Game g = new Game();
  
        iPlayer crossPlayer = new GeneBot();
        iPlayer noughtPlayer = new GeneBot();
        
        iPlayer winner = g.playGame(crossPlayer, noughtPlayer);
    }

}
