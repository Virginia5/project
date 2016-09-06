package video2;

/**
 * The interface of operation of the the video recording. 
 * contains three functions:
 * start()
 * stop()
 * getVideoPath()
 * @author wujiayun01
 *
 */
public class ScreenRecord2{
	
	private static Record2 record = null;
	/**
	 * The function is used to open the video recording.
	 * @param path: the file path to save the images
	 */
	public void start(String saveVideo){
		record = new Record2();
		record.start(saveVideo);
		record.execute();
	}
	
	/**
	 * The function is used to close the video recording and return the video path.
	 * @return
	 */
	public String stop(){
		String video = record.stop();
		record.change();
		record.deletePic();
		return video;
	}
}
