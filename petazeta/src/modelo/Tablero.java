package modelo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import patrones.FactoryCasillas;
import patrones.PonerBombaCruzRompeObstaculos;
import patrones.PonerBombaSuper;
import patrones.PonerBombaUltra;
import patrones.StateGanar;
import patrones.StateJugador;
import patrones.StateVivo;
import patrones.StrategyPonerBomba;
import patrones.StateMuerto;
import patrones.StrategyTablero;
import patrones.TableroClassic;
import patrones.TableroEmpty;
import patrones.TableroSoft;

@SuppressWarnings("deprecation")
public class Tablero extends Observable{
	private static Tablero miTablero;
	private Casilla[][] mapa;

	private ArrayList<Enemigo> ListaEnemigos = new ArrayList<Enemigo>();
	private ArrayList<Teletransporte> ListaTeletransporte = new ArrayList<Teletransporte>();
	private Random rng = new Random();

	//Patrones
	private StrategyTablero stratTablero = new TableroClassic();
	private StrategyPonerBomba stratBomba = new PonerBombaSuper();
	private StateJugador state;
	
	private boolean fin = false;

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

	public static void reiniciarTablero() {
		miTablero = new Tablero();
	}

	//==================================METODOS AUXILIARES==================================
	public Enemigo getEnemigo(int pX, int pY) {
		Enemigo enemigoB = null;
		for (Enemigo enemigo : ListaEnemigos) {
			if (enemigo.estaEn(pX,pY)) {
				enemigoB = enemigo;
			}
		}
		return enemigoB;
	}

	public Casilla getCasilla(int pCol, int pFila) {
		Casilla c = null;
		if (esValido(pCol, pFila)) {
			c = mapa[pFila][pCol];
		}
		return c;
	}

	public void addTeletransporte(String pColor) {
		if (ListaTeletransporte.size() <= 2) {
			Teletransporte tp = new Teletransporte(Jugador.getJugador().getPosX(), Jugador.getJugador().getPosY(), pColor);

			if (ListaTeletransporte.size() == 0) {
				ListaTeletransporte.add(tp);
				mapa[tp.getCoordY()][tp.getCoordX()] = tp;
				setChanged();
				notifyObservers(new Object[] {"PonerImagen",tp.getCoordX(), tp.getCoordY(),pColor});
			} else if (ListaTeletransporte.size() == 1) {
				if(buscarMismoColor(tp.getColor())) {
					Teletransporte antiguo = ListaTeletransporte.get(0);
					ListaTeletransporte.remove(0);
					mapa[antiguo.getCoordY()][antiguo.getCoordX()] = new Casilla(antiguo.getCoordX(), antiguo.getCoordY());
					setChanged();
					notifyObservers(new Object[] {"PonerImagen", antiguo.getCoordX(), antiguo.getCoordY(), "Casilla"});
				}
					ListaTeletransporte.add(tp);
					mapa[tp.getCoordY()][tp.getCoordX()] = tp;
					ListaTeletransporte.get(0).setOcupado(false);
					setChanged();
					notifyObservers(new Object[] {"PonerImagen",tp.getCoordX(), tp.getCoordY(),pColor});

			} else if (ListaTeletransporte.size() == 2) {
				if(mapa[tp.getCoordY()][tp.getCoordX()].tipoCasilla().equals("Teletransporte")) {
					if(mapa[tp.getCoordY()][tp.getCoordX()] != tp) {
						ListaTeletransporte.remove(mapa[tp.getCoordY()][tp.getCoordX()]);

					}
				}
				//Verifica si ya existe un teletransporte del mismo color
				boolean encontrado = false;
				int i = 0;
				while ( i < ListaTeletransporte.size() && !encontrado) {
					if (ListaTeletransporte.get(i).getColor().equals(pColor)) {
						Teletransporte antiguo = ListaTeletransporte.get(i); // Guardamos la referencia
						ListaTeletransporte.remove(i);
						mapa[antiguo.getCoordY()][antiguo.getCoordX()] = new Casilla(antiguo.getCoordX(), antiguo.getCoordY());
						setChanged();
						notifyObservers(new Object[] {"PonerImagen", antiguo.getCoordX(), antiguo.getCoordY(), "Casilla"});
						ListaTeletransporte.add(i, tp);
						mapa[tp.getCoordY()][tp.getCoordX()] = tp;
						setChanged();
						notifyObservers(new Object[] {"PonerImagen", tp.getCoordX(), tp.getCoordY(), pColor});
						encontrado = true;
					} else if (!ListaTeletransporte.get(i).getColor().equals(pColor)) {


					}
					i++;
				}
			}
			if(ListaTeletransporte.size() == 1) {
				ListaTeletransporte.get(0).setOcupado(true);
			}
		}
	}

