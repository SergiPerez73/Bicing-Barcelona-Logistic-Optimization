package IA.practica;

import java.util.*;
import IA.Bicing.Estaciones;

public class PracticaBiciCiutat {
    
    static private int nfurgonetas;
    static private int seed;
    static public Estaciones Estacions;
    
    /*Para cada furgoneta, su viaje. Con su origen, destino1
    destino2, dropoff1 y dropoff2.
    Si origen == -1, no hay viaje.
    Si destino2==-1, no hay segundo dropoff*/
    private ArrayList<short[]> ViajeFurgo;
    
    /*Para cada estacion, el dato actualizado de las 
    bicicletas que habra, las que se pueden sacar y
    si sale alguna furgoneta de la estacion*/
    private short [][] ActEstacions;
    
    
    /* Constructor de estado inicial */
    public PracticaBiciCiutat(int numbicis, int numestacions, int seed1, int numfurgonetas, Boolean horapunta1,int inicioviajes) {
        seed = seed1;
        nfurgonetas = numfurgonetas;
        int horapunta= Estaciones.EQUILIBRIUM;
        if (horapunta1) horapunta = Estaciones.RUSH_HOUR;
        Estacions = new Estaciones(numestacions,numbicis,horapunta,seed);
        
        //1er creador: Viajes vacios
        ViajeFurgo = new ArrayList<> ();
        
        ActEstacions = new short [Estacions.size()][3];
        for(int i=0; i<Estacions.size(); i++){
            ActEstacions[i][0] = (short) Estacions.get(i).getNumBicicletasNext();
            ActEstacions[i][1] = (short) Estacions.get(i).getNumBicicletasNoUsadas();
            ActEstacions[i][2] = 0;
        }
        
        //2o creador: Con viajes
        if (inicioviajes == 1){
            ArrayList<short[]> BicisRestantes = new ArrayList<short[]> ();
            //Para cada estación, las bicis que sobran, la demanda no las requiere
            for(int i=0; i<Estacions.size(); i++){
                short [] BicisAux = new short [2];
                BicisAux[0] = (short) (ActEstacions[i][0] - Estacions.get(i).getDemanda());
                BicisAux[1] = (short) i;
                BicisRestantes.add(BicisAux);
            }

            //Ordenamos descendentemente las estaciones según las bicis restantes
            CustomComparator cust = new CustomComparator();
            BicisRestantes.sort(cust);
            Random myRandom=new Random();

            //Para la mitad más sobrante añadimos viajes a la mitad más carente con aleatoriedad
            for(int i=0; i<(BicisRestantes.size()/2); i++){
                int e0 = BicisRestantes.get(i)[1];
                if(ActEstacions[e0][1]>0){
                    //Estación aleatoria de la segunda mitad
                    int e1 = BicisRestantes.size()/2 + myRandom.nextInt(BicisRestantes.size()-(BicisRestantes.size()/2));
                    //Bicis aleatorias sin sobrepasar las que se pueden sacar
                    int b1 = 1+ myRandom.nextInt(ActEstacions[e0][1]);
                    if(anadirDespB(e0,e1,b1)) this.anadirDesp(e0, e1, b1);
                }
            }
        
        }
        
        if (inicioviajes == 2){
            ArrayList<short[]> BicisRestantes = new ArrayList<short[]> ();
            //Para cada estación, las bicis que sobran, la demanda no las requiere
            for(int i=0; i<Estacions.size(); i++){
                short [] BicisAux = new short [2];
                BicisAux[0] = (short) (ActEstacions[i][0] - Estacions.get(i).getDemanda());
                BicisAux[1] = (short) i;
                BicisRestantes.add(BicisAux);
            }

            //Ordenamos descendentemente las estaciones según las bicis restantes
            CustomComparator cust = new CustomComparator();
            BicisRestantes.sort(cust);
            
            for(int i=0; i<(BicisRestantes.size()); i++){
                int e0 = BicisRestantes.get(i)[1];
                if(anadirDespB(e0,-1,0)) this.anadirDesp(e0, -1, 0);
            }
            
        }
 
    }
    
    public static class CustomComparator implements Comparator<short[]> {
    @Override
        public int compare(short[] o1, short[] o2) {
            return o2[0]-o1[0];
        }
    }
    
    
    /* Constructor de copia */
    public PracticaBiciCiutat(ArrayList<short[]> Viaje, short[][] Act) {
        ViajeFurgo = new ArrayList<> ();
        for(int i=0; i<Viaje.size(); i++){
            short[] s = {Viaje.get(i)[0],Viaje.get(i)[1],Viaje.get(i)[2],Viaje.get(i)[3],Viaje.get(i)[4]};
            ViajeFurgo.add(s);
        }
        
        ActEstacions = new short[Estacions.size()][3];
        for(int i=0; i<Estacions.size(); i++){
            ActEstacions[i][0] = Act[i][0];
            ActEstacions[i][1] = Act[i][1];
            ActEstacions[i][2] = Act[i][2];
        }
    }
    
