/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

/**
 * All classes implementing the iPlayer inteface should be able to compete
 * at tic tac toe
 * @author Alex
 */
public interface iPlayer {
    public int takeTurn (int board);
}
