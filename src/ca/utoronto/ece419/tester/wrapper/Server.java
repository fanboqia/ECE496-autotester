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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Server extends Wrapper{
    public enum CacheStrategy {
        None,
        FIFO,
        LRU,
        LFU
    };

    class RunnableServer implements Runnable {
        private Server t = null;

        public RunnableServer(Server t) {
            this.t = t;
        }

        public void run() {
            this.t.run_blocking();
        }
    }

    private static Class classObject = null;
    private static String className = null;
    private static Constructor ctor = null;
    private static Map<String, Method> methods = null;

    private Object instance = null;
    private ExecutorService execServ = null;
    private Runnable runnable = null;
    private Future runnableFuture = null;

    private static void loadClass() {
        Set<String> captureMethods = new HashSet<>();
        captureMethods.add("getPort");
        captureMethods.add("getHostname");
        captureMethods.add("getCacheStrategy");
        captureMethods.add("getCacheSize");
        captureMethods.add("inStorage");
        captureMethods.add("inCache");
        captureMethods.add("getKV");
        captureMethods.add("putKV");
        captureMethods.add("clearCache");
        captureMethods.add("clearStorage");
        captureMethods.add("run");
        captureMethods.add("kill");
        captureMethods.add("close");
        //m2-added
        captureMethods.add("start");
        captureMethods.add("stop");
        captureMethods.add("lockWrite");
        captureMethods.add("unlockWrite");
        captureMethods.add("moveData");

        methods = new HashMap<>();
        Wrapper.loadClassMethods(Server.classObject, captureMethods, methods);

        try {
            Server.ctor = classObject.getConstructor(int.class, int.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class `" + classObject.getName() + "` is missing a constructor with (int, int, String) parameters.");
        }
    }

    public static void setClass(Class clientClass) {
        Server.classObject = clientClass;
        Server.className = Server.classObject.getName();
        Server.loadClass();
    }

    public static Class getClassObject() {
        return Server.classObject;
    }

    public Server(int port, int cacheSize, String strategy) {
        this.execServ = Executors.newFixedThreadPool(1);

        try {
            this.instance = Server.ctor.newInstance(port, cacheSize, strategy);
        } catch (InstantiationException e) {
            throw new RuntimeException("Encountered InstantiationException! This means an abstract class's constructor was called! How did you get here?");
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Contructor of `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Contructor of `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Contructor of `" + Server.className + "` called with incorrect parameters.", e);
        } catch (ExceptionInInitializerError e) {
            throw new EncounteredException("Call to contructor of `" + Server.className + "` failed.", e);
        }
    }

    public int getPort() {
        String method = "getPort";

        try {
            Object obj = Server.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (Integer) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public String getHostname() {
        String method = "getHostname";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public CacheStrategy getCacheStrategy() {
        String method = "getCacheStrategy";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return CacheStrategy.valueOf(((Enum) obj).toString());
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public int getCacheSize() {
        String method = "getCacheSize";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (Integer) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean inStorage(String key) {
        String method = "inStorage";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance, key);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public boolean inCache(String key) {
        String method = "inCache";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance, key);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public String getKV(String key) {
        String method = "getKV";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance, key);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (String) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public void putKV(String key, String value) {
        String method = "putKV";
        try {
            Server.methods.get(method).invoke(this.instance, key, value);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new ForwardedException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public void clearCache() {
        String method = "clearCache";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public void clearStorage() {
        String method = "clearStorage";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public void run() {
        this.runnable = new RunnableServer(this);
        this.runnableFuture = this.execServ.submit(this.runnable);
    }

    protected void run_blocking() {
        String method = "run";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    public void kill() {
        String method = "kill";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
        this.execServ.shutdownNow();
        try {
            this.runnableFuture.get();
        } catch (Exception e) {
            throw new EncounteredException("Encountered exception in method `" + method + "` from `" + Server.className + "`");
        }
    }

    public void close() {
        String method = "close";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
        this.execServ.shutdownNow();
        try {
            this.runnableFuture.get();
        } catch (Exception e) {
            throw new EncounteredException("Encountered exception in method `" + method + "` from `" + Server.className + "`");
        }
    }


    //m2-added
    public void start() {
        String method = "start";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public void stop() {
        String method = "stop";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public void lockWrite() {
        String method = "lockWrite";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public void unlockWrite() {
        String method = "unlockWrite";
        try {
            Server.methods.get(method).invoke(this.instance);
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }

    //m2-added
    public boolean moveData(String[] hashRange, String targetName) {
        String method = "moveData";
        try {
            Object obj = Server.methods.get(method).invoke(this.instance,hashRange,targetName);
            if(obj == null) {
                throw new NullReturnException("Method `" + method + "` from `" + Server.className + "` returned null.");
            }
            return (Boolean) obj;
        } catch (IllegalAccessException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` must be public.", e);
        } catch (InvocationTargetException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` raised an exception. " + e.getCause(), e);
        } catch (IllegalArgumentException e) {
            throw new EncounteredException("Method `" + method + "` from `" + Server.className + "` called with incorrect parameters.", e);
        }
    }
}
