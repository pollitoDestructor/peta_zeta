package petazeta;

public class Main {

	public static void main(String[] args) {
		Jugador jugador = Jugador.getJugador();
		Tablero tablero = Tablero.getTablero();

		//tablero.
		//tablero.printMap();
		System.out.println("El jugador está en: "+jugador.getPosX()+" "+jugador.getPosY());
	
		//Revisar listeners (leer lo que se introduce por teclado)
		
	}

}
