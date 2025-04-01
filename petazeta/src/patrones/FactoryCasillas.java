package patrones;

import java.util.Random;

import modelo.BloqueBlando;
import modelo.BloqueDuro;
import modelo.Bomba;
import modelo.BombaUltra;
import modelo.Casilla;
import modelo.Explosion;

public class FactoryCasillas {
	private static FactoryCasillas miFC;
	private FactoryCasillas() {
		
	}
	public static FactoryCasillas getFactoryCasillas() {
		if(miFC == null) {
			miFC = new FactoryCasillas();
		}
		return miFC;
	}
	
	public Casilla genCasilla(String pType, int pX, int pY) {
		Casilla c = null;
		switch(pType) {
			case "Casilla":
				c = new Casilla(pX,pY);
			break;
			case "Explosion":
				c = new Explosion(pX,pY);
			break;
			case "BloqueBlando":
				c = new BloqueBlando(pX,pY);
			break;
			case "BloqueDuro":
				c = new BloqueDuro(pX,pY);
			break;
			case "Bomba":
				c = new Bomba(pX,pY);
			break;
			case "BombaUltra":
				c = new BombaUltra(pX,pY);
			break;
		}
		return c;
	}
}
