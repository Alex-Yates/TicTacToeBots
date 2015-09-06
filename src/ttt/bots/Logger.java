/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles the logging of various stats about generations of GeneBots.
 * A csv file is created when the logger is created and initialised with
 * headers appropriate for a set of tournaments with a particular number
 * of rounds. 
 * @author Alex
 */
public class Logger {

    /**
     * The path the the log file. Initialised by the constructor.
     */
    String _logFile;
    

    
    public Logger(String path, int rounds)
    {     
    /**
     * Creates a logger that can log stats to a specific csv file
     * @param path A file path in format "C:\\a\\file\\path". This will be the 
     * location where the csv file is created.
     * @param rounds The number of tournament rounds you are expecting
     */
        
        // setting the file path and file name for the log file
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        _logFile = path + "\\TicTacToeBotLogs" + date + ".csv";
 
        // creating a .csv file to log results to
        try
	{            
            FileWriter writer = new FileWriter(_logFile);
            // Initialising the cloumn headers approporiately
            for (int i = 0; i < rounds; i++)
            {
                writer.append("Round " + i);
                writer.append(',');
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
                writer.append(',');
            }
            writer.append("\n");
            writer.flush();
	    writer.close();
        }    
	catch(IOException e)
	{
	     e.printStackTrace();
	}
    }
    
    /**
     * Logs various stats from a tournament
     * 
     * @param legalMoves The total number of legal moves by all bots
     * @param failedMoves The total number of failed moves by all bots
     * @param totalCrossWins The number of games won by crosses
     * @param totalNoughtWins The number of games won by noughts
     * @param totalWinners The number of games that were won (not drawn)
     * @param totalContestants The total number of bots that took part
     */
    public void saveStats(int legalMoves, int failedMoves, 
                           int totalCrossWins, int totalNoughtWins,
                           int totalWinners, int totalContestants)
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
        
        // determining the string to add to the file:
        String stats = ',' + Integer.toString(legalMoves) + ',' 
                     + Integer.toString(failedMoves) + ','
                     + Double.toString(hitRate) + ','
                     + Integer.toString(totalCrossWins) + ','
                     + Integer.toString(totalNoughtWins) + ','
                     + Integer.toString(totalWinners) + ','
                     + Integer.toString(totalContestants) + ',';
        
        // appending a .csv file with stats
        try
	{
            Files.write(Paths.get(_logFile), stats.getBytes(), StandardOpenOption.APPEND);
        }    
	catch(IOException e)
	{
	     e.printStackTrace();
	}
    }
    
    /**
     * Adds a new line to the log file
     */
    public void newLine()
    {        
        try
	{
            Files.write(Paths.get(_logFile), "\n".getBytes(), StandardOpenOption.APPEND);
        }    
	catch(IOException e)
	{
	     e.printStackTrace();
	}
    }
}
