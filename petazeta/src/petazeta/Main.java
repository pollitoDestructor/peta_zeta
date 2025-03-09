package petazeta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class Main { 

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) throws Exception {
		
		Tablero tablero = Tablero.getTablero();
		TableroVisual f = new TableroVisual();
		
		tablero.ponerBloques();
		
		Jugador jugador = Jugador.getJugador("blanco");
		jugador.inicio(); //para imprimir el primer Bomberman nada mas
		
		Gestor.getGestor(f,jugador);
		//tablero.printMap();
		System.out.println("El jugador esta en: "+jugador.getPosX()+" "+jugador.getPosY());
		f.setVisible(true);
		
		
	}

}