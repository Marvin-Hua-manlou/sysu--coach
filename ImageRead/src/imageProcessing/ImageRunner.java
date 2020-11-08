package imageProcessing;
//import javax.imageio.ImageReader;
import imagereader.Runner;

public class ImageRunner {
    public static void main(String[] args) {
        ImplementImageIO IO = new ImplementImageIO();
        ImplementImageProcessor processor = new ImplementImageProcessor();
        Runner.run(IO, processor);
	} 		
    
}
