package petazeta;

import java.util.Random;

public class Tablero {
	private static Tablero miTablero;
	private String[][] mapa;
	private Random rng = new Random();
	
	private Tablero() {
		mapa = new String[17][17];
		ponerBloques();
	}
	
	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new Tablero();
		}
		return miTablero;
	}
	
//	public boolean casillaDisponible(int pX, int pY) {
//		return !mapa[pX][pY].estaOcupada();
//	}
	
	private void ponerBloques() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[i].length; j++) {
				if(i%2==1&&j%2==1) {
					mapa[i][j] = "#";
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.8) {
						mapa[i][j] = "+";
					} else {
						mapa[i][j] = "·";
					}
				} else {
					mapa[i][j] = "·";
				}
			}
		}
	}
	
	public void printMap()
    {
    	String matrix[][] = this.mapa;
        for (int j = 0; j < matrix.length; j++)
        {
	      for (int i = 0; i < matrix[j].length; i++)
	      {
			System.out.print(matrix[i][j]);
		  }
		  System.out.println();
        }
	}
}