package ttt.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneBot implements iPlayer
{

    private static final int NUM_RULES = 2000;
    private static final float MUT_PROB = 0.005f;

    private List<Integer>  _rules;

    public GeneBot()
    {
        _rules = new ArrayList<Integer>(NUM_RULES);
        Random rand = new Random();
        for (int i = 0; i < NUM_RULES; i++)
            _rules.add(rand.nextInt());
    }

    public GeneBot(List<Integer> rules)
    {
        _rules = rules;
    }

    public GeneBot(GeneBot mother, GeneBot father)
    {
        Random rand = new Random();
        _rules = new ArrayList<Integer>(NUM_RULES);

        for (int i = 0; i < NUM_RULES; i++)
        {
            if (rand.nextBoolean())
                _rules.add(mother.getRule(i));
            else
                _rules.add(father.getRule(i));
        }

        for (int i = 0; i < NUM_RULES; i++)
        {
            int mutation = 0;
            for (int j = 0; j < 32; j++)
            {
                if (rand.nextFloat() < MUT_PROB)
                    mutation = mutation + (1 << i);
            }
            _rules.set(i, _rules.get(i) ^ mutation);
        }
    }

    private boolean ruleMatch(int board, int rule)
    {
        return (rule & board & 0xFFFFFFE0) == 0;
    }

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
