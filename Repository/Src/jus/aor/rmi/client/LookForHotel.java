package jus.aor.rmi.client;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jus.aor.rmi.common.Hotel;
import jus.aor.rmi.common.Numero;
import jus.aor.rmi.common._Annuaire;
import jus.aor.rmi.common._Chaine;

/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */

/**
 * Représente un client effectuant une requête lui permettant d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */
public class LookForHotel{
	
	public static void main(String[] args) {
		
		if( args.length < 1){
			System.out.println("Localisation attendu");
			System.exit(1);
		}
		
		System.out.println("Début recherche d'Hotel à "+args[0]);
		
		LookForHotel requete= new LookForHotel(args[0]);
		
		System.out.println("Les numéros des Hotels situés à "+args[0]+" sont :");
		
		long dureeinterrogation;
		try {
			dureeinterrogation = requete.call();
			
			requete.printResults();
		
			System.out.println("L'intérrogation à pris "+dureeinterrogation+" millisecondes");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** le critère de localisaton choisi */
	private String localisation;
	/** la liste des hotels pour la localisation demandée **/
	protected List<Hotel> hotels = new ArrayList<Hotel>();
	/** la liste des numéros d'hotel **/
	protected List<Numero> numeros = new ArrayList<Numero>();
	
	private int nbchaines = 4;
	
	/** stubs des chaines d'hotels **/
	private List<_Chaine> chaines = new ArrayList<_Chaine>(); 
	
	/** stub de l'annuaire **/
	private _Annuaire annuaire = null;
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args){
		
		localisation = args[0];
		
		String host = "127.0.0.1";
		int port = 1099;
		
		Registry reg;
		try {
		
			_Chaine cha;
			//récupère les chaines d'hotels
			for (int i=1;i<=nbchaines;i++){
				reg = LocateRegistry.getRegistry(host,port+i);
				
				cha = (_Chaine) reg.lookup("chaine"+i);
				
				chaines.add(cha);
			}
			
			//récupère l'annuaire
			
			reg = LocateRegistry.getRegistry(host);
			_Annuaire ann = (_Annuaire) reg.lookup("annuaire");
			
			annuaire = ann;
			
			
			

		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() throws RemoteException {
		
		long datedeb = System.currentTimeMillis();
		
			for(_Chaine cha : chaines){
				
				hotels.addAll(cha.get(localisation));
				
			}
			
			Numero num;
			
			Hotel hot;
			for(int i=0; i<hotels.size();i++){
				
				hot = hotels.get(i);
				 
				num = annuaire.get(hot.name);
				
				numeros.add(num);
				
			}
			
		long datefin = System.currentTimeMillis();
		
		return (datefin - datedeb);
		
	}
	
	public void printResults(){
		
		for(int i=0;i<hotels.size();i++){
			System.out.println(hotels.get(i)+" : "+numeros.get(i));
		}
		
	}

}
