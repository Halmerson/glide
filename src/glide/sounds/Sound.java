package glide.sounds;

import glide.Glide;

public class Sound {
	private kuusisto.tinysound.Sound sound;
	
	public Sound(String url)
	{
		this.sound = kuusisto.tinysound.TinySound.loadSound(url);
	}
	
	public void play()
	{
		if (Glide.sounds) {
			sound.play();
		}
	}
}
