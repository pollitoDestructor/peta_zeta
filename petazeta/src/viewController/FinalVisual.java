package viewController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.GestorFinalVisual;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("deprecation")
public class FinalVisual extends JFrame implements Observer{

	private static final long serialVersionUID = -1526416068663302084L;
	private JPanel contentPane;
	private JLabel Subtitulo;
	private JLabel Titulo;
	private boolean estadoPartida;
	private JLabel lblPartidaEstado;
	private JLabel espacio;
	private Controlador controlador = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinalVisual frame = new FinalVisual(true);
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
	public FinalVisual(boolean pPartida) {
		estadoPartida= pPartida; //Atrbiuto para el texto visual
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 1, 0, 0));
		contentPane.add(getEspacio());
		contentPane.add(getTitulo());
		contentPane.add(getLblPartidaEstado());
		contentPane.add(getSubtitulo());
		
		GestorFinalVisual.getFinal(pPartida).addObserver(this);
		
		setVisible(true);
	}

	
	private JLabel getTitulo() {
		if (Titulo == null) {
			Titulo = new JLabel("Game Over");
			Titulo.setForeground(new Color(255, 0, 0));
			Titulo.setFont(new Font("Cambria", Font.BOLD, 45));
			Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return Titulo;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GestorFinalVisual)
		{
			Object[] datos = (Object[]) arg;
			int accion = (int) datos[0];
			if(accion == 0)
			{
				int r = (int) datos[1];
				int g = (int) datos[2];
				int b = (int) datos[3];
				Subtitulo.setForeground(new Color(r,g,b));
			}
			else if (accion == 1)
			{
				String mensaje = (String) datos [1];
				lblPartidaEstado.setText(mensaje);
			}
			
		}
		
	}
	private JLabel getLblPartidaEstado() {
		if (lblPartidaEstado == null) {
			lblPartidaEstado = new JLabel();
			
			if(estadoPartida) lblPartidaEstado.setForeground(new Color(0, 255, 0));
			else lblPartidaEstado.setForeground(new Color(255, 0, 0));
			
			lblPartidaEstado.setHorizontalAlignment(SwingConstants.CENTER);
			lblPartidaEstado.setFont(new Font("OCR A Extended", Font.PLAIN, 30));
		}
		return lblPartidaEstado;
	}
	
	private JLabel getEspacio() {
		if (espacio == null) {
			espacio = new JLabel("");
			espacio.setFont(new Font("Tahoma", Font.PLAIN, 5));
		}
		return espacio;
	}
	
	private JLabel getSubtitulo() {
		if (Subtitulo == null) {
			Subtitulo = new JLabel("Click to return");
			Subtitulo.setForeground(new Color(200,200,200));
			Subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
			Subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
			
			Subtitulo.addMouseListener(getControlador());
		}
		return Subtitulo;
	}
	
	private Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}
	//2- CONTROLADOR : listener
	private class Controlador implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Click!");
            GestorFinalVisual.getFinal(true).detenerTimer(); // Asegúrate de que este método existe
            setVisible(false);
        }
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
	}
}
