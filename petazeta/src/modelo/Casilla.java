package modelo;

public class Casilla 
{
	protected int coordX; // Para la interfaz igual son necesarias
	protected int coordY; // Para la interfaz igual son necesarias
	protected boolean ocupado;
	
	public Casilla(int pX, int pY) //Constructora
	{
		coordX = pX;
		coordY = pY;
		ocupado = false;
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
		System.out.println("Casilla"+coordX+","+coordY+"destruido.");
	}

	
}