package petazeta;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelBomba extends JLabel {

    private int cX;
    private int cY;

    public JLabelBomba(int pCX, int pCY, ImageIcon icon) {
        super(icon); // Llama al constructor de JLabel con el ícono
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
