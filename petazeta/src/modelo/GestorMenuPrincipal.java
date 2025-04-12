package modelo;

import java.util.Observable;

@SuppressWarnings("deprecation")
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
        if(Jugador.getJugador().getColor()==null){System.out.println("SELECCIONA UN JUGADOR");}
        else {
            opcionesMenu("Cerrar");
            Tablero tablero = Tablero.getTablero();
            this.opcionesMenu("Crear TableroVisual");
            tablero.ponerBloques();
            tablero.ponerEnemigos();
            Jugador jugador = Jugador.getJugador();
            jugador.inicio(); //para imprimir el primer Bomberman nada mas
            System.out.println("El jugador esta en: " + jugador.getPosX() + " " + jugador.getPosY());
            this.opcionesMenu("Juego");
        }
    }

    public void cambiarJugador(String pColor) {
        if (pColor == Jugador.getJugador().getColor()) {
        } else {
            Jugador.getJugador().setColor(pColor);
            System.out.println("Cambio de color a: "+pColor);
            if (pColor == "white") {
            	Tablero.getTablero().changeStrategyBombaString("Super");
                System.out.println("Cambio de estrategia a: PonerBombaNormal");
            } else if (pColor == "black") {
            	 Tablero.getTablero().changeStrategyBombaString("Ultra");
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
    			Tablero.getTablero().changeStrategyTableroString("Classic");
    			System.out.println("Cambio de estrategia a: TableroClassic");
    			break;
    		case "Soft":
    			Tablero.getTablero().changeStrategyTableroString("Soft");
    			System.out.println("Cambio de estrategia a: TableroSoft");
    			break;
    		case "Empty":
    			Tablero.getTablero().changeStrategyTableroString("Empty");
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
