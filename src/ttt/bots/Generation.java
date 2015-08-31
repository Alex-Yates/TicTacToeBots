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

    
    // constructors
    public Generation()
    {
        for (int i = 0; i < 2000; i++)
        {
            _contestants.add(new GeneBot());
        }
    }

    public Generation(int num_rules, float mut_prob)
    {
        for (int i = 0; i < 2000; i++)
        {
            _contestants.add(new GeneBot(num_rules, mut_prob));
        }
    }
    
    public Generation(Set<GeneBot> bots)
    {
        _contestants.addAll(bots);
    }
    
    private List<GeneBot> runGeneration()
    {
        Game game = new Game();
        for (int i = 0; i < _contestants.size(); i+=2)
        {
            iPlayer crossPlayer = _contestants.get(i);
            iPlayer noughtPlayer = _contestants.get(i+1);
        
            iPlayer winner = game.playGame(crossPlayer, noughtPlayer);
            _winners.add((GeneBot)winner);
        }
        System.out.println("Total legal moves: " + game.getTotalLegalMoves());
        System.out.println("Total failed moves: " + game.getTotalFailedMoves());
        System.out.println("Total Cross wins: " + game.getTotalCrossWins());
        System.out.println("Total Nought wins: " + game.getTotalNoughtWins());
        System.out.println("Total number of winners: " + _winners.size());
        System.out.println("Total number of contestants: " + _contestants.size());
        System.out.println("*************");
        return _winners;
    }
    
    public void regenerate()
    {
        List<GeneBot> children = new ArrayList<>();
        
            for (int i = 0; i < _winners.size(); i+=2)
            {
                children.add(new GeneBot(_winners.get(i), _winners.get(i+1)));
                children.add(new GeneBot(_winners.get(i), _winners.get(i+1)));
             }
            _contestants.clear();
            _contestants.addAll(_winners);
            _contestants.addAll(children);
            _winners.clear();    
    }
    
    public static void main(String[] args)
    {
        Generation g = new Generation(500, 0.005f);
  
        g.runGeneration();
        for (int i = 0; i < 5000; i++)
        {
            System.out.println("Generation number: " + i);
            g.regenerate();
            g.runGeneration();
        }
    }
    
}
