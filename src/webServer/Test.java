package webServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {
	public static void main(String[] args) throws IOException {
		File f=new File("C:\\Users\\Administrator\\Desktop\\java\\tools\\tts9Windows\\tts9\\workspace\\webServer\\Untitled 1");
		InputStreamReader is=new InputStreamReader(new FileInputStream(f));
		char[] buff=new char[1024];
		is.read(buff);
		String s=new String(buff);
		System.out.println(s);
	}
}
