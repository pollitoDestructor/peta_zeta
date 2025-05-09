package modelo;

public class BloqueBlando extends Casilla {
	
	public BloqueBlando(int pX, int pY) //Constructora
	{
		super(pX,pY);
		setOcupado(true);
	}
	
	public void destruir() //Destruye el BloqueBlando
	{
//		System.out.println("BloqueBlando"+coordX+","+coordY+"destruido.");
	}

	
	

}