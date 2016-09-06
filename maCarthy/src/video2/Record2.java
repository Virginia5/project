package video2;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
public class Record2 {
	//the size of screen image
	private static Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	//like as message queue
	private static ExecutorService dataProcesser = null;
	//the image data queue
	private static BlockingQueue<BufferedImage> screenQueue = null;
	//the task operation controlled by this , the content inside function run() means the task
	private static ScheduledExecutorService screenCapture;
	//the save path of video
	private String saveVideo ;
	
	/**
	 * start to put screen image to queue
	 * @param path: the save path for screen image
	 */
	public void start(String saveVideo) {
		this.saveVideo = saveVideo+"\\"+System.currentTimeMillis()+"\\";
		File file = new File(this.saveVideo);
		if(!file.exists()){
			file.mkdir();
		}
		
		try {
			screenCapture = Executors.newSingleThreadScheduledExecutor();
			screenQueue = new LinkedBlockingQueue<>();
			screenCapture.scheduleAtFixedRate(new Runnable() {
				public void run() {
					try {
						screenQueue.put(Image2.getScreen(rectangle));
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
						BufferedImage imageData = screenQueue.take();
						File file = new File(saveVideo + i + ".jpeg");
						ImageIO.write(imageData, "jpeg", file);
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
		File file = new File(saveVideo);
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
        	array[i] = saveVideo+images.get(i)+".jpeg";
        }
        //change the screen image to video
//		new Files2Mov(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, "video.mov");
        if(saveVideo.contains(":")){
        	new Files2Mov2(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, "file:"+saveVideo+"video.mov");
        }else{
        	new Files2Mov2(array, MovieInfoProvider.TYPE_QUICKTIME_JPEG, saveVideo+"video.mov");
        }
        
		}

	/**
	 * delete the local screen image
	 * @param path: the save path of local screen image
	 */
	public void deletePic() {
		File file = new File(saveVideo);
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
		return saveVideo+"video.mov";
	}
}
