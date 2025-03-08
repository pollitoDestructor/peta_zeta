package petazeta;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelCasilla extends JLabel implements Observer{

	private int cX;
	private int cY;
	
	
	public JLabelCasilla(int pCX, int pCY) {
		super();
		cX = pCX;
		cY = pCY;
		//Tablero.getTablero().addObserver(this);
	}
	
	public int getCX() {
		return cX;
	}
	public int getCY() {
		return cY;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//La idea es que cada vez que tablero hace una casilla llama al update diciendo que tipo
		/*System.out.println("update");
		if(o instanceof Tablero) {
			Object[] param = (Object[])arg; //[accion:int, pX:int, pY:int, tipoBloque:String]
			int y = 10-(int)param[2];
			int x = (int)param[1];
			if ((int)param[0]==0&&y==cY&&x==cX) { //comprueba la accion que se quiere realizar (poner una casilla, detonar una bomba, mostrar el mapa, etc.)
				System.out.println("switch");
				System.out.println((int)param[3]);
				switch ((int)param[3]) {
				case 0:
					setBackground(new Color(252, 208, 126)); //Casilla vacia
					break;
				case 1:
					setBackground(new Color(245, 115, 59)); //Bloque Blando
					break;
				case 2:
					setBackground(new Color(94, 16, 4)); //Bloque Duro
					break;
				case 3:
					setBackground(new Color(0,0,0)); //Bomba
					break;
				}
			}
		}*/
	}

}
