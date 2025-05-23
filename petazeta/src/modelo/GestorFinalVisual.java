package modelo;

import java.util.Map;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class GestorFinalVisual extends Observable {
	
	private Timer timer = null; //Timer para color
	private Timer timerLetras = null; //Timer para mensaje "Has ganado!"
	
	private boolean estadoFin; //True: ganado
	private boolean asc=false; //True:incrementa
	private int r=200,g=200,b=200;
	
	private String mensaje; //El mensaje a imprimir, en <<constructor>>
	private String montaje = ""; //Donde se a�ade letra a letra
	private int numLetra = 0; //Para el charAt()
	
	private static GestorFinalVisual miFinal;
	
	//Constructora
	private GestorFinalVisual() {}
	
	public static GestorFinalVisual getFinal() {
		if (miFinal == null){
			miFinal = new GestorFinalVisual();	 
		}
		return miFinal;
	}
	
	//Establece parametros y notifica al vista
	public void setFinal(boolean pEstadoPartida) {
		estadoFin = pEstadoPartida;

		numLetra = 0;
		montaje = "";

		if(estadoFin) {
			this.actualizarRankingVisual();
			mensaje = "HAS GANADO!!!"; //Elegimos mensaje
		}
		else mensaje = "HAS PERDIDO!!!";
		
		setChanged();
		notifyObservers(new Object[] {3,estadoFin});
		
		//Timer para actualizacion del color de "Click to return"
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				actualizarRGB();
			}		
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 50); //Cada 50ms
		
		//Timer para actualizacion del mensaje de "Has ganado!"
		TimerTask timerTask2 = new TimerTask() {
			@Override
			public void run() {
				actualizarLetras();
			}		
		};
		timerLetras = new Timer();
		timerLetras.scheduleAtFixedRate(timerTask2, 0, 250); //Cada 500ms
		
	}
	
	private void actualizarRankingVisual() {
		Ranking ranking = Ranking.getRanking();
		int[] colores = {
				255, 215, 0,     // Oro
				192, 192, 192,   // Plata
				205, 127, 50     // Bronce
		};
		
		int rankSize = ranking.obtenerRankingOrdenado().size();
		setChanged();
		notifyObservers(new Object[] {5,rankSize});
		
		int fila = 1;
		for (Map.Entry<String, Integer> entrada : ranking.obtenerRankingOrdenado().entrySet()) {
			String jugador = entrada.getKey();
			int puntos = entrada.getValue();

			String texto = String.format("%-4d %-6s %7d", fila, jugador.toUpperCase(), puntos);
			
			setChanged();
			notifyObservers(new Object[] {6,texto});
			
			if (fila <= 3) {
				setChanged();
				notifyObservers(new Object[] {7,colores,fila});
			}
			setChanged();
			notifyObservers(new Object[] {8,fila,rankSize});
			fila++;
		}
		setChanged();
		notifyObservers(new Object[] {9});
	}
	
	private void actualizarLetras()
	{
		if (numLetra < mensaje.length())
		{
			montaje = montaje + mensaje.charAt(numLetra); //Va montando la frase
            numLetra++;
            setChanged();
			notifyObservers(new Object[] {1,montaje}); //1: update de Letras
			//System.out.println(montaje);
        } 
		else 
        {
            timerLetras.cancel();
            System.out.println("Detenido");
        }
	}
	
	private void actualizarRGB() {
		setChanged();
		notifyObservers(new Object[] {0,r,g,b}); //0: update de Color
		if(asc) //Incrementa sus valores
		{
			r=r+5;
			g=g+5;
			b=b+5;
		}
		else //Decrementa
		{
			r=r-5;
			g=g-5;
			b=b-5;
		}
		//System.out.println(r+" "+g+" "+b);
		if(r==255 || r==120) //Una vez alcanza un l�mite, invierte
		{
			asc=!asc;
		}
		
	}
	
	public void detenerTimer()
	{
		if (timer != null) {
            timer.cancel();  
            timer.purge();
        }
	}

	private void volverMenu(){
		setChanged();
		notifyObservers(new Object[] {2});
	}
	
	public void crearMenuPplVisual() { 
		setChanged();
		notifyObservers(new Object[] {4});
		volverMenu();
	}
}
