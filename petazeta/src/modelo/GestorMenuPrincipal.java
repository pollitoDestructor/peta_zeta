package modelo;

import patrones.TableroClassic;
import patrones.TableroEmpty;
import patrones.TableroSoft;
import patrones.PonerBombaGigante;
import patrones.PonerBombaNormal;
import viewController.TableroVisual;

import java.util.Observable;

public class GestorMenuPrincipal extends Observable {
    private static GestorMenuPrincipal miMenu;
    private String ultimo_mapa_elegido = "Classic";

    private GestorMenuPrincipal(){}

    public static GestorMenuPrincipal getMenu() {
        if (miMenu == null){
            miMenu = new GestorMenuPrincipal();
        }
        return miMenu;
    }

    public void opcionesMenu(String accion) {
        setChanged();
        notifyObservers(accion);
    }

    public void iniciarJuego() {
    	//Tablero.setStrategyTablero(new TableroClassic()); //TODO elige la estrategia antes de generarlo
        Tablero tablero = Tablero.getTablero();
        TableroVisual f = new TableroVisual();
        tablero.ponerBloques();
        tablero.ponerEnemigos();
        Jugador jugador = Jugador.getJugador();
        jugador.inicio(); //para imprimir el primer Bomberman nada mas
        System.out.println("El jugador esta en: "+jugador.getPosX()+" "+jugador.getPosY());
        this.opcionesMenu("Juego");
    }

    public void cambiarJugador(String pColor) {
        if (pColor == Jugador.getJugador().getColor()) {
        } else {
            Jugador.getJugador().setColor(pColor);
            System.out.println("Cambio de color a: "+pColor);
            if (pColor == "white") {
            	Tablero.changeStrategyBomba(new PonerBombaNormal());
                System.out.println("Cambio de estrategia a: PonerBombaNormal");
            } else if (pColor == "black") {
            	 Tablero.changeStrategyBomba(new PonerBombaGigante());
                System.out.println("Cambio de estrategia a: PonerBombaGigante");
            }
        }
    }
    
    public void cambiarMapa(String pMapa)
    {
    	if(!pMapa.equals(ultimo_mapa_elegido)) {
    		switch(pMapa)
    		{
    		case "Classic":
    			Tablero.changeStrategyTablero(new TableroClassic());
    			System.out.println("Cambio de estrategia a: TableroClassic");
    			break;
    		case "Soft":
    			Tablero.changeStrategyTablero(new TableroSoft());
    			System.out.println("Cambio de estrategia a: TableroSoft");
    			break;
    		case "Empty":
    			Tablero.changeStrategyTablero(new TableroEmpty());
    			System.out.println("Cambio de estrategia a: TableroEmpty");
    			break;
    		}
    		ultimo_mapa_elegido=pMapa;
    	}
    }

    public void end() {
        System.exit(0);
    }

}
