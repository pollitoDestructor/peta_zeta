package patrones;

import modelo.Casilla;
import modelo.Enemigo;

public abstract class TableroAbstract implements StrategyTablero {
	
	protected TableroAbstract() {}
	
	//==================================Metodos para dependencias a Factory===================
	protected Casilla genCasilla(String pCasilla, int pX, int pY) {
		return FactoryCasillas.getFactoryCasillas().genCasilla(pCasilla, pX, pY);
	}
	
	protected Enemigo genEnemigo(String pEnemigo, int pX, int pY) {
		return FactoryEnemigos.getFactoryEnemigos().genEnemigo(pEnemigo, pX, pY);
	}
	

	public abstract Casilla ponerBloques(int x, int y);
	public abstract Enemigo ponerEnemigos(int x, int y);
	public abstract String tipoTablero();
	
	

}
