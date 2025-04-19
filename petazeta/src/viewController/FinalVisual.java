package viewController;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelo.*;
import patrones.FacadeRestart;
import petazeta.Main;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
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
	private JPanel rankingPanel;
	private Controlador controlador = null;

	/**
	 * Create the frame.
	 */
	public FinalVisual() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));

		GestorFinalVisual.getFinal().addObserver(this);

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
			else if (accion == 2)
			{
				setVisible(false);
			}
			else if (accion == 3)
			{
				estadoPartida = (boolean) datos [1]; //Atrbiuto para el texto visual
				if (!estadoPartida) {
					contentPane.add(getTitulo());
				}
				contentPane.add(getLblPartidaEstado());
				if (estadoPartida) { //Mostrar el ranking si se gana
					contentPane.add(getRankingPanel());
					actualizarRankingVisual();
				}
				contentPane.add(getSubtitulo());
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

	private JPanel getRankingPanel() {
		if (rankingPanel == null) {
			rankingPanel = new JPanel();
			rankingPanel.setBackground(Color.BLACK);
			rankingPanel.setLayout(new GridLayout(6, 1)); // 5 jugadores + cabecera
			setBounds(100, 100, 550, 600);

		}
		return rankingPanel;
	}


	private void actualizarRankingVisual() {
		Ranking ranking = Ranking.getRanking();
		int[] colores = {
				255, 215, 0,     // Oro
				192, 192, 192,   // Plata
				205, 127, 50     // Bronce
		};

		getRankingPanel().removeAll();
		getRankingPanel().setLayout(new GridLayout(ranking.obtenerRankingOrdenado().size() * 2 + 1, 1));

		JLabel cabecera = new JLabel(String.format("%-4s %-6s %s", "RANK", "NAME", "SCORE"));
		cabecera.setForeground(Color.YELLOW);
		cabecera.setFont(new Font("Monospaced", Font.BOLD, 28));
		cabecera.setHorizontalAlignment(SwingConstants.CENTER);
		rankingPanel.add(cabecera);
		rankingPanel.add(crearEspacioGrande());

		int fila = 1;
		for (Map.Entry<String, Integer> entrada : ranking.obtenerRankingOrdenado().entrySet()) {
			String jugador = entrada.getKey();
			int puntos = entrada.getValue();

			String texto = String.format("%-4d %-6s %7d", fila, jugador.toUpperCase(), puntos);
			JLabel filaLabel = new JLabel(texto);
			filaLabel.setFont(new Font("Monospaced", Font.PLAIN, 22));
			filaLabel.setHorizontalAlignment(SwingConstants.CENTER);
			filaLabel.setForeground(Color.WHITE);

			if (fila <= 3) {
				filaLabel.setForeground(new Color(
						colores[(fila - 1) * 3],
						colores[(fila - 1) * 3 + 1],
						colores[(fila - 1) * 3 + 2]
				));
			}

			rankingPanel.add(filaLabel);

			if (fila < ranking.obtenerRankingOrdenado().size()) {
				rankingPanel.add(crearEspacioGrande());
			}

			fila++;
		}

		getRankingPanel().revalidate();
		getRankingPanel().repaint();
	}

	// Método auxiliar para crear espacios grandes (igual que tu separador original)
	private JLabel crearEspacioGrande() {
		JLabel espacio = new JLabel(" ");
		espacio.setPreferredSize(new Dimension(0, 40)); // Aumenté a 40 píxeles para más separación
		return espacio;
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
			GestorFinalVisual.getFinal().detenerTimer();
			FacadeRestart.getFacadeRestart().reiniciar();
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
