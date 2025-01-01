package Res;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MP3Mplayer {
    public static void playAudio(String sf) {
        String sff="/home/thanglm2006/Audios/"+sf;
        Player p = null;
        try {
            p = new Player(new FileInputStream(sff));
            p.play();
        } catch (JavaLayerException | FileNotFoundException e) {
            try {
                p = new Player(new FileInputStream("/C:/Audios/"+sf));
                p.play();
            } catch (FileNotFoundException | JavaLayerException ex) {
                e.printStackTrace();
            }
        }
    }
}
