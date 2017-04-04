package jus.aor.rmi.serveur;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.rmi.common.Numero;
import jus.aor.rmi.common._Annuaire;

public class Annuaire extends UnicastRemoteObject implements _Annuaire {

	/**
	 * 
	 */
	private static final long serialVersionUID = -909221380754771198L;
	private HashMap<String, Numero> numeros;
	
	public Annuaire(String filename) throws RemoteException
	{
		numeros = new HashMap<String, Numero>();
		
		DocumentBuilder docBuilder = null;
		org.w3c.dom.Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//DataStore/Annuaire.xml
			doc = docBuilder.parse(new File(filename));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de l'annuaire */
		for(int i =0; i<list.getLength();i++) {
			attrs = list.item(i).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			numero=attrs.getNamedItem("numero").getNodeValue();
			numeros.put(name,new Numero(numero));
		}

		/* récupération d'informations de configuration */
//		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		doc = docBuilder.parse(new File(filename));
//		//On récupère les arguments pour la construction de Chaine
//		String arguments = doc.getElementsByTagName("service").item(0).getAttributes().getNamedItem("args").getNodeValue();
	}
	
	public Numero get(String abonne) throws RemoteException {
		return numeros.get(abonne);
	}

}
