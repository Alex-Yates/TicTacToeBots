package ttt.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneBot implements iPlayer {

    private List<Integer>  _rules;

    public GeneBot() {
        _rules = new ArrayList<Integer>(2000);
        Random rand = new Random();
        for (int i = 0; i < 2000; i++) {
            _rules.add(rand.nextInt());        
        }
    }

    public GeneBot(List<Integer> rules) {
        _rules = rules;
    }

    private boolean ruleMatch(int board, int rule){
        return (rule & board & 0xFFFFFFE0) == 0;
    } 

    public int takeTurn(int board) {
        for (Integer rule : _rules) {
            if (ruleMatch(board, rule)){
                return rule & 0xF;
            }
        }
        return 0;
    }

}
