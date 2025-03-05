package petazeta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws Exception {
		Jugador jugador = Jugador.getJugador();
		Tablero tablero = Tablero.getTablero();
		JFrame f = new JFrame();
				
		f.addKeyListener(new KeyAdapter() {
			public void KeyPressed(KeyEvent e) {
				int keyCode=e.getKeyCode();
				System.out.println(keyCode);
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
		});
				tablero.printMap();
				System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());
				f.setVisible(true);
	}

}
