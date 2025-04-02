package modelo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import patrones.FactoryCasillas;
import patrones.FactoryEnemigos;
import patrones.StateJugador;
import patrones.StateVivo;
import patrones.StateMuerto;
import patrones.StrategyTablero;
import patrones.TableroClassic;
import viewController.FinalVisual;

@SuppressWarnings("deprecation")
public class Tablero extends Observable{
	private static Tablero miTablero;
	private Casilla[][] mapa;
	private ArrayList<Enemigo> ListaEnemigos = new ArrayList<Enemigo>();
	private Random rng = new Random();
	private static boolean finPartida = false; //TODO static?
	private static StrategyTablero stratTablero = new TableroClassic();
	private StateJugador state;  
  
	
	private Tablero() { 
		mapa = new Casilla[11][17]; //NOTA: las matrices funcionan mediante Object[y][x]
		state = new StateVivo();  // El juego comienza con el estado "vivo"
	}
	
	public static Tablero getTablero() {
		if (miTablero == null){
			miTablero = new Tablero();
		}
		return miTablero;
	}
	
	//Para generar el Tablero correspondiente
	public static void setStrategyTablero(StrategyTablero pStrat)
	{
		stratTablero = pStrat;
	}
	
	public void ponerBloques()
	{
		setChanged();
		notifyObservers(new Object[]{"PonerFondo", stratTablero.tipoTablero()});
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				setChanged();
				mapa[i][j] = stratTablero.ponerBloques(i,j);
				notifyObservers(new Object[]{"PonerImagen", j, i, mapa[i][j].tipoCasilla()});
			}
		}
		
	}

	public void ponerEnemigos() {
		int cantE = rng.nextInt(3)+4; // 4-6 enemigos
		int cantEC = 0;
		while(cantEC < cantE) {
			int y = rng.nextInt(mapa.length);
			int x = rng.nextInt(mapa[0].length);

			if(casillaDisponible(x,y,x, y,"Globo")) {
				if(x > 1 || y > 1) {
					System.out.println("Generando enemigo en x:" + x + " y:" + y);
					Enemigo enemigo = stratTablero.ponerEnemigos(x,y);
					ListaEnemigos.add(enemigo);
					String Tipo = enemigo.tipoEnemigo();
					setChanged();
					notifyObservers(new Object[] {"PonerImagen", x, y, Tipo});
					setChanged();
					notifyObservers(new Object[] {"NuevoEnemigo", enemigo});
					cantEC++;
				}
			}
		}
		System.out.println("Total enemigos generados: " + cantEC);
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
	    
	    if (pX >= 0 && pX < mapa[0].length && pY >= 0 && pY < mapa.length) {
	        disponible = !mapa[pY][pX].estaOcupada();

	        if (mapa[pY][pX].tipoCasilla().equals("Explosion")) {
	            switch (pType) {
	                case "Jugador": 
	                    System.out.println("¡El jugador ha muerto!");
						this.changeState(new StateMuerto());  // Cambiamos a estado de muerte
	                    break;
					case "Pass":
					case "Doria":
	                case "Globo":
	                    Enemigo enemigo = getEnemigo(pXOld, pYOld);
	                    if (enemigo != null) {
	                        enemigo.destruir();
	                        disponible = false;
	                        if (ListaEnemigos.isEmpty()) //Si la lista de enemigos esta vacía
	        				{
	        						this.pantallaFinal(true);
	        				}
	                    }
	                    break;
	            }
	        }


	        Enemigo enemigo = getEnemigo(pX, pY);
	        if (enemigo != null && enemigo.estaEnCasilla(pX, pY)) {
	            switch (pType) {
					case "Pass":
					case "Doria":
	                case "Globo": // Si enemigo se mueve donde hay otro enemigo
	                    disponible = false;
	                    break;
	                case "Jugador": // Si jugador se mueve donde hay un enemigo
	                    System.out.println("Se choca con un enemigo muere Dios qué horror.");
	                    this.changeState(new StateMuerto());  // Cambiamos a estado de muerte

	                    break;
	            }
	        }

	        if (Jugador.getJugador().estaEnCasilla(pX, pY)) { // Si un enemigo se mueve donde está el jugador
	            switch (pType) {
					case "Pass":
					case "Doria":
	                case "Globo":
	                    System.out.println("Se lo come un enemigo Dios qué horror.");
	                    this.changeState(new StateMuerto());  // Cambiamos a estado de muerte

	                    break;
	            }
	        }
	    }
	    return disponible;
	}


	
	public void detonarBomba(int pX, int pY, String pTipo) {
		int radio = 1;
		if(pTipo.equals("BombaUltra"))
		{
			radio = 20;
		}
		int[] dx = {0, 0, 0, -1, 1};
		int[] dy = {0, -1, 1, 0, 0};

		for (int i = 0; i < 5; i++) {
			for(int j=0; j<=radio;j++)
			{
				int newX = pX + dx[i]*j;
				int newY = pY + dy[i]*j;
				
				if (!esValido(newX, newY)) {
				    break; // Evita acceder fuera del array
				}
				if (mapa[newY][newX].tipoCasilla().equals("BloqueDuro")) {
				    break;
				}
				
				if (esValido(newX, newY)) {
					Jugador.getJugador().addBomba();
					procesarExplosion(newX, newY, i);
					if (Jugador.getJugador().estaEnCasilla(newX, newY)) {
						this.changeState(new StateMuerto());  // Cambiamos a estado de muerte
					}
				}
			}
		}

		// La explosión central
		procesarExplosion(pX, pY, -1);

		// Verificar si el jugador ha sido alcanzado
		if (Jugador.getJugador().estaEnCasilla(pX, pY)) {
			this.changeState(new StateMuerto());  // Cambiamos a estado de muerte
		}

		// Verificar victoria después de todas las explosiones
		if (ListaEnemigos.isEmpty()) {
			pantallaFinal(true); //TODO poner que gane mediante state, para cohesion con cuando pierde
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
	            ListaEnemigos.remove(enemigo); // Asegura que se elimine de la lista
	        }
	    }

	    if (ListaEnemigos.isEmpty()) { // Comprobar después de actualizar la lista
	        pantallaFinal(true);
	    }

	    switch (tipo) {
	        case "Casilla":
	        case "BloqueBlando":
	            mapa[y][x].destruir();
	            setChanged();
	            mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
	            notifyObservers(new Object[]{"PonerImagen", x, y, "Explosion"});
	            break;
	        case "BombaUltra":
	        case "Bomba":
	            if (pItr == 0) { //La propia bomba
	                setChanged();
	                mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
	                notifyObservers(new Object[]{"PonerImagen", x, y, "Explosion"});
	            } else { //Bombas que encuentra en su explosion
	                mapa[y][x].destruir();
	                detonarBomba(x, y, "Normal");
	            }
	            break;
	        case "BloqueDuro":
	            break;
	        case "Explosion":
	            Explosion e = (Explosion) mapa[y][x];
	            e.iniciarTimer();
	            break;
	    }
	}

	
	
	public void ponerBomba(int pX, int pY, String pTipo)
	{
		setChanged();
		mapa[pY][pX] = FactoryCasillas.getFactoryCasillas().genCasilla(pTipo, pX, pY); //Pone la bomba en esas coords
		notifyObservers(new Object[] {"PonerImagen",pX, pY,pTipo,Jugador.getJugador().getColor()});
	}
	
	public void explosionTerminada(int pX, int pY)
	{
        setChanged();
        mapa[pY][pX] =  FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", pX, pY);
        notifyObservers(new Object[] {"PonerImagen", pX, pY, "Casilla"});
	}
	
	private void pantallaFinal(boolean pEstadoPartida) {
	    if (!finPartida) {
	        finPartida = true; // Evita que se muestre más de una vez
	        System.out.println("Fin de la partida.");
	        setChanged();
	        notifyObservers(new Object[]{"Muerte"});
	        
	        // Mostrar la pantalla de final con el estado correspondiente
	        FinalVisual fv = new FinalVisual(pEstadoPartida);
	        fv.setVisible(true);
	    }
	}

	 public void changeState(StateJugador pState) { 
	        state = pState; 
	        state.manejarEstado();  
	    }
	 public void setFinPartida(boolean fin) { //TODO no se si es static.    TO2 yo lo he quitado para que acabe bien la partida, no se si esta bien
	        finPartida = fin;
		    this.pantallaFinal(false); // Llamar pantalla de derrota
	    }
	 public boolean isFinPartida() {
	        return finPartida;
	    }
}