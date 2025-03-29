package modelo;

public class EnemigoNormal extends Enemigo {

    public EnemigoNormal(int pX, int pY) {
        super(pX,pY);
    }

    public String tipoEnemigo()
    {
        return this.getClass().getSimpleName();
    }

    public boolean estaEnCasilla(int pX, int pY) {
        boolean esta = false;
        if(this.getPosX()==pX && this.getPosY()==pY) {
            esta = true;
        }
        return esta;
    }

    //movimiento del enemigo
    public void mover(int x, int y, String pType) {
        if (Tablero.getTablero().casillaDisponible(getPosX()+x,getPosY()+y, pType)) {
            setChanged();
            notifyObservers(new Object[] {getPosX(),getPosY(),x,y}); //le manda la pos SIN ACTUALIZAR
            setPosX(getPosX() + x);
            setPosY(getPosY() + y);
        }
        //else {System.out.println("El movimiento no se ha podido efectuar");}
    }
}
