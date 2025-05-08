package modelo;


import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Musica 
{ 
	private static Musica miMusica;
	private boolean cancion = false;
	Clip clip ;
	
	public static Musica getMusica() 
	{
        if (miMusica == null){
        	miMusica = new Musica();
        }
        return miMusica;
    }
	
	public void ponerMusica() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	    if (!cancion) {
	        InputStream audioSrc = getClass().getResourceAsStream("/petazeta/Musica.wav");
	        if (audioSrc == null) {
	            throw new IOException("No se encontró el archivo de música dentro del JAR.");
	        }

	        // Convertir InputStream a AudioInputStream
	        AudioInputStream audioPrueba = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));

	        clip = AudioSystem.getClip();
	        clip.open(audioPrueba);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	        clip.start();
	        cancion = true;
	        System.out.println("Música reproducida");
	    } else {
	        if (clip != null && clip.isRunning()) {
	            clip.stop();
	            clip.close();
	            System.out.println("Música detenida");
	        }
	        cancion = false;
	    }
	}
}
