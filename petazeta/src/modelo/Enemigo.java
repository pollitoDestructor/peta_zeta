package modelo;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public abstract class Enemigo extends Observable {
    private int posX;
    private int posY;
    private boolean estaVivo;
    private Timer timer;
    private static final int INTERVALO_MOVIMIENTO = 2000; // 3 segundo en milisegundos

    protected Enemigo(int pX, int pY) {
        this.posX = pX;
        this.posY = pY;
        estaVivo = true;
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
        if (Tablero.getTablero().casillaDisponible(posX,posY,posX+x,posY+y, pType)) {
            if(!Tablero.getTablero().getCasilla(posX+x, posY+y).tipoCasilla().equals("Teletransporte")) {
                setChanged();
                notifyObservers(new Object[] {"mover",posX,posY,posX+x,posY+y,tipoEnemigo()}); //le manda la pos
                posX = posX + x;
                posY = posY + y;
            }
        }
    }
    private void detenerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void destruir() {
        estaVivo = false;
        detenerTimer();
        Tablero.getTablero().deleteEnemigo(this);
        if(Tablero.getTablero().getCasilla(posX,posY).tipoCasilla() != "Explosion")
        {
            setChanged();
            notifyObservers(new Object[] {"EnemigoDestruido",posX,posY,tipoEnemigo()});
        }
        System.out.println("Enemigo muerto");
    }

    public boolean estaVivo() {
        return estaVivo;
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
    
    public boolean estaEn(int pX, int pY) {
    	return posX == pX && posY == pY;
    }

}