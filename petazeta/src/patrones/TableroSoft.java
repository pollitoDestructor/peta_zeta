package patrones;

import java.util.Random;

import modelo.Casilla;
import modelo.Enemigo;

public class TableroSoft implements StrategyTablero{

	@Override
	public Casilla ponerBloques(int i, int j) {
		Random rng = new Random();
		Casilla c;
		if (j > 1 || i > 1){
			if(rng.nextDouble() <= 0.7) {
				c = FactoryCasillas.getFactoryCasillas().genCasilla("BloqueBlando", i, j);				
			} else {
				c = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
			}
		} else {
			c = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
		}
		return c;
	}

	public Enemigo ponerEnemigos(int i, int j) {
		Enemigo myEnemigo;
		myEnemigo = FactoryEnemigos.getFactoryEnemigos().genEnemigo("Doria", i, j);

		return myEnemigo;
	}

	@Override
	public String tipoTablero() {
		return "Soft";
	}
	

}
