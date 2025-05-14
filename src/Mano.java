import java.util.ArrayList;

public class Mano {
    private ArrayList<Carta> cartas;

    public Mano(ArrayList<Carta> cartas){
        this.cartas = cartas;

    }
    //ordena las cartas de mayor a menor
    public void ordenar(ArrayList<Carta> cartas){
        cartas.sort((cartaA, cartaB) -> Integer.compare(
            cartaB.getCategoria(), cartaA.getCategoria()
            ));
    }

    public int cartaAlta(){
        int puntuacion = 0;
        Carta cartaAlta= cartas.get(0);

        for(int i =1; i<cartas.size();i++){
            Carta carta = cartas.get(i);
            if(cartaAlta.getCategoria()< carta.getCategoria()){
                cartaAlta = carta;
            }
        }
        puntuacion = cartaAlta.getCategoria();
        if(cartaAlta.getCategoria()==1){
            puntuacion += 13;
        }
        return puntuacion;
    }

    public int buscarPar(){
        int puntuacion =0;
        for(int i =0; i<cartas.size()-1;i++){
            Carta cartaA = cartas.get(i);
            for(int j =1; j<cartas.size();j++){
                Carta cartaB = cartas.get(j);
                if(cartaA.getCategoria() == cartaB.getCategoria()){
                    puntuacion = cartaA.getCategoria()+cartaB.getCategoria()+100;
                    if(cartaA.getCategoria()==1){
                        puntuacion+=26;
                    }
                }
            }
        }

        return puntuacion;
    }

    public int buscarDoblePar(){
        int puntuacion=0;
        int paresEncontrados=0;
        ordenar(cartas);

        for(int i = 0; i<cartas.size()-1;i++){
            Carta cartaA = cartas.get(i);
            Carta cartaB = cartas.get(i+1);
            if(cartaA.getCategoria() == cartaB.getCategoria()){
                puntuacion += cartaA.getCategoria() + cartaB.getCategoria();
                if(cartaA.getCategoria()==1){
                    puntuacion+=26;
                }
                i++;
                paresEncontrados++;
            }
        }

        if(paresEncontrados == 2){
            puntuacion += 200;
        }else{
            puntuacion=0;
        }
        
        return puntuacion;
    }


    public int buscarTrio(){
        int puntuacion =0;
        for(int i=0; i<cartas.size();i++){
            Carta cartaA = cartas.get(i);
            int hayTrio=0;
            for(int j =0; j<cartas.size();j++){
                Carta cartaB = cartas.get(j);
                if(cartaB.getCategoria() == cartaA.getCategoria()){
                    hayTrio++;
                }
            }
            if(hayTrio == 3){
                puntuacion = cartaA.getCategoria() +300;
                if(cartaA.getCategoria()==1){
                    puntuacion+=13;
                }
            }
        }
        return puntuacion;
    }

    public int buscarEscalera(){
        int puntuacion=0;
        int contadorEscalera=0;
        ArrayList<Carta> cartasConAs = new ArrayList<>(cartas);
        boolean hayAs=false;
        for(int i =0; i<cartas.size(); i++){
            Carta cartaA = cartas.get(i);
            if(cartaA.getCategoria() == 1){
                Carta carta = new Carta(14,cartaA.getPalo());
                cartasConAs.add(carta);
                hayAs=true;
            }
        }
        ordenar(cartasConAs);
        for(int i=0; i<cartasConAs.size()-1;i++){
                Carta cartaA = cartasConAs.get(i);
                Carta cartaB = cartasConAs.get(i+1);
                if(cartaA.getCategoria() == cartaB.getCategoria()+1){
                    contadorEscalera++;
                }
            }
        if(contadorEscalera == 4){
            for(int i =0; i<cartas.size();i++){
                puntuacion+=cartas.get(i).getCategoria();
            }
            puntuacion += 400;
            if(hayAs && cartas.get(0).getCategoria()==13){
                puntuacion+=13;
            }
        }
        return puntuacion;
    }

    public int buscarColor(){
        int puntuacion =0;
        boolean hayColor=true;
        Carta carta = cartas.get(0);
        for(int i =0; i<cartas.size();i++){
            Carta cartaA = cartas.get(i);
            if(!cartaA.getPalo().equals(carta.getPalo())){
                hayColor=false;
            }
        }
        if(hayColor){
            puntuacion+=500;
            for(int i =0; i<cartas.size();i++){
                puntuacion+=cartas.get(i).getCategoria();
            }
            if(cartas.get(cartas.size()-1).getCategoria()==1){
                puntuacion+=13;
            }
        }
        return puntuacion;
    }

    public int buscarFull(){
        ordenar(cartas);
        int puntuacion=0;
        boolean hayPar=false;
        boolean hayTrio=false;
        int categoriaTrio =0;
        Carta carta = null;
        if(buscarTrio()!=0){
            categoriaTrio = buscarTrio()-300;
            if(categoriaTrio == 14){
                categoriaTrio-=13;
            }
            hayTrio=true;
        }

        if(hayTrio){
            for(int i =0; i<cartas.size();i++){
                Carta cartaA = cartas.get(i);
                if(carta == null && cartaA.getCategoria()!=categoriaTrio){
                    carta = cartaA;
                }else if(carta != null && cartaA.getCategoria() == carta.getCategoria()){
                    hayPar =true;
                }
            }
        }
        if(hayTrio && hayPar){
            puntuacion += categoriaTrio +600;
            if(categoriaTrio ==1){
                puntuacion += 13;
            }
        }

        return puntuacion;
    }

    public int buscarPoker(){
        int puntuacion =0;
        for(int i=0; i<cartas.size();i++){
            Carta cartaA = cartas.get(i);
            int hayPoker=0;
            for(int j =0; j<cartas.size();j++){
                Carta cartaB = cartas.get(j);
                if(cartaB.getCategoria() == cartaA.getCategoria()){
                    hayPoker++;
                }
            }
            if(hayPoker == 4){
                puntuacion = cartaA.getCategoria() +700;
                if(cartaA.getCategoria()==1){
                    puntuacion+=13;
                }
            }
        }
        return puntuacion;
    }

    public int buscarEscaleraDeColor(){
        int puntuacion=0;

        if(buscarEscalera()!=0 && buscarColor() != 0){
            puntuacion += buscarColor() +300;
        }
        return puntuacion;
    }


    public int buscarEscaleraReal(){
        int puntuacion =0;
        ordenar(cartas);
        boolean esEscaleraReal=true;
        if(buscarEscalera()!=0 && buscarColor() != 0){
            if(cartas.get(cartas.size()-1).getCategoria()==1){
                for(int i =0; i<cartas.size()-1;i++){
                    Carta cartaA = cartas.get(i);
                    Carta cartaB = cartas.get(i+1);
                    if(cartaA.getCategoria() != (cartaB.getCategoria()+1)){
                        esEscaleraReal=false;
                    }
                }
            }else{
                esEscaleraReal=false;
            }
        }

        if(esEscaleraReal){
            puntuacion+=buscarColor()+400;
        }
        return puntuacion;
    }
}
