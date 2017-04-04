/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.mobilagent.kernel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

/**
 * Définit la feuille de route que l'agent va suivre
 * @author  Morat
 */
class Route implements Iterable<Etape>, Serializable{
	private static final long serialVersionUID = 9081294824590167316L;
	/** la liste des étapes à parcourir autres que la dernière */
	protected List<Etape> route;
	/** la dernière étape de la feuille de route de l'agent qui désigne le serveur de départ. */
	protected Etape retour;
	/** Indique si la feuille de route est épuisée ou non. */
	protected boolean hasNext;
	/**
	 * Construction d'une route.
	 * @param retour  le server initial et de retour.
	 */
	public Route(Etape retour) {
		route = new LinkedList<Etape>();
		this.retour = retour;
		hasNext=true;
	}
	/**
	 * Ajoute une étape en fin de route.
	 * @param e l'étape à ajouter
	 */
	public void add(Etape e) { route.add(route.size(),e);}
	/**
	 * Restitue la prochaine �tape ou la derni�re qui est la base de d�part.
	 * @return la prochaine �tape.
	 */
	Etape get() throws NoSuchElementException {
		

		if(hasNext()){
			if(!this.route.isEmpty()){
				return this.route.get(0);
			}
			return this.retour;
		}
		throw new NoSuchElementException("get() : C'est pas normal !");

	}
	/**
	 * Restitue la prochaine étape et élimine de la route ou la dernière qui est la base de départ.
	 * @return la prochaine étape.
	 */
	Etape next() throws NoSuchElementException {
		
		if (this.hasNext){
			if(!this.route.isEmpty()){
				return this.route.remove(0);
			}
			this.hasNext = false;
			return this.retour;
		}
		throw new NoSuchElementException("next(): C'est pas normal !");
	}
	/**
	 * Il y a-t-il encore une étape à parcourir.
	 * @return vrai si une étape est possible.
	 */
	public boolean hasNext() { return hasNext;}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Etape> iterator(){return route.iterator();}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {return route.toString().replaceAll(", ","->");}
}
