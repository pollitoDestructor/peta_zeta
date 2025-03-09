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
		jugador.inicio(); //para imprimir el primer Bomberman nada más
		
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode=e.getKeyCode();
				//System.out.println(keyCode);
				if(keyCode == KeyEvent.VK_W) { //Movimiento arriba
					jugador.mover(0, -1);
					System.out.println("Se mueve arriba");
				}
				else if(keyCode == KeyEvent.VK_S) { //Movimiento abajo
					jugador.mover(0, 1);
					System.out.println("Se mueve abajo");
				}
				else if(keyCode == KeyEvent.VK_A) { //Movimiento izquierda
					jugador.mover(-1, 0);
					System.out.println("Se mueve a la izquierda");
				}
				else if(keyCode == KeyEvent.VK_D) { //Movimiento derecha
					jugador.mover(1,0);
					System.out.println("Se mueve a la derecha");
				}
				else if (keyCode == KeyEvent.VK_SPACE) { // Poner bomba
				    if (jugador.getColor().equals("blanco")) {
				        jugador.ponerBomba();
				        // Crear la vista de la bomba y agregarla a la interfaz
				        /*ViewBomba viewBomba = new ViewBomba(jugador.getPosX(), jugador.getPosY());
				        f.getContentPane().add(viewBomba); // Agregar la bomba a la ventana
				        f.revalidate(); // Refrescar la interfaz
				        f.repaint(); */

				        System.out.println("Se pone una bomba en (" + jugador.getPosX() + ", " + jugador.getPosY() + ")");
				    }
				} 

				
			}
		});
		
		//tablero.printMap();
		System.out.println("El jugador estï¿½ en: "+jugador.getPosX()+" "+jugador.getPosY());
		f.setVisible(true);
		
		
	}

}