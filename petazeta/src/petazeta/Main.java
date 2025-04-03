package petazeta;

import modelo.GestorMenuPrincipal;

public class Main {

	public static void main(String[] args){

		GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
		menu.opcionesMenu("Iniciar");
		System.out.println("Inicia juego");

	}

}