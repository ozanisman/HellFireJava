import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class Sound {
	
	public File backgroundMusic;
	public Clip clip;
	
	public Sound() {
		this.backgroundMusic = new File("images/backgroundMusic.wav"); //https://www.youtube.com/watch?v=EF_8ZFSxgyQ
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playSound() {
		try {
			clip.open(AudioSystem.getAudioInputStream(backgroundMusic));
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}

	}
	
	public void stopSound() {
		clip.stop();
		clip.close();
	}


}
