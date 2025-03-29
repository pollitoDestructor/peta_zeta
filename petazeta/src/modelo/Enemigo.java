package modelo;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Enemigo extends Observable {
    protected int posX;
    protected int posY;
    private Timer timer;
    private static final int INTERVALO_MOVIMIENTO = 2000; // 3 segundo en milisegundos

    protected Enemigo(int pX, int pY) {
        this.posX = pX;
        this.posY = pY;
        iniciarTimerMovimiento();
    }

    private void iniciarTimerMovimiento() {
        detenerTimer(); // Detener timer existente si lo hay
        timer = new Timer(true); // Timer como daemon
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moverAleatoriamente();
            }
        }, INTERVALO_MOVIMIENTO, INTERVALO_MOVIMIENTO);
    }

    public void moverAleatoriamente() {
        // Generar número aleatorio entre 0 y 3 para las 4 direcciones
        int direccion = (int)(Math.random() * 4);
        int movX = 0, movY = 0;

        switch (direccion) {
            case 0: // Arriba
                movY = -1;
                break;
            case 1: // Abajo
                movY = 1;
                break;
            case 2: // Izquierda
                movX = -1;
                break;
            case 3: // Derecha
                movX = 1;
                break;
        }
        mover(movX, movY,tipoEnemigo());
    }

    //movimiento del enemigo
    public void mover(int x, int y, String pType) {
        if (Tablero.getTablero().casillaDisponible(posX+x,posY+y, pType)) {
            setChanged();
            notifyObservers(new Object[] {"mover",posX,posY,posX+x,posY+y,tipoEnemigo()}); //le manda la pos
            posX = posX + x;
            posY = posY + y;
            System.out.println(tipoEnemigo() + " movido a (" + posX + "," + posY + ")");
        }
    }
    private void detenerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void destruir() {
        detenerTimer();
    }

    public boolean estaEnCasilla(int pX, int pY) {
        boolean esta = false;
        if(this.posX==pX && this.posY==pY) {
            esta = true;
        }
        return esta;
    }

    public String tipoEnemigo()
    {
        return this.getClass().getSimpleName();
    }

}