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

public final class Client extends Wrapper{
    private static Class classObject = null;
    private static String className = null;
    private static Constructor ctor = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        captureMethods.add("newConnection");
        captureMethods.add("getStore");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(Client.classObject, captureMethods, methods);

        try {
            Client.ctor = Client.classObject.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new EncounteredException("Class `" + Client.classObject.getName() + "` is missing a constructor with no parameters.", e);
        }
    }

    public static void setClass(Class clientClass) {
        Client.classObject = clientClass;
        Client.className = Client.classObject.getName();
        Client.loadClass();
    }

    public static Class getClassObject() {
        return Client.classObject;
    }

    public Client() {
        try {
            this.instance = Client.ctor.newInstance();
        } catch (InstantiationException e) {
            throw new EncounteredException("Encountered InstantiationException! This means an abstract class's constructor was called! How did you get here?", e);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Contructor of `" + Client.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Contructor of `" + Client.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Contructor of `" + Client.className + "` called with incorrect parameters.", e);
        } catch (ExceptionInInitializerError e) {
            throw new EncounteredException("Call to contructor of `" + Client.className + "` failed.", e);
        }
    }

    public void newConnection(String hostname, int port) {
        String method = "newConnection";
        try {
            Client.methods.get(method).invoke(this.instance, hostname, port);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Client.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Client.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Client.className + "` called with incorrect parameters.", e);
        }
    }

    public Store getStore() {
        String method = "getStore";
        try {
            Object obj = Client.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Client.className + "` returned null.");
            }
            return Store.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Client.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Client.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Client.className + "` called with incorrect parameters.", e);
        }
    }
}
