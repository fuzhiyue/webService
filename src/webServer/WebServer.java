package webServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	// using the default socket ip port
	public static final int HTTP_PORT = 8080;
	private ServerSocket serverSocket;

	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("web server start at " + port);
			while (true) {
				Socket socket = serverSocket.accept();
				new Processor(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[]args){
		WebServer server=new WebServer();
		if(args.length==1){
			server.startServer(Integer.parseInt(args[0]));
		}else{
			server.startServer(WebServer.HTTP_PORT);
		}
	}
}
