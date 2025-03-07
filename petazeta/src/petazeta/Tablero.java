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
//		notifyObservers(new Object[] {1, 0,0,(String)""});
//		setChanged();
		return disponible;
	}
	
	
	public void ponerBloques() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[i].length; j++) {
				setChanged();
				if(i%2==1&&j%2==1) {
					mapa[i][j] = new BloqueDuro(j,i);
					super.notifyObservers(new Object[] {0, j,i,2});

					
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.7) {
						mapa[i][j] = new BloqueBlando(j,i);
						super.notifyObservers(new Object[] {0, j,i,1});
						
						
					} else {
						mapa[i][j] =  new Casilla(j,i);

						super.notifyObservers(new Object[] {0, j,i,0});

						
					}
				} else {
					mapa[i][j] = new Casilla(j,i);

					super.notifyObservers(new Object[] {0, j,i,0});

					
				}
			}
		}

		
	}
	
	public void detonarBomba(int pX, int pY, String pTipo) {
		if(pX - 1 >= 0) {	//Comprueba que no se salga del tablero
			switch (mapa[pY][pX-1].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY][pX-1].destruir();
				mapa[pY][pX-1] = new Casilla(pY,pX-1);
				setChanged();
				super.notifyObservers(new Object[] {0, pX-1,pY,0});
				break;
			case "Bomba":
				//Decidir si detona o ignora
				break;
			default:
				//Mostrar fueguito uff mamiç
				break;
			}
		}
		if(pX + 1 < mapa[0].length) {	//Comprueba que no se salga del tablero
			switch (mapa[pY][pX+1].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY][pX+1].destruir();
				mapa[pY][pX+1] = new Casilla(pY,pX+1);
				setChanged();
				super.notifyObservers(new Object[] {0, pX+1,pY,0});
				break;
			case "Bomba":
				//Decidir si detona o ignora
				break;
			default:
				//Mostrar fueguito uff mami
				break;
			}
		}
		if(pY - 1 >= 0) {	//Comprueba que no se salga del tablero
			switch (mapa[pY-1][pX].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY-1][pX].destruir();
				mapa[pY-1][pX] = new Casilla(pY-1,pX);
				setChanged();
				super.notifyObservers(new Object[] {0,pX, pY-1,0});
				break;
			case "Bomba":
				//Decidir si detona o ignora
				break;
			default:
				//Mostrar fueguito uff mami
				break;
			}
		}
		if(pX + 1 < mapa.length) {	//Comprueba que no se salga del tablero
			switch (mapa[pY+1][pX].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY+1][pX].destruir();
				mapa[pY+1][pX] = new Casilla(pY+1,pX);
				setChanged();
				super.notifyObservers(new Object[] {0,pX, pY+1,0});
				break;
			case "Bomba":
				//Decidir si detona o ignora
				break;
			default:
				//Mostrar fueguito uff mami
				break;
			}
		}
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
						System.out.print("·");
						break;
				}
			}
			System.out.println();
        	}
	}
}

