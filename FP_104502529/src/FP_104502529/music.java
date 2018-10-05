package FP_104502529;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class music {
	
	private Clip clip;

	music(File hall)
	{
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(hall));
		}
		catch(Exception e)
		{	
			System.out.println("Error with playing sound.");
			e.printStackTrace();
		}
	}
	
		void play()
		{
			clip.start();
		}
		
		void stop()
		{
			clip.stop();
		}
}