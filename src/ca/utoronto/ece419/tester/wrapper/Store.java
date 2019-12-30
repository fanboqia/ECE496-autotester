package ca.utoronto.ece419.tester.wrapper;

import ca.utoronto.ece419.tester.wrapper.exceptions.EncounteredException;
import ca.utoronto.ece419.tester.wrapper.exceptions.ForwardedException;
import ca.utoronto.ece419.tester.wrapper.exceptions.NullReturnException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Store extends Wrapper {
    private static Class classObject = null;
    private static String className = null;
    private static Constructor ctor = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        captureMethods.add("connect");
        captureMethods.add("disconnect");
        //m2-added
        captureMethods.add("isConnected");
        captureMethods.add("put");
        captureMethods.add("get");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(Store.classObject, captureMethods, methods);

        try {
            Store.ctor = Store.classObject.getConstructor(String.class,int.class);
        } catch (NoSuchMethodException e) {
            throw new EncounteredException("Class `" + Store.classObject.getName() + "` is missing a constructor with (String,int) parameters", e);
        }
    }

    public static void setClass(Class storeClass) {
        Store.classObject = storeClass;
        Store.className = Store.classObject.getName();
        Store.loadClass();
    }

    public static Class getClassObject() {
        return Store.classObject;
    }

    public static Store bind(Object instance) {
        Store res = new Store();
        res.instance = instance;
        return res;
    }

    private Store() {
    }

    public Store(String name, Integer port) {
        try {
            this.instance = Store.ctor.newInstance(name,port);
        } catch (InstantiationException e) {
            throw new EncounteredException("Encountered InstantiationException! This means an abstract class's constructor was called! How did you get here?", e);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Contructor of `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Contructor of `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Contructor of `" + Store.className + "` called with incorrect parameters.", e);
        } catch (ExceptionInInitializerError e) {
            throw new EncounteredException("Call to contructor of `" + Store.className + "` failed.", e);
        }
    }

    public void connect() {
        String method = "connect";
        try {
            Store.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` called with incorrect parameters.", e);
        }
    }

    public void disconnect() {
        String method = "disconnect";
        try {
            Store.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public boolean isConnected() {
        String method = "isConnected";
        try {
            Object obj = Store.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Store.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` called with incorrect parameters.", e);
        }
    }

    public KVMessage put(String key, String value) {
        String method = "put";
        try {
            Object obj = Store.methods.get(method).invoke(this.instance, key, value);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Store.className + "` returned null.");
            }
            return KVMessage.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` called with incorrect parameters.", e);
        }
    }

    public KVMessage get(String key) {
        String method = "get";
        try {
            Object obj = Store.methods.get(method).invoke(this.instance, key);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Store.className + "` returned null.");
            }
            return KVMessage.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Store.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Store.className + "` called with incorrect parameters.", e);
        }
    }
}
