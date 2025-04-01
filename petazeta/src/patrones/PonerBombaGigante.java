package patrones;

import modelo.Tablero;

public class PonerBombaGigante implements StrategyPonerBomba {

	@Override
	public void ponerBomba(int posX, int posY) {
		Tablero.getTablero().ponerBomba(posX, posY,"BombaUltra");
		System.out.println("Se pone una bomba en (" + posX + ", " + posY + ")");
	}
	public int valorBomba() {
		return 10;
	}
}
