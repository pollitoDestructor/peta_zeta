package petazeta;

import java.util.Timer;
import java.util.TimerTask;

public class Bomba extends Casilla
{
	private int numBombas;
	private Timer timer = null;
	private static final int PERIODO = 15;
	private int cont;
	
	public Bomba(int a, int b, boolean c){
		super(a,b,c); //si quitas esto da error, esta puesto para que se calle
		cont = PERIODO;
		//Cada segundo se ejectuta actualizarCont para actualizar el contador del semáforo
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
//				actualizarCont();
			}		
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	public void explotar() // metodo explotar
	{


	}
}
