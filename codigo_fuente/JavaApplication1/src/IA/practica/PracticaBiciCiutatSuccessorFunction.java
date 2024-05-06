package IA.practica;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;


public class PracticaBiciCiutatSuccessorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        
        PracticaBiciCiutat board = (PracticaBiciCiutat) state;
        board.printEstado();
        int nestacions = board.Estacions.size();
        int nviajes = board.getViajeFurgo().size();
        
        //1. Añadir desplazamiento
        for(int e0=0; e0<nestacions; e0++){
            for(int e1=0; e1<nestacions; e1++){
                for(int b1=1; b1<10; b1++){
                    if (board.anadirDespB(e0, e1,b1)){
                        PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                        board_aux.anadirDesp(e0,e1,b1);
                        String st = "Añadir desplazamiento: e0: "+String.valueOf(e0)+", e1: "+String.valueOf(e1)+", b1: "+String.valueOf(b1);
                        retval.add(new Successor(st,board_aux));
                    }
                }
            }
        }
        
        //2. Añadir desplazamiento 2
        for(int f1=0; f1<nviajes; f1++){
            for(int e2=0; e2<nestacions; e2++){
                for(int b2=1; b2<10; b2++){
                    if (board.anadirDesp2B(f1, e2,b2)){
                        PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                        board_aux.anadirDesp2(f1,e2,b2);
                        String st = "Añadir desplazamiento 2: f1: "+String.valueOf(f1)+", e2: "+String.valueOf(e2)+", b2: "+String.valueOf(b2);
                        retval.add(new Successor(st,board_aux));
                    }
                }
            }
        }
        
        //3. Modificar destino 1
        for(int f1=0; f1<nviajes; f1++){
            for(int e1=0; e1<nestacions; e1++){
                if(board.modifEstDestB(f1, e1)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.modifEstDest(f1,e1);
                    String st = "Modificar destino: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e1);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        
        //4. Modificar destino 2
        for(int f1=0; f1<nviajes; f1++){
            for(int e2=0; e2<nestacions; e2++){
                if(board.modifEstDest2B(f1, e2)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.modifEstDest2(f1,e2);
                    String st = "Modificar destino 2: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e2);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        
        //5. Añadir bicis 1
        for(int f1=0; f1<nviajes; f1++){
            for(int b1=1; b1<5; b1++){
                if(board.anadirBicis1B(f1, b1)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.anadirBicis1(f1, b1);
                    String st = "Añadir bicis 1: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        
        //6. Añadir bicis 2
        for(int f1=0; f1<nviajes; f1++){
            for(int b1=1; b1<5; b1++){
                if(board.anadirBicis2B(f1, b1)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.anadirBicis2(f1, b1);
                    String st = "Añadir bicis 1: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        /*
        //7. Quitar bicis 1
        for(int f1=0; f1<nviajes; f1++){
            for(int b1=1; b1<5; b1++){
                if(board.quitarBiciB(f1, b1)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.quitarBici(f1, b1);
                    String st = "Quitar bicis 1: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        
        //8. Quitar bicis 2
        for(int f1=0; f1<nviajes; f1++){
            for(int b1=1; b1<5; b1++){
                if(board.quitarBici2B(f1, b1)){
                    PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                    board_aux.quitarBici2(f1, b1);
                    String st = "Quitar bicis 2: f1: "+String.valueOf(f1)+", b1: "+String.valueOf(b1);
                    retval.add(new Successor(st,board_aux));
                }
            }
        }
        */
        //9. Añadir destino 1
        for(int f1=0; f1<nviajes; f1++){
            for(int e1=0; e1<nestacions; e1++){
                for(int b1=1; b1<10; b1++){
                    if (board.anadirDest1B(f1, e1,b1)){
                        PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                        board_aux.anadirDest1(f1,e1,b1);
                        String st = "Añadir destino 1: f1: "+String.valueOf(f1)+", e1: "+String.valueOf(e1)+", b1: "+String.valueOf(b1);
                        retval.add(new Successor(st,board_aux));
                    }
                }
            }
        }
        
        /*
        //10. Quitar desplazamiento
        for(int f1=0; f1<nviajes; f1++){
            if(board.quitarDespB(f1)){
                PracticaBiciCiutat board_aux = new PracticaBiciCiutat(board.getViajeFurgo(), board.getActEstacions());
                board_aux.quitarDesp(f1);
                String st = "Quitar bicis Desp: f1: "+String.valueOf(f1);
                retval.add(new Successor(st,board_aux));
            }
        }
        */
        
        return retval;

    }

}
