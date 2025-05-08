package petazeta;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import modelo.GestorMenuPrincipal;
import modelo.Musica;
import viewController.MenuPrincipalVisual;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		Musica.getMusica().ponerMusica();
		GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
		MenuPrincipalVisual menuVisual = new MenuPrincipalVisual();
		menu.opcionesMenu("Inicio");
		System.out.println("Inicia juego");

	}

}