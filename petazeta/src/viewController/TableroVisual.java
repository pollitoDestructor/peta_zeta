package viewController;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import modelo.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

@SuppressWarnings("deprecation")

public class TableroVisual extends JFrame implements Observer{

	private static final long serialVersionUID = -1526416068663302084L;
	private JPanel grid;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblPuntuacion;
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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelPuntuacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelPuntuacion.setOpaque(true);
		lblPuntuacion = new JLabel("Puntuación: 0");
		lblPuntuacion.setForeground(Color.WHITE);
		panelPuntuacion.setBackground(new Color(170, 170, 170));
		lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 18));
		lblPuntuacion.setFocusable(false);
		panelPuntuacion.add(lblPuntuacion);

		contentPane.add(panelPuntuacion, BorderLayout.NORTH);
		contentPane.add(getPanel());
		contentPane.add(getPanel_Casillas(), BorderLayout.CENTER);

		Tablero.getTablero().addObserver(this);
		Jugador.getJugador().addObserver(this);
		GestorMenuPrincipal.getMenu().addObserver(this);
		this.addKeyListener(getControlador());
	}

	@SuppressWarnings("serial")
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
		if(o instanceof GestorMenuPrincipal&&(String)arg=="Juego"){setVisible(true);}
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
					case "BombaSuper":
						String color = (String)param[4];
						if(color.equals("red")){
							pCasilla.setIcon(new ImageIcon(getClass().getResource((String)param[4]+"withbomb3.png"))); //Bomba
						} else {
							pCasilla.setIcon(new ImageIcon(getClass().getResource((String)param[4]+"withbomb1.png"))); //Bomba
						}
						break;
					case "BombaUltra":
						pCasilla.setIcon(new ImageIcon(getClass().getResource((String)param[4]+"withbomb2.png"))); //Bomba
						break;
					case "BombaCruz":
						pCasilla.setIcon(new ImageIcon(getClass().getResource((String)param[4]+"withbomb4.png"))); //Bomba
						break;
					case "Explosion1":
						if(param.length==5){pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast1x"+String.valueOf((((int)param[4]-2)%3)+1)+".gif")));}
						else{pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast1.gif")));} //Explosion
						break;
					case "Explosion2":
						if(param.length==5){pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast3x"+String.valueOf((((int)param[4]-2)%3)+1)+".gif")));}
						else{pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast2.gif")));} //Explosion
						break;
					case "Explosion3":
						if(param.length==5){pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast2x"+String.valueOf((((int)param[4]-2)%3)+1)+".gif")));}
						else{pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast3.gif")));} //Explosion
						break;
					case "Explosion4":
						if(param.length==5){pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast4x"+String.valueOf((((int)param[4]-2)%3)+1)+".gif")));}
						else{pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast4.gif")));} //Explosion
						break;
					case "blue":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("redwithportal1.gif"))); //Portal azul
						break;
					case "purple":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("redwithportal2.gif"))); //Portal morado
						break;
					case "Globo":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("baloon1.png")));
						break;
					case "Doria":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("doria1.png")));
						break;
					case "Pass":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("pass1.png")));
						break;
					case "Bomba":
						pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb4.png")));
						break;
				}
			} 
			else if((String)param[0]=="Muerte") //Ventana de muerte
			{
				setVisible(false);
				dispose();
			}
			else if((String)param[0]=="FinalVisual") 
			{
				@SuppressWarnings("unused")
				FinalVisual f = new FinalVisual();
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
			Object[] params = (Object[]) arg; //[posX:int, posY:int, x:int, y:int]
			String accion = (String) params[0];
				switch (accion) {
					case "mover":
						int posX = (int) params[1];
						int posY = (int) params[2];
						int x = (int) params[3];
						int y = (int) params[4];
						String color = (String) params[5];
						//System.out.println(color);

						int index = 17 * posY + posX; //la pos que dejamos atr�s
						JLabel pCasilla = (JLabel) grid.getComponent(index);
						String[] partes = null;
						if (x != 0 || y != 0) {
							if (pCasilla.getIcon() != null) {
								String imagenActual = pCasilla.getIcon().toString();
								String nombreImagen = imagenActual.substring(imagenActual.lastIndexOf("/") + 1);
								partes = nombreImagen.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\.)");
							} else {
								// Manejar el caso cuando no hay icono (por ejemplo, establecer partes a un valor por defecto)
								partes = new String[]{color + "right", "1"}; // Ejemplo de valor por defecto
							}
						}
						if (pCasilla.getIcon() != null) //Para evitar errores
						{
							String descr = ((ImageIcon) pCasilla.getIcon()).getDescription();
							if (descr.contains(color + "withbomb1.png") || descr.contains(color + "withbomb2.png") || descr.contains(color + "withbomb3.png") || descr.contains(color + "withbomb4.png")) //TODO
							{
								//Si DEJAMOS bomba (el anterior es Bomberman con bomba
								switch (color) {
									case "white":
										pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb1.png")));
										break;
									case "black":
										pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb2.png")));
										break;
									case "red":
										pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb3.png")));
										break;
									case "blue":
										pCasilla.setIcon(new ImageIcon(getClass().getResource("bomb4.png")));
										break;
								}
							} else if (descr.contains(color + "withportal1.gif")) {
								pCasilla.setIcon(new ImageIcon(getClass().getResource("portal1.gif")));
							} else if (descr.contains(color + "withportal2.gif")) {
								pCasilla.setIcon(new ImageIcon(getClass().getResource("portal2.gif")));
							} else {
								//Si no es null y NO dejamos atr�s bomba (anda normal)
								pCasilla.setIcon(null);
							}
						}

						index = 17 * (posY + y) + (posX + x); //la nueva pos (a la que avanza)
						pCasilla = (JLabel) grid.getComponent(index);
						if (x != 0 || y != 0) {
							if (x == 1) {
								if (partes[0].equals(color + "right")) {
									int frame = Integer.parseInt(partes[1]) % 5;
									frame = frame + 1;
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "right" + String.valueOf(frame) + ".png")));//derecha
								} else {
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "right1.png")));//derecha
								}
							} else if (x == -1) {
								if (partes[0].equals(color + "left")) {
									int frame = Integer.parseInt(partes[1]);
									frame = frame % 5 + 1;
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "left" + String.valueOf(frame) + ".png")));//derecha
								} else {
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "left1.png")));//derecha
								}
							} else if (y == 1) {
								if (partes[0].equals(color + "down")) {
									int frame = Integer.parseInt(partes[1]);
									frame = frame % 4 + 1;
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "down" + String.valueOf(frame) + ".png")));//derecha
								} else {
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "down1.png")));//derecha
								}
							} else if (y == -1) {
								if (partes[0].equals(color + "up")) {
									int frame = Integer.parseInt(partes[1]);
									frame = frame % 5 + 1;
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "up" + String.valueOf(frame) + ".png")));//derecha
								} else {
									pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "up1.png")));//derecha
								}
							} else if (x == 0 && y == 0)
								pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "happy1.png")));//inicializar
						} else if (x == 0 && y == 0)
							pCasilla.setIcon(new ImageIcon(getClass().getResource(color + "happy1.png")));//inicializar
						break;
					case "punt":
						int punt = (int) params[1];
						lblPuntuacion.setText("Puntuación: " + punt);
						break;
					case "tp":
						int tpOldposX = (int) params[1];
						int tpOldposY = (int) params[2];
						int tpPosX = (int) params[3];
						int tpPosY = (int) params[4];
						int tpColor = (int) params[5];

						int tpOldindex = 17 * tpOldposY + tpOldposX; //la pos que dejamos atras
						JLabel tpOldCasilla = (JLabel) grid.getComponent(tpOldindex);

						if (tpOldCasilla.getIcon() != null) //Para evitar errores
						{
							String descr = ((ImageIcon) tpOldCasilla.getIcon()).getDescription();
							if (descr.contains("redwithbomb3.png")) //TODO
							{
								tpOldCasilla.setIcon(new ImageIcon(getClass().getResource("bomb3.png")));
							}
							else
							{
								tpOldCasilla.setIcon(null);
							}

							int tpIndex = 17 * tpPosY + tpPosX;
							JLabel tpCasilla = (JLabel) grid.getComponent(tpIndex);
							if(tpColor==0) {
								tpCasilla.setIcon(new ImageIcon(getClass().getResource("redwithportal1.gif")));
							}
							else if(tpColor==1) {
								tpCasilla.setIcon(new ImageIcon(getClass().getResource("redwithportal2.gif")));
							}
							break;
						}
				}

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
					String tipo = (String) params[5];
