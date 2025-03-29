package modelo;

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
        Tablero tablero = Tablero.getTablero();
        TableroVisual f = new TableroVisual();
        tablero.ponerBloques();
        Jugador jugador = Jugador.getJugador();
        jugador.inicio(); //para imprimir el primer Bomberman nada mas
        System.out.println("El jugador esta en: "+jugador.getPosX()+" "+jugador.getPosY());
        f.setVisible(true);
    }

    public void end() {
        System.exit(0);
    }
}
