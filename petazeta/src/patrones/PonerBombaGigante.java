package patrones;

import java.util.HashSet;
import java.util.Set;

import modelo.Jugador;
import modelo.Tablero;

public class PonerBombaGigante implements StrategyPonerBomba {
	
	@Override
	public String getTipo() {
		return "BombaUltra";
	}
	
	@Override
	public void detonarBomba(int pX, int pY) {
		Tablero tab = Tablero.getTablero();

		Jugador.getJugador().addBomba();

		int[] dx = {0, 0, 0, -1, 1};
		int[] dy = {0, -1, 1, 0, 0};

		for (int i = 0; i < 5; i++) {
			for(int j=1; j<=20;j++)
			{
				int newX = pX + dx[i]*j;
				int newY = pY + dy[i]*j;

				if (!tab.esValido(newX, newY)||tab.esDuro(newX, newY)) break; // Evita acceder fuera del array
				// Verificar si el jugador ha sido alcanzado
				if (Jugador.getJugador().estaEnCasilla(newX, newY)) tab.changeState(new StateMuerto());  // Cambiamos a estado de muerte
				
				tab.procesarExplosion(newX, newY, pX,pY);
			}
		}

		// Verificar victoria después de todas las explosiones
		tab.verificarVictoria();
	}
}