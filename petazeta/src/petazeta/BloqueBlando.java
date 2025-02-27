package petazeta;

public class BloqueBlando extends Casilla {
	
	public BloqueBlando(int pX, int pY) //Constructora
	{
		super(pX,pY,true);
	}
	
	
	public void destruir() //Destruye el BloqueBlando
	{
		System.out.println("BloqueBlando"+coordX+","+coordY+"destruido.");
	}

	
	

}