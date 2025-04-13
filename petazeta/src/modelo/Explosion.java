package modelo;

import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends Casilla {

    private Timer timer = null;
    private static final int PERIODO = 2;
    
	public Explosion(int pX, int pY)
	{
		super(pX,pY);
		ocupado = false;
		iniciarTimer();
	     
	}
	
	 public void iniciarTimer() {
		 TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	            	System.out.println("Explotado");
	            	Tablero.getTablero().explosionTerminada(coordX,coordY);
	            	timer.cancel();
	            	timer.purge();
	            }       
	        };
	        if(timer != null) {
	        	timer.cancel();
            	timer.purge();
	        }
	        timer = new Timer();
	        timer.schedule(timerTask, PERIODO * 1000); // Termina despues de 2 segundos
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
}