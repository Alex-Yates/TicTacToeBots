/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class Generation {
    
    // member variuables to hold all our bots
    private List<GeneBot> _contestants = new ArrayList<>();
    private List<GeneBot> _winners = new ArrayList<>();
    Game _game = new Game();
    
    // constructors
    public Generation()
    {
        for (int i = 0; i < 2000; i++)
        {
            _contestants.add(new GeneBot());
        }
    }
    
    public Generation(Set<GeneBot> bots)
    {
        _contestants.addAll(bots);
    }
    
    private List<GeneBot> runGeneration()
    {
        for (int i = 0; i < _contestants.size(); i++)
        {
            iPlayer crossPlayer = _contestants.get(i);
            i++;
            iPlayer noughtPlayer = _contestants.get(i);
        
            iPlayer winner = _game.playGame(crossPlayer, noughtPlayer);
            _winners.add((GeneBot)winner);
        }
        System.out.println("Total legal moves: " + _game.getTotalLegalMoves());
        System.out.println("Total failed moves: " + _game.getTotalFailedMoves());
        System.out.println("Total Cross wins: " + _game.getTotalCrossWins());
        System.out.println("Total Nought wins: " + _game.getTotalNoughtWins());
        return _winners;
    }
    
    public static void main(String[] args)
    {
        Generation g = new Generation();
  
        g.runGeneration();
    }
    
}
