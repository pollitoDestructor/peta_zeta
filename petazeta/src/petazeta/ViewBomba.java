package petazeta;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ViewBomba extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabelBomba lblBomba;
    private Bomba bomba;

    public ViewBomba(int x, int y) {
        inicializar();
    }

    private void inicializar() //INICIALIZAMOS LA IMAGEN DE LA BOMBA
    {
    	/* this.setLayout(new GridBagLayout());
        lblBomba = new JLabelBomba( new ImageIcon(getClass().getResource("bomb1.png")));
        this.add(lblBomba);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                explotar();
            }
        }, 3000); // EXPLOTA
    */ //Trasladado a JLabelCasilla
    }

    private void explotar() //AQUI AL EXPLOTAR CAMBIAMOS AL GIF (solo en la bomba)
    {
        lblBomba.setIcon(new ImageIcon(getClass().getResource("blast.gif")));
        bomba.destruir();
    }
}
