package petazeta;

public class Jugador {
	
	private static Jugador miJugador; //referencia estatica
	private int posX;
	private int posY;
	private String color;

	private Jugador(String color) {
		this.posX=0;
		this.posY=0;
		this.color=color;
	}
	
	//obtener obj estatico
	public static Jugador getJugador(String color) {
		if (miJugador == null){
			miJugador = new Jugador(color);
		}
		return miJugador;
	}
	
	//getters
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}

	public String getColor() {
		return this.color;
	}

	
	//setters
	/*
	public void setPosX(int x) {
		this.posX=x;
	}
	
	public void setPosY(int y) {
		this.posY=y;
	}
	
	*/

	public void ponerBomba()
	{ 
		Tablero.getTablero().ponerBomba(this.posX, this.posY);
	}
	
	//movimiento del jugador
	public void mover(int x, int y) {
		if (Tablero.getTablero().casillaDisponible(x,y)){
			this.posX=x; this.posY=y;
		}
		//else {System.out.println("El movimiento no se ha podido efectuar");}
	}
}