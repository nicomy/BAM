package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class AgentServer  implements Runnable{
	
	//name 
	private String name;

	//port
	private int port;
	
	private String surname ; 
	
	public AgentServer(int port, String name) {
		this.name = name; 
		this.port = port ; 
	}

	/** List of services in the AgentService */
	
	public String toString(){
		return name ; 
	}
	
	
//	private Map<String, _Service<?>> pServices;

	@Override
	public void run() {
		try {
			ServerSocket servSocket = new ServerSocket(port);
			
			
			while(true){
				Socket client = servSocket.accept(); // wait for a connection
				_Agent agent = getAgent(client);
				agent.reInit(this, name);
				
				new Thread(agent).start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private _Agent getAgent(Socket client) {
		
		BAMAgentClassLoader agentLoader = new BAMAgentClassLoader(this.getClass().getClassLoader());

		InputStream in;
		_Agent agent = null;
		
		try {
			in = client.getInputStream();

		ObjectInputStream inRepo = new ObjectInputStream(in);
		AgentInputStream ais = new AgentInputStream(in, agentLoader);

		Jar repo = (Jar) inRepo.readObject();
		
		agentLoader.integrateCode(repo);
		

		agent = (_Agent) ais.readObject();
		ais.close();
		
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		return agent;
	}

	public URI site() {
		
		URI uri = null;
		try {
			uri = new URI("mobileagent://localhost:" + port);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	public _Service<?> getService(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setsurname(String surname2) {
		this.surname = surname2;
		
	}

}
