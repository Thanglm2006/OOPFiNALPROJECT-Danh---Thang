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
//package Res;
//
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
//
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MP3Mplayer {
//    public static void playAudio(String urlString) {
//        Player player = null;
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
//                player = new Player(inputStream);
//                player.play();
//            } else {
//                System.out.println("Failed to fetch audio. Response code: " + connection.getResponseCode());
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error playing audio: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
////        String audioUrl = "https://drive.google.com/uc?id=YOUR_FILE_ID&export=download";
////        playAudio(audioUrl);
//    }
//}
//
