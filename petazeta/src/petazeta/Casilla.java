package petazeta;

public class Casilla 
{
	protected int coordX;
	protected int coordY;
	protected boolean ocupado;
	
	public Casilla(int pX, int pY, boolean pO) //Constructora
	{
		coordX = pX;
		coordY = pY;
		ocupado = pO;
	}
	
	
	public boolean estaOcupada() //Devuelve true si está ocupado (Jugador no puede atravesarlo)
	{
		return ocupado;
	}
	public void destruir() //Destruye la casilla
	{
		System.out.println("Casilla"+coordX+","+coordY+"destruido.");
	}

	
}