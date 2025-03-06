package petazeta;

import java.util.Random;

public class Tablero {
	private static Tablero miTablero;
	private String[][] mapaS;
	private Casilla[][] mapa;
	private Random rng = new Random();
	
	private Tablero() {
		mapaS = new String[11][17];
		ponerBloquesString();       //en caso de que se quiera probar con strings
		mapa = new Casilla[11][17]; //NOTA: las matrices funcionan mediante Object[y][x]
		ponerBloques();
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
		return disponible;
	}
	
	private void ponerBloquesString() {
		for(int i = 0; i < mapaS.length; i++) {
			for(int j = 0; j < mapaS[i].length; j++) {
				if(i%2==1&&j%2==1) {
					mapaS[i][j] = "#";
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.8) {
						mapaS[i][j] = "+";
					} else {
						mapaS[i][j] = "·";
					}
				} else {
					mapaS[i][j] = "·";
				}
			}
		}
	}
	
	private void ponerBloques() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[i].length; j++) {
				if(i%2==1&&j%2==1) {
					mapa[i][j] = new BloqueDuro(j,i);
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.8) {
						mapa[i][j] = new BloqueBlando(j,i);
					} else {
						mapa[i][j] =  new BloqueDuro(j,i);
					}
				} else {
					mapa[i][j] = new Casilla(j,i);
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
			case "Bomba":
				//Decidir si detona o ignora
			default:
				//Mostrar fueguito uff mami
			}
		}
		if(pX + 1 < mapa[0].length) {	//Comprueba que no se salga del tablero
			switch (mapa[pY][pX+1].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY][pX+1].destruir();
			case "Bomba":
				//Decidir si detona o ignora
			default:
				//Mostrar fueguito uff mami
			}
		}
		if(pY - 1 >= 0) {	//Comprueba que no se salga del tablero
			switch (mapa[pY-1][pX].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY-1][pX].destruir();
			case "Bomba":
				//Decidir si detona o ignora
			default:
				//Mostrar fueguito uff mami
			}
		}
		if(pX + 1 < mapa.length) {	//Comprueba que no se salga del tablero
			switch (mapa[pY+1][pX].tipoCasilla()) {
			case "BloqueBlando":
				//mostrar explosion (a la hora de la parte visual)
				mapa[pY+1][pX].destruir();
			case "Bomba":
				//Decidir si detona o ignora
			default:
				//Mostrar fueguito uff mami
			}
		}
	}
	
	public void printMap()
    {
    	String matrix[][] = this.mapaS;
        for (int j = 0; j < matrix.length; j++)
        {
	      for (int i = 0; i < matrix[j].length; i++)
	      {
			System.out.print(matrix[j][i]);
		  }
		  System.out.println();
        }
	}
}