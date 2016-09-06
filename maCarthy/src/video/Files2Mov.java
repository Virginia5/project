package video;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jim2mov.DefaultMovieInfoProvider;
import org.jim2mov.FrameSavedListener;
import org.jim2mov.ImageProvider;
import org.jim2mov.Jim2Mov;
import org.jim2mov.MovieInfoProvider;
import org.jim2mov.MovieSaveException;
import org.jim2mov.MovieUtils;

public class Files2Mov implements ImageProvider, FrameSavedListener
{
    private String[] files = null;
    private int type = MovieInfoProvider.TYPE_QUICKTIME_JPEG;
    
    /**
     * Creates a new instance of Files2Mov and init
     */
    public Files2Mov(String[] files, int type, String saveFileLocation)
    {
        this.files = files;
        this.type = type;
        DefaultMovieInfoProvider dmip = new DefaultMovieInfoProvider(saveFileLocation);
        dmip.setFPS(3);
        dmip.setNumberOfFrames(files.length);
        if(files.length<1) return;
        try {
			BufferedImage image = ImageIO.read(new FileInputStream(files[0]));
			//set video size
			dmip.setMWidth(image.getWidth());
	        dmip.setMHeight(image.getHeight());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        
        try {
        	Jim2Mov obj = new Jim2Mov(this, dmip, this);
        			obj.saveMovie(type);
        }
        catch(MovieSaveException mse)
        {
            mse.printStackTrace();
        }        
    }

    public void frameSaved(int frameNumber)
    {
        //System.out.println("Saved frame: " + frameNumber);
    }

    public byte[] getImage(int frame)
    {
        try
        {            
            return MovieUtils.convertImageToJPEG(new File(files[frame].trim()), 1.0f);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        
        return null;
    }    
    
}

