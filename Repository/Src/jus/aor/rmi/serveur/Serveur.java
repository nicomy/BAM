package jus.aor.rmi.serveur;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/*
 * Crée les différents services, pour chacune des chaine, et pour l'annuaire. 
 * Chaque service est crée sur un port différent.
 */
public class Serveur {

    public static void main(String[] args) {
    	
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        Registry reg = null;
        
        int nbchaines = 4;
        int port = 1099;
        
        try {
        	
        	Chaine cha;
        	Chaine stubc;
        	//Création des chaines et enregistrement dans le registre.
        	for (int i=1;i<=nbchaines;i++){
        		
        		cha = new Chaine("Src/jus/aor/rmi/DataStore/Hotels"+i+".xml");        		
        		reg = LocateRegistry.createRegistry(port+i);
        		
        		reg.rebind(("chaine"+i), cha);
        		
        		for (String s : reg.list()){
            		System.out.println(s);
            	}
        		       		
        	}
        	
        	
        	//Création du registre et enregistrement dans le registre.  	
        	
        	
        	Annuaire ann = new Annuaire("Src/jus/aor/rmi/DataStore/Annuaire.xml");            
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("annuaire", ann);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
