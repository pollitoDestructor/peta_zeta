package petazeta;

import java.util.Observable;
import java.util.Random; 

@SuppressWarnings("deprecation")
public class Tablero extends Observable{
	private static Tablero miTablero;
	private Casilla[][] mapa;
	private Random rng = new Random();
	
	private Tablero() { 
		
		
		mapa = new Casilla[11][17]; //NOTA: las matrices funcionan mediante Object[y][x]
//		ponerBloques();
	}
	
	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new Tablero();
			
		}
		return miTablero;
	}
	
	public boolean casillaDisponible(int pX, int pY) {
		boolean disponible = false;
		if(pX>=0 && pX<mapa[0].length && pY>=0 && pY<mapa.length) {
			disponible = !mapa[pY][pX].estaOcupada();
		}
		if(mapa[pY][pX].tipoCasilla().equals("Explosion"))
		{
			System.out.println("Explota muere Dios qué horror.");
			//TODO update
			setChanged();
			
			notifyObservers(new Object[] {1});
			FinalVisual fv = new FinalVisual(false);
	        fv.setVisible(true);
		}
//		notifyObservers(new Object[] {1, 0,0,(String)""});
//		setChanged();
		return disponible;
	}
	
	
	public void ponerBloques() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[i].length; j++) {
				setChanged();
				if(i%2==1&&j%2==1) {
					mapa[i][j] = new BloqueDuro(i,j);
					notifyObservers(new Object[] {0, i,j,2});

					
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.7) {
						mapa[i][j] = new BloqueBlando(i,j);
						notifyObservers(new Object[] {0, i,j,1});
						
						
					} else {
						mapa[i][j] =  new Casilla(i,j);

						notifyObservers(new Object[] {0, i,j,0});
					}
				} else {
					mapa[i][j] = new Casilla(i,j);

					notifyObservers(new Object[] {0, i,j,0});
				}
			}
		}

		
	}
	
	public void detonarBomba(int pX, int pY, String pTipo) {
		setChanged();
		mapa[pY][pX] = new Explosion(pY,pX);
		notifyObservers(new Object[] {0, pY, pX, 4});
		
		if(pY - 1 >= 0) { // Comprueba que no se salga del tablero
		    switch (mapa[pY - 1][pX].tipoCasilla()) {
		        case "BloqueBlando":
		            // mostrar explosion (a la hora de la parte visual)
		            mapa[pY - 1][pX].destruir();
		            setChanged();
		            mapa[pY - 1][pX] = new Explosion(pY - 1, pX);
		            notifyObservers(new Object[] {0, pY - 1, pX, 4});
		            break;
		        case "Bomba":
		            // Decidir si detona o ignora
		        	setChanged();
		            mapa[pY - 1][pX] = new Explosion(pY - 1, pX);
		            notifyObservers(new Object[] {0, pY - 1, pX, 4});
		            break;
		        case "BloqueDuro":
		            // Evitar explosion bloque
		            break;
		        default:
		            setChanged();
		            mapa[pY - 1][pX] = new Explosion(pY - 1, pX);
		            notifyObservers(new Object[] {0, pY - 1, pX, 4});
		            break;
		    }
		}
		if(pY + 1 < mapa.length) { // Comprueba que no se salga del tablero
		    switch (mapa[pY + 1][pX].tipoCasilla()) {
		        case "BloqueBlando":
		            // mostrar explosion (a la hora de la parte visual)
		            mapa[pY + 1][pX].destruir();
		            setChanged();
		            mapa[pY + 1][pX] = new Explosion(pY + 1, pX);
		            notifyObservers(new Object[] {0, pY + 1, pX, 4});
		            break;
		        case "Bomba":
		        	setChanged();
		            mapa[pY + 1][pX] = new Explosion(pY + 1, pX);
		            notifyObservers(new Object[] {0, pY + 1, pX, 4});
		            // Decidir si detona o ignora
		            break;
		        case "BloqueDuro":
		            // Evitar explosion bloque
		            break;
		        default:
		        	setChanged();
		            mapa[pY + 1][pX] = new Explosion(pY + 1, pX);
		            notifyObservers(new Object[] {0, pY + 1, pX, 4});
		            break;
		    }
		}
		if(pX - 1 >= 0) { // Comprueba que no se salga del tablero
		    switch (mapa[pY][pX - 1].tipoCasilla()) {
		        case "BloqueBlando":
		            // mostrar explosion (a la hora de la parte visual)
		            mapa[pY][pX - 1].destruir();
		            setChanged();
		            mapa[pY][pX - 1] = new Explosion(pY, pX - 1);
		            notifyObservers(new Object[] {0, pY, pX - 1, 4});
		            break;
		        case "Bomba":
		            // Decidir si detona o ignora
		        	setChanged();
		            mapa[pY][pX - 1] = new Explosion(pY, pX - 1);
		            notifyObservers(new Object[] {0, pY, pX - 1, 4});
		            break;
		        case "BloqueDuro":
		            // Evitar explosion bloque
		            break;
		        default:
		        	setChanged();
		            mapa[pY][pX - 1] = new Explosion(pY, pX - 1);
		            notifyObservers(new Object[] {0, pY, pX - 1, 4});
		            break;
		    }
		}
		if(pX + 1 < mapa[0].length) { // Comprueba que no se salga del tablero
		    switch (mapa[pY][pX + 1].tipoCasilla()) {
		        case "BloqueBlando":
		            // mostrar explosion (a la hora de la parte visual)
		            mapa[pY][pX + 1].destruir();
		            setChanged();
		            mapa[pY][pX + 1] = new Explosion(pY, pX + 1);
		            notifyObservers(new Object[] {0, pY, pX + 1, 4});
		            break;
		        case "Bomba":
		            // Decidir si detona o ignora
		        	setChanged();
		            mapa[pY][pX + 1] = new Explosion(pY, pX + 1);
		            notifyObservers(new Object[] {0, pY, pX + 1, 4});
		            break;
		        case "BloqueDuro":
		            // Evitar explosion bloque
		            break;
		        default:
		        	setChanged();
		            mapa[pY][pX + 1] = new Explosion(pY, pX + 1);
		            notifyObservers(new Object[] {0, pY, pX + 1, 4});
		            break;
		    }
		}
	}
	
	
	public void ponerBomba(int pX, int pY)
	{
		setChanged();
		mapa[pY][pX] = new Bomba(pX,pY); //Pone la bomba en esas coords
		notifyObservers(new Object[] {0,pY, pX,3});
	}
	
	public void explosionTerminada(int pX, int pY)
	{
        setChanged();
        mapa[pY][pX] = new Casilla(pY, pX);
        notifyObservers(new Object[] {0, pY, pX, 0});
	}
	
	
	public void printMap() {
		setChanged();
		super.notifyObservers(new Object[] {1, 0,0,(String)""});
		for (int j = 0; j < mapa.length; j++) {
			for (int i = 0; i < mapa[j].length; i++) {
				switch (mapa[j][i].tipoCasilla()) {
					case "BloqueDuro":
						System.out.print("#");
						break;
					case "BloqueBlando":
						System.out.print("+");
						break;
					case "Casilla":
						System.out.print("Â·");
						break;
				}
			}
			System.out.println();
        	}
	}
	
}