	public void patearBomba() {
		int x = Jugador.getJugador().getPosX(); int y = Jugador.getJugador().getPosY();
		String dir=null;
		if (x>0) {
			if(mapa[y][x-1].tipoCasilla().equals("BombaCruz")) {
				dir = "izq";
				BombaCruz bC = (BombaCruz) mapa[y][x-1];
				bC.rodar(dir);
			}
		}
		if (x<11) {
			if(mapa[y][x+1].tipoCasilla().equals("BombaCruz")) {
				dir = "der";
				BombaCruz bC = (BombaCruz) mapa[y][x+1];
				bC.rodar(dir);
			}
		}
		if (y>0) {
			if(mapa[y-1][x].tipoCasilla().equals("BombaCruz")) {
				dir = "abj";
				BombaCruz bC = (BombaCruz) mapa[y-1][x];
				bC.rodar(dir);
			}
		}
		if (y<10) {
			if(mapa[y+1][x].tipoCasilla().equals("BombaCruz")) {
				dir = "sup";
				BombaCruz bC = (BombaCruz) mapa[y+1][x];
				bC.rodar(dir);
			}
		}
	}

	public void moverBomba(String pDir, int pX, int pY) {
		int nY = pY;
		int nX = pX;
		if (pX>0 && pDir.equals("izq")){nX--;}
		else if (pX<17 && pDir.equals("der")){nX++;}
		else if (pY>0 && pDir.equals("abj")){nY--;}
		else if (pY<10 && pDir.equals("sup")){nY++;}
		if(nY >= 0 && nY<= 11 && nX >= 0 && nX <= 16 && !hayEnemigo(nX,nY) && !mapa[nY][nX].estaOcupada()) {
			if(mapa[nY][nX].tipoCasilla().equals("Explosion")) {
				mapa[nY][nX].destruir(); //Para detener la explosion que encuentra y "reiniciarla"
				mapa[nY][nX] = new BombaCruz(nX, nY);
				mapa[pY][pX].destruir();
				mapa[pY][pX] = new Casilla(pX, pY);
				mapa[nY][nX].destruir();
				detonarBomba(nX, nY, 1);
				setChanged();
				notifyObservers(new Object[]{"PonerImagen", pX, pY, "Casilla"});
				setChanged();
				notifyObservers(new Object[]{"PonerImagen", nX, nY, "Explosion"});
			} else {
				mapa[nY][nX] = new BombaCruz(nX, nY);
				mapa[pY][pX].destruir();
				mapa[pY][pX] = new Casilla(pX, pY);
				BombaCruz bC = (BombaCruz) mapa[nY][nX];
				bC.rodar(pDir);
				setChanged();
				notifyObservers(new Object[]{"PonerImagen", pX, pY, "Casilla"});
				setChanged();
				notifyObservers(new Object[]{"PonerImagen", nX, nY, "Bomba"});
			}
		}
	}

	public boolean buscarMismoColor(String pColor) {
		boolean encontrado = false;
		for (Teletransporte tp : ListaTeletransporte) {
			if(tp.getColor() == pColor)
				encontrado = true;
		}
		return encontrado;
	}

	public ArrayList<Integer> buscarOtroTeletransporte(int pX, int pY) {
		ArrayList<Integer> pos = new ArrayList<Integer>();
		for (Teletransporte tp : ListaTeletransporte) {
			if (tp.getCoordX() != pX || tp.getCoordY() != pY) {
				pos.add(tp.getCoordX());
				pos.add(tp.getCoordY());
				if(tp.getColor().equals("blue")){
					pos.add(0);
				}
				else{
					pos.add(1);
				}
			}
		}
		return pos;
	}

