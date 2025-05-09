package patrones;

import modelo.GestorFinalVisual;
import modelo.GestorMenuPrincipal;
import modelo.Jugador;
import modelo.Tablero;

public class FacadeRestart {
	private static FacadeRestart miFacade;
	private GestorMenuPrincipal menuPpl = GestorMenuPrincipal.getMenu();
	private GestorFinalVisual finV = GestorFinalVisual.getFinal();
	
	private FacadeRestart() {}
	
	public static FacadeRestart getFacadeRestart() {
		if(miFacade == null) {
			miFacade = new FacadeRestart();
		}
		return miFacade;
	}
	
	public void reiniciar() { 
		Tablero.reiniciarTablero(); 
		Jugador.reiniciarJugador(); 
		GestorMenuPrincipal.reiniciarMenuPrincipal();
		menuPpl = GestorMenuPrincipal.getMenu();
		finV.crearMenuPplVisual();
		menuPpl.opcionesMenu("Inicio");
	}
}
