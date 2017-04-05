package jus.aor.mobilagent.kernel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class BAMAgentClassLoader extends ClassLoader{

	/* classes du jar */
	Map<String, byte[]> classes = new HashMap<String, byte[]>();

	BAMAgentClassLoader(String nomjar, ClassLoader cl) throws JarException, IOException {
		super(cl);
		integrateCode(new Jar(nomjar));
	}

	BAMAgentClassLoader(ClassLoader cl) {
		super(cl);
	}

	public void integrateCode(Jar jar) {
		Iterator<Entry<String, byte[]>> it = jar.iterator();
		Entry<String, byte[]> entry;
		/* Iter sur les différentes classes du classloader */
		while(it.hasNext()){
			entry = it.next();
			/* ajoute la classe a la map du classloader */
			classes.put(entry.getKey(), entry.getValue());
			/* resoud la classe afin de pouvoir l'utiliser */
			Class<?> c = defineClass(entry.getKey(), entry.getValue(), 0, entry.getValue().length);
			resolveClass(c);
		}
	}

	public Jar extractCode() throws JarException, IOException {
		File fileJar = File.createTempFile("bam", ".jar");
		
		JarOutputStream outstream = new JarOutputStream(new FileOutputStream(fileJar));
		Iterator<Entry<String, byte[]>> it = classes.entrySet().iterator();
		Entry<String, byte[]> entry;
		while(it.hasNext()){
			entry = it.next();
			outstream.putNextEntry(new JarEntry(entry.getKey()));
			outstream.write(entry.getValue());
		}
		outstream.close();
		return new Jar(fileJar.getPath());		
	}
	
	private String className(String s) {
		return s.replace("/", ".").replace(".class", "");
	}

}