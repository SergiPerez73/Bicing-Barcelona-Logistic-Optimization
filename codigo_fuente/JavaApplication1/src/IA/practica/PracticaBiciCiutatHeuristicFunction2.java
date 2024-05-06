package IA.practica;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class PracticaBiciCiutatHeuristicFunction2 implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((PracticaBiciCiutat) n).heuristic2();
    }
}
