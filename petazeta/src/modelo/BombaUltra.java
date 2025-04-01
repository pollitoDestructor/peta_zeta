package modelo;

import java.util.Timer;
import java.util.TimerTask;

public class BombaUltra extends Bomba {
    private static final int PERIODO = 3; // Explota después de 3 segundos

    public BombaUltra(int a, int b) {
        super(a, b); // Llama al constructor de la clase base Bomba
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                explotar(); 
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, PERIODO * 1000); //3 segundos
    }
    
    protected void explotar() {
        destruir(); // Destruye la bomba 
        // Llama al método de Tablero 
        Tablero.getTablero().detonarBomba(coordX, coordY, tipoCasilla());
    }
}
