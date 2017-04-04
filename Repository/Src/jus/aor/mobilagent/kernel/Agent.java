package jus.aor.mobilagent.kernel;

import java.net.URL;

public abstract class Agent implements _Agent {
	
//	public public Agent() {
//		// TODO Auto-generated constructor stub
//	}

	private Route route;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void init(AgentServer agentServer, String serverName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEtape(Etape etape) {
		route.add(etape);
		
	}
	
	public String toString(){
		return null;
		
	}
	
	protected abstract _Action retour();
	
	protected _Service<?> getService(String s){
		return null;
		
	}
	
	private void move(){
		
	}
	
	protected void move(URL url){
		
	}
	
	protected String route(){
		return null;
		
	}

	
	
}
