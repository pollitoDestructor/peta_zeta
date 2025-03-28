package viewController;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import modelo.Jugador;
import modelo.Tablero;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode=e.getKeyCode();
				//System.out.println(keyCode)	
				getControlador().actionPerformed(keyCode,Jugador.getJugador());
			}
		});
	}
	
	@SuppressWarnings("serial") //TODO es por el UID (numero largo)
	private JPanel getPanel_Casillas() {
	    if (grid == null) {
	        grid = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                ImageIcon background = new ImageIcon(getClass().getResource("stageBack1.png"));
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
				
				int index = 17*x+y;
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
				case "Explosion":
					pCasilla.setIcon(new ImageIcon(getClass().getResource("miniBlast1.gif"))); //Explosion
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
			
			/*grid.validate();
			grid.repaint();*/
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
				if(((ImageIcon) pCasilla.getIcon()).getDescription().contains("whitewithbomb1.png"))
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
	}
	private Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}
	//2CONTROLADOR
	private class Controlador {
		public void actionPerformed(int keyCode, Jugador jugador) {
			if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) { //Movimiento arriba
				jugador.mover(0, -1);
				//System.out.println("Se mueve arriba");
			}
			else if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) { //Movimiento abajo
				jugador.mover(0, 1);
				//System.out.println("Se mueve abajo");
			}
			else if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) { //Movimiento izquierda
				jugador.mover(-1, 0);
				//System.out.println("Se mueve a la izquierda");
			}
			else if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) { //Movimiento derecha
				jugador.mover(1,0);
				//System.out.println("Se mueve a la derecha");
			}
			else if (keyCode == KeyEvent.VK_SPACE) { // Poner bomba
			        jugador.ponerBomba();
			        // Crear la vista de la bomba y agregarla a la interfaz
			        /*ViewBomba viewBomba = new ViewBomba(jugador.getPosX(), jugador.getPosY());
			        f.getContentPane().add(viewBomba); // Agregar la bomba a la ventana
			        f.revalidate(); // Refrescar la interfaz
			        f.repaint(); */
			} 
		}
	}
}
