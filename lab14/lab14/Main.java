package lab14;
import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;
import lab14lib.GeneratorPlayer;
import lab14lib.GeneratorDrawer;
import lab14lib.GeneratorAudioVisualizer;
import java.util.ArrayList;
import lab14lib.MultiGenerator;

public class Main {
	public static void main(String[] args) {
		Generator generator = new StrangeBitwiseGenerator(1024);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(128000, 1000000);
//		Generator generator = new StrangeBitwiseGenerator(6000);
////		GeneratorDrawer dra = new GeneratorDrawer(generator);
////		dra.draw(5000);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);
//		Generator generator = new SawToothGenerator(512);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);
	}
} 