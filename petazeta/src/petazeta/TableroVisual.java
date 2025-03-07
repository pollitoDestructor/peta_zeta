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
	private JPanel contentPane;
	private JPanel grid;
	private JLabel[][] mapa;



	/**
	 * Create the frame.
	 */
	public TableroVisual() {
		mapa = new JLabel[17][11]; //NOTA: las matrices funcionan mediante Object[y][x]
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 550);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		grid = new JPanel();
		contentPane.add(grid);
		grid.setLayout(new GridLayout(11, 17, 0, 0));
		
//		mapa = new JLabel[17][11]; //NOTA: las matrices funcionan mediante Object[y][x]
//		setLayout(new GridLayout(11,17));
//		for(int m = 0; m < 17; m++) {
//			for(int n = 0; n < 11; n++) {
//			   mapa[m][n] = new JPanel();
//			   add(mapa[m][n]);
//			}
//		}
		
		Tablero.getTablero().addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//La idea es que cada vez que tablero hace una casilla llama al update diciendo que tipo
		System.out.println("update");
		if(o instanceof Tablero) {
			JLabelCasilla labelCasilla;
			JLabelBBlando labelBBlando;
			JLabelBDuro labelBDuro;
			JLabelBomba labelBomba;
			
			System.out.println("tab");
			Object[] param = (Object[])arg; //[accion:String, pX:int, pY:int, tipoBloque:String]
			int y = (int)param[2];
			int x = (int)param[1];
			int yInv = 11-y;		//para que se imprima con la orientacion correcta; no funciona :/
			System.out.println((int)param[0]);
			if ((int)param[0]==0) { //comprueba la accion que se quiere realizar (poner una casilla, detonar una bomba, mostrar el mapa, etc.)
				System.out.println("switch");
				System.out.println((int)param[3]);
				switch ((int)param[3]) {
				case 0:
					System.out.println("casilla intento");
					
					labelCasilla = new JLabelCasilla((int)param[1],(int)param[2]);
					labelCasilla.setBackground(new Color(255,255,255));
					labelCasilla.setOpaque(true);
					mapa[(int)param[1]][(int)param[2]] = labelCasilla;
					grid.add(labelCasilla,yInv,x);
					System.out.println("casilla puesta");
					break;
				case 1:
					
					labelBBlando = new JLabelBBlando((int)param[1],(int)param[2]);
					labelBBlando.setBackground(new Color(255,100,100));
					labelBBlando.setOpaque(true);
					mapa[(int)param[1]][(int)param[2]] = labelBBlando;
					grid.add(labelBBlando,yInv,x);
					break;
				case 2:
					
					labelBDuro = new JLabelBDuro((int)param[1],(int)param[2]);
					labelBDuro.setBackground(new Color(0,0,0));
					labelBDuro.setOpaque(true);
					mapa[(int)param[1]][(int)param[2]] = labelBDuro;
					grid.add(labelBDuro,yInv,x);
					break;
				case 3:
					labelBomba = new JLabelBomba((int)param[1],(int)param[2]);
					labelBomba.setBackground(new Color(0,0,0));
					labelBomba.setOpaque(true);
					mapa[(int)param[1]][(int)param[2]] = labelBomba;
					grid.add(labelBomba,yInv,x);
					break;
				}
			} 
//			else if((int)param[0]==2){
//				JPanel contentPane = new JPanel();
//				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//				setContentPane(contentPane);
//				contentPane.setLayout(new BorderLayout(0, 0));
//				
//				grid = new JPanel();
//				contentPane.add(grid, BorderLayout.CENTER);
//				grid.setLayout(new GridLayout(11, 17, 0, 0));
//				
//				for(int i = 0;i<17;i++) {
//					for(int j = 0;j<11;j++) {
//						System.out.println("act mapa");
////						if(mapa[i][j] != null) {
//							grid.add(mapa[i][j],10-j,i);
////						} 
//					}
//				}
//			}
//			grid.validate();
//			grid.repaint();
		}
	}
}
