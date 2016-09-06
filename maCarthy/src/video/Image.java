package video;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Image {
	private static final Robot robot;
	static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
	/**
	 * call system function to cut the screen image
	 * @param rectangle: the feature of screen image
	 * @return Bufferimage
	 */
	public static BufferedImage getScreen(Rectangle rectangle) {
        return robot.createScreenCapture(rectangle);
    }
}