//					System.out.println("Enemigo movido de (" + oldX + "," + oldY + ") a (" + newX + "," + newY + ")");

					// Borrar posición anterior
					int oldIndex = oldY * 17 + oldX;
					int newIndex = newY * 17 + newX;
					JLabel pCasilla = (JLabel) grid.getComponent(oldIndex);
					JLabel pCasillaN = (JLabel) grid.getComponent(newIndex);
					switch (tipo) {
						case "Globo":
							tipo = "baloon";
							break;
						case "Doria":
							tipo = "doria";
							break;
						case "Pass":
							tipo = "pass";
							break;
					}

					String imgDefecto = tipo + "1.png"; // Imagen por defecto
					if (pCasilla.getIcon() != null) {
						ImageIcon icon = (ImageIcon) pCasilla.getIcon();
						if (icon.getDescription() != null) {
							String imagenActual = pCasilla.getIcon().toString();
							String nombreImagen = imagenActual.substring(imagenActual.lastIndexOf("/"  ) + 1);

							// Expresión regular para separar "nombre", "número" y ".png"
							String[] partes = null;
							partes = nombreImagen.split("(?<=[a-zA-Z])(?=\\d)|(?<=\\d)(?=\\.)");
							pCasillaN.setIcon(new ImageIcon(getClass().getResource(tipo+String.valueOf(Integer.parseInt(partes[1])%2+1)+".png")));
						}
					} else {
						pCasillaN.setIcon(new ImageIcon(getClass().getResource(imgDefecto)));
					}

					// Limpiar la casilla anterior
					pCasilla.setIcon(null);
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
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) { //Movimiento arriba
				Jugador.getJugador().mover(0, -1);
				//System.out.println("Se mueve arriba");
			} else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) { //Movimiento abajo
				Jugador.getJugador().mover(0, 1);
				//System.out.println("Se mueve abajo");
			} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) { //Movimiento izquierda
				Jugador.getJugador().mover(-1, 0);
				//System.out.println("Se mueve a la izquierda");
			} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) { //Movimiento derecha
				Jugador.getJugador().mover(1, 0);
				//System.out.println("Se mueve a la derecha");
			} else if (keyCode == KeyEvent.VK_SPACE) { // Poner bomba
				Jugador.getJugador().ponerBomba();
				// Crear la vista de la bomba y agregarla a la interfaz
					/*ViewBomba viewBomba = new ViewBomba(jugador.getPosX(), jugador.getPosY());
				       f.getContentPane().add(viewBomba); // Agregar la bomba a la ventana
				       f.revalidate(); // Refrescar la interfaz
				       f.repaint(); */
			} else if (keyCode == KeyEvent.VK_Z) {
				if (Jugador.getJugador().getColor().equals("red")){// Poner portal azul
					Tablero.getTablero().addTeletransporte("blue");
				}
				else if (Jugador.getJugador().getColor().equals("blue")){
					Tablero.getTablero().patearBomba();
				}
			} else if (keyCode == KeyEvent.VK_X) {
				if (Jugador.getJugador().getColor().equals("red")){// Poner portal naranja
					Tablero.getTablero().addTeletransporte("purple");
				}
			}
		}

		@Override
		public void keyTyped (KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased (KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}