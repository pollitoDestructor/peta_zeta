package modelo;

import java.util.Observable;

import patrones.PonerBombaGigante;
import patrones.PonerBombaNormal;

@SuppressWarnings("deprecation")
public class Jugador extends Observable { 
	
	private static Jugador miJugador; //referencia estatica
	private int posX;
	private int posY;
	private int bombas=10;
	private String color="blanco";

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
	
	public void addBomba() {
		if (this.bombas<10) {
			this.bombas++;
		}
	}
	
	public boolean estaEnCasilla(int pX, int pY) {
		boolean esta = false;
		if(this.posX==pX && this.posY==pY) {
			esta = true;
		}
		return esta;
	}
	
	public void inicio() //para imprimir la primera pos
	{
		setChanged();
		notifyObservers(new Object[] {posX,posY,0,0});
	}

	public void ponerBomba()
	{ 
		if (this.bombas>0 && Tablero.getTablero().casillaDisponible(this.posX, this.posY,"Jugador")) {
		bombas--;
			if (this.color=="blanco") {
				PonerBombaNormal Normal = new PonerBombaNormal();
				Normal.ponerBomba(posX, posY);}
			else if (this.color=="negro") {
				PonerBombaGigante Gigante = new PonerBombaGigante();
				Gigante.ponerBomba(posX, posY);}
			}
	}
	
	
	//movimiento del jugador
	public void mover(int x, int y) {
		if (Tablero.getTablero().casillaDisponible(posX+x,posY+y,"Jugador")) {
			setChanged();
			notifyObservers(new Object[] {posX,posY,x,y}); //le manda la pos SIN ACTUALIZAR
			this.posX=posX+x; this.posY=posY+y;
			
		}
		//else {System.out.println("El movimiento no se ha podido efectuar");}
	}
}