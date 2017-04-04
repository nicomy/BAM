package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class AgentServer  implements Runnable{
	
	//name 
	private String name;

	//port
	private int port = 10140; // initie avec le port par defaut;
	
	
	public AgentServer(int port, String name) {
		this.name = name; 
		this.port = port ; 
	}

	/** List of services in the AgentService */
	
	
	
	
//	private Map<String, _Service<?>> pServices;

	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(port, 1000);
			
			Socket client = null;
			_Agent agent = null;
			while(true){
				client = ss.accept();
				agent = getAgent(client);
				
				agent.reInit(this, name);
				
				new Thread(agent).start();
			}
		} catch (IOException e) {
			System.out.println("ici");
			e.printStackTrace();
		}
	}

	private _Agent getAgent(Socket client) {
		// TODO Auto-generated method stub
		return null;
	}

	public URI site() {
		// TODO Auto-generated method stub
		return null;
	}

	public _Service<?> getService(String s) {
		// TODO Auto-generated method stub
		return null;
	}

}
