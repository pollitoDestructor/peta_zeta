package petazeta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;


@SuppressWarnings("deprecation")
public class GestorFinalVisual extends Observable {
	
	private Timer timer = null; //Timer para color
	private Timer timerLetras = null; //Timer para mensaje "Has ganado!"
	
	private boolean estadoFin; //True: ganado
	private boolean asc=false; //True:incrementa
	private int r=200,g=200,b=200;
	
	private String mensaje; //El mensaje a imprimir, en <<constructor>>
	private String montaje = ""; //Donde se añade letra a letra
	private int numLetra = 0; //Para el charAt()
	
	private static GestorFinalVisual miFinal;

	//TODO main para pruebas
	/*public static void main(String[] args) {
        getFinal();
    }*/
	
	//Constructora
	public GestorFinalVisual(boolean pEstadoFin)
	{
		estadoFin = pEstadoFin;
		
		if(estadoFin) mensaje = "HAS GANADO!!!"; //Elegimos mensaje
		else mensaje = "HAS PERDIDO!!!";
		
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
	
	public static GestorFinalVisual getFinal(boolean pEstadoPartida) {
		if (miFinal == null){
			miFinal = new GestorFinalVisual(pEstadoPartida);	 //TODO
		}
		return miFinal;
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
		if(r==255 || r==120) //Una vez alcanza un límite, invierte
		{
			asc=!asc;
		}
		
	}
	
	public void detenerTimer()
	{
		if (timer != null) {
            timer.cancel();  
        }
		
		System.out.println("AQUÍ SE DETIENE EL MAIN!!!!!"); //TODO
        System.exit(0);
	}
}
