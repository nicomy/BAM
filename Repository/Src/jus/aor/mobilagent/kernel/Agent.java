package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public abstract class Agent implements _Agent {
	
	private Route route;
	private AgentServer agentServer;
	private String serverName;
	private Etape etapeVide; //TODO : regarder que ca fou pas la merde, sinon remettre comme GitHub
	private Socket socket;
	
	public Agent() { //TODO : peut etre mettre Object ... args

	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void init(AgentServer agentServer, String serverName) {
		
		this.agentServer = agentServer;
		this.serverName = serverName;
		
		//TODO : au cas ou ca marche pas faire le truc d'Antoine qui est pas content
		etapeVide = new Etape(this.agentServer.site(), _Action.NIHIL);
		
		this.route = new Route(new Etape(agentServer.site(), this.retour()));

	
		this.route.add(etapeVide);

		
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		
		this.agentServer = agentServer;
		this.serverName = serverName;
		
	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);
		
	}
	
	public String toString(){
		return "Agent on server [" + serverName + "] with route: " + route.toString(); //TODO : attention ca vient du github
		
	}
	
	protected abstract _Action retour();
	
	protected _Service<?> getService(String s){
		return agentServer.getService(s);
		
	}
	
	private void move(){
		// on recup l'URI de la prochaine étape
		move(route.get().server);
	}
	
	protected void move(URI uri){ //TODO : Ca c'est du copier coller
		
		try {
			
		socket = new Socket(uri.getHost(), uri.getPort());
		BAMAgentClassLoader agentLoader = (BAMAgentClassLoader) this.getClass().getClassLoader();
		Jar repo = agentLoader.extractCode();
		OutputStream out = socket.getOutputStream();
		ObjectOutputStream outRepo = new ObjectOutputStream(out);
		ObjectOutputStream outAgent = new ObjectOutputStream(out);
		outRepo.writeObject(repo);
		outAgent.writeObject(this);
		
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (NoSuchElementException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
}
		
	}
	
	protected String route(){
		return route.toString();
		
	}

	
	
}
