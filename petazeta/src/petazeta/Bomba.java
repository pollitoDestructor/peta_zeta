package petazeta;

import java.util.Timer;
import java.util.TimerTask;

public class Bomba extends Casilla {
    private Timer timer = null;
    private static final int PERIODO = 3; // Explota cada 3 segundos
    private Tablero tablero; // Referencia al tablero para acceder a las casillas ,
    						//esto es para despues para el metodo destruir linea

    public Bomba(int a, int b, boolean c, Tablero tablero) {
        super(a, b, c); 
        this.tablero = tablero;
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                explotar();
            }       
        };
        timer = new Timer();
        timer.schedule(timerTask, PERIODO * 1000); // Explota después de 3 segundos
    }
    
    public void explotar() {
        // Aqui destruimos la bomba xd
        destruir();
        
        //Aqui se situa en los bloques de alrededor de la bomba 
        destruirLinea(1, 0);  // Derecha (aumenta la x)
        destruirLinea(-1, 0); // Izquierda (dism la x)
        destruirLinea(0, 1);  // Abajo (dismin la y)
        destruirLinea(0, -1); // Arriba (aumenta la y)
    }
    
    private void destruirLinea(int dirX, int dirY) {
        int x = coordX;
        int y = coordY;
        // Esto se puede hacer de otra manera es decir hacer los calculos desde tablero
        while (tablero.esCasillaDestructible(x, y)) { // FALTA ESTE METODO
            tablero.obtenerCasilla(x, y).destruir(); // FALTA ESTE METODO
            x += dirX;
            y += dirY;
        }
    }
}
