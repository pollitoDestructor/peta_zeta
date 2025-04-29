package patrones;

import modelo.Jugador;
import modelo.Tablero;

public class PonerBombaUltra implements StrategyPonerBomba {
	
	@Override
	public String getTipoExplosion() {
		return "Explosion3";
	}
	
	@Override
	public String getTipoBomba() {
		return "BombaUltra";
	}
	
	@Override
	public void detonarBomba(int pX, int pY) {
	    Tablero tab = Tablero.getTablero();
	    
	    Jugador.getJugador().addBomba();

	    int[] dx = {0, 0, 0, -1, 1};
	    int[] dy = {0, -1, 1, 0, 0};
		int combo = 1;

	    for (int i = 0; i < 5; i++) {
	        for (int j = 1; j <= 20; j++) {
	            int newX = pX + dx[i] * j;
	            int newY = pY + dy[i] * j;

	            // 🛑 Verificación antes de acceder a `mapa[y][x]`
	            if (!tab.esValido(newX, newY)) break; 
	            if (tab.esDuro(newX, newY)) break; 

	            // Si el jugador está en la explosión, cambiar estado a muerto
	            if (Jugador.getJugador().estaEnCasilla(newX, newY)) {
	                tab.changeStateString("Muerto");
	            }

	            combo = tab.procesarExplosion(newX, newY, pX, pY, combo);
	        }
	    }

	    tab.verificarVictoria();
	}
	}