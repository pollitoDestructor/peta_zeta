package patrones;
import modelo.Tablero;
public class StateVivo implements StateJugador {
    @Override
    public void manejarEstado() {
        System.out.println("El jugador sigue vivo, la partida continúa.");

    }
}
