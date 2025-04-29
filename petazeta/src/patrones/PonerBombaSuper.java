package patrones;

import modelo.Jugador;
import modelo.Tablero;

public class PonerBombaSuper implements StrategyPonerBomba {
	
	@Override
	public String getTipoExplosion() {
		return "Explosion1";
	}
	
	@Override
	public String getTipoBomba() {
		return "BombaSuper";
	}
	
	@Override
	public void detonarBomba(int pX, int pY) {
		Tablero tab = Tablero.getTablero();

		Jugador.getJugador().addBomba();
		
		int[] dx = {0, 0, 0, -1, 1};
		int[] dy = {0, -1, 1, 0, 0};
		int combo = 1; //multiplicador de enemigos eliminados con una sola bomba

		for (int i = 0; i < 5; i++) {
			int newX = pX + dx[i];
			int newY = pY + dy[i];

			if (tab.esValido(newX, newY)) 
			{
				combo = tab.procesarExplosion(newX, newY, pX, pY, combo);
				if (Jugador.getJugador().estaEnCasilla(newX, newY)) tab.changeStateString("Muerto");  // Cambiamos a estado de muerte
			}
		}

		// Verificar victoria después de todas las explosiones
		tab.verificarVictoria();
	}
}