    public ArrayList<short[]> getViajeFurgo(){
        return (ViajeFurgo);
    }
    
    public short[][] getActEstacions(){
        return (ActEstacions);
    }
    
    
    //Operadores
    
    /* Pre: No hay ningun viaje que salga de la estacion e0,
    hay como minimo b1 bicicletas que se pueda sacar de e0, hay furgonetas
    que no hacen viajes, e0 != e1 y b1 <=30
    
    Post: Se ha creado un desplazamiento para otra furgoneta
    con origen en e0, primer destino en e1 (donde deja b1 bicicletas) 
    y sin segundo destino
    */
    public Boolean anadirDespB(int e0, int e1, int b1){
        if (ActEstacions[e0][2] == 0 && ActEstacions[e0][1]>=1 && ViajeFurgo.size()<nfurgonetas && e0 != e1 && ActEstacions[e0][1]>=b1 && b1<=30){
            return true;
        }
        return false;
    }
    
    public void anadirDesp(int e0, int e1, int b1){
        short[] s = {(short) e0,(short) e1,-1,(short)b1,0}; //Origen del viaje es e0, Destino del viaje es e1
        //No hay segundo destino, Se deja una bici en e1, //No hay segundo dropoff
        ViajeFurgo.add(s);
        ActEstacions[e0][0]-=b1; //Habrá b1 bicis menos en el origen
        ActEstacions[e0][1]-=b1; //Se podra mover b1 bicis menos en el origen
        ActEstacions[e0][2]=1; //Pasa una furgoneta a recoger bicis de e0
        if (e1 != -1) ActEstacions[e1][0]+=b1; //Habra b1 bicis mas en el destino
    }
    
    /* Pre: Existe la furgoneta f1, no tiene primer destino, e1 es diferente al origen de f1, en el origen del viaje de f1 como mínimo tantas bicis como se quieren añadir
    No se pueden añadir más de 30 bicis.
    */
    
    public Boolean anadirDest1B (int f1, int e1, int b1){
        if (f1 < ViajeFurgo.size() && ViajeFurgo.get(f1)[1] == -1 && ViajeFurgo.get(f1)[0] != e1 && ActEstacions[ViajeFurgo.get(f1)[0]][1] >= b1 && b1 <= 30){
            return true;
            
        }
        return false;
    }
    
    public void anadirDest1(int f1, int e1, int b1){
        ViajeFurgo.get(f1)[1] = (short)e1;
        ViajeFurgo.get(f1)[3] = (short)b1;
        ActEstacions[ViajeFurgo.get(f1)[0]][0]-=b1; //Habra b1 bicis menos en el origen
        ActEstacions[ViajeFurgo.get(f1)[0]][1]-=b1; //Se podran mover b1 bicis menos en el origen
        ActEstacions[ViajeFurgo.get(f1)[1]][0]+=b1; //Habra b1 bicis mas en el destino
    }
    
    
    public Boolean anadirDesp2B(int f1, int e2, int b2){
        if (f1 < ViajeFurgo.size() && ViajeFurgo.get(f1)[1] != -1 && ViajeFurgo.get(f1)[2] == -1 && ViajeFurgo.get(f1)[0] != e2 && ViajeFurgo.get(f1)[1] != e2 && ViajeFurgo.get(f1)[3] + b2 <= 30 && ActEstacions[ViajeFurgo.get(f1)[0]][1] >= b2){
            return true;
            // La furgoneta hace un viaje
            // f1 tiene un primer destino
            // No había segunda estacion
            // e2 no es estacion origen
            // e2 no es primer destino
            // No habrá más de 30 bicis en la furgoneta
            // En el origen se puede coger b2 bicis más
        }
        return false;
    }
    
    
    public void anadirDesp2(int f1, int e2, int b2){
        ViajeFurgo.get(f1)[2] = (short)e2;
        ViajeFurgo.get(f1)[4] = (short)b2;
        ActEstacions[ViajeFurgo.get(f1)[0]][0]-=b2; //Habra b2 bicis menos en el origen
        ActEstacions[ViajeFurgo.get(f1)[0]][1]-=b2; //Se podran mover b2 bicis menos en el origen
        ActEstacions[ViajeFurgo.get(f1)[2]][0]+=b2; //Habra b2 bicis mas en el destino
    }
    
    
    /* Pre: La furgoneta f1 hace un viaje y a una estación distinta a e1, al origen y al posible segundo destino
    
    Post: Se ha cambiado el destino del viaje de f1 al destino e1
    */
    public Boolean modifEstDestB(int f1, int e1){
        if ((int)f1<ViajeFurgo.size() && ViajeFurgo.get(f1)[1] != -1 && ViajeFurgo.get(f1)[1] !=e1 && ViajeFurgo.get(f1)[0] !=e1 && ViajeFurgo.get(f1)[2] !=e1){
            return true;
        }
        return false;
    }
    
