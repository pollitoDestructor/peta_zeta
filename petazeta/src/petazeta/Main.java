package petazeta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws Exception {
		Tablero tablero = Tablero.getTablero();
		TableroVisual f = new TableroVisual();

		tablero.ponerBloques();
		Jugador jugador = Jugador.getJugador("blanco");
		
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode=e.getKeyCode();
				//System.out.println(keyCode);
				if(keyCode == KeyEvent.VK_W) { //Movimiento arriba
					jugador.mover(jugador.getPosX(), jugador.getPosY()-1);
					System.out.println("Se mueve arriba");
				}
				else if(keyCode == KeyEvent.VK_S) { //Movimiento abajo
					jugador.mover(jugador.getPosX(), jugador.getPosY()+1);
					System.out.println("Se mueve abajo");
				}
				else if(keyCode == KeyEvent.VK_A) { //Movimiento izquierda
					jugador.mover(jugador.getPosX()-1, jugador.getPosY());
					System.out.println("Se mueve a la izquierda");
				}
				else if(keyCode == KeyEvent.VK_D) { //Movimiento derecha
					jugador.mover(jugador.getPosX()+1, jugador.getPosY());
					System.out.println("Se mueve a la derecha");
				}
				else if(keyCode == KeyEvent.VK_SPACE) { //Poner bomba
					if (jugador.getColor()=="blanco") {
						Bomba bomba = new Bomba(jugador.getPosX(),jugador.getPosY());
					}
					System.out.println("Se pone una bomba");
				}
				
				tablero.printMap();
				System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());
				
			}
		});
		
		//tablero.printMap();
		System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());
		f.setVisible(true);
		
		
	}

}