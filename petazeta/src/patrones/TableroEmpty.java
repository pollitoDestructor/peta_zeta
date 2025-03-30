package patrones;

import modelo.Casilla;

public class TableroEmpty implements StrategyTablero{

	@Override
	public Casilla ponerBloques(int x, int y) {
		return FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", x, y);
	}

	@Override
	public String tipoTablero() {
		return "Empty";
	}
	

}
