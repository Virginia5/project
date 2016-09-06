package video;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.jim2mov.MovieInfoProvider;
/**
 * The implement of the operation of video recording.
 * @author wujiayun01
 *
 */
public class Record {
	//the size of screen image
	private static Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	//like as message queue
	private static ExecutorService dataProcesser = null;
	//the image data queue
	private static BlockingQueue<BufferedImage> screenQueue = null;
	//the task operation controlled by this , the content inside function run() means the task
	private static ScheduledExecutorService screenCapture;
	//the save path of video
	private static String SAVE_VIDEO_PATH ;//= System.currentTimeMillis()+"";
	
	private static String DEFAULT_FILE_PATH ;//= System.getProperty("java.io.tmpdir")+System.currentTimeMillis()+"\\";
	
	private static String WEB_HOME;
	
	private static Properties prop= new Properties();
	
	/**
	 * start to put screen image to queue
	 * @param path: the save path for screen image
	 */
	public void start() {
		try {
			
			prop.load(new FileInputStream("D:/apache-tomcat-7.0.70/webapps/maCarthy/res/conf.properties"));
			String temp = System.currentTimeMillis()+"/";
			DEFAULT_FILE_PATH = prop.getProperty("DEFAULT_FILE_PATH")+"/"+temp;
			SAVE_VIDEO_PATH = prop.getProperty("SAVE_VIDEO_PATH")+"/"+temp;
			WEB_HOME = prop.getProperty("WEB_HOME")+"/"+temp;
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		File file = new File(DEFAULT_FILE_PATH);
		if(!file.exists()){
			file.mkdir();
		}
		
		try {
			
			screenCapture = Executors.newSingleThreadScheduledExecutor();
			screenQueue = new LinkedBlockingQueue<>();
			screenCapture.scheduleAtFixedRate(new Runnable() {
				public void run() {
					try {
						// put the screen image
						screenQueue.put(Image.getScreen(rectangle));
//						System.out.println("screenQueue! put");
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}, 0, 1000 / 5, TimeUnit.MILLISECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * to take the data in queue to local image
	 * @param path: the save path for local image
	 */
	public void execute() {
		dataProcesser = Executors.newSingleThreadExecutor();
		dataProcesser.execute(new Runnable() {
			public void run() {
				try {
					for (int i = 0;; i++) {
						// take the screen image
						BufferedImage imageData = screenQueue.take();
						File file = new File(DEFAULT_FILE_PATH + i + ".jpeg");
						ImageIO.write(imageData, "jpeg", file);
//						System.out.println("screenQueue! take");
					}
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}
		});
	}
	
	/**change the screen image to video
	 * @param path
	 */
	public void change() {
		ArrayList<Integer> images = new ArrayList<>();
		File file = new File(DEFAULT_FILE_PATH);
		for (File tmp : file.listFiles()) {
			String name = tmp.getName();
			if (name.endsWith(".jpeg")){
				images.add(Integer.parseInt(name.substring(0, name.indexOf("."))));
			}
		}
		
		Collections.sort(images);
		int size = images.size();  
        String[] array = new String[size];
        for(int i=0;i<size;i++){
        	array[i] = DEFAULT_FILE_PATH+images.get(i)+".jpeg";
        }
        //change the screen image to video
//		new Files2Mov(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, "video.mov");
        if(SAVE_VIDEO_PATH.contains(":")){
        	new Files2Mov(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, "file:"+SAVE_VIDEO_PATH+"video.mov");
        }else{
        	new Files2Mov(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, SAVE_VIDEO_PATH+"video.mov");
        }
        
		}

	/**
	 * delete the local screen image
	 * @param path: the save path of local screen image
	 */
	public void deletePic() {
		File file = new File(DEFAULT_FILE_PATH);
		for (File tmp : file.listFiles()) {
			if (tmp.toString().endsWith(".jpeg")) {
				tmp.delete();
			}
		}
	}

	/**
	 * shut down the task
	 */
	public String stop() {
		screenCapture.shutdownNow();
		dataProcesser.shutdownNow();
		return WEB_HOME+"video.mov";
	}
}