    public void modifEstDest(int f1, int e1){
        short olde1 = ViajeFurgo.get(f1)[1]; //Guardamos el destino anterior
        ViajeFurgo.get(f1)[1] = (short) e1; //Cambiamos el primer destino
        ActEstacions[olde1][0] -= ViajeFurgo.get(f1)[3]; //Las bicis dejadas en olde1, ya no seran dejadas alli
        ActEstacions[e1][0] += ViajeFurgo.get(f1)[3]; //Las bicis seran dejadas en e1
    }
    
    
    /* Pre: La furgoneta f1 hace un viaje y con segundo destino a una estación distinta a e2, al primer destino y al origen
    
    Post: Se ha cambiado el segundo destino del viaje de f1 al destino e2
    */
    public Boolean modifEstDest2B(int f1, int e2){
        if ((int)f1<ViajeFurgo.size() && ViajeFurgo.get(f1)[2] != -1 && ViajeFurgo.get(f1)[2] !=e2 && ViajeFurgo.get(f1)[1] !=e2 && ViajeFurgo.get(f1)[0] !=e2 ){
            return true;
        }
        return false;
    }
    
    public void modifEstDest2(int f1, int e2){
        short olde2 = ViajeFurgo.get(f1)[2]; //Guardamos el destino anterior
        ViajeFurgo.get(f1)[2] = (short) e2; //Cambiamos el segundo destino
        ActEstacions[olde2][0] -= ViajeFurgo.get(f1)[4]; //Las bicis dejadas en olde2, ya no seran dejadas alli
        ActEstacions[e2][0] += ViajeFurgo.get(f1)[4]; //Las bicis seran dejadas en e1
    }
    
    
    /* Pre: La furgoneta f1 hace un viaje con primer destino. El orgien tiene como mínimo tantas
    bicicletas libres como las que se van a añadir y dropoff1 + dropoff2 no puede sumar más de 30
    
    Post: Se han añadido b1 bicis al primer dropoff del viaje de la furgoneta f1
    */
    public Boolean anadirBicis1B(int f1, int b1){
        if ((int)f1<ViajeFurgo.size() && ViajeFurgo.get(f1)[1] != -1 && ActEstacions[ViajeFurgo.get(f1)[0]][1]>=b1 && (b1+ViajeFurgo.get(f1)[3] + ViajeFurgo.get(f1)[4])<=30){
            return true;
        }
        return false;
    }
    
    public void anadirBicis1(int f1, int b1){
        ViajeFurgo.get(f1)[3] += b1; //Se dejan b1 bicis más en el primer destino
        ActEstacions[ViajeFurgo.get(f1)[0]][0] -= b1; //Habra b1 bicis menos en el origen del viaje
        ActEstacions[ViajeFurgo.get(f1)[0]][1] -= b1; //Habra b1 bicis menos que se puedan sacar del origen
        ActEstacions[ViajeFurgo.get(f1)[1]][0] += b1; //Habrá b1 bicis más en el destino del viaje
    }
    
    /* Pre: La furgoneta f1 hace un viaje con segundo destino. El orgien tiene como mínimo tantas
    bicicletas libres como las que se van a añadir y dropoff1 + dropoff2 no puede sumar más de 30
    
    Post: Se han añadido b1 bicis al segundo dropoff del viaje de la furgoneta f1
    */
    public Boolean anadirBicis2B(int f1, int b1){
        if ((int)f1<ViajeFurgo.size() && ViajeFurgo.get(f1)[2] != -1 && ActEstacions[ViajeFurgo.get(f1)[0]][1]>=b1 && (b1+ViajeFurgo.get(f1)[3] + ViajeFurgo.get(f1)[4])<=30){
            return true;
        }
        return false;
    }
    
