package temp;

import java.io.File;

public class Temp {
public static void main(String[] args) {
//	Chrome obj = new Chrome();
//	obj.init();
//	System.out.println(obj.OneVideo("http://www.xmjl-gy.com/"));
//	System.out.println(obj.Videos("res/url.txt"));
	
	File a = new File("res/conf.properties");
	String s = a.getAbsolutePath();
	System.out.println(s);
}
}
