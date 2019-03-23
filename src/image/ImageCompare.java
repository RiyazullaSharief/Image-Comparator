package image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class ImageCompare
{
	public static void main(String[] args) throws IOException
	{
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://demo.actitime.com");
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebElement img=driver.findElement(By.xpath("(//img)[1]"));
		int x=img.getLocation().getX();
		int y=img.getLocation().getY();
		int h=img.getSize().getHeight();
		int w=img.getSize().getWidth();
		TakesScreenshot t=(TakesScreenshot)driver;
		File src=t.getScreenshotAs(OutputType.FILE);
		String path="./photo/logo.png";
		File dest=new File(path); 
		FileUtils.copyFile(src, dest);
		BufferedImage fullPage=ImageIO.read(new File("./photo/logo.png"));
		BufferedImage act1Img=fullPage.getSubimage(x, y, w, h);	
		ImageIO.write(act1Img,"png",new File("./photo/logo1.png"));
		BufferedImage actImg=ImageIO.read(new File("./photo/logo1.png"));
		BufferedImage expImg=ImageIO.read(new File("./photo/a.png"));
		int count=0;
		System.out.println(expImg.getData());
		System.out.println(expImg.getData().getDataBuffer());
		System.out.println(expImg.getData().getDataBuffer().getSize());
		System.out.println(expImg.getData().getDataBuffer().getElem(25));
		for(int i=0;i<expImg.getData().getDataBuffer().getSize();i++)
		{
			int aP=actImg.getData().getDataBuffer().getElem(i);
			int eP=expImg.getData().getDataBuffer().getElem(i);
			if(aP==eP)
			{
				count++;
			}
		}
		int Per=(count*100)/(expImg.getData().getDataBuffer().getSize());
		System.out.println(Per+"%");
	}
}