    public void anadirBicis2(int f1, int b1){
        ViajeFurgo.get(f1)[4] += b1; //Se dejan b1 bicis más en el primer destino
        ActEstacions[ViajeFurgo.get(f1)[0]][0] -= b1; //Habra b1 bicis menos en el origen del viaje
        ActEstacions[ViajeFurgo.get(f1)[0]][1] -= b1; //Habra b1 bicis menos que se puedan sacar del origen
        ActEstacions[ViajeFurgo.get(f1)[2]][0] += b1; //Habrá b1 bicis más en el destino del viaje
    }
    
    public Boolean quitarBiciB(int f1, int b1){
        if (f1 < ViajeFurgo.size() && b1 < ViajeFurgo.get(f1)[3]){
            return true;
            // Hace un viaje
            // No puedes quitar mas bicis de las que ibas a dejar en e1
        }
        return false;
    }
    
    public void quitarBici(int f1, int b1){
        ViajeFurgo.get(f1)[3] -= (short)b1; // quitamos b1 bicis del primer dropoff
        ActEstacions[ViajeFurgo.get(f1)[0]][0] += b1; //Habra b1 bicis mas en el origen
        ActEstacions[ViajeFurgo.get(f1)[0]][1] += b1; //Se podrá mover b1 bicis más en el origen
        ActEstacions[ViajeFurgo.get(f1)[1]][0] -= b1; //Habrá una b1 bicis menos en el destino 1
    }
    
    
    
    public Boolean quitarBici2B(int f1, int b2){
        if (f1 < ViajeFurgo.size() && ViajeFurgo.get(f1)[2] == -1 && b2 < ViajeFurgo.get(f1)[4]){
            return true;
            // Va a e2
            // Hace un viaje
            // No puedes quitar mas bicis de las que ibas a dejar en e2
        }
        return false;
    }
    
    public void quitarBici2(int f1, int b2){
        ViajeFurgo.get(f1)[4] -= (short)b2; // quitamos b2 bicis del primer dropoff
        ActEstacions[ViajeFurgo.get(f1)[0]][0] += b2; //Habrá una b2 bicis más en el origen
        ActEstacions[ViajeFurgo.get(f1)[0]][1] += b2; //Se podrá mover b2 bicis mas en el origen
        ActEstacions[ViajeFurgo.get(f1)[2]][0] -= b2; //Habrá una b2 bicis menos en el destino 2
    }
    
    public Boolean quitarDespB (int f1){
        if (f1 < ViajeFurgo.size()) return true;
        return false;
    }
    
    public void quitarDesp (int f1){
        
        //En el origen estarán y se podrán mover las bicis que se transportaban
        ActEstacions[ViajeFurgo.get(f1)[0]][0] +=  ViajeFurgo.get(f1)[3] + ViajeFurgo.get(f1)[4];
        ActEstacions[ViajeFurgo.get(f1)[0]][1] +=  ViajeFurgo.get(f1)[3] + ViajeFurgo.get(f1)[4];
        //En el primer destino no estarán las que llegaban
        ActEstacions[ViajeFurgo.get(f1)[1]][0] -=  ViajeFurgo.get(f1)[3];
        //En el segundo destino no estarán las que llegaban
        if (ViajeFurgo.get(f1)[2] != -1) ActEstacions[ViajeFurgo.get(f1)[2]][0] -=  ViajeFurgo.get(f1)[4];
        ViajeFurgo.remove(f1);
        
    }
    
    
    //Distancia en Km entre dos estaciones
    
    public static int distancia(short e0, short e1){
        int distX = Estacions.get(e0).getCoordX() - Estacions.get(e1).getCoordX();
        if (distX <0) distX = -distX;
        
        int distY = Estacions.get(e0).getCoordY() - Estacions.get(e1).getCoordY();
        if (distY <0) distY = -distY;
        
        return (distX + distY)/1000;
    }
    
    public static int distanciam(short e0, short e1){
        int distX = Estacions.get(e0).getCoordX() - Estacions.get(e1).getCoordX();
        if (distX <0) distX = -distX;
        
        int distY = Estacions.get(e0).getCoordY() - Estacions.get(e1).getCoordY();
        if (distY <0) distY = -distY;
        
        return (distX + distY);
    }
    
    //Función heurística que tiene en cuenta solo los traslados de bicicletas
    
