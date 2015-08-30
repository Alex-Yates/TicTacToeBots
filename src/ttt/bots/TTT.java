package ttt.bots;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class TTT
{
    static public final int NOUGHT = 4;
    static public final int CROSS = 2;
    static public final int EMPTY = 1;
    
    static public final int NOUGHT_MATCH = 3;
    public final static int CROSS_MATCH = 5;
    
    static public final int NOUGHT0 = 4<<29;
    static public final int NOUGHT1 = 4<<26;
    static public final int NOUGHT2 = 4<<23;
    static public final int NOUGHT3 = 4<<20;
    static public final int NOUGHT4 = 4<<17;
    static public final int NOUGHT5 = 4<<14;
    static public final int NOUGHT6 = 4<<11;
    static public final int NOUGHT7 = 4<<8;
    static public final int NOUGHT8 = 4<<5;
    
    static public final int CROSS0 = 2<<29;
    static public final int CROSS1 = 2<<26;
    static public final int CROSS2 = 2<<23;
    static public final int CROSS3 = 2<<20;
    static public final int CROSS4 = 2<<17;
    static public final int CROSS5 = 2<<14;
    static public final int CROSS6 = 2<<11;
    static public final int CROSS7 = 2<<8;
    static public final int CROSS8 = 2<<5;
    
    static public final int EMPTY0 = 1<<29;
    static public final int EMPTY1 = 1<<26;
    static public final int EMPTY2 = 1<<23;
    static public final int EMPTY3 = 1<<20;
    static public final int EMPTY4 = 1<<17;
    static public final int EMPTY5 = 1<<14;
    static public final int EMPTY6 = 1<<11;
    static public final int EMPTY7 = 1<<8;
    static public final int EMPTY8 = 1<<5;
    
    static public final int MASK0 = 7<<29;
    static public final int MASK1 = 7<<26;
    static public final int MASK2 = 7<<23;
    static public final int MASK3 = 7<<20;
    static public final int MASK4 = 7<<17;
    static public final int MASK5 = 7<<14;
    static public final int MASK6 = 7<<11;
    static public final int MASK7 = 7<<8;
    static public final int MASK8 = 7<<5;
    
    public final static int CROSS_MATCH_0 = CROSS_MATCH << 29;
    public final static int CROSS_MATCH_1 = CROSS_MATCH << 26;
    public final static int CROSS_MATCH_2 = CROSS_MATCH << 23;
    public final static int CROSS_MATCH_3 = CROSS_MATCH << 20;
    public final static int CROSS_MATCH_4 = CROSS_MATCH << 17;
    public final static int CROSS_MATCH_5 = CROSS_MATCH << 14;
    public final static int CROSS_MATCH_6 = CROSS_MATCH << 11;
    public final static int CROSS_MATCH_7 = CROSS_MATCH << 8;
    public final static int CROSS_MATCH_8 = CROSS_MATCH << 5;
    
    public final static int NOUGHT_MATCH_0 = NOUGHT_MATCH << 29;
    public final static int NOUGHT_MATCH_1 = NOUGHT_MATCH << 26;
    public final static int NOUGHT_MATCH_2 = NOUGHT_MATCH << 23;
    public final static int NOUGHT_MATCH_3 = NOUGHT_MATCH << 20;
    public final static int NOUGHT_MATCH_4 = NOUGHT_MATCH << 17;
    public final static int NOUGHT_MATCH_5 = NOUGHT_MATCH << 14;
    public final static int NOUGHT_MATCH_6 = NOUGHT_MATCH << 11;
    public final static int NOUGHT_MATCH_7 = NOUGHT_MATCH << 8;
    public final static int NOUGHT_MATCH_8 = NOUGHT_MATCH << 5;
    
    static public final int TESTBOARD1 = NOUGHT4 + NOUGHT3 + NOUGHT5 + CROSS0 + CROSS1 + EMPTY2 + EMPTY6 + EMPTY7 + EMPTY8;
    static public final int NEWBOARD = EMPTY0 + EMPTY1 + EMPTY2 + EMPTY3 + EMPTY4 + EMPTY5 + EMPTY6 + EMPTY7 + EMPTY8;
    
    private static final int[] _noughts = {NOUGHT0, NOUGHT1, NOUGHT2, NOUGHT3, NOUGHT4, NOUGHT5, NOUGHT6, NOUGHT7, NOUGHT8};
    private static final int[] _crosses = {CROSS0, CROSS1, CROSS2, CROSS3, CROSS4, CROSS5, CROSS6, CROSS7, CROSS8}; 
    private static final int[] _empties = {EMPTY0, EMPTY1, EMPTY2, EMPTY3, EMPTY4, EMPTY5, EMPTY6, EMPTY7, EMPTY8};
    private static final int[] _masks = {MASK0, MASK1, MASK2, MASK3, MASK4, MASK5, MASK6, MASK7, MASK8};
      
    private static final int[] CROSS_VICTORIES = { CROSS_MATCH_0 + CROSS_MATCH_1 + CROSS_MATCH_2,
                                                   CROSS_MATCH_3 + CROSS_MATCH_4 + CROSS_MATCH_5,
                                                   CROSS_MATCH_6 + CROSS_MATCH_7 + CROSS_MATCH_8,
                                                   CROSS_MATCH_0 + CROSS_MATCH_3 + CROSS_MATCH_6,
                                                   CROSS_MATCH_1 + CROSS_MATCH_4 + CROSS_MATCH_7,
                                                   CROSS_MATCH_2 + CROSS_MATCH_5 + CROSS_MATCH_8,
                                                   CROSS_MATCH_0 + CROSS_MATCH_4 + CROSS_MATCH_8,
                                                   CROSS_MATCH_2 + CROSS_MATCH_4 + CROSS_MATCH_6};
    
    private static final int[] NOUGHT_VICTORIES = { NOUGHT_MATCH_0 + NOUGHT_MATCH_1 + NOUGHT_MATCH_2,
                                                    NOUGHT_MATCH_3 + NOUGHT_MATCH_4 + NOUGHT_MATCH_5,
                                                    NOUGHT_MATCH_6 + NOUGHT_MATCH_7 + NOUGHT_MATCH_8,
                                                    NOUGHT_MATCH_0 + NOUGHT_MATCH_3 + NOUGHT_MATCH_6,
                                                    NOUGHT_MATCH_1 + NOUGHT_MATCH_4 + NOUGHT_MATCH_7,
                                                    NOUGHT_MATCH_2 + NOUGHT_MATCH_5 + NOUGHT_MATCH_8,
                                                    NOUGHT_MATCH_0 + NOUGHT_MATCH_4 + NOUGHT_MATCH_8,
                                                    NOUGHT_MATCH_2 + NOUGHT_MATCH_4 + NOUGHT_MATCH_6};
    
    public static int getNought(int square)
    {
       return _noughts[square];
    }
    
    public static int getCross(int square)
    {
       return _crosses[square];
    }    
    
    public static int getEmpty(int square)
    {
       return _empties[square];
    }
    
    public static int getMask(int square)
    {
       return _masks[square];
    }
    
    public static int getCrossVictory(int vcNumber)
    {
       return CROSS_VICTORIES[vcNumber];
    }
    
    public static int getNoughtVictory(int vcNumber)
    {
       return NOUGHT_VICTORIES[vcNumber];
    }
    
    public static String getSquare(int square, int board)
    {
        if ((_masks[square] & board) == _noughts[square])
            return "0";
        
        if ((_masks[square] & board) == _crosses[square])
            return "X";
                
        if ((_masks[square] & board) == _empties[square])
            return " ";       
                
        else return "?"; 
    }
    
    public static void main(String[] args)
    {
        System.out.println(Utils.toString(getEmpty(8)));
    }
}
