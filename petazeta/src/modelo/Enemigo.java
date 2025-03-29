package modelo;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Enemigo extends Observable {
    private int posX;
    private int posY;
    private Timer timer = null;
    private static final int PERIODO = 1; //Periodo de 1 segundo

    protected Enemigo(int pX, int pY) {
        posX = pX;
        posY = pY;
        iniciarTimer();
    }

    public void iniciarTimer() {
        detenerTimer();

        timer = new Timer(true); // Timer como daemon (no evita que el programa termine)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Movimiento aleatorio (ejemplo: -1, 0, o +1 en X/Y)
                int movX = (int) (Math.random() * 3) - 1; // Valores: -1, 0, 1
                int movY = (int) (Math.random() * 3) - 1;
                mover(movX, movY, tipoEnemigo());
            }
        }, PERIODO, PERIODO);
    }

    public void detenerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Método para limpieza cuando el enemigo se elimina
    public void destruir() {
        detenerTimer();
    }

    public abstract boolean estaEnCasilla(int pX, int pY);
    public abstract void mover(int x, int y,String tipo);
    public abstract String tipoEnemigo();
    protected int getPosX() {
        return posX;
    }

    protected int getPosY() {
        return posY;
    }

    protected void setPosX(int posX) {
        this.posX = posX;
    }

    protected void setPosY(int posY) {
        this.posY = posY;
    }
}
