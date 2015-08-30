/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.alex;

import java.util.Random;
import ttt.bots.iPlayer;

/**
 *
 * @author Alex
 */
public class RandomPlayer implements iPlayer {
    final private Random _random = new Random();
    
    public int takeTurn (int board){
    return _random.nextInt(10);
    }
}
