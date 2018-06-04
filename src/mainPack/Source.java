package mainPack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Source {
	static BufferedImage SPACE1_IMAGE;
	static BufferedImage SPACE2_IMAGE;
	static BufferedImage SPACE3_IMAGE;
	static BufferedImage SUN_IMAGE;
	static BufferedImage MERCURY_IMAGE;
	static BufferedImage VENUS_IMAGE;
	static BufferedImage EARTH_IMAGE;
	static BufferedImage MARS_IMAGE;
	static BufferedImage JUPITER_IMAGE;
	static BufferedImage URANUS_IMAGE;
	static BufferedImage NEPTUNE_IMAGE;
	static BufferedImage ASTEROID_IMAGE;
	static BufferedImage MOON_IMAGE;
	static BufferedImage ENCELADUS_IMAGE;
	static BufferedImage TITAN_IMAGE;
	static BufferedImage IO_IMAGE;
	static BufferedImage BLACK_HOLE_IMAGE;
	static BufferedImage CRATER_IMAGE;
	static BufferedImage MARBLE_IMAGE;
	static Clip EXPLOSION;
	static Clip DUN_DUN;
	static Clip THEME;
	static void install() {
		try {
			SPACE1_IMAGE = ImageIO.read(new File("images\\space1.jpg"));
			SPACE2_IMAGE = ImageIO.read(new File("images\\space2.jpg"));
			SPACE3_IMAGE = ImageIO.read(new File("images\\space3.jpg"));
			CRATER_IMAGE = ImageIO.read(new File("images\\crater.png"));
			Star.SUN.setPicture(SUN_IMAGE = ImageIO.read(new File("images\\sun.png")));
			Star.SIRIUS_A.setPicture(SUN_IMAGE = ImageIO.read(new File("images\\sun.png")));
			Star.SIRIUS_B.setPicture(SUN_IMAGE = ImageIO.read(new File("images\\sun.png")));
			Star.RIGEL.setPicture(SUN_IMAGE = ImageIO.read(new File("images\\sun.png")));
			Star.VY_CANIS_MAJORIS.setPicture(SUN_IMAGE = ImageIO.read(new File("images\\sun.png")));
			Planet.MERCURY.setPicture(MERCURY_IMAGE = ImageIO.read(new File("images\\mercury.png")));
			Planet.VENUS.setPicture(VENUS_IMAGE = ImageIO.read(new File("images\\venus.png")));
			Planet.EARTH.setPicture(EARTH_IMAGE = ImageIO.read(new File("images\\earth.png")));
			Planet.MARS.setPicture(MARS_IMAGE = ImageIO.read(new File("images\\mars.png")));
			Planet.JUPITER.setPicture(JUPITER_IMAGE = ImageIO.read(new File("images\\jupiter.png")));
			Planet.URANUS.setPicture(URANUS_IMAGE = ImageIO.read(new File("images\\uranus.png")));
			Planet.NEPTUNE.setPicture(NEPTUNE_IMAGE = ImageIO.read(new File("images\\neptune.png")));
			Satellite.MOON.setPicture(MOON_IMAGE = ImageIO.read(new File("images\\moon.png")));
			Satellite.ENCELADUS.setPicture(ENCELADUS_IMAGE = ImageIO.read(new File("images\\enceladus.png")));
			Satellite.TITAN.setPicture(TITAN_IMAGE = ImageIO.read(new File("images\\titan.png")));
			Satellite.IO.setPicture(IO_IMAGE = ImageIO.read(new File("images\\io.png")));
			BlackHole.SAGITARRUS_A.setPicture(BLACK_HOLE_IMAGE = ImageIO.read(new File("images\\blackhole.png")));
			MARBLE_IMAGE = ImageIO.read(new File("images\\marble.png"));
			
			EXPLOSION = AudioSystem.getClip();
			EXPLOSION.open(AudioSystem.getAudioInputStream(new File("sounds\\explosion.wav")));
			DUN_DUN = AudioSystem.getClip();
			DUN_DUN.open(AudioSystem.getAudioInputStream(new File("sounds\\dun.wav")));
			THEME = AudioSystem.getClip();
			THEME.open(AudioSystem.getAudioInputStream(new File("sounds\\theme.wav")));
			ASTEROID_IMAGE = ImageIO.read(new File("images\\asteroid.png"));
		}catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
