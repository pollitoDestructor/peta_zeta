package modelo; 

import java.util.Timer;
import java.util.TimerTask;

public abstract class Bomba extends Casilla {
    private Timer timer = null;
    private static final int PERIODO = 3; //Periodo de 3 segundos
    private Tablero tablero; //Referencia al tablero (constructora)

    protected Bomba(int a, int b) {
        super(a, b);  
        setOcupado(true);
        this.tablero = Tablero.getTablero(); //Puntero a Tablero (por comodidad)
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                explotar(); //Pasados 3 segundos, explota
            }       
        };
        timer = new Timer();
        timer.schedule(timerTask, PERIODO * 1000); // Explota despues de 3 segundos
    }
    
    protected void explotar() {
        destruir(); // Aqui destruimos la bomba
        
        tablero.detonarBomba(getCoordX(),getCoordY()); //Pasa coords y nombre(para herencia)
    }
    
    public void destruir() //Destruye la Bomba
	{
		System.out.println("Bomba "+getCoordX()+", "+getCoordY()+" destruida."); 
		timer.cancel();
		timer.purge();
		
	}
    
}