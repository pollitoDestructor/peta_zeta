package petazeta;

import java.util.Observable;

public class Jugador extends Observable { 
	
	private static Jugador miJugador; //referencia estatica
	private int posX;
	private int posY;
	private String color="blanco";
	private int bombas=10;

	private Jugador() {
		this.posX=0;
		this.posY=0;
	}
	
	//obtener obj estatico
	public static Jugador getJugador() {
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

	public String getColor() {
		return this.color;
	}
	
	public void addBomba() {
		if (this.bombas<5) {
			this.bombas++;
		}
	}
	
	public void inicio() //para imprimir la primera pos
	{
		setChanged();
		notifyObservers(new Object[] {posX,posY,0,0});
	}

	public void ponerBomba()
	{ 
		if (this.bombas!=0) {
		bombas--;
		Tablero.getTablero().ponerBomba(this.posX, this.posY);
		System.out.println("Se pone una bomba en (" + this.posX + ", " + this.posY + ")");
		}
	}
	
	//movimiento del jugador
	public void mover(int x, int y) {
		if (Tablero.getTablero().casillaDisponible(posX+x,posY+y)){
			setChanged();
			notifyObservers(new Object[] {posX,posY,x,y}); //le manda la pos SIN ACTUALIZAR
			this.posX=posX+x; this.posY=posY+y;
			
		}
		//else {System.out.println("El movimiento no se ha podido efectuar");}
	}
}