package petazeta;

public class BloqueBlando extends Casilla {
	
	public BloqueBlando(int pX, int pY) //Constructora
	{
		super(pX,pY,true);
	}
	
	
	public void destruir() //Destruye el BloqueBlando
	{
		Casilla casillaRota= new Casilla(coordX,coordY,false);
		System.out.println("BloqueBlando"+coordX+","+coordY+"destruido.");
	}

	
	

}