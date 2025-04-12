package patrones;

import modelo.Tablero;

public class StateMuerto implements StateJugador {
    @Override
    public void manejarEstado() {
        System.out.println("El jugador ha muerto, fin del juego.");
        Tablero.getTablero().pantallaFinal(false);  // Marcamos la partida como finalizada
//       new FinalVisual(false).setVisible(true);  // Mostramos la pantalla de derrota
    }
}
