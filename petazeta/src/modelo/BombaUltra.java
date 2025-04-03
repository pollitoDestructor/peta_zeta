package modelo;

import java.util.Timer;
import java.util.TimerTask;

public class BombaUltra extends Bomba {
    private static final int PERIODO = 5; // Explota después de 3 segundos

    public BombaUltra(int a, int b) {
        super(a, b); // Llama al constructor de la clase base Bomba
    }
}
