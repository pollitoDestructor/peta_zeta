package viewController;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import modelo.GestorMenuPrincipal;

import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MenuPrincipal extends JFrame /*implements Observer*/{

    private static final long serialVersionUID = -1526416068663302084L;
    private JPanel contentPane;
    private JLabel bomber1;
    private JLabel bomber2;
    private JLabel bomber3;
    private JLabel bomber4;
    private JPanel opciones;
    private GestorMenuPrincipal menu;
    private ControladorTeclado controladorTeclado;
    private ControladorRaton controladorRaton;
    private Reescaler reescaler;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuPrincipal frame = new MenuPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MenuPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel() {   //AÑADIR SPRITE DE FONDO AL MENU
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
                g.drawString("<space> to start, <m>usic, <o>ptions & <esc> to exit", getWidth()-300, getHeight()-10);
            }
        };

        setContentPane(contentPane);
        contentPane.setLayout(null);

        contentPane.add(getBomber1());
        contentPane.add(getBomber2());
        contentPane.add(getBomber3());
        contentPane.add(getBomber4());
        contentPane.add(getOpciones());

        this.addKeyListener(getControladorTeclado());
        bomber1.addMouseListener(getControladorRaton());
        bomber2.addMouseListener(getControladorRaton());
        bomber3.addMouseListener(getControladorRaton());
        bomber4.addMouseListener(getControladorRaton());
        this.addComponentListener(getReescaler()); //Ajusta la posicion de los bombermans a la pantalla
        this.menu=GestorMenuPrincipal.getMenu();
    }

    private JLabel getBomber1() { //Bomber1 (blanco) - pj default
        if (bomber1 == null) {
            bomber1 = new JLabel();
            bomber1.setBounds(getBounds().width/10, getBounds().height/3, 60, 82); //TODO Es necesario?
            bomber1.setName("bomber1");
            //bomber1.setIcon(new ImageIcon(getClass().getResource("bomber1.png")));
        }
        return bomber1;
    }
    private JLabel getBomber2() { //Bomber2 (negro)
        if (bomber2 == null) {
            bomber2 = new JLabel();
            bomber2.setIcon(new ImageIcon(getClass().getResource("bomberUnknown2.png")));
            bomber2.setName("bomber2");
            //bomber2.setBounds(getBounds().width/2, getBounds().height/4, 58, 107);
        }
        return bomber2;
    }

    private JLabel getBomber3() { //Bomber3
        if (bomber3 == null) {
            bomber3 = new JLabel();
            bomber3.setIcon(new ImageIcon(getClass().getResource("bomberUnknown3.png")));
            bomber3.setName("bomber3");
            //bomber3.setBounds(getBounds().width/2+getBounds().width/4, getBounds().height/3, 47, 92);
        }
        return bomber3;
    }

    private JLabel getBomber4() { //Bomber4
        if (bomber4 == null) {
            bomber4 = new JLabel();
            bomber4.setIcon(new ImageIcon(getClass().getResource("bomberUnknown4.png")));
            bomber4.setName("bomber4");
            //bomber4.setBounds(getBounds().width/2+getBounds().width/25, getBounds().height/2, 114, 100);
        }
        return bomber4;
    }

    private JPanel getOpciones(){
        if (opciones == null) {
            opciones = new JPanel();
            Border borde = BorderFactory.createLineBorder(Color.BLACK, 1);
            opciones.setLayout(new GridLayout(3,1,0,0));
                JLabel Classic = new JLabel("Classic");
                Classic.setBorder(borde);
                //Classic.setFont(new Font("Arial", Font.BOLD, 5));
                Classic.setHorizontalAlignment(SwingConstants.CENTER);
                Classic.setForeground(new Color(255, 20, 100));
                Classic.addMouseListener(getControladorRaton());
                opciones.add(Classic);
                JLabel Soft = new JLabel("Soft");
                Soft.setBorder(borde);
                //Soft.setFont(new Font("Arial", Font.BOLD, 5));
                Soft.addMouseListener(getControladorRaton());
                Soft.setHorizontalAlignment(SwingConstants.CENTER);
                Soft.setForeground(new Color(20, 255, 100));
                opciones.add(Soft);
                JLabel Empty = new JLabel("Empty");
                Empty.setBorder(borde);
                //Empty.setFont(new Font("Arial", Font.BOLD, 5));
                Empty.addMouseListener(getControladorRaton());
                Empty.setHorizontalAlignment(SwingConstants.CENTER);
                Empty.setForeground(new Color(20, 100, 255));
                opciones.add(Empty);
                opciones.setVisible(false);
            //opciones.setBounds(getBounds().width-getBounds().width/10, getBounds().height/20, 50, 100);
        }
        return opciones;
    }

    private ImageIcon escalarImagen(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    private ControladorTeclado getControladorTeclado() {
        if (controladorTeclado == null) {
            controladorTeclado = new ControladorTeclado();
        }
        return controladorTeclado;
    }

    private ControladorRaton getControladorRaton() {
        if (controladorRaton == null) {
            controladorRaton = new ControladorRaton();
        }
        return controladorRaton;
    }

    private Reescaler getReescaler() {
        if (reescaler == null) {
            reescaler = new Reescaler();
        }
        return reescaler;
    }

    private class Reescaler implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            //Reescalar bomber1
            bomber1.setSize((60*getBounds().width)/450, (82*getBounds().height)/300);
            ImageIcon sprite1 = new ImageIcon(getClass().getResource("bomberUnknown1.png"));
            sprite1.setDescription("bomber1.png"); 
            bomber1.setIcon(escalarImagen(sprite1,bomber1.getWidth(),bomber1.getHeight()));
            bomber1.setLocation(getBounds().width/10, getBounds().height/3);
            //Reescalar bomber2
            bomber2.setSize((58*getBounds().width)/450, (107*getBounds().height)/300);
            bomber2.setLocation(getBounds().width/4, getBounds().height/2);
            ImageIcon sprite2 = new ImageIcon(getClass().getResource("bomberUnknown2.png"));
            bomber2.setIcon(escalarImagen(sprite2,bomber2.getWidth(),bomber2.getHeight()));
            //Reescalar bomber3
            bomber3.setSize((47*getBounds().width)/450, (92*getBounds().height)/300);
            ImageIcon sprite3 = new ImageIcon(getClass().getResource("bomberUnknown3.png"));
            bomber3.setIcon(escalarImagen(sprite3,bomber3.getWidth(),bomber3.getHeight()));
            bomber3.setLocation(getBounds().width/2+getBounds().width/4, getBounds().height/3);
            //Reescalar bomber4
            bomber4.setSize((114*getBounds().width)/450, (100*getBounds().height)/300);
            ImageIcon sprite4 = new ImageIcon(getClass().getResource("bomberUnknown4.png"));
            bomber4.setIcon(escalarImagen(sprite4,bomber4.getWidth(),bomber4.getHeight()));
            bomber4.setLocation(getBounds().width/2+getBounds().width/25, getBounds().height/2);
            //Reescaler opciones
            opciones.setSize((50*getBounds().width)/900, (100*getBounds().height)/600);
            for(int i=0; i<3; i++){
                opciones.getComponent(i).setFont(new Font("Arial", Font.BOLD, (5*opciones.getSize().width/50)));
            }
            opciones.setLocation(getBounds().width-getBounds().width/10, getBounds().height/20);
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

    //CONTROLADOR KeyListener
    private class ControladorTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if(keyCode==KeyEvent.VK_ESCAPE){
                setVisible(false);
                menu.end();
            }
            else if(keyCode==KeyEvent.VK_SPACE){
                setVisible(false);
                menu.iniciarJuego();
            }
            else if (keyCode==KeyEvent.VK_M){/*Ajustar musica*/}
            else if (keyCode==KeyEvent.VK_O){
                if(opciones.isVisible()){opciones.setVisible(false);}
                else{opciones.setVisible(true);}
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ControladorRaton implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getComponent()==bomber1){System.out.println("bomber1");menu.cambiarJugador("white");}
            else if(e.getComponent()==bomber2){System.out.println("bomber2");menu.cambiarJugador("black");}

            //Botones opciones
            else if (e.getComponent()==opciones.getComponent(0)){System.out.println("Pantalla Classic");}
            else if (e.getComponent()==opciones.getComponent(1)){System.out.println("Pantalla Soft");}
            else if (e.getComponent()==opciones.getComponent(2)){System.out.println("Pantalla Empty");}
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getComponent()==bomber1||e.getComponent()==bomber2
             ||e.getComponent()==bomber3||e.getComponent()==bomber4) {
                JLabel pBomber = (JLabel) e.getComponent();
                char pNum = e.getComponent().getName().charAt(6);
                ImageIcon sprite = new ImageIcon(getClass().getResource("bomber" + pNum + ".png"));
                pBomber.setIcon(escalarImagen(sprite, pBomber.getWidth(), pBomber.getHeight()));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getComponent()==bomber1||e.getComponent()==bomber2
             ||e.getComponent()==bomber3||e.getComponent()==bomber4) {
                JLabel pBomber = (JLabel) e.getComponent();
                char pNum = e.getComponent().getName().charAt(6);
                ImageIcon sprite = new ImageIcon(getClass().getResource("bomberUnknown" + pNum + ".png"));
                pBomber.setIcon(escalarImagen(sprite, pBomber.getWidth(), pBomber.getHeight()));
            }
        }
    }
}
