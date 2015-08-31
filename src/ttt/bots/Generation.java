/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    private List<GeneBot> runTournament(String logFilePath)
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
        saveStats(game.getTotalLegalMoves(), game.getTotalFailedMoves(),
                  game.getTotalCrossWins(), game.getTotalNoughtWins(),
                  _winners.size(), _contestants.size(), logFilePath);
       
        return _winners;
    }
    
    private void runMultiRoundTournament(int rounds, String logFilePath)
    {
        // creating a .csv file to log results to
        try
	{
	    FileWriter writer = new FileWriter(logFilePath);
            
            writer.append("Total legal moves");
	    writer.append(',');
	    writer.append("Total failed moves");
            writer.append(',');
	    writer.append("Legal move rate");	    
            writer.append(',');
	    writer.append("Total Cross wins");
            writer.append(',');
	    writer.append("Total Nought wins");
            writer.append(',');
	    writer.append("Total number of winners");
            writer.append(',');
	    writer.append("Total number of contestants");
            writer.append('\n');
            
            writer.flush();
	    writer.close();
        }    
	catch(IOException e)
	{
	     e.printStackTrace();
	}
        
        
        // run multiple generations, killing the weakest
        // 50% each time
        for (int r = 0; r < rounds; r++)
        {
            System.out.println("Round: " + r);
            _winners.clear();
            runTournament(logFilePath);
            _contestants.clear();
            _contestants.addAll(_winners);
 
        }
        
        // spawn population back to original size
        Regenerate(rounds);      
    }
    
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
    
    private void saveStats(int legalMoves, int failedMoves, 
                           int totalCrossWins, int totalNoughtWins,
                           int totalWinners, int totalContestants,
                           String logFilePath)
    {      
        System.out.println("Total legal moves: " + legalMoves);
        System.out.println("Total failed moves: " + failedMoves);
        double hitRate = ((double)legalMoves / ((double)failedMoves + (double)legalMoves));
        System.out.println("Legal move rate: " + hitRate);
        System.out.println("Total Cross wins: " + totalCrossWins);
        System.out.println("Total Nought wins: " + totalNoughtWins);
        System.out.println("Total number of winners: " + totalWinners);
        System.out.println("Total number of contestants: " + totalContestants);
        System.out.println("*************");
        
               // creating a .csv file to log results to
        try
	{
	    FileWriter writer = new FileWriter(logFilePath);
            
            writer.append('\n');
            writer.append(Integer.toString(legalMoves));
	    writer.append(',');
	    writer.append(Integer.toString(failedMoves));
            writer.append(',');
	    writer.append(Double.toString(hitRate));	    
            writer.append(',');
	    writer.append(Integer.toString(totalCrossWins));
            writer.append(',');
	    writer.append(Integer.toString(totalNoughtWins));
            writer.append(',');
	    writer.append(Integer.toString(totalWinners));
            writer.append(',');
	    writer.append(Integer.toString(totalContestants));

            
            writer.flush();
	    writer.close();
        }    
	catch(IOException e)
	{
	     e.printStackTrace();
	}
    }
    
    public static void main(String[] args)
    {
        Generation g = new Generation(500, 0.05f);
        String logFile = new SimpleDateFormat("'C:\\Users\\Alex\\Documents\\TicTacToeLogs'yyyyMMddhhmmss'.csv'").format(new Date());
 
        for (int i = 0; i < 5000; i++)
        {
        System.out.println("Tournament number: " + i);
        g.runMultiRoundTournament(5, logFile);
        }

    }
    
}
