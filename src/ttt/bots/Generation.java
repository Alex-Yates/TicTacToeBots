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
 * Handles lifecycles of generations of bots as compete in tournaments,
 * die and regenerate
 * @author Alex Yates
 */
public class Generation {
    
    // member variables to hold all our bots
 
    // A list of all competing bots
    private List<GeneBot> _contestants = new ArrayList<>();
    // A list reserved only for winning bots
    private List<GeneBot> _winners = new ArrayList<>();
    
    // constructors
    /**
     * Creates a new generation with 2000 randomly created bots with default 
     * numbers of genes and mutation rates
     */
    public Generation()
    {
        for (int i = 0; i < 2000; i++)
        {
            _contestants.add(new GeneBot());
        }
    }

    /**
     * Creates a new generation with 2000 randomly created bots with 
     * configurable mutation rates and numbers of genes
     * @param num_rules The number of genes each bot should have
     * @param mut_prob The mutation rate each both should have
     */
    public Generation(int num_rules, float mut_prob)
    {
        for (int i = 0; i < 2000; i++)
        {
            _contestants.add(new GeneBot(num_rules, mut_prob));
        }
    }
    
    /**
     * Creates a new generation from an existing set of GeneBots
     * @param bots a set of GeneBots
     */
    public Generation(Set<GeneBot> bots)
    {
        _contestants.addAll(bots);
    }
    
    /**
     * Pairs bots from _contestants off against each other and makes each pair
     * play a game. Adds the winners to _winners. Also logs various stats.
     * @param log A Logger object that is used for logging results.
     * @return A list containing all the winning bots
     */
    private List<GeneBot> runTournament(Logger log)
    {
        // ensuring there are an even number of contestants
        if ((_contestants.size() % 2) == 1)
        {
            _contestants.remove(_contestants.size() - 1);
        }
        
        // killing the weak
        Game game = new Game();
        for (int i = 0; i < _contestants.size(); i+=2)
        {
            iPlayer crossPlayer = _contestants.get(i);
            iPlayer noughtPlayer = _contestants.get(i+1);
        
            iPlayer winner = game.playGame(crossPlayer, noughtPlayer);
            _winners.add((GeneBot)winner);
        }
        
        // logging stats        
        log.saveStats(game.getTotalLegalMoves(), game.getTotalFailedMoves(),
                  game.getTotalCrossWins(), game.getTotalNoughtWins(),
                  _winners.size(), _contestants.size());
       
        return _winners;
    }
    
    /**
     * Runs multiple tournaments on after the other, hence killing off a higher
     * percentage of weak bots
     * @param rounds The number of rounds to play
     * @param log A Logger object, for logging stats
     */
    private void runMultiRoundTournament(int rounds, Logger log)
    {    
        log.newLine();

        // run multiple generations, killing the weakest
        // 50% each time
        for (int r = 0; r < rounds; r++)
        {
            System.out.println("Round: " + r);
            _winners.clear();
            runTournament(log);
            _contestants.clear();
            _contestants.addAll(_winners);
 
        }
        
        // spawn population back to original size
        Regenerate(rounds);      
    }
    
    /**
     * Deletes the entire contents of _contestants and generates new GeneBots
     * using the bots in _winners as the parents.
     * @param spawnRate The number of children to spawn from each pair of bots,
     * as a power of 2
     */
    public void Regenerate(int spawnRate)
    {
        List<GeneBot> children = new ArrayList<>();
        
        // ensuring there are an even number of winners
        if ((_winners.size() % 2) == 1)
        {
            _winners.remove(_winners.size() - 1);
        } 
        
        // repopulating contestants back to original size
        // note, it is possible the size will reduce slightly
        // due to odd numbers
        for (int numKids = 0; numKids < Math.pow(2, spawnRate); numKids++)
        {
            for (int i = 0; i < (_winners.size()); i+=2)
            {   
                children.add(new GeneBot(_winners.get(i), _winners.get(i+1)));                
                children.add(new GeneBot(_winners.get(i), _winners.get(i+1))); 
            }
        }   

        _contestants.clear();
        _contestants.addAll(_winners);
        _contestants.addAll(children);
        _winners.clear();    
    }
    
    /**
     * A place to play God. Create generations. Make them fight. Do evolution.
     * @param args Unused
     */
    public static void main(String[] args)
    {
        Generation g = new Generation(500, 0.05f);    
        int numRounds = 5;
        
        Logger log = new Logger("C:\\Users\\Alex\\Documents", numRounds);
        
        for (int i = 0; i < 5000; i++)
        {
        System.out.println("Tournament number: " + i);
        g.runMultiRoundTournament(numRounds, log);
        }

    }
    
}
