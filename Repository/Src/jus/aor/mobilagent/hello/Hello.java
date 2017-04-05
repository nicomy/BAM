package jus.aor.mobilagent.hello;


import java.util.logging.Level;

import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.Starter;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class Hello extends Agent{

	private String etape;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3882387847447156013L;
	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public Hello(Object... args) {
		 etape = "";
	 }
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action doIt = new _Action(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -9129644307555501553L;

		@Override
		public void execute() {
			Starter.getLogger().log(Level.INFO, "Hello Wolrd on est sur le serveur : \n" + agentServer.toString());
			etape = etape.concat(String.format("%s\n", agentServer.toString()));
			
		}
		

	};
	/* (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
	@Override
	protected _Action retour(){
		return new _Action(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void execute() {
				Starter.getLogger().log(Level.INFO, "Les serveurs visitées sont : \n " +etape);
			}
			
			
		};
	}
	// ...
}
