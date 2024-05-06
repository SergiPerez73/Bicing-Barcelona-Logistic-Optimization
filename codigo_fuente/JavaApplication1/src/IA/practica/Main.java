package IA.practica;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception{
        int numbicis=1250;
        int numestacions=25;
        int seed=1234;
        int numfurgonetas=5;
        Boolean horapunta=false;
        int generadorinicial=1;

        PracticaBiciCiutat c = new PracticaBiciCiutat(numbicis,numestacions,seed,numfurgonetas,horapunta,generadorinicial);
        boolean hillclimbing = true;
        if (hillclimbing){
            Problem problem =  new Problem(c,new PracticaBiciCiutatSuccessorFunction(), new PracticaBiciCiutatGoalTest(),new PracticaBiciCiutatHeuristicFunction2());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            printActions(agent.getActions());
                
        }
        else{
            Problem problem =  new Problem(c,new PracticaBiciCiutatSuccessorFunctionSA(), new PracticaBiciCiutatGoalTest(),new PracticaBiciCiutatHeuristicFunction2());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(4000,30,5,0.5);
            SearchAgent agent = new SearchAgent(problem,search);
            PracticaBiciCiutat nouc = (PracticaBiciCiutat) agent.getActions().get(agent.getActions().size()-1);
            nouc.printEstado();
            
            
        }
        
        
    }

        private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
        }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
    
}
