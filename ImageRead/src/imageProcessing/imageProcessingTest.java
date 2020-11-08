package imageProcessing;
import static org.junit.Assert.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.junit.Before;
import org.junit.Test;

public class imageProcessingTest {
	private ImplementImageIO io = new ImplementImageIO();
	private ImplementImageProcessor processor = new ImplementImageProcessor();
	private Image img1 = io.myRead("F:\\eclipse-Worksapce\\ImageRead\\bin\\imageProcessing\\1.bmp");
	private Image img2 = io.myRead("F:\\eclipse-Worksapce\\ImageRead\\bin\\imageProcessing\\2.bmp");
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void greenTest() {
		Image img1Test = processor.showChanelG(img1);
		Image img2Test = processor.showChanelG(img2);
		testMethod("green",img1Test,img2Test);
	}
	
	@Test
	public void redTest() {
		Image img1Test = processor.showChanelR(img1);
		Image img2Test = processor.showChanelR(img2);
		testMethod("red",img1Test,img2Test);
	}
	
	@Test
	public void blueTest() {
		Image img1Test = processor.showChanelB(img1);
		Image img2Test = processor.showChanelB(img2);
		testMethod("blue",img1Test,img2Test);
	}
	
	@Test
	public void grayTest() {
		Image img1Test = processor.showGray(img1);
		Image img2Test = processor.showGray(img2);
		testMethod("gray",img1Test,img2Test);
	}
	
	
	public static int[] getPixes(BufferedImage bimg) {
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		int[] rgbs = new int[h * w];
		bimg.getRGB(0, 0, w, h, rgbs, 0, w);
		return rgbs;
	}
	
	public void testMethod(String color, Image img1Test, Image img2Test) {

		Image img1Goal = io.myRead("F:\\eclipse-Worksapce\\ImageRead\\bin\\imageProcessing\\goal\\1_"+color+"_goal.bmp");
		Image img2Goal = io.myRead("F:\\eclipse-Worksapce\\ImageRead\\bin\\imageProcessing\\goal\\2_"+color+"_goal.bmp");	
		
		//Test image's width and height
		BufferedImage bufferTest1 = toBufferedImage(img1Test);
		BufferedImage bufferGoal1 = toBufferedImage(img1Goal);
		assertEquals(bufferTest1.getWidth(null), bufferGoal1.getWidth(null));
		assertEquals(bufferTest1.getHeight(), bufferGoal1.getHeight());
		
		BufferedImage bufferTest2 = toBufferedImage(img2Test);
		BufferedImage bufferGoal2 = toBufferedImage(img2Goal);
		assertEquals(bufferTest2.getWidth(null), bufferGoal2.getWidth(null));
		assertEquals(bufferTest2.getHeight(), bufferGoal2.getHeight());
		
		//Test every pixel
		int pixTest1[] = getPixes(bufferTest1);
		int pixGoal1[] = getPixes(bufferGoal1);
		for (int i = 0; i < pixTest1.length; i++) {
			assertEquals(pixTest1[i], pixGoal1[i]);
		}
		
		int pixTest2[] = getPixes(bufferTest1);
		int pixGoal2[] = getPixes(bufferGoal1);
		for (int i = 0; i < pixTest2.length; i++) {
			assertEquals(pixTest2[i], pixGoal2[i]);
		}
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

}
