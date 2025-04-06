package patrones;

import modelo.Jugador;
import modelo.Tablero;

public class PonerBombaNormal implements StrategyPonerBomba {
	
	@Override
	public String getTipoExplosion() {
		return "Explosion1";
	}
	
	@Override
	public String getTipoBomba() {
		return "Bomba";
	}
	
	@Override
	public void detonarBomba(int pX, int pY) {
		Tablero tab = Tablero.getTablero();

		Jugador.getJugador().addBomba();
		
		int[] dx = {0, 0, 0, -1, 1};
		int[] dy = {0, -1, 1, 0, 0};

		for (int i = 0; i < 5; i++) {
			int newX = pX + dx[i];
			int newY = pY + dy[i];

			if (tab.esValido(newX, newY)) 
			{
				tab.procesarExplosion(newX, newY, pX,pY);
				if (Jugador.getJugador().estaEnCasilla(newX, newY)) tab.changeState(new StateMuerto());  // Cambiamos a estado de muerte
			}
		}

		// Verificar victoria después de todas las explosiones
		tab.verificarVictoria();
	}
}
