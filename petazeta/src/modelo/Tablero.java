package modelo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import patrones.FactoryCasillas;
import patrones.PonerBombaNormal;
import patrones.StateGanar;
import patrones.StateJugador;
import patrones.StateVivo;
import patrones.StrategyPonerBomba;
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
	
	//Patrones
	private static StrategyTablero stratTablero = new TableroClassic();
	private static StrategyPonerBomba stratBomba = new PonerBombaNormal();
	private StateJugador state;  
  
	//==================================SINGLETON==================================
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
	
	//==================================METODOS AUXILIARES==================================
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
		Casilla c = null;
		if (esValido(pX, pY)) { 
			c = mapa[pY][pX];
		}
		return c;
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
	        						changeState(new StateGanar());
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
	
	//==================================STRATEGY TABLERO==================================
	//Para generar el Tablero correspondiente
	public static void changeStrategyTablero(StrategyTablero pStrat)
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

			if(x > 1 || y > 1) {
				if(casillaDisponible(x,y,x, y,"Globo")) {
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

	
	//==================================STRATEGY BOMBA==================================
	public static void changeStrategyBomba(StrategyPonerBomba pStrat){
		stratBomba = pStrat;
	}
	
	public void ponerBomba(int pX, int pY)
	{
		setChanged();
		mapa[pY][pX] = FactoryCasillas.getFactoryCasillas().genCasilla(stratBomba.getTipo(), pX, pY); //Pone la bomba en esas coords
		notifyObservers(new Object[] {"PonerImagen",pX, pY,stratBomba.getTipo(),Jugador.getJugador().getColor()});
	}
	
	public void detonarBomba(int pX, int pY) {
		stratBomba.detonarBomba(pX, pY);
	}

	// Metodo auxiliar para manejar la explosion en una casilla
	public void procesarExplosion(int x, int y, int pOriginalX, int pOriginalY) {
	    String tipo = mapa[y][x].tipoCasilla();
	    ArrayList<Enemigo> copiaEnemigos = new ArrayList<>(ListaEnemigos);

	    for (Enemigo enemigo : copiaEnemigos) {
	        if (enemigo.estaEnCasilla(x, y) && enemigo.estaVivo()) {
	            enemigo.destruir();
	            ListaEnemigos.remove(enemigo); // Asegura que se elimine de la lista
	        }
	    }

	    if (ListaEnemigos.isEmpty()) { // Comprobar después de actualizar la lista
	        changeState(new StateGanar());
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
	            if (pOriginalX == x && pOriginalY == y) { //La propia bomba
	                setChanged();
	                mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
	                notifyObservers(new Object[]{"PonerImagen", x, y, "Explosion"});
	            } else { //Bombas que encuentra en su explosion
	                mapa[y][x].destruir();
	                detonarBomba(x, y);
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

	public void explosionTerminada(int pX, int pY)
	{
        setChanged();
        mapa[pY][pX] =  FactoryCasillas.getFactoryCasillas().genCasilla("Casilla", pX, pY);
        notifyObservers(new Object[] {"PonerImagen", pX, pY, "Casilla"});
	}
	
	// Metodo para verificar si la posicion esta dentro del mapa
	public boolean esValido(int x, int y) {
	    return x >= 0 && x < mapa[0].length && y >= 0 && y < mapa.length;
	}
	
	public boolean esDuro(int x, int y) {
		return mapa[y][x].tipoCasilla().equals("BloqueDuro");
	}
	
	//===================================STATE JUGADOR===================================
	public void changeState(StateJugador pState) {
	    if (state.getClass() != pState.getClass()) //TODO preguntar si esto se puede hacer.
	    {
	        state = pState;
	        state.manejarEstado();
	    }
	}
	
	public void pantallaFinal(boolean pEstadoPartida) {
		System.out.println("Fin de la partida.");
		setChanged();
		notifyObservers(new Object[]{"Muerte"});

		GestorFinalVisual.getFinal(pEstadoPartida);
		FinalVisual f = new FinalVisual(pEstadoPartida);  // TODO parametros?
	}

	public void verificarVictoria() {
		if (ListaEnemigos.isEmpty()) {
			changeState(new StateGanar()); //TODO poner que gane mediante state, para cohesion con cuando pierde
		}
	}
}