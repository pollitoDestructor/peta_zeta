package modelo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import patrones.FactoryCasillas;
import patrones.FactoryEnemigos;

@SuppressWarnings("deprecation")
public class Tablero extends Observable{
	private static Tablero miTablero;
	private Casilla[][] mapa;
	private ArrayList<Enemigo> ListaEnemigos = new ArrayList<Enemigo>();
	private Random rng = new Random();
	private boolean finPartida = false;
	
	private Tablero() { 
		mapa = new Casilla[11][17]; //NOTA: las matrices funcionan mediante Object[y][x]
	}
	
	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new Tablero();
			
		}
		return miTablero;
	}

	public ArrayList<Enemigo> getListaEnemigos() {
		return ListaEnemigos;
	}

	public Enemigo getEnemigo(int pX, int pY) {
		Enemigo enemigoB = null;
		for (Enemigo enemigo : ListaEnemigos) {
			if (enemigo.posX == pX && enemigo.posY == pY) {
				enemigoB = enemigo;
			}
		}
		return enemigoB;
	}

	public Casilla getCasilla(int pX, int pY) {
		return mapa[pX][pY];
	}
	
	public boolean casillaDisponible(int pXOld, int pYOld, int pX, int pY, String pType) {
		boolean disponible = false;
		if(pX>=0 && pX<mapa[0].length && pY>=0 && pY<mapa.length) {
			disponible = !mapa[pY][pX].estaOcupada();

			if (mapa[pY][pX].tipoCasilla().equals("Explosion")) {
				switch (pType) {
					case "Jugador": // Si jugador se mueve a una casilla explosion
						System.out.println("Explota muere Dios quï¿½ horror.");
						this.pantallaFinal(false);
						break;
					case "EnemigoNormal": // Si EnemigoNormal se mueve a una casilla explosion
						Enemigo enemigo = getEnemigo(pXOld, pYOld);
						if (enemigo != null) {
							enemigo.destruir();
							disponible = false;
						}
						break;
				}
			}
			Enemigo enemigo = getEnemigo(pX, pY);
			if (enemigo != null && enemigo.estaEnCasilla(pX, pY)) { // Si
				switch (pType) {
					case "EnemigoNormal": //Si enemigo se mueve donde hay otro enemigo
						disponible = false;
						break;
					case "Jugador": // Si jugador se mueve donde hay un enemigo
						System.out.println("Explota muere Dios quï¿½ horror.");
						this.pantallaFinal(false);
						break;
				}
			}
			if(Jugador.getJugador().estaEnCasilla(pX, pY)) { // Si un enemigo se mueve donde esta jugador
				switch (pType) {
					case "EnemigoNormal":
						System.out.println("Explota muere Dios quï¿½ horror.");
						this.pantallaFinal(false);
						break;
				}
			}
		}
		return disponible;
	}

