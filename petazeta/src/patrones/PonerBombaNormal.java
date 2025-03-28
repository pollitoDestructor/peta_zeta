package patrones;

import modelo.Tablero;

public class PonerBombaNormal implements StrategyPonerBomba {

	@Override
	public void ponerBomba(int posX, int posY) {
		Tablero.getTablero().ponerBomba(posX, posY);
		System.out.println("Se pone una bomba en (" + posX + ", " + posY + ")");
	}

}
