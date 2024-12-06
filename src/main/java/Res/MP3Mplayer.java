package Res;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MP3Mplayer {
    public static void playAudio(String sf) {
        Player p = null;
        try {
            p = new Player(new FileInputStream(sf));
            p.play();
        } catch (JavaLayerException | FileNotFoundException e) {

            System.out.println("error!");
        }
    }
}
