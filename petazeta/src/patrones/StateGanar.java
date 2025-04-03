// StateGanar.java
package patrones;
import modelo.GestorFinalVisual;
import modelo.Tablero;
import viewController.FinalVisual;


public class StateGanar implements StateJugador {
    @Override
    public void manejarEstado() {
        System.out.println("¡Has ganado!");
        Tablero.getTablero().pantallaFinal(false); // Detener enemigos
        new FinalVisual(true).setVisible(true);  // Mostramos la pantalla de derrota
//        GestorFinalVisual.getFinal(true);         // Mostrar pantalla de "HAS GANADO"
    }
}
