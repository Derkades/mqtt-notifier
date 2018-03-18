package xyz.derkades.mqtt_notifier;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class App {
	
	public static final String MQTT_HOST = "192.168.1.100";
	public static final int MQTT_PORT = 1883;
	public static final String MQTT_USER = "etenKnop";
	public static final String MQTT_PASSWORD = "z3Z4poX4vSov54SP3Rrz";
	
    public static void main(String[] args) throws Exception {
        
        System.out.println("Starting...");
        
        Thread.sleep(1000);
        
        while (true) {
        	subscribe();
        	System.out.println("Connection lost. Reconnecting in 3 seconds.");
        	Thread.sleep(3000);
        }
        
    }
    
    private static void subscribe() throws Exception {
    	System.out.println("Connecting...");
        MQTT mqtt = new MQTT();
        mqtt.setHost(MQTT_HOST, MQTT_PORT);
        mqtt.setUserName(MQTT_USER);
        mqtt.setPassword(MQTT_PASSWORD);
        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        System.out.println("Connected!");
        connection.subscribe(new Topic[] {new Topic("iot/eten", QoS.AT_LEAST_ONCE)});
        Message message = connection.receive();
        playSound();
        message.ack();
        connection.disconnect();
    }
    
    private static void playSound() throws Exception {
    	System.out.println("Playing sound");
		URL url = new URL("https://files.derkades.xyz/eten.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

		clip.open(audioInputStream);

		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		clip.start();
    }
    
}
