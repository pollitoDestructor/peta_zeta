package patrones;

import modelo.Casilla;

public interface StrategyTablero {
	public Casilla ponerBloques(int x, int y);
	public String tipoTablero();
}
