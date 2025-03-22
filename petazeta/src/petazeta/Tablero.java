package petazeta;

import java.util.Observable;
import java.util.Random; 

@SuppressWarnings("deprecation")
public class Tablero extends Observable{
	private static Tablero miTablero;
	private Casilla[][] mapa;
	private Random rng = new Random();
	private boolean finPartida = false;
	
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
			System.out.println("Explota muere Dios quï¿½ horror.");
			this.pantallaFinal(false);
		
		}
		}
		return disponible;
	}
	
	
	public void ponerBloques() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[i].length; j++) {
				setChanged();
				if(i%2==1&&j%2==1) {
					mapa[i][j] = new BloqueDuro(i,j);
					notifyObservers(new Object[] {"PonerImagen", i,j,"BloqueDuro"});

					
				} else if (i > 1 || j > 1){
					if(rng.nextDouble() <= 0.7) {
						mapa[i][j] = new BloqueBlando(i,j);
						notifyObservers(new Object[] {"PonerImagen", i,j,"BloqueBlando"});
						
						
					} else {
						mapa[i][j] =  new Casilla(i,j);

						notifyObservers(new Object[] {"PonerImagen", i,j,"Casilla"});
					}
				} else {
					mapa[i][j] = new Casilla(i,j);

					notifyObservers(new Object[] {"PonerImagen", i,j,"Casilla"});
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
	            procesarExplosion(newX, newY, i);
	            if(Jugador.getJugador().estaEnCasilla(newX, newY))
	            {
	            	pantallaFinal(false);
	            }
	        }
	    }
	}

	// MÃ©todo para verificar si la posiciÃ³n estÃ¡ dentro del mapa
	private boolean esValido(int x, int y) {
	    return x >= 0 && x < mapa[0].length && y >= 0 && y < mapa.length;
	}

	// MÃ©todo auxiliar para manejar la explosiÃ³n en una casilla
	private void procesarExplosion(int x, int y, int pItr) {
	    String tipo = mapa[y][x].tipoCasilla();

	    switch (tipo) {
	    	case "Casilla":
	        case "BloqueBlando":
	            mapa[y][x].destruir();
	            setChanged();
	            mapa[y][x] = new Explosion(y, x);
	            notifyObservers(new Object[] {"PonerImagen", y, x, "Explosion"});
	            break;
	        case "Bomba": // TambiÃ©n explota si es bomba
	        	if(pItr == 0) {
	        		setChanged();
		            mapa[y][x] = new Explosion(y, x);
		            notifyObservers(new Object[] {"PonerImagen", y, x, "Explosion"});
	        	} else {
	        		this.detonarBomba(x, y, "Normal");
	        	}
	        	break;
	        case "BloqueDuro":
	            // No hace nada, la explosiÃ³n no pasa a travÃ©s de un bloque duro.
	            break;
	    }
	}
	
	
	public void ponerBomba(int pX, int pY)
	{
		setChanged();
		mapa[pY][pX] = new Bomba(pX,pY); //Pone la bomba en esas coords
		notifyObservers(new Object[] {"PonerImagen",pY, pX,"Bomba"});
	}
	
	public void explosionTerminada(int pX, int pY)
	{
        setChanged();
        mapa[pY][pX] = new Casilla(pY, pX);
        notifyObservers(new Object[] {"PonerImagen", pY, pX, "Casilla"});
	}
	
	private void pantallaFinal(boolean pEstadoPartida)
	{
		if(!finPartida)
		{
			finPartida = true;
			System.out.println("Fin");
			setChanged();

			notifyObservers(new Object[] {"Muerte"});
			FinalVisual fv = new FinalVisual(pEstadoPartida);
			fv.setVisible(true);
		}
	}
	
}