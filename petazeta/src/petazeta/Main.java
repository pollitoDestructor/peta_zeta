package petazeta;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {

	public static void main(String[] args) {
		Jugador jugador = Jugador.getJugador();
		Tablero tablero = Tablero.getTablero();
		
		tablero.printMap();
		System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());
		prueba.main(args);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Jugador jugador = Jugador.getJugador();
		if(e.getKeyCode() == KeyEvent.VK_W) { //Movimiento arriba
			jugador.mover(jugador.getPosX(), jugador.getPosY()+1);
		}
		else if(e.getKeyCode() == KeyEvent.VK_S) { //Movimiento abajo
			jugador.mover(jugador.getPosX(), jugador.getPosY()-1);
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) { //Movimiento izquierda
			jugador.mover(jugador.getPosX()-1, jugador.getPosY());
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) { //Movimiento derecha
			jugador.mover(jugador.getPosX()+1, jugador.getPosY());
		}
		System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
