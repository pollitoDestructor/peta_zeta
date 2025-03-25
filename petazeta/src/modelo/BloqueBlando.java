package Modelo;

public class BloqueBlando extends Casilla {
	
	public BloqueBlando(int pX, int pY) //Constructora
	{
		super(pX,pY);
		ocupado = true;
	}
	
	
	public void destruir() //Destruye el BloqueBlando
	{
		System.out.println("BloqueBlando"+coordX+","+coordY+"destruido.");
	}

	
	

}