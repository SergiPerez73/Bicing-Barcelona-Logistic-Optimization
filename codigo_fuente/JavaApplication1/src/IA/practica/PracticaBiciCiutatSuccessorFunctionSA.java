package IA.practica;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PracticaBiciCiutatSuccessorFunctionSA implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        PracticaBiciCiutat board = (PracticaBiciCiutat) state;
        int nestacions = board.Estacions.size();
        if (nestacions ==0) nestacions=1; //Debe ser positivo para el bound
        int nviajes = board.getViajeFurgo().size();
        if (nviajes ==0) nviajes=1; //Debe ser positivo para el bound
        PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
        
        Random myRandom=new Random();
        do{
            int op = myRandom.nextInt(7);
            //Añadir desplazamiento
            if (op == 0){
                int e0 = myRandom.nextInt(nestacions);
                int e1 = myRandom.nextInt(nestacions);
                int b1 = myRandom.nextInt(11);
                if(board.anadirDespB(e0, e1,1) && b1>0){
                    board_aux.anadirDesp(e0,e1,b1);
                    String st = "Añadir desplazamiento: e0: "+String.valueOf(e0)+", e1: "+String.valueOf(e1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Añadir desplazamiento 2
            else if (op == 1){
                int f1 = myRandom.nextInt(nviajes);
                int e2 = myRandom.nextInt(nestacions);
                int b2 = myRandom.nextInt(11);
                if(board.anadirDesp2B(f1, e2,b2) && b2>0){
                    board_aux.anadirDesp2(f1,e2,b2);
                    String st = "Añadir desplazamiento 2: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e2)+", b2: "+String.valueOf(b2);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Modificar destino 1
            else if(op == 2){
                int f1 = myRandom.nextInt(nviajes);
                int e1 = myRandom.nextInt(nestacions);
                if(board.modifEstDestB(f1, e1)){
                    board_aux.modifEstDest(f1,e1);
                    String st = "Modificar destino: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e1);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Modificar destino 2
            else if(op == 3){
                int f1 = myRandom.nextInt(nviajes);
                int e2 = myRandom.nextInt(nestacions);
                if(board.modifEstDest2B(f1, e2)){
                    board_aux.modifEstDest2(f1,e2);
                    String st = "Modificar destino 2: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e2);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Anadir bicis 1
            else if(op == 4){
                int f1 = myRandom.nextInt(nviajes);
                int b1 = myRandom.nextInt(6);
                if(board.anadirBicis1B(f1, b1) && b1 !=0){
                    board_aux.anadirBicis1(f1, b1);
                    String st = "Añadir bicis 1: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Anadir bicis 2
            else if(op == 5){
                int f1 = myRandom.nextInt(nviajes);
                int b2 = myRandom.nextInt(6);
                if(board.anadirBicis2B(f1, b2) && b2 !=0){
                    board_aux.anadirBicis2(f1, b2);
                    String st = "Añadir bicis 2: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b2);
                    retval.add(new Successor(st,board_aux));
                }
            }
            /*
            //Quitar bicis 1
            else if(op == 6){
                int f1 = myRandom.nextInt(nviajes);
                int b1 = myRandom.nextInt(6);
                if(board.quitarBiciB(f1, b1) && b1 !=0){
                    board_aux.quitarBici(f1, b1);
                    String st = "Quitar bicis 1: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
            //Quitar bicis 2
            else if(op == 7){
                int f1 = myRandom.nextInt(nviajes);
                int b2 = myRandom.nextInt(6);
                if(board.quitarBici2B(f1, b2) && b2 !=0){
                    board_aux.quitarBici2(f1, b2);
                    String st = "Quitar bicis 2: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b2);
                    retval.add(new Successor(st,board_aux));
                }
            }
            */
            //Añadir dest2
            else if(op == 6){
                int f1 = myRandom.nextInt(nviajes);
                int e1 = myRandom.nextInt(nestacions);
                int b1 = myRandom.nextInt(10);
                if(board.anadirDest1B(f1, e1,b1) && b1>0){
                    board_aux.anadirDest1(f1,e1,b1);
                    String st = "Añadir desplazamiento 2: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e1)+", b2: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
            
            
        }while (retval.isEmpty());
        
        return retval;

    }

}
