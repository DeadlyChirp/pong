// package gui;
// import java.io.File; 
// import java.io.IOException; 
// import java.util.Scanner; 
// import java.io.File;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

// public class Sound{

//     public String s1;

//     Sound(String s){
//         this.s1 = s;
//     }

//     // public void audio(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
//     // Scanner scanner = new Scanner(System.in);
//     // File file = new File(s1);
//     // AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//     // Clip clip = AudioSystem.getClip();
//     // clip.open(audioStream);
//     // clip.start();
//     // String response = scanner.next();
//     // }

    
//     public void audio(){
//     // InputStream in = new FileInputStream(this.s1);
//     // AudioStream as = new AudioStream(in);
//     // AudioPlayer.player.start(as);
//     Media hit = new Media(new File(s1).toURI().toString());
//     MediaPlayer mediaPlayer = new MediaPlayer(hit);
//     mediaPlayer.play();
//     }
// }