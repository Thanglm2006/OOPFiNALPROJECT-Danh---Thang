package Component.PanelPrototype;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioBar extends JPanel {
    private JButton play, pause;
    private JSlider au;
    private AdvancedPlayer go;
    private long fps;
    private boolean isPause = false, isPlaying = false;
    private String file;
    private Thread playThread;
    private double totalSeconds;
    private Mp3File mp3File;
    private Timer sliderTimer;
    private ImageIcon im,i1;
    private JLabel timeLabel;
    public AudioBar(int maxWidth, String file) {
        setBackground(Color.WHITE);
        this.file = file;

        try {
            mp3File = new Mp3File(file);
            totalSeconds = mp3File.getLengthInSeconds();
            fps = mp3File.getFrameCount() / (long) totalSeconds;
        } catch (IOException | InvalidDataException | UnsupportedTagException e) {
            e.printStackTrace();
        }

        setMaximumSize(new Dimension(maxWidth, 50));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        im = new ImageIcon(smallQuestion.class.getResource("/Image/play_arrow_16dp_000000_FILL0_wght400_GRAD0_opsz20.png"));
        Image scaledImage = im.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        i1 = new ImageIcon(smallQuestion.class.getResource("/Image/pause_16dp_000000_FILL0_wght400_GRAD0_opsz20 (1).png"));
        Image scaledImage1 = i1.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        play= new JButton(scaledIcon);
        play.addActionListener(e->{
            beginPlay();
        });
        pause= new JButton(scaledIcon1);
        pause.setVisible(false);
        JLabel time= new JLabel();
        pause.setPreferredSize(new Dimension(20,20));
        play.setPreferredSize(new Dimension(20,20));
        play.addActionListener(e -> beginPlay());
        pause.setVisible(false);
        pause.addActionListener(e -> pausePlayback());

        au = new JSlider(0, (int) totalSeconds);
        au.setValue(0);

        timeLabel = new JLabel("00:00");
        timeLabel.setFont(new Font("MV Boli", Font.PLAIN, 12));

        au.addChangeListener(e -> {
            if (!isPlaying && !isPause) {
                if(au.getValue()==au.getMaximum()) {
                    au.setValue(0);
                    timeLabel.setText("00:00");
                }
                timeLabel.setText(formatTime(au.getValue()));
            }
        });

        au.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isPlaying=false;
                isPause=true;
                pause.setVisible(false);
                play.setVisible(true);
                go.close();
                playThread.interrupt();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                isPlaying=false;
                isPause=true;
                pause.setVisible(false);
                play.setVisible(true);
                go.close();
                playThread.interrupt();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                    beginPlay();
            }
        });

        add(play);
        add(pause);
        add(au);
        add(timeLabel);

        sliderTimer = new Timer(1000, e -> updateSlider(timeLabel));
    }

    private void beginPlay() {
        if (isPlaying) return;

        isPause = false;
        isPlaying = true;

        play.setVisible(false);
        pause.setVisible(true);

        playThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                go = new AdvancedPlayer(bis);
                go.play((int) (au.getValue() * fps), Integer.MAX_VALUE);
                play.setVisible(false);
                pause.setVisible(true);
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            } finally {
                isPlaying = false;isPause=true;
                sliderTimer.stop();
                SwingUtilities.invokeLater(() -> {
                    play.setVisible(true);
                    pause.setVisible(false);

                });
                if(au.getValue()==au.getMaximum()){
                    au.setValue(0);
                    timeLabel.setText("00:00");
                }
            }
        });

        playThread.start();
        sliderTimer.start();
    }
    public void updateSlider(JLabel timeLabel){
        if(au.getValue()<au.getMaximum()){
            au.setValue(au.getValue()+1);
            timeLabel.setText(formatTime(au.getValue()));
        }else{
            au.setValue(0);
            timeLabel.setText("00:00");
        }
    }
    public void pausePlayback(){
        if(isPause){
            beginPlay();
        }else{
            if(go!=null){
                go.close();
            }
            isPause=true;
            isPlaying=false;
            play.setVisible(true);
            pause.setVisible(false);
        }
    }
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
