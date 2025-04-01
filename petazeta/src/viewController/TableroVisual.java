package viewController;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import modelo.*;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.util.Random;

@SuppressWarnings("deprecation")

public class TableroVisual extends JFrame implements Observer{

	private static final long serialVersionUID = -1526416068663302084L;
	private JPanel grid;
	private JPanel contentPane; 
	private JPanel panel;
	private Controlador controlador;
	private String fondo;


	/**
	 * Create the frame.
	 */
	public TableroVisual() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(getPanel());
		contentPane.add(getPanel_Casillas(), BorderLayout.CENTER);
		
		Tablero.getTablero().addObserver(this);
		Jugador.getJugador().addObserver(this);
		this.addKeyListener(getControlador());
	}
	
	private JPanel getPanel_Casillas() {
	    if (grid == null) {
	        grid = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                ImageIcon background = new ImageIcon(getClass().getResource(fondo));
	                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        };
	        grid.setLayout(new GridLayout(11, 17, 0, 0));

	        for (int m = 0; m < 17; m++) {
	            for (int n = 10; n >= 0; n--) {
	                JLabel labelCasilla = new JLabel();
	                labelCasilla.setOpaque(false);  // Hace que las casillas sean transparentes
	                grid.add(labelCasilla);
	            }
	        }
	    }
	    return grid;
	}

	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
		}
		return panel;
	}
	
	@Override
	public void update(Observable o, Object arg) {		
		if(o instanceof Tablero) {
			Object[] param = (Object[])arg; //[accion:String, pX:int, pY:int, tipoBloque:String]
			
			if ((String)param[0]=="PonerImagen") { //comprueba la accion que se quiere realizar (poner una casilla, detonar una bomba, mostrar el mapa, etc.)
				int y = (int)param[2]; 
				int x = (int)param[1];
				
				int index = 17*y+x;
				JLabel pCasilla = (JLabel) grid.getComponent(index);
				
				switch ((String)param[3]) {
				case "Casilla":
					pCasilla.setIcon(null); //Casilla vacia
					break;
				case "BloqueBlando": 
					Random r = new Random();		//Para que haya variaci�n en la imagen de los bloques blandos
					pCasilla.setIcon(new ImageIcon(getClass().getResource("soft4" + String.valueOf(r.nextInt(5) + 1)+".png"))); //Bloque Blando
				
					break;
				case "BloqueDuro":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("hard4.png"))); //Bloque Duro
					break;
				case "Bomba":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("whitewithbomb1.png"))); //Bomba
					break;
				case "BombaUltra":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("whitewithbomb2.png"))); //Bomba
					break;
				case "Explosion":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast1.gif"))); //Explosion
					break;
				case "Globo":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("baloon1.png")));
				break;
				}
			}
			else if((String)param[0]=="Muerte") //Ventana de muerte
			{
				//GestorFinalVisual.getFinal(false); TODO
				setVisible(false);
				System.out.println("Apaga paso1.");
				dispose();
				System.out.println("Dispose paso 2.");
				
			}
			else if((String)param[0]=="NuevoEnemigo"){
				Enemigo nuevoEnemigo = (Enemigo) param[1];
				nuevoEnemigo.addObserver(this); // Registrar el enemigo como observable
			}
			else if((String)param[0]=="PonerFondo") { //Elige el fondo
				String pMapa = (String)param[1];
				switch(pMapa) {
				case "Classic":
					fondo = "stageBack1.png";
					break;
				case "Soft":
					fondo = "stageBack3.png";
					break;
				case "Empty":
					fondo = "stageBack2.png";
					break;
				}
				grid.repaint();
				grid.validate();
				
			}
		}
		else if(o instanceof Jugador)
		{
			Object[] movimiento = (Object[]) arg; //[posX:int, posY:int, x:int, y:int]
            int posX = (int)movimiento[0];
            int posY = (int)movimiento[1];
            int x = (int)movimiento[2];
            int y = (int)movimiento[3];

            int index = 17*posY+posX; //la pos que dejamos atr�s
			JLabel pCasilla = (JLabel) grid.getComponent(index);
			String[] partes = null;
			if(x != 0 || y != 0) {
				String imagenActual = pCasilla.getIcon().toString();
				String nombreImagen = imagenActual.substring(imagenActual.lastIndexOf("/"  ) + 1);
	            partes = nombreImagen.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\.)");
			}
			if(pCasilla.getIcon() != null) //Para evitar errores
			{
				String descr = ((ImageIcon) pCasilla.getIcon()).getDescription();
				if(descr.contains("whitewithbomb1.png") || descr.contains("whitewithbomb2.png")) //TODO
				{
					//Si DEJAMOS bomba (el anterior es Bomberman con bomba
					pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb1.png")));
				}
				else
				{
					//Si no es null y NO dejamos atr�s bomba (anda normal)
					pCasilla.setIcon(null);
				}
			}
			
			index = 17*(posY+y)+(posX+x); //la nueva pos (a la que avanza)
			pCasilla = (JLabel) grid.getComponent(index);
			if(x != 0 || y != 0) {	
	            if (x == 1) {
	            	if(partes[0].equals("whiteright")) {
	            		int frame = Integer.parseInt(partes[1])%5;
	            		frame = frame+1;
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteright"+String.valueOf(frame)+".png")));//derecha
	            	} else {
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteright1.png")));//derecha
	            	}
	            } else if (x == -1) {
	            	if(partes[0].equals("whiteleft")) {
	            		int frame = Integer.parseInt(partes[1]);
	            		frame = frame%5+1;
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteleft"+String.valueOf(frame)+".png")));//derecha
	            	} else {
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteleft1.png")));//derecha
	            	}
	            } else if (y == 1) {
	            	if(partes[0].equals("whitedown")) {
	            		int frame = Integer.parseInt(partes[1]);
	            		frame = frame%4+1;
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whitedown"+String.valueOf(frame)+".png")));//derecha
	            	} else {
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whitedown1.png")));//derecha
	            	}
	            } else if (y == -1) {
	            	if(partes[0].equals("whiteup")) {
	            		int frame = Integer.parseInt(partes[1]);
	            		frame = frame%5+1;
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteup"+String.valueOf(frame)+".png")));//derecha
	            	} else {
	            		pCasilla.setIcon(new ImageIcon(getClass().getResource("whiteup1.png")));//derecha
	            	}
	            } else if (x == 0 && y == 0)pCasilla.setIcon(new ImageIcon(getClass().getResource("whitehappy1.png")));//inicializar
			}    else if (x == 0 && y == 0)pCasilla.setIcon(new ImageIcon(getClass().getResource("whitehappy1.png")));//inicializar
		}
		else if(o instanceof Enemigo)
		{
			Object[] params = (Object[]) arg;
			String accion = (String) params[0];

			switch (accion) {
				case "mover":
				int oldX = (int) params[1];
				int oldY = (int) params[2];
				int newX = (int) params[3];
				int newY = (int) params[4];
				System.out.println("Enemigo movido de (" + oldX + "," + oldY + ") a (" + newX + "," + newY + ")");

				//Borrar posición anterior
				int oldIndex = oldY * 17 + oldX;
				JLabel pCasilla = (JLabel) grid.getComponent(oldIndex);
				boolean esBaloon1 = pCasilla.getIcon() != null && ((ImageIcon)pCasilla.getIcon()).getDescription().contains("baloon1.png");
				pCasilla.setIcon(null);

				//Poner en nueva posición
				int newIndex = newY * 17 + newX;
				pCasilla = (JLabel) grid.getComponent(newIndex);
				if(esBaloon1)
				{
					pCasilla.setIcon(new ImageIcon(getClass().getResource("baloon2.png")));
				}
				else {
					pCasilla.setIcon(new ImageIcon(getClass().getResource("baloon1.png")));
				}
				break;

				case "EnemigoDestruido":
					int x = (int) params[1];
					int y = (int) params[2];
					Casilla casilla = Tablero.getTablero().getCasilla(x,y);
					if(casilla.tipoCasilla() != "Explosion") {
						int index = y * 17 + x;
						pCasilla = (JLabel) grid.getComponent(index);
						pCasilla.setIcon(null);
					}
				break;
			}
		}
	}
	private Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}
	//CONTROLADOR
	private class Controlador implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode=e.getKeyCode();
			if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) { //Movimiento arriba
				Jugador.getJugador().mover(0, -1);
				//System.out.println("Se mueve arriba");
			}
			else if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) { //Movimiento abajo
				Jugador.getJugador().mover(0, 1);
				//System.out.println("Se mueve abajo");
			}
			else if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) { //Movimiento izquierda
				Jugador.getJugador().mover(-1, 0);
				//System.out.println("Se mueve a la izquierda");
			}
			else if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) { //Movimiento derecha
				Jugador.getJugador().mover(1,0);
				//System.out.println("Se mueve a la derecha");
			}
			else if (keyCode == KeyEvent.VK_SPACE) { // Poner bomba
					Jugador.getJugador().ponerBomba();
			        // Crear la vista de la bomba y agregarla a la interfaz
					/*ViewBomba viewBomba = new ViewBomba(jugador.getPosX(), jugador.getPosY());
				       f.getContentPane().add(viewBomba); // Agregar la bomba a la ventana
				       f.revalidate(); // Refrescar la interfaz
				       f.repaint(); */
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
