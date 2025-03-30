package modelo;

import patrones.FactoryCasillas;

public class TableroSoft extends Tablero {

	private TableroSoft() {
		super();
	}

	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new TableroSoft();
		}
		return miTablero;
	}

	@SuppressWarnings("deprecation")
	public void ponerBloques() {
		setChanged();
		notifyObservers(new Object[]{"PonerFondo", "Soft"});
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				setChanged();
				if (j > 1 || i > 1){
					if(rng.nextDouble() <= 0.7) {
						mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("BloqueBlando", i, j);				
					} else {
						mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
					}
				} else {
					mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
				}
				notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
			}
		}

	}

}
