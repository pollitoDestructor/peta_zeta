package patrones;

import modelo.Enemigo;
import modelo.EnemigoNormal;

public class FactoryEnemigos {
    private static FactoryEnemigos miFE;

    private FactoryEnemigos() {
    }

    public static FactoryEnemigos getFactoryEnemigos() {
        if (miFE == null) {
            miFE = new FactoryEnemigos();
        }
        return miFE;
    }

    public Enemigo genEnemigo(String pType, int pX, int pY) {
        Enemigo myEnemigo = null;
        if (pType.equals("EnemigoNormal")) {
            myEnemigo = new EnemigoNormal(pX, pY);
        }
        return myEnemigo;
    }
}
