package petazeta;

public class BloqueDuro extends Casilla{
	
	public BloqueDuro(int pX, int pY)
	{
		super(pX,pY,true);
	}
	
	public void destruir() //Destruye el BloqueBlando
	{
		System.out.println("BloqueDuro"+coordX+","+coordY+"destruido.");
		
	}


}
