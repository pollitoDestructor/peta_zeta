package petazeta;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
@SuppressWarnings("deprecation")

public class TableroVisual extends JFrame implements Observer{

	private static final long serialVersionUID = -1526416068663302084L;
	private JPanel grid;


	/**
	 * Create the frame.
	 */
	public TableroVisual() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 550);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		grid = new JPanel();
		contentPane.add(grid);
		grid.setLayout(new GridLayout(11, 17, 0, 0));
		
		JLabelCasilla labelCasilla;
		for(int m = 0; m < 17; m++) {
			for(int n = 10; n >= 0; n--) {
				labelCasilla = new JLabelCasilla(m,n);
				labelCasilla.setBackground(new Color(0,0,255));
				labelCasilla.setOpaque(true);
				grid.add(labelCasilla,n,m);
			}
		}
		
		Tablero.getTablero().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//La idea es que cada vez que tablero hace una casilla llama al update diciendo que tipo
		System.out.println("update");
		if(o instanceof Tablero) {
			grid.validate();
			grid.repaint();
		}
	}
}
