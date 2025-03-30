package modelo;

import patrones.PonerBombaGigante;
import patrones.PonerBombaNormal;
import viewController.TableroVisual;

public class GestorMenuPrincipal {
    private static GestorMenuPrincipal miMenu;

    private GestorMenuPrincipal(){}

    public static GestorMenuPrincipal getMenu() {
        if (miMenu == null){
            miMenu = new GestorMenuPrincipal();
        }
        return miMenu;
    }

    public void iniciarJuego() {
    	Tablero.setTablero("Empty"); //TODO con el String se cambia el tipo de tablero
        Tablero tablero = Tablero.getTablero();
        TableroVisual f = new TableroVisual();
        tablero.ponerBloques();
        tablero.ponerEnemigos();
        Jugador jugador = Jugador.getJugador();
        jugador.inicio(); //para imprimir el primer Bomberman nada mas
        System.out.println("El jugador esta en: "+jugador.getPosX()+" "+jugador.getPosY());
        f.setVisible(true);
    }

    public void cambiarJugador(String pColor) {
        if (pColor == Jugador.getJugador().getColor()) {
        } else {
            Jugador.getJugador().setColor(pColor);
            System.out.println("Cambio de color a: "+pColor);
            if (pColor == "blanco") {
                Jugador.getJugador().changeStrategyPonerBomba(new PonerBombaNormal());
                System.out.println("Cambio de estrategia a: PonerBombaNormal");
            } else if (pColor == "negro") {
                Jugador.getJugador().changeStrategyPonerBomba(new PonerBombaGigante());
                System.out.println("Cambio de estrategia a: PonerBombaGigante");
            }
        }
    }

    public void end() {
        System.exit(0);
    }

}