	public void deleteEnemigo(Enemigo pEnemigo) {
		ListaEnemigos.remove(pEnemigo);
	}

	public boolean hayEnemigo(int pX, int pY) {
		return ListaEnemigos.stream().anyMatch(enemigo -> enemigo.estaEn(pX, pY)); // JAVA 8 !!!!!!!!! :DDDD YIPPPEEEE !!!!!
	}

	public boolean casillaDisponible(int pXOld, int pYOld, int pX, int pY, String pType) {
	    boolean disponible = false;

	    if (esValido(pX,pY)) {
	        disponible = !mapa[pY][pX].estaOcupada();

	        if (mapa[pY][pX].tipoCasilla().equals("Explosion")) {
	        	
	        	//TODO ==== Metodo matarA(pTipo, pXOld, pYOld): boolean ====
	            switch (pType) {
	                case "Jugador":
	                    System.out.println("�El jugador ha muerto!");
						this.changeStateString("Muerto");  // Cambiamos a estado de muerte
	                    break;
					case "Pass":
					case "Doria":
	                case "Globo":
	                    Enemigo enemigo = getEnemigo(pXOld, pYOld);
	                    if (enemigo != null) {
	                        enemigo.destruir();
							Jugador.getJugador().actualizarPuntuacion(250);
	                        disponible = false;
	                        if (ListaEnemigos.isEmpty()) //Si la lista de enemigos esta vacía
	        				{
	        						changeStateString("Ganar");
	        				}
	                    }
	                    break;
	            }
	            //TODO ==== Metodo matarA ending ===
	        }

	        //TODO ==== Metodo enemigoSeMueve(pType, pX, pY): boolean ====
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
	                    this.changeStateString("Muerto");  // Cambiamos a estado de muerte
	                    break;
	            }
	        }
	        
