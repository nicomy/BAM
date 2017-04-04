package jus.aor.rmi.serveur;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.rmi.common.Hotel;
import jus.aor.rmi.common._Chaine;

public class Chaine extends UnicastRemoteObject implements _Chaine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055319164611062713L;
	private List<Hotel> hotels = new ArrayList<Hotel>();
	
	public Chaine(String filename) throws RemoteException
	{
		/* récupération des hôtels de la chaîne dans le fichier xml passé en 1er argument */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File(filename));
		} catch (ParserConfigurationException | IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name, localisation;
		NodeList list = doc.getElementsByTagName("Hotel");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de la base d'hôtels */
		for(int i =0; i<list.getLength();i++) {
			attrs = list.item(i).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			localisation=attrs.getNamedItem("localisation").getNodeValue();
			hotels.add(new Hotel(name,localisation));
		}

		/* récupération d'informations de configuration */
//		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		Document doc = docBuilder.parse(new File(filename));
//		//On récupère les arguments pour la construction de Chaine
//		String arguments = doc.getElementsByTagName("service").item(0).getAttributes().getNamedItem("args").getNodeValue();


	}
	
	
	public List<Hotel> get(String localisation) throws RemoteException {
		List<Hotel> resultList = new ArrayList<Hotel>();
		for (Hotel h : hotels){
			if (h.localisation.equals(localisation)){resultList.add(h);}
		}
		return resultList;
	}

	
	
}
