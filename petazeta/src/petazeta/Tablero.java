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
		
		if(mapa[pY][pX].tipoCasilla().equals("Explosion"))
		{
			System.out.println("Explota muere Dios qu� horror.");
			//TODO update
			pantallaFinal(false);
		
		}
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

	    // Direcciones: arriba, abajo, izquierda, derecha
	    int[] dx = {0,0, 0, -1, 1};
	    int[] dy = {0,-1, 1, 0, 0};

	    for (int i = 0; i < 5; i++) {
	        int newX = pX + dx[i];
	        int newY = pY + dy[i];

	        if (esValido(newX, newY)) {
	            procesarExplosion(newX, newY);
	            if(Jugador.getJugador().estaEnCasilla(newX, newY))
	            {
	            	pantallaFinal(false);
	            }
	        }
	    }
	}

	// Método para verificar si la posición está dentro del mapa
	private boolean esValido(int x, int y) {
	    return x >= 0 && x < mapa[0].length && y >= 0 && y < mapa.length;
	}

	// Método auxiliar para manejar la explosión en una casilla
	private void procesarExplosion(int x, int y) {
	    String tipo = mapa[y][x].tipoCasilla();

	    switch (tipo) {
	        case "BloqueBlando":
	            mapa[y][x].destruir();
	        case "Bomba": // También explota si es bomba
	        case "Casilla": // Cualquier otro caso que no sea bloque duro
	            setChanged();
	            mapa[y][x] = new Explosion(y, x);
	            notifyObservers(new Object[] {0, y, x, 4});
	            break;
	        case "BloqueDuro":
	            // No hace nada, la explosión no pasa a través de un bloque duro.
	            break;
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
	
	private void pantallaFinal(boolean pEstadoPartida)
	{
		setChanged();
		
		notifyObservers(new Object[] {1});
		FinalVisual fv = new FinalVisual(pEstadoPartida);
        fv.setVisible(true);
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