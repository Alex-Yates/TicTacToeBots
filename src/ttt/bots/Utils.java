/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.bots;


/**
 *
 * @author Alex
 */
public class Utils
{
    /**
     * converts an int to a sting containing a binary representation of the int
     * @param n the int to convert into binary
     * @return a binary representation of n
     */
    public static String toString(int n)
    {
        StringBuilder buf = new StringBuilder();
        
        int mask = 1<<31;
        
        for(int i=0; i<32; i++)
        {
            buf.append((n&mask)==0 ? '0' : '1');
            mask = mask >>> 1;
            
            if(i%3==2 && i<28)
                buf.append(',');  
        }
        
        return buf.toString();
    }
    
    /**
     * Prints the current state of the board to the console
     * @param input the board to be printed
     */
    public static void printBoard(int input)
    {
        System.out.println(" " + TTT.getSquare(0, input) + " | " + TTT.getSquare(1, input) + " | " + TTT.getSquare(2, input));
        System.out.println("---+---+---");
        System.out.println(" " + TTT.getSquare(3, input) + " | " + TTT.getSquare(4, input) + " | " + TTT.getSquare(5, input));
        System.out.println("---+---+---");
        System.out.println(" " + TTT.getSquare(6, input) + " | " + TTT.getSquare(7, input) + " | " + TTT.getSquare(8, input));
    }
    
    /**
     * Just used for testing
     * @param args
     */
    public static void main(String[] args)
    {
        printBoard(TTT.TESTBOARD1);
    }
}
