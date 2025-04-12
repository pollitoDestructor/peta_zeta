package petazeta;

import modelo.GestorMenuPrincipal;
import viewController.MenuPrincipalVisual;

public class Main {

	public static void main(String[] args){

		GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
		MenuPrincipalVisual menuVisual = new MenuPrincipalVisual(); //TODO quitar
		menu.opcionesMenu("Inicio");
		System.out.println("Inicia juego");

	}

}