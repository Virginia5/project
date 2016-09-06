package browser;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.baidu.selenium.control.er.Button;
import com.baidu.selenium.control.html.TextArea;

import video.ScreenRecord;

public class Chrome {
	private static String CHROME;
	private static String CHROME_DRIVER;
	private static Properties prop= new Properties();
	
	public void init() {
		try {
			prop.load(new FileInputStream("D:/apache-tomcat-7.0.70/webapps/maCarthy/res/conf.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CHROME = prop.getProperty("CHROME");
		CHROME_DRIVER = prop.getProperty("CHROME_DRIVER");
		System.setProperty("webdriver.chrome.driver",CHROME_DRIVER);
	}
	
	public String OneVideo(String url) {
		
		WebDriver driver = null;
		ChromeDriverService service = null;
		ScreenRecord screen = new ScreenRecord();
		String res = "";
		try {
			service = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(CHROME_DRIVER)).usingAnyFreePort().build();
			service.start();
			driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			screen.start();
			driver.get("https://www.baidu.com");
			By by = By.id("kw");
			TextArea planname = new TextArea(driver, by);
			planname.setText(url);
			by = By.id("su");
			Button baidu = new Button(driver, by);
			baidu.click();
			Thread.sleep(4000);
			
			String hasXpath = "//div[@id='1']//a"; // element is exist
			by = By.xpath(hasXpath);
			Button href = new Button(driver, by);
			href.click();
			Thread.sleep(8000);

			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(500);
			r.keyRelease(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			driver.quit();
			res = screen.stop();
	        service.stop();
		}
		return res;
	}
	
	public ArrayList<String> Videos(String path) {
		
		ArrayList<String> list = new ArrayList<>();
        BufferedReader br = null;
        String str = null;
		try {
			br = new BufferedReader(new FileReader(path));
			while ((str = br.readLine()) != null) {
				list.add(OneVideo(str));
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
