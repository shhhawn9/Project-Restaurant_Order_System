//package gui;
//
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.DataLine;
//import java.io.File;
//
//public class ButtonSound {
//    private Clip clip;
//
//    public ButtonSound() {
//        try {
//            String sep = System.getProperty("file.separator");
//            File soundFile = new File("./res/odd.wav");
//            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
//            AudioFormat format = sound.getFormat();
//            DataLine.Info info = new DataLine.Info(Clip.class, format);
//
//            if (!AudioSystem.isLineSupported(info)) {
//                clip = null;
//            } else {
//                clip = (Clip) AudioSystem.getLine(info);
//                clip.open(sound);
//            }
//        } catch (Exception ex) {
//            clip = null;
//        }
//
//    }
//}
