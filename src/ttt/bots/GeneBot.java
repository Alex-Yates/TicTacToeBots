package ttt.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An individual set of rules/genes, encoded as bitfields, that will be used to
 * play tic tac toe. Each GeneBot contains an ArrayList of rules (_rules) 
 * represented as 32 bit ints. The first 27 bits of each int/rule are used to
 * represent the expected state of each square on the board (3 bits per square).
 * If all squares match the last four bits are used to specify a number for a 
 * square where the geneBot will go.
 * @author Alex Yates
 */
public class GeneBot implements iPlayer
{

    // member variables used to represent the number of rules and mutation rate
    private int _num_rules = 2000;
    private float _mut_prob = 0.0005f;

    // A list to hold all the rules this GeneBot will use to decide where to go
    private List<Integer>  _rules;

    /**
     * Creates a new GeneBot with the default number of random rules
     */
    public GeneBot()
    {
        _rules = new ArrayList<Integer>(_num_rules);
        Random rand = new Random();
        for (int i = 0; i < _num_rules; i++)
            _rules.add(rand.nextInt());
    }
    
    /**
     * Creates a new GeneBot with a specific number of rules and a custom 
     * mutation rate
     * @param num_rules Number of rules
     * @param mut_prob Mutation rate
     */
    public GeneBot(int num_rules, float mut_prob)
    {
        _num_rules = num_rules;
        _mut_prob = mut_prob;
        _rules = new ArrayList<Integer>(_num_rules);
        Random rand = new Random();
        for (int i = 0; i < _num_rules; i++)
            _rules.add(rand.nextInt());
    }

    /**
     * Creates a GeneBot with a given set of rules
     * @param rules A set of rules
     */
    public GeneBot(List<Integer> rules)
    {
        _rules = rules;
    }

    /**
     * Creates a new GeneBot, using two previous GeneBots as parents
     * @param mother A parent GeneBot
     * @param father A parent GeneBot
     */
    public GeneBot(GeneBot mother, GeneBot father)
    {
        // children should have the same number of genes
        // as the parent with the least genes
        _num_rules = mother.getNumRules();
        if (father.getNumRules() < _num_rules)
        {
            _num_rules = father.getNumRules();
        }
        
        // children inherit mutation probability from mother
        _mut_prob = mother.getMutProb();
        
        // children inherit randomly from mother an father
        Random rand = new Random();
        _rules = new ArrayList<Integer>(_num_rules);

        for (int i = 0; i < _num_rules; i++)
        {
            if (rand.nextBoolean())
                _rules.add(mother.getRule(i));
            else
                _rules.add(father.getRule(i));
        }

        for (int i = 0; i < _num_rules; i++)
        {
            int mutation = 0;
            for (int j = 0; j < 32; j++)
            {
                if (rand.nextFloat() < _mut_prob)
                    mutation = mutation + (1 << i);
            }
            _rules.set(i, _rules.get(i) ^ mutation);
        }
    }

    public int getNumRules()
    {
        return _num_rules;
    }
    
    public float getMutProb()
    {
        return _mut_prob;
    }
    
    private boolean ruleMatch(int board, int rule)
    {
        return (rule & board & 0xFFFFFFE0) == 0;
    }

    /**
     * Loop through all the rules, if any match use this to determine where to 
     * go. If no matches go in square 15 (an illegal move - there are only 9 
     * squares)
     * @param board
     * @return 
     */
    public int takeTurn(int board)
    {
        for (Integer rule : _rules)
        {
            if (ruleMatch(board, rule))
                return rule & 0xF;
        }
        return 15;
    }

    public int getRule(int i)
    {
        return _rules.get(i);
    }

    public void printRules()
    {
        for (Integer rule : _rules)
            System.out.println(Utils.toString(rule));
    }

    public int countOnes()
    {
        int numOnes = 0;
        for (Integer rule : _rules)
            numOnes += Integer.bitCount(rule);
        return numOnes;
    }

    /**
     * Just for testing
     * @param args Not used
     */
    public static void main(String[] args)
    {
        GeneBot parent1 = new GeneBot();
        GeneBot parent2 = new GeneBot();
        GeneBot baby = new GeneBot(parent1, parent2);
        parent1.printRules();
        System.out.println("");
        parent2.printRules();
        System.out.println("");
        baby.printRules();
    }

}
