package modelo;

import java.util.Timer;
import java.util.TimerTask;

public class BombaCruz extends Bomba {

    public BombaCruz(int a, int b) {
        super(a, b); // Llama al constructor de la clase base Bomba
    }
    public void rodar(String pDir){
        Timer timerRodar = new Timer();
        TimerTask rodado = new TimerTask() {
            @Override
            public void run() {
                Tablero.getTablero().moverBomba(pDir,getCoordX(),getCoordY());
            }
        };
        timerRodar.schedule(rodado,200);
    }
}
