package patrones;

import modelo.Casilla;
import modelo.Enemigo;

import java.util.Random;

public class TableroEmpty implements StrategyTablero{

	@Override
	public Casilla ponerBloques(int x, int y) {
		return FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", x, y);
	}

	@Override
	public Enemigo ponerEnemigos(int i, int j) {
		Random rng = new Random();
		int numEn = rng.nextInt(3);
		String Tipo = null;
		switch (numEn) {
			case 0:
				Tipo = "Globo";
				break;
			case 1:
				Tipo = "Doria";
				break;
			case 2:
				Tipo = "Pass";
				break;
		}
		Enemigo myEnemigo;
		myEnemigo = FactoryEnemigos.getFactoryEnemigos().genEnemigo(Tipo, i, j);

		return myEnemigo;
	}

	@Override
	public String tipoTablero() {
		return "Empty";
	}
	

}
