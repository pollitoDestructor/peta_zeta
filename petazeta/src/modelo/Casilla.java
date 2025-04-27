package modelo;

public class Casilla 
{
	private int coordX; // Para la interfaz igual son necesarias
	private int coordY; // Para la interfaz igual son necesarias
	private boolean ocupado;
	
	public Casilla(int pX, int pY) //Constructora
	{
		coordX = pX;
		coordY = pY;
		ocupado = false;
	}
	
	protected int getCoordY() {
		return coordY;
	}
	
	protected int getCoordX() {
		return coordX;
	}
	
	protected void setOcupado(boolean pOcupado) {
        this.ocupado = pOcupado;
	}


	public String tipoCasilla() 
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean estaOcupada() //Devuelve true si est� ocupado (Jugador no puede atravesarlo)
	{
		return ocupado;
	}
	public void destruir() //Destruye la casilla
	{
		//System.out.println("Casilla"+coordX+","+coordY+"destruido.");
	}

	public void actualizar(int pX, int pY) {coordX=pX;coordY=pY;}
	
}