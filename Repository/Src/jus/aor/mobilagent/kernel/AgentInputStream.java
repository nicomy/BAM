package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * ObjectInputStream sp�cifique au bus � agents mobiles. Il permet d'utiliser le loader de l'agent.
 * @author   Morat
 */
class AgentInputStream extends ObjectInputStream{
    /**
     * le classLoader � utiliser
     */
    BAMAgentClassLoader loader;
    AgentInputStream(InputStream is, BAMAgentClassLoader cl) throws IOException{super(is); loader = cl;}
    protected Class<?> resolveClass(ObjectStreamClass cl) throws IOException,ClassNotFoundException{return loader.loadClass(cl.getName());}
}