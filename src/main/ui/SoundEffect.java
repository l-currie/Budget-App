package ui;
// taken from https://www.youtube.com/watch?v=qPVkRtuf9CQ

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//This class is for playing sound effects on button presses.
// has a field for the clip that it plays.
// can set the clip with setFile.
// can play the clip with play.

public class SoundEffect {

    private Clip clip;

    //modifies this
    //effects: sets the file of clip to the file with path soundFileName
    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //modifies: this
    //effects: plays the clip, sets the track to start before playing.
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
}
