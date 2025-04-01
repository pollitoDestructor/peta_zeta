package patrones;

import modelo.Tablero;

public class PonerBombaNormal implements StrategyPonerBomba {

	@Override
	public void ponerBomba(int posX, int posY) {
		Tablero.getTablero().ponerBomba(posX, posY,"Bomba");
		System.out.println("Se pone una bomba en (" + posX + ", " + posY + ")");
	}

}
