// StateGanar.java
package patrones;

import modelo.Tablero;

public class StateGanar implements StateJugador {
    @Override
    public void manejarEstado() {
        System.out.println("¡Has ganado!");
        Tablero.getTablero().pantallaFinal(true); // Detener enemigos
    }
}
