package petazeta;

import javax.swing.JLabel;

public class JLabelBDuro extends JLabel {

	private int cX;
	private int cY;
	
	public JLabelBDuro(int pCX, int pCY) {
		super();
		cX = pCX;
		cY = pCY;
	}
	
	public int getCX() {
		return cX;
	}
	public int getCY() {
		return cY;
	}

}
