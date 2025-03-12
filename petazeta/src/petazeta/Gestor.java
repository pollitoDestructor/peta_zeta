package petazeta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gestor {
	private static Gestor mGestor;
	private Controlador controlador = null;
	
	private Gestor(TableroVisual f, Jugador jugador) {
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode=e.getKeyCode();
				//System.out.println(keyCode)	
				getControlador().actionPerformed(keyCode,jugador);
			}
		});		
	}
	public static Gestor getGestor(TableroVisual f, Jugador jugador) {
		if (mGestor == null){
			mGestor = new Gestor(f, jugador);
		}
		return mGestor;
	}
	
		private Controlador getControlador() {
			if (controlador == null) {
				controlador = new Controlador();
			}
			return controlador;
		}
		//2- CONTROLADOR : listener
		private class Controlador {
			public void actionPerformed(int keyCode, Jugador jugador) {
				if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) { //Movimiento arriba
					jugador.mover(0, -1);
					System.out.println("Se mueve arriba");
				}
				else if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) { //Movimiento abajo
					jugador.mover(0, 1);
					System.out.println("Se mueve abajo");
				}
				else if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) { //Movimiento izquierda
					jugador.mover(-1, 0);
					System.out.println("Se mueve a la izquierda");
				}
				else if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) { //Movimiento derecha
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
		}

}
