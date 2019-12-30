package ca.utoronto.ece419.tester.wrapper;

import ca.utoronto.ece419.tester.wrapper.exceptions.EncounteredException;
import ca.utoronto.ece419.tester.wrapper.exceptions.NullReturnException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class KVMessage extends Wrapper {
    public enum StatusType {
        GET, 			/* Get - request */
        GET_ERROR, 		/* requested tuple (i.e. score) not found */
        GET_SUCCESS, 	/* requested tuple (i.e. score) found */
        PUT, 			/* Put - request */
        PUT_SUCCESS, 	/* Put - request successful, tuple inserted */
        PUT_UPDATE, 	/* Put - request successful, i.e. score updated */
        PUT_ERROR, 		/* Put - request not successful */
        DELETE_SUCCESS, /* Delete - request successful */
        DELETE_ERROR, 	/* Delete - request successful */

        SERVER_STOPPED,         /* Server is stopped, no requests are processed */
        SERVER_WRITE_LOCK,      /* Server locked for out, only get possible */
        SERVER_NOT_RESPONSIBLE  /* Request not successful, server not responsible for key */
    }

    private static Class classObject = null;
    private static String className = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        captureMethods.add("getKey");
        captureMethods.add("getValue");
        captureMethods.add("getStatus");
        //m2-added
        captureMethods.add("getResponsibleServer");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(KVMessage.classObject, captureMethods, methods);
    }

    public static void setClass(Class storeClass) {
        KVMessage.classObject = storeClass;
        KVMessage.className = KVMessage.classObject.getName();
        KVMessage.loadClass();
    }

    public static Class getClassObject() {
        return KVMessage.classObject;
    }

    public static KVMessage bind(Object instance) {
        KVMessage res = new KVMessage();
        KVMessage.className = KVMessage.classObject.getName();
        res.instance = instance;
        return res;
    }

    private KVMessage() {
    }

    public String getKey() {
        String method = "getKey";
        try {
            Object obj = KVMessage.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + KVMessage.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` called with incorrect parameters.", e);
        }
    }

    public String getValue() {
        String method = "getValue";
        try {
            Object obj = KVMessage.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + KVMessage.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` called with incorrect parameters.", e);
        }
    }

    public StatusType getStatus() {
        String method = "getStatus";
        try {
            Object obj = KVMessage.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + KVMessage.className + "` returned null.");
            }
            return StatusType.valueOf(((Enum) obj).toString());
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public ECSNode getResponsibleServer() {
        String method = "getResponsibleServer";
        try {
            Object obj = KVMessage.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + KVMessage.className + "` returned null.");
            }
            return ECSNode.bind(obj);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + KVMessage.className + "` called with incorrect parameters.", e);
        }
    }
}
