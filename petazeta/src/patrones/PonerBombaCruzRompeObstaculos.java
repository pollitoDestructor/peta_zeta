package patrones;

import modelo.Jugador;
import modelo.Tablero;

public class PonerBombaCruzRompeObstaculos implements StrategyPonerBomba {

    @Override
    public String getTipoExplosion() {
        return "Explosion1";
    }

    @Override
    public String getTipoBomba() {
        return "BombaCruz";
    }

    @Override
    public void detonarBomba(int pX, int pY, int pCombo) {
        Tablero tab = Tablero.getTablero();

        Jugador.getJugador().addBomba();

        // Coordenadas de movimiento para explosión en forma de cruz (diagonal)
        int[] dx = {-1, -1, 1, 1}; // Arriba-Izquierda, Arriba-Derecha, Abajo-Izquierda, Abajo-Derecha
        int[] dy = {-1, 1, -1, 1};
        int combo = pCombo;
        
        //Esta en la propia bomba?
        if (Jugador.getJugador().estaEnCasilla(pX, pY)) {
            tab.changeStateString("Muerto");
        }
        
        tab.procesarExplosion(pX, pY, pX, pY, 1); //Donde la propia bomba

        for (int i = 0; i < 4; i++) { // Las 4 direcciones diagonales
            for (int j = 1; j <= 20; j++) { // Explosión hasta un rango de 20
                int newX = pX + dx[i] * j;
                int newY = pY + dy[i] * j;

                // Verificación antes de acceder a las casillas
                if (!tab.esValido(newX, newY)) break; // Detener si está fuera de límites

                // Si el jugador está en la explosión, cambiar estado a muerto
                if (Jugador.getJugador().estaEnCasilla(newX, newY)) {
                    tab.changeStateString("Muerto");
                }

                // Procesar la explosión en la casilla
                combo = tab.procesarExplosion(newX, newY, pX, pY, combo);
            }
        }

        tab.verificarVictoria();
    }
}
