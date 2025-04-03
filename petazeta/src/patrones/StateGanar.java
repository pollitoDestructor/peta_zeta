// StateGanar.java
package patrones;
import modelo.GestorFinalVisual;
import modelo.Tablero;
import viewController.FinalVisual;


public class StateGanar implements StateJugador {
    @Override
    public void manejarEstado() {
        System.out.println("¡Has ganado!");
        Tablero.getTablero().pantallaFinal(true); // Detener enemigos
    }
}
