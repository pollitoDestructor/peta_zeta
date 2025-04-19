package patrones;

import modelo.GestorMenuPrincipal;
import viewController.FinalVisual;
import viewController.MenuPrincipalVisual;

public class FacadeRestart {
	private static FacadeRestart miFacade;
	private GestorMenuPrincipal menuPpl = GestorMenuPrincipal.getMenu();
	
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
		MenuPrincipalVisual menuVisual = new MenuPrincipalVisual(); //TODO arreglar
		menuPpl.opcionesMenu("Inicio");
	}
}
