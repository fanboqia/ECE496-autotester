package ca.utoronto.ece419.tester.wrapper;

import ca.utoronto.ece419.tester.wrapper.exceptions.EncounteredException;
import ca.utoronto.ece419.tester.wrapper.exceptions.ForwardedException;
import ca.utoronto.ece419.tester.wrapper.exceptions.NullReturnException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * ECSClient
 */
public final class ECS extends Wrapper{
    private static Class classObject = null;
    private static String className = null;
    private static Constructor ctor = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        //m2-added
        captureMethods.add("start");
        captureMethods.add("stop");
        captureMethods.add("shutdown");
        captureMethods.add("addNode");
        captureMethods.add("addNodes");
        captureMethods.add("setupNodes");
        captureMethods.add("awaitNodes");
        captureMethods.add("removeNodes");
        captureMethods.add("getNodes");
        captureMethods.add("getNodeByKey");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(ECS.classObject, captureMethods, methods);

        try {
            ECS.ctor = ECS.classObject.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new EncounteredException("Class `" + ECS.classObject.getName() + "` is missing a constructor with (String) parameters", e);
        }
    }

    public static void setClass(Class clientClass) {
        ECS.classObject = clientClass;
        ECS.className = ECS.classObject.getName();
        ECS.loadClass();
    }

    public static Class getClassObject() {
        return ECS.classObject;
    }

    public static ECS bind(Object instance) {
        ECS res = new ECS();
        res.instance = instance;
        return res;
    }

    private ECS(){
    }

    public ECS(String configFile) {
        try {
            this.instance = ECS.ctor.newInstance(configFile);
        } catch (InstantiationException e) {
            throw new EncounteredException("Encountered InstantiationException! This means an abstract class's constructor was called! How did you get here?", e);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Contructor of `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Contructor of `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Contructor of `" + ECS.className + "` called with incorrect parameters.", e);
        } catch (ExceptionInInitializerError e) {
            throw new EncounteredException("Call to contructor of `" + ECS.className + "` failed.", e);
        }
    }

    public boolean start() {
        String method = "start";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean stop() {
        String method = "stop";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean shutdown() {
        String method = "shutdown";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public ECSNode addNode(String cacheStrategy, int cacheSize) {
        String method = "addNode";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance, cacheStrategy, cacheSize);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return ECSNode.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public Collection<ECSNode> addNodes(int count, String cacheStrategy, int cacheSize) {
        String method = "addNode";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance, count, cacheStrategy, cacheSize);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }

            return ECSNode.bindCollection(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public Collection<ECSNode> setupNodes(int count, String cacheStrategy, int cacheSize) {
        String method = "setupNodes";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance, count, cacheStrategy, cacheSize);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }

            return ECSNode.bindCollection(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean awaitNodes(int count, int timeout) {
        String method = "awaitNodes";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance,count,timeout);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean removeNodes(Collection<String> nodeNames) {
        String method = "removeNodes";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance,nodeNames);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public Map<String, ECSNode> getNodes() {
        String method = "getNodes";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return (Map<String, ECSNode>) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }

    public ECSNode getNodeByKey(String Key) {
        String method = "getNodeByKey";
        try {
            Object obj = ECS.methods.get(method).invoke(this.instance,Key);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + ECS.className + "` returned null.");
            }
            return ECSNode.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + ECS.className + "` called with incorrect parameters.", e);
        }
    }
}
