package jus.aor.mobilagent.kernel;

public class AgentServer implements Runnable{

	
	String Name ; 
	int port ;
	
	public AgentServer(int port, String name){
		this.Name = name; 
		this.port = port ;
		
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
