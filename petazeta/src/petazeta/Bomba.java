package petazeta; 

import java.util.Timer;
import java.util.TimerTask;

public class Bomba extends Casilla {
    private Timer timer = null;
    private static final int PERIODO = 3; //Periodo de 3 segundos
    private Tablero tablero; //Referencia al tablero (constructora)

    public Bomba(int a, int b) {
        super(a, b);  
        ocupado = true;
        this.tablero = Tablero.getTablero(); //Puntero a Tablero (por comodidad)
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                explotar(); //Pasados 3 segundos, explota
                Jugador.getJugador().addBomba();
            }       
        };
        timer = new Timer();
        timer.schedule(timerTask, PERIODO * 1000); // Explota despues de 3 segundos
    }
    
    protected void explotar() {
        destruir(); // Aqui destruimos la bomba
        
        tablero.detonarBomba(coordX,coordY,tipoCasilla()); //Pasa coords y nombre(para herencia)
    }
    
    public void destruir() //Destruye la Bomba
	{
		System.out.println("Bomba "+coordX+", "+coordY+" destruida."); 
		timer.cancel();
	}
}
