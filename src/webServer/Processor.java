package webServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Processor extends Thread {
	private PrintStream out;
	private InputStream input;
	public static final String WEB_ROOT="C:\\Users\\Administrator\\Desktop\\java\\tools\\tts9Windows\\tts9\\workspace\\webServer";
	public Processor(Socket socket){
		try {
			input =socket.getInputStream();
			out=new PrintStream(socket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void run(){
		try {
			String fileName=parse(input);
			readFile(fileName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String parse(InputStream input) throws IOException{
		BufferedReader in=new BufferedReader(new InputStreamReader(input));
		String inputContent=in.readLine();
		if(inputContent==null||inputContent.length()==0){
			sendError(400,"clent invoke error");
			return null;
		}
		String[] request=inputContent.split(" ");
		if(request.length!=3){
			sendError(400,"clent invoke error");
			return null;
		}
		String method=request[0];
		String fileName=request[1];
		String httpVersion=request[2];
		System.out.println(method+" method ;"+" fileName:"+ fileName+"httpVersion:"+ httpVersion);
		return fileName ;
	}
	
	public void readFile(String fileName) throws IOException{
		File file=new File(Processor.WEB_ROOT+fileName);
		if(!file.exists()){
			sendError(404,"file not found");
			return;
		}
		InputStream in =new FileInputStream(file);
		byte[] content =new byte[(int)file.length()];
		in.read(content);
		out.println("HTTP/1.1 200 sendFile");
		out.println("content-length: "+content.length);
		out.println(" ");
		out.write(content);//write the file read from server side
		out.flush();
		out.close();
		in.close();
	}
	
	public void sendError(int errNum,String errMsg){
		out.println("HTTP/1.1"+ errNum+" "+ errMsg);
		out.println("content-type: text/html");
		out.println();
		out.println("<html>");
		out.println("<head><title>Error"+errNum+"--"+errMsg+"</title></head>");
		out.println("<h1>Error"+errNum+"--"+errMsg+"<h1>");
		out.println("<html>");
		out.flush();
		out.close();
		
	}
}
