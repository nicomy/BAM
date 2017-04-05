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
import java.util.logging.Level;

public abstract class Agent implements _Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4332202893912153271L;
	private Route route;
	protected transient AgentServer agentServer;
	private transient String serverName;
	private  Etape etapeVide;
	private transient Socket socket;

	public Agent() { // TODO : peut etre mettre Object ... args

	}

	@Override
	public void run() {
		

		if (this.route.hasNext()) {
			
			Etape etape = this.route.next();
			etape.action.execute();
			
			move();
		}

	}

	@Override
	public void init(AgentServer agentServer, String serverName) {

		this.agentServer = agentServer;
		this.serverName = serverName;

		
		etapeVide = new Etape(this.agentServer.site(), _Action.NIHIL);

		this.route = new Route(new Etape(agentServer.site(), this.retour()));

		this.route.add(etapeVide);

	}

	@Override
	public void reInit(AgentServer server, String serverName) {

		this.agentServer = server;
		this.serverName = serverName;

	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);

	}

	public String toString() {
		return "Agent actually on: " + serverName + " with route: " + route.toString(); 

	}

	protected abstract _Action retour();

	protected _Service<?> getService(String s) {
		return agentServer.getService(s);

	}

	private void move() {
		// on recup l'URI de la prochaine étape
		move(route.get().server);
	}

	protected void move(URI destination) {

		try {
			// création de la socket
			socket = new Socket(destination.getHost(), destination.getPort());
			
			// récupération du classLoaderAgent
			BAMAgentClassLoader agentLoader = (BAMAgentClassLoader) this.getClass().getClassLoader();
			Jar jar = agentLoader.extractCode();
			
			// flux sortant 
			OutputStream out = socket.getOutputStream();
			ObjectOutputStream outJar = new ObjectOutputStream(out);
			ObjectOutputStream outAgent = new ObjectOutputStream(out);
			
			// envoie du jar + agent
			outJar.writeObject(jar);
			outAgent.writeObject(this);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected String route() {
		return route.toString();

	}

}
