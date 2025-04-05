package petazeta;

import modelo.GestorMenuPrincipal;
import viewController.menuPruebitas;

public class Main {

	public static void main(String[] args){

		GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
		menuPruebitas menuVisual = new menuPruebitas();
		menu.opcionesMenu("Inicio");
		System.out.println("Inicia juego");

	}

}