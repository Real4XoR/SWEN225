package src.main.java.nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * Audio class, responsible for loading and playing sound effects
 */
public class Audio {
  Clip clip;
  File audioURL[] = new File[30];

  public Audio() {

    audioURL[0] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/deathSound.wav");
    audioURL[1] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/doorOpen.wav");
    audioURL[2] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/finishLevel.wav");
    audioURL[3] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/itemPickup.wav");
    audioURL[4] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/keyPickup.wav");
    audioURL[5] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/Step.wav");
    audioURL[6] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/GameMusic.wav");
    audioURL[7] = new File("src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Audio/Hmmm.wav");
  }

  AudioInputStream audioInputStream;

  /**
   * Sets correct audio file to play
   * 
   * @param i File to play
   */
  public void setFile(int i) {

    try {
      if(i > audioURL.length) { System.out.println("Audio doesn't exist"); }
      audioInputStream = AudioSystem.getAudioInputStream(audioURL[i]);
      clip = AudioSystem.getClip();
      clip.open(audioInputStream); 
    } catch (Exception e) { e.printStackTrace(); }
  }

  /**
   * Plays the actual audio file
   */
  public void play() {
    clip.start();
  }

  /**
   * Checks is audio is already running to prevent overlap
   * @return isRunning
   */
  public boolean isRunning() {
    if(clip == null) return false;
    return clip.isRunning();
  }

  /**
   * Loops audio for background music
   */
  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * Stop the looped audio
   */
  public void stop() {
    clip.stop();
  }
}
