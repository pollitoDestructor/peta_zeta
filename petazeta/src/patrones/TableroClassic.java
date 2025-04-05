package patrones;

import java.util.Random;

import modelo.Casilla;
import modelo.Enemigo;

public class TableroClassic extends TableroAbstract { //TODO nombre a TableroClassic

	@Override
	public Casilla ponerBloques(int i, int j) {
		Random rng = new Random();
		Casilla c;
		if(j%2 == 1 && i%2 == 1) {
			c = genCasilla("BloqueDuro", i, j);
		} else if (j > 1 || i > 1){
			if(rng.nextDouble() <= 0.7) {
				c = genCasilla("BloqueBlando", i, j);				
			} else {
				c = genCasilla("Casilla", i, j);
			}
		} else {
			c = genCasilla("Casilla", i, j);
		}
		return c;
	}

	@Override
	public Enemigo ponerEnemigos(int i, int j) {
		Enemigo myEnemigo;
		myEnemigo = genEnemigo("Globo", i, j);

		return myEnemigo;
	}

	@Override
	public String tipoTablero() {
		return "Classic";
	}

}
