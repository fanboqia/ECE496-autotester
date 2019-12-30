package ca.utoronto.ece419.tester.wrapper;

import ca.utoronto.ece419.tester.wrapper.exceptions.EncounteredException;
import ca.utoronto.ece419.tester.wrapper.exceptions.ForwardedException;
import ca.utoronto.ece419.tester.wrapper.exceptions.NullReturnException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public final class ECSNode extends Wrapper{
    private static Class classObject = null;
    private static String className = null;
    private static Constructor ctor = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        captureMethods.add("getNodeName");
        captureMethods.add("getNodeHost");
        captureMethods.add("getNodePort");
        captureMethods.add("getNodeHashRange");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(ECSNode.classObject, captureMethods, methods);

        try {
            ECSNode.ctor = ECSNode.classObject.getConstructor(String.class,String.class,int.class);
        } catch (NoSuchMethodException e) {
            throw new EncounteredException("Class `" + ECSNode.classObject.getName() + "` is missing a constructor with (String, String, int) parameters", e);
        }
    }

    public static void setClass(Class clientClass) {
        ECSNode.classObject = clientClass;
        ECSNode.className = ECSNode.classObject.getName();
        ECSNode.loadClass();
    }

    public static Class getClassObject() {
        return ECSNode.classObject;
    }

    public static ECSNode bind(Object instance) {
        ECSNode res = new ECSNode();
        res.instance = instance;
        return res;
    }

    //not-sure
    public static Collection<ECSNode> bindCollection(Object instance) {
        Collection collection = (Collection) instance;
        Collection ret = new ArrayList<ECSNode>();
        for(Object col : collection){
            ECSNode res = new ECSNode();
            res.instance = col;
            ret.add(res);
        }
        return ret;
    }

    private ECSNode(){
    }

    public ECSNode(String name, String host, Integer port) {
        try {
            this.instance = ECSNode.ctor.newInstance(name,host,port);
        } catch (InstantiationException e) {
            throw new EncounteredException("Encountered InstantiationException! This means an abstract class's constructor was called! How did you get here?", e);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Contructor of `" + ECSNode.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Contructor of `" + ECSNode.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Contructor of `" + ECSNode.className + "` called with incorrect parameters.", e);
        } catch (ExceptionInInitializerError e) {
            throw new EncounteredException("Call to contructor of `" + ECSNode.className + "` failed.", e);
        }
    }

    public String getNodeName() {
        String method = "getNodeName";
        try {
            Object obj = ECSNode.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECSNode.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + ECSNode.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` called with incorrect parameters.", e);
        }
    }

    public String getNodeHost() {
        String method = "getNodeHost";
        try {
            Object obj = ECSNode.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECSNode.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` called with incorrect parameters.", e);
        }
    }

    public int getNodePort() {
        String method = "getNodePort";
        try {
            Object obj = ECSNode.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECSNode.className + "` returned null.");
            }
            return (Integer) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` called with incorrect parameters.", e);
        }
    }

    public String[] getNodeHashRange() {
        String method = "getNodeHashRange";
        try {
            Object obj = ECSNode.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECSNode.className + "` returned null.");
            }
            return (String[]) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECSNode.className + "` called with incorrect parameters.", e);
        }
    }
}
