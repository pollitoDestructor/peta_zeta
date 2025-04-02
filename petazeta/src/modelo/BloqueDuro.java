package modelo;

public class BloqueDuro extends Casilla{
	
	public BloqueDuro(int pX, int pY)
	{
		super(pX,pY);
		ocupado = true;
	}
	
	public void destruir() //Destruye el BloqueBlando
	{
//		System.out.println("BloqueDuro"+coordX+","+coordY+"destruido.");
		
	}


}
