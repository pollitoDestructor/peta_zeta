package petazeta;

import java.io.IOException;

import javax.sound.sampled.*;

import modelo.GestorMenuPrincipal;
import modelo.Musica;
import viewController.MenuPrincipalVisual;


public class Main {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		Musica.getMusica().ponerMusica();
		GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
		@SuppressWarnings("unused")
		MenuPrincipalVisual menuVisual = new MenuPrincipalVisual();
		menu.opcionesMenu("Inicio");
		System.out.println("Inicia juego");

	}

}