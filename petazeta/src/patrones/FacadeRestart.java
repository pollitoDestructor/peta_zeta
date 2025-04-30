package patrones;

import modelo.GestorFinalVisual;
import modelo.GestorMenuPrincipal;

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
		GestorMenuPrincipal.reiniciarMenuPrincipal();
		menuPpl = GestorMenuPrincipal.getMenu();
		finV.crearMenuPplVisual();
		menuPpl.opcionesMenu("Inicio");
	}
}
