package petazeta;

import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends Casilla {

    private Timer timer = null;
    private static final int PERIODO = 2;
    
	public Explosion(int pX, int pY)
	{
		super(pX,pY);
		ocupado = true;
		
	     TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	            	System.out.println("Explotado");
	            }       
	        };
	        timer = new Timer();
	        timer.schedule(timerTask, PERIODO * 1000); // Explota despues de 3 segundos
	}
	
}