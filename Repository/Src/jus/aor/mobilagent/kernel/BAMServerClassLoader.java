package jus.aor.mobilagent.kernel;

import java.net.URL;
import java.net.URLClassLoader;

public class BAMServerClassLoader extends URLClassLoader{

	public BAMServerClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void addURL(URL url) {
		// TODO Auto-generated method stub
		super.addURL(url);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}



}
