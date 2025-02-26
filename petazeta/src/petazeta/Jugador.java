package petazeta;

public class Jugador {
	
	private Jugador miJugador; //referencia estatica
	private int posX;
	private int posY;
	//private String color;

	private Jugador(/*String color*/) {
		this.posX=0;
		this.posY=0;
		//this.color=color;
	}
	
	//obtener obj estatico
	public Jugador getJugador() {
		if (miJugador == null){
			miJugador = new Jugador();
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
	
	/*

	public String getColor() {
		return this.color;
	}
	*/
	
	//setters
	/*
	public void setPosX(int x) {
		this.posX=x;
	}
	
	public void setPosY(int y) {
		this.posY=y;
	}
	
	*/
	
	//movimiento del jugador
	public void mover(int x, int y) {
		//if (Tablero.getTablero().casillaDisponible(x,y)){
		this.posX=x; this.posY=y;
	}
}