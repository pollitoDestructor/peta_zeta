package patrones;

import modelo.Casilla;
import modelo.Enemigo;

public interface StrategyTablero {
	public Casilla ponerBloques(int x, int y);
	public Enemigo ponerEnemigos(int x, int y);
	public String tipoTablero();
}
