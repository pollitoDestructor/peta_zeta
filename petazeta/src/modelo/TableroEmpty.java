package modelo;

import patrones.FactoryCasillas;

public class TableroEmpty extends Tablero{

	private TableroEmpty() {
		super();
	}
	
	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new TableroEmpty();
		}
		return miTablero;
	}

	@SuppressWarnings("deprecation")
	public void ponerBloques() {
		setChanged();
		notifyObservers(new Object[]{"PonerFondo", "Empty"});
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				setChanged();
				mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
				notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
			}
		}
	}
}