//	Con pMapa opta por Classic, Soft o Empty
	public void ponerBloques(String pMapa) {
		switch (pMapa) {
		case "Classic":
			setChanged();
			notifyObservers(new Object[]{"PonerFondo", "Classic"});
			for (int i = 0; i < mapa.length; i++) {
				for (int j = 0; j < mapa[i].length; j++) {
					setChanged();
					if(j%2==1&&i%2==1) {
						mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("BloqueDuro", i, j);
					} else if (j > 1 || i > 1){
						if(rng.nextDouble() <= 0.7) {
							mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("BloqueBlando", i, j);				
						} else {
							mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
						}
					} else {
						mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
					}
					notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
				}
			}
			break;
		case "Soft":
			setChanged();
			notifyObservers(new Object[]{"PonerFondo", "Soft"});
			for (int i = 0; i < mapa.length; i++) {
				for (int j = 0; j < mapa[i].length; j++) {
					setChanged();
					if (j > 1 || i > 1){
						if(rng.nextDouble() <= 0.7) {
							mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("BloqueBlando", i, j);				
						} else {
							mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
						}
					} else {
						mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
					}
					notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
				}
			}
			break;
		case "Empty":
			setChanged();
			notifyObservers(new Object[]{"PonerFondo", "Empty"});
			for (int i = 0; i < mapa.length; i++) {
				for (int j = 0; j < mapa[i].length; j++) {
					setChanged();
					mapa[i][j] = FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", i, j);
					notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
				}
			}
			break;
		}
	}
	
	public void ponerEnemigos() {
		int cantE = rng.nextInt(3)+2; // 2-4 enemigos
		int cantEC = 0;
		while(cantEC < cantE) {
			int y = rng.nextInt(mapa.length);
			int x = rng.nextInt(mapa[0].length);

			if(casillaDisponible(x,y,x, y,"Normal")) {
				if(x > 1 || y > 1) {
					System.out.println("Generando enemigo en x:" + x + " y:" + y);
					Enemigo enemigo = FactoryEnemigos.getFactoryEnemigos().genEnemigo("EnemigoNormal", x, y);
					ListaEnemigos.add(enemigo);
					setChanged();
					notifyObservers(new Object[] {"PonerImagen", x, y, "Enemigo"});
					setChanged();
					notifyObservers(new Object[] {"NuevoEnemigo", enemigo});
					cantEC++;
				}
			}
		}
		System.out.println("Total enemigos generados: " + cantEC);
	}
	
	public void detonarBomba(int pX, int pY, String pTipo) {

	    // Direcciones: arriba, abajo, izquierda, derecha
	    int[] dx = {0,0, 0, -1, 1};
	    int[] dy = {0,-1, 1, 0, 0};

	    for (int i = 0; i < 5; i++) {
	        int newX = pX + dx[i];
	        int newY = pY + dy[i];

	        if (esValido(newX, newY)) {
                Jugador.getJugador().addBomba();
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

		ArrayList<Enemigo> copiaEnemigos = new ArrayList<>(ListaEnemigos);

		for (Enemigo enemigo : copiaEnemigos) {
			if (enemigo.estaEnCasilla(x, y) && enemigo.estaVivo()) {
				enemigo.destruir();
			}
		}

	    switch (tipo) {
	    	case "Casilla":
	        case "BloqueBlando":
	            mapa[y][x].destruir();
	            setChanged();
	            mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);;
	            notifyObservers(new Object[] {"PonerImagen", x, y, "Explosion"});
	            break;
	        case "Bomba": // TambiÃ©n explota si es bomba
	        	if(pItr == 0) {
	        		setChanged();
		            mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
		            notifyObservers(new Object[] {"PonerImagen", x, y, "Explosion"});
	        	} else {
	        		mapa[y][x].destruir();
	        		this.detonarBomba(x, y, "Normal");
	        	}
	        	break;
	        case "BloqueDuro":
	            // No hace nada, la explosiÃ³n no pasa a travÃ©s de un bloque duro.
	            break;
	        case "Explosion":
	        	Explosion e = (Explosion) mapa[y][x];
	        	e.iniciarTimer();
	        	break;
	    }
	}
	
	
	public void ponerBomba(int pX, int pY)
	{
		setChanged();
		mapa[pY][pX] = FactoryCasillas.getFactoryCasillas().genCasilla("Bomba", pX, pY); //Pone la bomba en esas coords
		notifyObservers(new Object[] {"PonerImagen",pX, pY,"Bomba"});
	}
	
	public void explosionTerminada(int pX, int pY)
	{
        setChanged();
        mapa[pY][pX] =  FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", pX, pY);
        notifyObservers(new Object[] {"PonerImagen", pX, pY, "Casilla"});
	}
	
	private void pantallaFinal(boolean pEstadoPartida)
	{
		if(!finPartida)
		{
			/*finPartida = true;
			System.out.println("Fin");
			setChanged();

			notifyObservers(new Object[] {"Muerte"});
			FinalVisual fv = new FinalVisual(pEstadoPartida);
			fv.setVisible(true);*/
			System.out.println("Cerramos main");
			System.exit(0);
		}
	}
	
}