    public int heuristic1(){
        int total =0;
        //Control de traslados
        for(int i=0; i<Estacions.size(); i++){
            int benefici=0; //Beneficio de esa estación
            int bicishabra =ActEstacions[i][0];
            int bicisprev = Estacions.get(i).getNumBicicletasNext();
            int bicisdemanda = Estacions.get(i).getDemanda();
            
            //No se cuentan las bicis que se traen de más de la demanda
            if (bicishabra>bicisdemanda) bicishabra = bicisdemanda;
            
            //La prevision no sirve de baremo si es mayor a la demanda
            if (bicisprev>bicisdemanda) bicisprev = bicisdemanda;
            
            benefici = bicishabra-bicisprev;
            
            total +=benefici;
        }
        return -total;
    }
    
    //Función heurística que tiene en cuenta los traslados y los kilometros recorridos
    
    public int heuristic2(){
        int total =0;
        
         //Control de traslados
        for(int i=0; i<Estacions.size(); i++){
            int benefici=0; //Beneficio de esa estación
            int bicishabra =ActEstacions[i][0];
            int bicisprev = Estacions.get(i).getNumBicicletasNext();
            int bicisdemanda = Estacions.get(i).getDemanda();
            
            //No se cuentan las bicis que se traen de más de la demanda
            if (bicishabra>bicisdemanda) bicishabra = bicisdemanda;
            
            //La prevision no sirve de baremo si es mayor a la demanda
            if (bicisprev>bicisdemanda) bicisprev = bicisdemanda;
            
            benefici = bicishabra-bicisprev;
            
            total +=benefici;
        }
        
        //Control de kilometros recorridos
        int costetotal=0;
        for(int i=0; i<ViajeFurgo.size(); i++){
            int coste=0;
            int bicistransportadas = ViajeFurgo.get(i)[3]+ ViajeFurgo.get(i)[4]; 
            //Seguimos la formula indicada
            if (ViajeFurgo.get(i)[1] != -1){
                coste = PracticaBiciCiutat.distancia(ViajeFurgo.get(i)[0],ViajeFurgo.get(i)[1]) * ((bicistransportadas + 9)/10);
                if(ViajeFurgo.get(i)[2] != -1){ //Observamos el posible segundo viaje
                    bicistransportadas = ViajeFurgo.get(i)[4]; //Solo tenemos en cuenta las bicis del segundo dropoff
                    coste += PracticaBiciCiutat.distancia(ViajeFurgo.get(i)[1],ViajeFurgo.get(i)[2]) * ((bicistransportadas + 9)/10);
                }
            }
            costetotal +=coste;
        }
        
        return (costetotal-total);
    }
    
    public int heuristic3(){
        int viajes=0;
        for(int i=0; i<ViajeFurgo.size(); i++){
            //Seguimos la formula indicada
            if (ViajeFurgo.get(i)[1] != -1){
                viajes++;
                if(ViajeFurgo.get(i)[2] != -1){ //Observamos el posible segundo viaje
                    viajes++;
                }
            }
        }
        
        return (this.distanciarec()-viajes*1000);
    }
    
    public int distanciarec(){
        int costetotal=0;
        for(int i=0; i<ViajeFurgo.size(); i++){
            int coste=0;
            if(ViajeFurgo.get(i)[1] != -1){
                coste = PracticaBiciCiutat.distanciam(ViajeFurgo.get(i)[0],ViajeFurgo.get(i)[1]);
                if(ViajeFurgo.get(i)[2] != -1){ //Observamos el posible segundo viaje
                    coste += PracticaBiciCiutat.distanciam(ViajeFurgo.get(i)[1],ViajeFurgo.get(i)[2]);
                } 
            }
            costetotal +=coste;
        }
        return costetotal;
    }
    
    //Escribir estado
    public void printEstado(){
        System.out.println("El estado es el siguiente:");
        for(int i=0; i<ViajeFurgo.size();i++){
            System.out.println("Origen: "+ViajeFurgo.get(i)[0]+", e1: "+ViajeFurgo.get(i)[1]+", e2: "+ViajeFurgo.get(i)[2]+", Bicis dejadas en e1:"+ViajeFurgo.get(i)[3]+", Bicis dejadas en e2: "+ViajeFurgo.get(i)[4]); 
        }
        System.out.println(" ");
        for(int i=0; i<ActEstacions.length;i++){
            System.out.println("Estación "+i+": Habrá: "+ActEstacions[i][0]+", Demanda: "+PracticaBiciCiutat.Estacions.get(i).getDemanda()+", Se pueden mover: "+ActEstacions[i][1]); 
        }
        System.out.println(" ");
        System.out.println("Beneficio: "+(-this.heuristic2()));
        System.out.println("Distancia rec: "+this.distanciarec());
        System.out.println(" ");
        
    }
    
 
}