	        if (Jugador.getJugador().estaEnCasilla(pX, pY)) { // Si un enemigo se mueve donde está el jugador
	            switch (pType) {
					case "Pass":
					case "Doria":
	                case "Globo":
	                    System.out.println("Se lo come un enemigo Dios qué horror.");
	                    this.changeStateString("Muerto");  // Cambiamos a estado de muerte
	                    break;
	            }
	        }
	        //TODO ==== Metodo enemigoSeMueve() ending
	    }
	    return disponible;
	}

	//==================================STRATEGY TABLERO==================================
	//Para generar el Tablero correspondiente
	public void changeStrategyTablero(StrategyTablero pStrat)
	{
		stratTablero = pStrat;
	}
	
	public void changeStrategyTableroString(String pStrat) {
		if(pStrat.equals("Classic")) changeStrategyTablero(new TableroClassic());
		else if (pStrat.equals("Soft")) changeStrategyTablero(new TableroSoft());
		else if (pStrat.equals("Empty")) changeStrategyTablero(new TableroEmpty());
			
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
	public void changeStrategyBomba(StrategyPonerBomba pStrat){
		stratBomba = pStrat;
	}
	
	public void changeStrategyBombaString(String pStrat) {
		if(pStrat.equals("Super")) changeStrategyBomba(new PonerBombaSuper());
		else if(pStrat.equals("Ultra")) changeStrategyBomba(new PonerBombaUltra());
		else if(pStrat.equals("Cruz")) changeStrategyBomba(new PonerBombaCruzRompeObstaculos());
	}

	public void ponerBomba(int pX, int pY)
	{
		if(!mapa[pY][pX].tipoCasilla().equals("Teletransporte")) {
			setChanged();
			mapa[pY][pX] = FactoryCasillas.getFactoryCasillas().genCasilla(stratBomba.getTipoBomba(), pX, pY); //Pone la bomba en esas coords
			notifyObservers(new Object[] {"PonerImagen",pX, pY,stratBomba.getTipoBomba(),Jugador.getJugador().getColor()});
		}
	}

	public void detonarBomba(int pX, int pY, int pCombo) {
		stratBomba.detonarBomba(pX, pY, pCombo);
	}

	// Metodo auxiliar para manejar la explosion en una casilla
	public int procesarExplosion(int x, int y, int pOriginalX, int pOriginalY, int pComboActual) {
		int newCombo = pComboActual;
	    String tipo = mapa[y][x].tipoCasilla();
	    ArrayList<Enemigo> copiaEnemigos = new ArrayList<>(ListaEnemigos);


		String exp = stratBomba.getTipoExplosion();
	    switch (tipo) {
	        case "Casilla":
	        case "BloqueBlando":
	            mapa[y][x].destruir();
	            setChanged();
	            mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
				if (Jugador.getJugador().getColor().equals("red")) {exp="Explosion2";}
				notifyObservers(new Object[]{"PonerImagen", x, y, exp});
	            break;
	        case "BombaUltra":
	        case "BombaSuper":
	        case "BombaCruz":
	            if (pOriginalX == x && pOriginalY == y) { //La propia bomba
	                setChanged();
	                mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
					if (Jugador.getJugador().getColor().equals("red")) {exp="Explosion2";}
					notifyObservers(new Object[]{"PonerImagen", x, y, exp});
	            } else { //Bombas que encuentra en su explosion
	                mapa[y][x].destruir();
	                detonarBomba(x, y, newCombo);
	            }
	            break;
	        case "BloqueDuro":
	        	if(stratBomba.getTipoBomba().equals("BombaCruz")) //Si es bombaCruz, lo rompe
	        	{
	        		setChanged();
	                mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
	                notifyObservers(new Object[]{"PonerImagen", x, y, stratBomba.getTipoExplosion()});
	        	}
	            break;
	        case "Explosion":
	            Explosion e = (Explosion) mapa[y][x];
	            e.iniciarTimer();
	            break;
	    }

		for (Enemigo enemigo : copiaEnemigos) {
			if (enemigo.estaEnCasilla(x, y) && enemigo.estaVivo()) {
				enemigo.destruir();
				ListaEnemigos.remove(enemigo); // Asegura que se elimine de la lista
				Jugador.getJugador().actualizarPuntuacion(500 * pComboActual);
				System.out.println("Combo actual: " + pComboActual);
				newCombo = pComboActual + 1;
				setChanged();
				mapa[y][x] = FactoryCasillas.getFactoryCasillas().genCasilla("Explosion", x, y);
				if (Jugador.getJugador().getColor().equals("red")) {exp="Explosion2";}
				notifyObservers(new Object[]{"PonerImagen", x, y, exp, newCombo});
			}
		}

		verificarVictoria();

		return newCombo;
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
	    if (state.getClass() != pState.getClass() && !(state.getClass() == StateMuerto.class))
	    {
	        state = pState;
	        state.manejarEstado();
	    }
	}
	
	//M�todo para evitar dependencias
	public void changeStateString(String pState) { 
		if(!fin) {
			if(pState.equals("Muerto")) changeState(new StateMuerto());
			else if(pState.equals("Ganar")) changeState(new StateGanar());
		}
		this.fin = true;
	}

	public void pantallaFinal(boolean pEstadoPartida) {
		System.out.println("Fin de la partida.");
		setChanged();
		notifyObservers(new Object[]{"Muerte"});

		GestorFinalVisual.getFinal(); //Accede al Singleton
		setChanged();
		notifyObservers(new Object[]{"FinalVisual"}); //Generamos vista
		GestorFinalVisual.getFinal().setFinal(pEstadoPartida); //Establecer booleano

		detenerTimers();
	}

	public void verificarVictoria() {
		if (ListaEnemigos.isEmpty()) {
			Jugador.getJugador().guardarPuntuacion();
			changeStateString("Ganar"); 
		}
	}

	public void detenerTimers() {
		//Detener timers de enemigos
		ArrayList<Enemigo> copiaEnemigos = new ArrayList<>(ListaEnemigos);
		for (Enemigo enemigo : copiaEnemigos) {
			enemigo.destruir();
		}

		//Detener timers de explosion
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				Casilla casilla = mapa[i][j];
				if (casilla.tipoCasilla()=="Explosion") {
					casilla.destruir();
				}
			}
		}

		//Detener timers de bombas
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				Casilla casilla = mapa[i][j];
				if (casilla.tipoCasilla().equals("BombaSuper")||casilla.tipoCasilla().equals("BombaUltra")) {
					casilla.destruir();
				}
			}
		}

	}
}