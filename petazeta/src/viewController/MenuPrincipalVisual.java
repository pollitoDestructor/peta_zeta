package viewController;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.GestorMenuPrincipal;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MenuPrincipalVisual extends JFrame implements Observer {

	private static final long serialVersionUID = -1526416068663302084L;
	private JPanel Fondo;
	//Contenido del selector de personajes
	private JPanel Selector_Pj;
	private JLabel Bomber1;
	private JLabel Bomber2;
	private JLabel Bomber3;
	private JLabel Bomber4;
	//Contenido del selector de mapas
	private JPanel Mapas;
	private JButton mapa_Preview;
	private JButton anterior_Mapa;
	private JButton siguiente_Mapa;
	private JLabel descr_Mapa;
	//Controladores
	private ControladorTeclado controladorTeclado;
	private ControladorBoton controladorBoton;
	private ControladorRaton controladorRaton;
	private Reescaler reescaler;
	//Gestor
	private GestorMenuPrincipal menu = GestorMenuPrincipal.getMenu();
	
	private CardLayout diapositiva; //Para transicionar entre mapas y jugadores
	private int diapo=0; // Para intercambiar entre menus, tal vez con bool basta

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipalVisual frame = new MenuPrincipalVisual();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public MenuPrincipalVisual() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 574);
		diapositiva = new CardLayout();
		Fondo = new JPanel(diapositiva){   //Anadir sprite al fondo
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("back.png"));
                ImageIcon explosion = new ImageIcon(getClass().getResource("background3.png"));
                ImageIcon logo = new ImageIcon(getClass().getResource("title.png"));
                ImageIcon boss2 = new ImageIcon(getClass().getResource("boss2.png"));
                ImageIcon boss3 = new ImageIcon(getClass().getResource("boss3.png"));
                ImageIcon boss4 = new ImageIcon(getClass().getResource("boss4.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                g.drawImage(explosion.getImage(), 0, 0, getWidth(), getHeight(), this);
                g.drawImage(logo.getImage(), (getWidth()-170)/4, 0, (getWidth()+170)/2, getHeight()/3, this);
                g.drawImage(boss2.getImage(), getWidth()/3, (getHeight()-50)/3, getWidth()/3, (getHeight()+50)/2, this);
                g.drawImage(boss3.getImage(), getWidth()-getWidth()/12, getHeight()/3, getWidth()/7, getHeight()/2, this);
                g.drawImage(boss4.getImage(), getWidth()/12-getWidth()/9, getHeight()-getHeight()/3, getWidth()/7, getHeight()/2, this);
                g.drawString("<Choose your player>", getWidth()-200, getHeight()-25);
                g.drawString("<space> start, <m> music, <o> options & <esc> exit", getWidth()-290, getHeight()-10);
            }
        };
		Fondo.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(Fondo);
		Fondo.add(getSelector_Pj(), "Selector_Pj"); //Se anade como diapositivas
		Fondo.add(getMapas(),"Mapas");
		diapositiva.show(Fondo, "Selector_Pj"); //Ejemplo de uso: pone en pantalla el Fondo Mapas
		
		this.addKeyListener(getControladorTeclado());
		this.addComponentListener(getReescaler()); //Ajusta la posicion de los bombermans a la pantalla

		menu.addObserver(this);
	}
	
	//===================================================SELECTOR PJ=============================================================
	private JPanel getSelector_Pj() {
		if (Selector_Pj == null) {
			Selector_Pj = new JPanel();
			Selector_Pj.setOpaque(false);
			Selector_Pj.setLayout(null);
			Selector_Pj.add(getBomber1());
			Selector_Pj.add(getBomber2());
			Selector_Pj.add(getBomber3());
			Selector_Pj.add(getBomber4());
		}
		return Selector_Pj;
	}
	private JLabel getBomber1() {
		if (Bomber1 == null) {
			Bomber1 = new JLabel();
			Bomber1.setName("bomber1");
			Bomber1.setBounds(getBounds().width/10, getBounds().height/3,(60*getBounds().width)/450, (82*getBounds().height)/300);
			ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown1.png"));
            Bomber1.setIcon(escalarImagen(sprite, Bomber1.getWidth(), Bomber1.getHeight()));
            Bomber1.addMouseListener(getControladorRaton());
		}
		return Bomber1;
	}
	private JLabel getBomber2() {
		if (Bomber2 == null) {
			Bomber2 = new JLabel();
			Bomber2.setName("bomber2");
			Bomber2.setBounds(getBounds().width/4, getBounds().height/2,(58*getBounds().width)/450, (107*getBounds().height)/300);
			ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown2.png"));
            Bomber2.setIcon(escalarImagen(sprite, Bomber2.getWidth(), Bomber2.getHeight()));
            Bomber2.addMouseListener(getControladorRaton());
		}
		return Bomber2;
	}
	private JLabel getBomber3() {
		if (Bomber3 == null) {
			Bomber3 = new JLabel();
			Bomber3.setName("bomber3");
			Bomber3.setBounds(getBounds().width/2+getBounds().width/4, getBounds().height/3,(47*getBounds().width)/450, (92*getBounds().height)/300);
			ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown3.png"));
            Bomber3.setIcon(escalarImagen(sprite, Bomber3.getWidth(), Bomber3.getHeight()));
            Bomber3.addMouseListener(getControladorRaton());
		}
		return Bomber3;
	}
	private JLabel getBomber4() {
		if (Bomber4 == null) {
			Bomber4 = new JLabel();
			Bomber4.setName("bomber4");
			Bomber4.setBounds(getBounds().width/2+getBounds().width/25, getBounds().height/2,(114*getBounds().width)/450, (100*getBounds().height)/300);
			ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown4.png"));
            Bomber4.setIcon(escalarImagen(sprite, Bomber4.getWidth(), Bomber4.getHeight()));
            Bomber4.addMouseListener(getControladorRaton());
		}
		return Bomber4;
	}
	
	private ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
	
	//=======================================MAPAS===================================================================
	private JPanel getMapas() {
		if (Mapas == null) {
			Mapas = new JPanel();
			Mapas.setLayout(new BorderLayout(0, 0));
			Mapas.add(getMapa_Preview(), BorderLayout.CENTER);
			Mapas.add(getAnterior_Mapa(), BorderLayout.WEST);
			Mapas.add(getSiguiente_Mapa(), BorderLayout.EAST);
			Mapas.add(getDescr_Mapa(), BorderLayout.SOUTH);
			Mapas.setOpaque(false); //TODO El fondo se sigue viendo
		
		}
		return Mapas;
	}
	private JButton getMapa_Preview() {
		if (mapa_Preview == null) {
			mapa_Preview = new JButton("");
			mapa_Preview.setBackground(new Color(0, 0, 0));
			mapa_Preview.setBounds(getBounds().width, getBounds().height,getBounds().width-100, getBounds().height-100);
			ImageIcon prescalada = new ImageIcon(getClass().getResource("mappreview1.png"));
  			mapa_Preview.setIcon(escalarImagen(prescalada, mapa_Preview.getWidth(), mapa_Preview.getHeight()+10));
			mapa_Preview.setName("prev1");
			mapa_Preview.addActionListener(getControladorBoton());
			mapa_Preview.setOpaque(false);
			mapa_Preview.setFocusable(false);
		}
		return mapa_Preview;
	}
	private JButton getAnterior_Mapa() {
		if (anterior_Mapa == null) {
			anterior_Mapa = new JButton("◄");
			anterior_Mapa.setFont(new Font("Arial", Font.BOLD, 24));
			anterior_Mapa.setForeground(Color.WHITE);
			anterior_Mapa.setBackground(new Color(170, 170, 170));
			anterior_Mapa.setFocusPainted(false);
			anterior_Mapa.addActionListener(getControladorBoton());
			anterior_Mapa.setFocusable(false);
		}
		return anterior_Mapa;
	}
	private JButton getSiguiente_Mapa() {
		if (siguiente_Mapa == null) {
			siguiente_Mapa = new JButton("►");
			siguiente_Mapa.setFont(new Font("Arial", Font.BOLD, 24));
			siguiente_Mapa.setForeground(Color.WHITE);
			siguiente_Mapa.setBackground(new Color(170, 170, 170));
			siguiente_Mapa.setFocusPainted(false);
			siguiente_Mapa.addActionListener(getControladorBoton());
			siguiente_Mapa.setFocusable(false);
		}
		return siguiente_Mapa;
	}
	private JLabel getDescr_Mapa() {
		if (descr_Mapa == null) {
			descr_Mapa = new JLabel("Classic");
			descr_Mapa.setFont(new Font("Nirmala UI", Font.BOLD, 35));
			descr_Mapa.setBackground(new Color(255, 255, 255));
			descr_Mapa.setHorizontalAlignment(SwingConstants.CENTER);
			descr_Mapa.setOpaque(true);
		}
		return descr_Mapa;
	}

	//=============================================OBSERVER=========================================

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GestorMenuPrincipal){
			String accion = (String)arg;
			//Teclas
			switch(accion) {
				case "Inicio":
					setVisible(true);
					break;
				case "Crear TableroVisual":
					@SuppressWarnings("unused") TableroVisual f = new TableroVisual();
					break;
				case "Opciones":
					if (diapo == 0) { //Si 0 representa Selector_Pj, cambiamos a Mapas (diapo=1)
						diapositiva.show(Fondo, "Mapas");
						diapo = 1;
					} else {
						diapositiva.show(Fondo, "Selector_Pj");
						diapo = 0;
					}
					break;
				case "Cerrar":
					setVisible(false);
					break;
				case "Reescale":
					//Reescalar bomber1
					Bomber1.setSize((60*getBounds().width)/450, (82*getBounds().height)/300);
					ImageIcon sprite1 = new ImageIcon(getClass().getResource("bomberUnknown1.png"));
					Bomber1.setIcon(escalarImagen(sprite1,Bomber1.getWidth(),Bomber1.getHeight()));
					Bomber1.setLocation(getBounds().width/10, getBounds().height/3);
					//Reescalar bomber2
					Bomber2.setSize((58*getBounds().width)/450, (107*getBounds().height)/300);
					ImageIcon sprite2 = new ImageIcon(getClass().getResource("bomberUnknown2.png"));
					Bomber2.setIcon(escalarImagen(sprite2,Bomber2.getWidth(),Bomber2.getHeight()));
					Bomber2.setLocation(getBounds().width/4, getBounds().height/2);
					//Reescalar bomber3
					Bomber3.setSize((47*getBounds().width)/450, (92*getBounds().height)/300);
					ImageIcon sprite3 = new ImageIcon(getClass().getResource("bomberUnknown3.png"));
					Bomber3.setIcon(escalarImagen(sprite3,Bomber3.getWidth(),Bomber3.getHeight()));
					Bomber3.setLocation(getBounds().width/2+getBounds().width/4, getBounds().height/3);
					//Reescalar bomber4
					Bomber4.setSize((114*getBounds().width)/450, (100*getBounds().height)/300);
					ImageIcon sprite4 = new ImageIcon(getClass().getResource("bomberUnknown4.png"));
					Bomber4.setIcon(escalarImagen(sprite4,Bomber4.getWidth(),Bomber4.getHeight()));
					Bomber4.setLocation(getBounds().width/2+getBounds().width/25, getBounds().height/2);
					break;
				default:
					break;
			}
			if(accion=="Cerrar"){setVisible(false);}

			//Botones
			if(accion.matches("Boton\\d")) {
				int pNum = Character.getNumericValue(accion.charAt(5));
				//System.out.println(pNum);
				mapa_Preview.setName("prev" + pNum);
				//System.out.println("Cambio de mapa"+pNum);
				ImageIcon prescalada = new ImageIcon(getClass().getResource("mappreview" + pNum + ".png"));
				mapa_Preview.setIcon(escalarImagen(prescalada, mapa_Preview.getWidth(), mapa_Preview.getHeight()));
				switch (pNum) {
					case 1:
						descr_Mapa.setText("Classic");
						break;
					case 2:
						descr_Mapa.setText("Soft");
						break;
					case 3:
						descr_Mapa.setText("Empty");
						break;
				}
			}

			//Mouse
			//Apariencia sprites bombers
			if(accion.matches("bomber\\d+Entered")){
				char pNum = accion.charAt(6);
				JLabel pBomber=null;
				if(pNum=='1'){pBomber = Bomber1;}
				else if(pNum=='2'){pBomber = Bomber2;}
				else if(pNum=='3'){pBomber = Bomber3;}
				else if(pNum=='4'){pBomber = Bomber4;}
					ImageIcon sprite = new ImageIcon(getClass().getResource("bomber" + pNum + ".png"));
					pBomber.setIcon(escalarImagen(sprite, pBomber.getWidth(), pBomber.getHeight()));
				}
			//Apariencia sprites bombers
			if(accion.matches("bomber\\d+Exited")){
				char pNum = accion.charAt(6);
				JLabel pBomber=null;
				if(pNum=='1'){pBomber = Bomber1;}
				else if(pNum=='2'){pBomber = Bomber2;}
				else if(pNum=='3'){pBomber = Bomber3;}
				else if(pNum=='4'){pBomber = Bomber4;}

				ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown" + pNum + ".png"));
				pBomber.setIcon(escalarImagen(sprite, pBomber.getWidth(), pBomber.getHeight()));
				
			//Al hacer click
				}
			if(accion.matches("bomber\\d+Click")) 
			{
				char pNum = accion.charAt(6);
				JLabel pBomber=null;
				if(pNum=='1'){pBomber = Bomber1;}
				else if(pNum=='2'){pBomber = Bomber2;}
				else if(pNum=='3'){pBomber = Bomber3;}
				else if(pNum=='4'){pBomber = Bomber4;}
				ImageIcon sprite = new ImageIcon(getClass().getResource("bomber" + pNum + "Ex.png"));
				pBomber.setIcon(escalarImagen(sprite, pBomber.getWidth(), pBomber.getHeight()));
			}

			//Pantalla
			//Reescalar y ajustar a la pantalla labels
			if(accion.matches("Reescale")){
				//Reescalar bomber1
				Bomber1.setSize((60*getBounds().width)/450, (82*getBounds().height)/300);
				ImageIcon sprite1 = new ImageIcon(getClass().getResource("bomberUnknown1.png"));
				Bomber1.setIcon(escalarImagen(sprite1,Bomber1.getWidth(),Bomber1.getHeight()));
				Bomber1.setLocation(getBounds().width/10, getBounds().height/3);
				//Reescalar bomber2
				Bomber2.setSize((58*getBounds().width)/450, (107*getBounds().height)/300);
				ImageIcon sprite2 = new ImageIcon(getClass().getResource("bomberUnknown2.png"));
				Bomber2.setIcon(escalarImagen(sprite2,Bomber2.getWidth(),Bomber2.getHeight()));
				Bomber2.setLocation(getBounds().width/4, getBounds().height/2);
				//Reescalar bomber3
				Bomber3.setSize((47*getBounds().width)/450, (92*getBounds().height)/300);
				ImageIcon sprite3 = new ImageIcon(getClass().getResource("bomberUnknown3.png"));
				Bomber3.setIcon(escalarImagen(sprite3,Bomber3.getWidth(),Bomber3.getHeight()));
				Bomber3.setLocation(getBounds().width/2+getBounds().width/4, getBounds().height/3);
				//Reescalar bomber4
				Bomber4.setSize((114*getBounds().width)/450, (100*getBounds().height)/300);
				ImageIcon sprite4 = new ImageIcon(getClass().getResource("bomberUnknown4.png"));
				Bomber4.setIcon(escalarImagen(sprite4,Bomber4.getWidth(),Bomber4.getHeight()));
				Bomber4.setLocation(getBounds().width/2+getBounds().width/25, getBounds().height/2);
				//Sprites mapa
				int pNum = Character.getNumericValue(mapa_Preview.getName().charAt(4));
				//System.out.println(pNum);
				mapa_Preview.setName("prev" + pNum);
				//System.out.println("Cambio de mapa"+pNum);
				ImageIcon prescalada = new ImageIcon(getClass().getResource("mappreview" + pNum + ".png"));
				mapa_Preview.setIcon(escalarImagen(prescalada, mapa_Preview.getWidth(), mapa_Preview.getHeight()));
				switch (pNum) {
					case 1:
						descr_Mapa.setText("Classic");
						break;
					case 2:
						descr_Mapa.setText("Soft");
						break;
					case 3:
						descr_Mapa.setText("Empty");
						break;
				}
			}
		}
	}

	//=============================================CONTROLADOR TECLADO=========================================
	
	private ControladorTeclado getControladorTeclado() {
        if (controladorTeclado == null) {
            controladorTeclado = new ControladorTeclado();
        }
        return controladorTeclado;
    }
	//CONTROLADOR KeyListener
    private class ControladorTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if(keyCode==KeyEvent.VK_ESCAPE){
				menu.opcionesMenu("Cerrar");
				menu.end();
			}
			else if(keyCode==KeyEvent.VK_SPACE){
				menu.iniciarJuego();
			}
			else if (keyCode==KeyEvent.VK_M){/*Ajustar musica*/}
			else if (keyCode==KeyEvent.VK_O){
				menu.opcionesMenu("Opciones");
			}
		}

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    
  //=============================================CONTROLADOR BOTON=========================================
  	private ControladorBoton getControladorBoton() {
  		if (controladorBoton == null) {
  			controladorBoton = new ControladorBoton();
  		}
  		return controladorBoton;
  	}
  	//2- CONTROLADOR : listener
  	private class ControladorBoton implements ActionListener {
  		@Override
  		public void actionPerformed(ActionEvent e) {

  			if(e.getSource()==siguiente_Mapa || e.getSource()==anterior_Mapa || e.getSource()==mapa_Preview) {
				int pNum = Character.getNumericValue(mapa_Preview.getName().charAt(4));
				if (e.getSource() == siguiente_Mapa) {
					pNum = (pNum % 3) + 1;
					menu.opcionesMenu(String.valueOf("Boton"+pNum));
				} else if (e.getSource() == anterior_Mapa) {
					pNum = pNum == 1 ? 3 : pNum - 1; //Si es 1, pasa al 3, si no, resta 1
					menu.opcionesMenu(String.valueOf("Boton"+pNum));
				} else if(e.getSource() == mapa_Preview) {
					GestorMenuPrincipal.getMenu().cambiarMapa(descr_Mapa.getText()); 
				}
			}
  		}
  	}

  //=============================================CONTROLADOR RATON=========================================
  	private ControladorRaton getControladorRaton() {
        if (controladorRaton == null) {
            controladorRaton = new ControladorRaton();
        }
        return controladorRaton;
    }
  	
  	private class ControladorRaton implements MouseListener {
  		@Override
  		public void mouseClicked(MouseEvent e) {
  			if(e.getComponent()==Bomber1){
  				System.out.println("bomber1");
  				GestorMenuPrincipal.getMenu().cambiarJugador("white");
  				menu.opcionesMenu(e.getComponent().getName()+"Click");
  			}
  			else if(e.getComponent()==Bomber2){
  				System.out.println("bomber2");
  				GestorMenuPrincipal.getMenu().cambiarJugador("black");
  				menu.opcionesMenu(e.getComponent().getName()+"Click");
  			}
  			else if(e.getComponent()==Bomber3){
  				System.out.println("bomber2");
//  				GestorMenuPrincipal.getMenu().cambiarJugador("black");
  				menu.opcionesMenu(e.getComponent().getName()+"Click");
  			}
  			else if(e.getComponent()==Bomber4){
  				System.out.println("bomber2");
//  				GestorMenuPrincipal.getMenu().cambiarJugador("black");
  				menu.opcionesMenu(e.getComponent().getName()+"Click");
  			}
  		}

  		@Override
  		public void mousePressed(MouseEvent e) {

  		}

  		@Override
  		public void mouseReleased(MouseEvent e) {

  		}

  		@Override
  		public void mouseEntered(MouseEvent e) {
  			if(e.getComponent()==Bomber1||e.getComponent()==Bomber2
  					||e.getComponent()==Bomber3||e.getComponent()==Bomber4) {
  				menu.opcionesMenu(e.getComponent().getName()+"Entered");
  			}
  		}

  		@Override
  		public void mouseExited(MouseEvent e) {
  			if(e.getComponent()==Bomber1||e.getComponent()==Bomber2
  					||e.getComponent()==Bomber3||e.getComponent()==Bomber4) {
  				menu.opcionesMenu(e.getComponent().getName()+"Exited");
  			}
  		}
  	}

	//========================================CONTROLADOR COMPONENTES=========================================
	private Reescaler getReescaler() {
		if (reescaler == null) {
			reescaler = new Reescaler();
		}
		return reescaler;
	}

	private class Reescaler implements ComponentListener {

		@Override
		//REPOSICIONA Y REESCALA LOS SPRITES DE LOS BOMBERMANS CON EL TAMAÑO DE PANTALLA
		public void componentResized(ComponentEvent e) {
			menu.opcionesMenu("Reescale");
		}

		@Override
		public void componentMoved(ComponentEvent e) {

		}

		@Override
		public void componentShown(ComponentEvent e) {

		}

		@Override
		public void componentHidden(ComponentEvent e) {

		}
	} 
}
