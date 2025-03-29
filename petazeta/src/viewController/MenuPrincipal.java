package viewController;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.GestorMenuPrincipal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MenuPrincipal extends JFrame /*implements Observer*/{

    private static final long serialVersionUID = -1526416068663302084L;
    private JPanel contentPane;
    private GestorMenuPrincipal menu;
    private Controlador controlador = null;

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
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel() {   //AÑADIR SPRITE DE FONDO AL MENU
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("back.png"));
                ImageIcon explosion = new ImageIcon(getClass().getResource("background3.png"));
                ImageIcon logo = new ImageIcon(getClass().getResource("title.png"));
                ImageIcon boss2 = new ImageIcon(getClass().getResource("boss2.png"));
                ImageIcon boss3 = new ImageIcon(getClass().getResource("boss3.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                g.drawImage(explosion.getImage(), 0, 0, getWidth(), getHeight(), this);
                g.drawImage(logo.getImage(), (getWidth()-170)/4, 0, (getWidth()+170)/2, getHeight()/3, this);
                g.drawImage(boss2.getImage(), getWidth()/3, (getHeight()-50)/3, getWidth()/3, (getHeight()+50)/2, this);
                g.drawImage(boss3.getImage(), getWidth()-getWidth()/12, getHeight()/3, getWidth()/7, getHeight()/2, this);
                g.drawString("<space> to start, <m>usic, <o>ptions & <esc> to exit", getWidth()/4, getHeight()-10);
            }
        };

        setContentPane(contentPane);

        this.addKeyListener(getControlador());
        this.menu=GestorMenuPrincipal.getMenu();
    }

    private Controlador getControlador() {
        if (controlador == null) {
            controlador = new Controlador();
        }
        return controlador;
    }
    //CONTROLADOR KeyListener
    private class Controlador implements KeyListener {

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
            else if (keyCode==KeyEvent.VK_O){/*Opciones*/}
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
