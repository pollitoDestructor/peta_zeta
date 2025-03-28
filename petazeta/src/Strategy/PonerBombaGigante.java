package Strategy;

import modelo.Tablero;

public class PonerBombaGigante implements StrategyPonerBomba {

	@Override
	public void ponerBomba(int posX, int posY) {
		Tablero.getTablero().ponerBomba(posX, posY);
		System.out.println("Se pone una bomba en (" + posX + ", " + posY + ")");
	}

}
