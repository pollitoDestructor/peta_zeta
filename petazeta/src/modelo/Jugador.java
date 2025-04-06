package modelo;

import java.util.Observable;


@SuppressWarnings("deprecation")
public class Jugador extends Observable {

	private static Jugador miJugador; //referencia estatica
	private int posX;
	private int posY;
	private int bombas=10;
	private int puntuacion=0;
	private String color="white";

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

	public String getColor() {return this.color;}
	public int getPuntuacion() {return this.puntuacion;}
	public void setColor(String pColor){
		this.color=pColor;
		if(color.equals("white")) {this.bombas=10;}
		else if(color.equals("black")) {this.bombas=1;}
	}

	public void addBomba() {
		this.bombas++;
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
		notifyObservers(new Object[] {"mover",posX,posY,0,0,color});
	}

	public void ponerBomba()
	{
		if (this.bombas>0 && Tablero.getTablero().casillaDisponible(posX,posY,this.posX, this.posY,"Jugador")) {
			bombas--;
			Tablero.getTablero().ponerBomba(posX, posY);
		}
	}


	//movimiento del jugador
	public void mover(int x, int y) {
		if (Tablero.getTablero().casillaDisponible(posX,posY,posX+x,posY+y,"Jugador")) {
			setChanged();
			notifyObservers(new Object[] {"mover",posX,posY,x,y,color}); //le manda la pos SIN ACTUALIZAR
			this.posX=posX+x; this.posY=posY+y;

		}
		//else {System.out.println("El movimiento no se ha podido efectuar");}
	}

	public void actualizarPuntuacion(int newP) {
		Jugador.getJugador().puntuacion = puntuacion + newP;
		setChanged();
		notifyObservers(new Object[] {"punt",puntuacion});
	}
	public void guardarPuntuacion() {
		Ranking.getRanking().anadirJugador(color, puntuacion);
	}

}