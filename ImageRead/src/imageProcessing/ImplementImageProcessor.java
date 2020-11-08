package imageProcessing;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import imagereader.IImageProcessor;


public class ImplementImageProcessor implements IImageProcessor {

	@Override//重载各个函数
	public Image showChanelB(Image image) {
		Filter filter = new Filter("Blue");
		Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
		return img;
	}

	@Override//重载各个函数
	public Image showChanelG(Image image) {//syui sidn snkwndk
		Filter filter = new Filter("Green");
		Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
		return img;//返回图像
	}

	@Override
	public Image showChanelR(Image image) {
		Filter filter = new Filter("Red");
		Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
		return img;//返回图像
	}

	@Override
	public Image showGray(Image image) {//灰色的函数
		Filter filter = new Filter("Gray");
		Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));
		return img;//返回图像
	}

}

class Filter extends RGBImageFilter {//获得当前的rgb
    private String color;
    public  Filter(String color) {
        this.color = color;//返回图像
	}
	@Override
	public int filterRGB(int x, int y, int rgb) {//获得直接的rgb
		try {
			if (color.equals("Red")) {
				return (int)(rgb & 0xffff0000);
			}
			else if (color.equals("Green")){
				return (int)(rgb & 0xff00ff00);
			}
			else if (color.equals("Blue")){
				return (int)(rgb & 0xff0000ff);//rgb的可能值提供给我们做函数的匹配
			}
			else if (color.equals("Gray")){
				int temp = (int)( ((rgb & 0x00ff0000) >> 16)*0.299 + ((rgb & 0x0000ff00) >> 8 )*0.587  
	                    + ((rgb & 0x000000ff))*0.114 );  
	            return (rgb & 0xff000000) + (temp << 16) + (temp << 8) + temp;  
			}
			else {
				throw new Exception("Wrong input!");
			}
		}
		catch(Exception e) {
			 System.out.println(e.getMessage());//返回图像的可能范围
		}
		return 0;
	}
	
} 
