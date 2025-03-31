package patrones;

import modelo.Doria;
import modelo.Enemigo;
import modelo.Globo;
import modelo.Pass;

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
        switch(pType) {
            case "Globo":
                myEnemigo = new Globo(pX, pY);
                break;
            case "Doria":
                myEnemigo = new Doria(pX, pY);
                break;
            case "Pass":
                myEnemigo = new Pass(pX, pY);
                break;
        }
        return myEnemigo;
    }
}
