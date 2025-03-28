package patrones;

import java.util.Random;

import modelo.BloqueBlando;
import modelo.BloqueDuro;
import modelo.Bomba;
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
	
	public Casilla genCasilla(int pI, int pJ) {
		Casilla c;
		Random rng = new Random();
		if(pI%2==1&&pJ%2==1) {
			c = new BloqueDuro(pI,pJ);
		} else if (pI > 1 || pJ > 1){
			if(rng.nextDouble() <= 0.7) {
				c = new BloqueBlando(pI,pJ);				
			} else {
				c =  new Casilla(pI,pJ);
			}
		} else {
			c = new Casilla(pI,pJ);
		}
		return c;
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
		}
		return c;
	}
}
