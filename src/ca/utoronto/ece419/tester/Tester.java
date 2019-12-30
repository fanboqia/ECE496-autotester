package ca.utoronto.ece419.tester;

import ca.utoronto.ece419.tester.test.TestBase;
import ca.utoronto.ece419.tester.wrapper.*;
import org.clapper.util.classutil.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Tester {
    private static Logger logger = Logger.getLogger("Autotest");
    private static List<Class> tests = null;
    private static boolean testsLoaded = false;
    private static boolean isRelease = true;

    private File jar = null;

    public Tester(String jarPath) {
        this.jar = new File(jarPath);

        if(!Tester.testsLoaded) {
            Tester.loadTestCases();
        }

        this.init();
    }

    private static void loadTestCases() {
        logger.fine("Starting test loading!");

        Tester.tests = new ArrayList<>();

        ClassFinder finder = new ClassFinder();
        finder.addClassPath();
        ClassFilter filter = new AndClassFilter(
                // Must implement Testcase interface
                new SubclassClassFilter(TestBase.class),
                // Must not be abstract
                new NotClassFilter(new AbstractClassFilter())
        );

        List<ClassInfo> testsFound = new ArrayList<>();
        finder.findClasses(testsFound, filter);

        int failedLoads = 0;

        for(ClassInfo test : testsFound) {
            try {
                Tester.tests.add(Class.forName(test.getClassName()));
                Tester.isRelease &= !test.getClassName().contains("ca.utoronto.ece419.tester.test.secret");
            } catch (ClassNotFoundException e) {
                failedLoads++;
            }
        }

        if(failedLoads > 0) {
            logger.severe("Failed to load anytests!");
            return;
        }

        logger.info("Loaded " + testsFound.size() + " test(s) from successfully!");

        Tester.testsLoaded = true;
    }

    private ClassInfo findSingleInterfaceClass(ClassLoader loader, ClassFinder finder, String intname) {
        try {
            logger.info("Loading `" + intname + "` interface from jar");
            Class intface = loader.loadClass(intname);
            List<ClassInfo> classes = new ArrayList<>();
            finder.findClasses(classes, new AndClassFilter(
                    new SubclassClassFilter(intface), new NotClassFilter(new AbstractClassFilter())));
            if(classes.size() == 0 || classes.size() > 1) {
                throw new RuntimeException("Exactly one class should inherit `" + intname + "`");
            }
            logger.info("Found one class implementing `" + intname + "`, called `" + classes.get(0).getClassName() + "`, in the jar");
            return classes.get(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing interface `" + intname + "`");
        }
    }

    private void init() {
        ClassLoaderBuilder builder = new ClassLoaderBuilder();
        builder.add(this.jar);
        ClassLoader loader = builder.createClassLoader();

        ClassFinder finder = new ClassFinder();
        finder.add(this.jar);

        logger.fine("Attempting to locate all classes from jar");
        ClassInfo clientInfo = this.findSingleInterfaceClass(loader, finder, "app_kvClient.IKVClient");
        ClassInfo serverInfo = this.findSingleInterfaceClass(loader, finder, "app_kvServer.IKVServer");
        ClassInfo storeInfo = this.findSingleInterfaceClass(loader, finder, "client.KVCommInterface");
        ClassInfo msgInfo = this.findSingleInterfaceClass(loader, finder, "common.KVMessage");
        //add ecsNodeInfo
        ClassInfo ecsNodeInfo = this.findSingleInterfaceClass(loader, finder, "ecs.IECSNode");
        //add ecsClientInfo
        ClassInfo ecsClientInfo = this.findSingleInterfaceClass(loader, finder, "app_kvECS.IECSClient");

        logger.fine("Loading and binding classes to wrappers");
        try {
            Client.setClass(loader.loadClass(clientInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + clientInfo.getClassName() + "`");
        }

        try {
            Server.setClass(loader.loadClass(serverInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + serverInfo.getClassName() + "`");
        }

        try {
            Store.setClass(loader.loadClass(storeInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + storeInfo.getClassName() + "`");
        }

        try {
            KVMessage.setClass(loader.loadClass(msgInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + msgInfo.getClassName() + "`");
        }

        //add ecsNodeInfo
        try {
            ECSNode.setClass(loader.loadClass(ecsNodeInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + ecsNodeInfo.getClassName() + "`");
        }

        //add ecsClientInfo
        try {
            ECS.setClass(loader.loadClass(ecsClientInfo.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load `" + ecsClientInfo.getClassName() + "`");
        }
    }

    public void checkLoadedClasses() {
        logger.info("Calling Server.ctor");
        Server server = new Server(0, 5, "LRU");

        logger.info("Calling Server.run");
        server.run();

        logger.info("Server is running");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Calling Client.ctor");
        Client client = new Client();

        logger.info("Calling Client.newConnection");
        client.newConnection("localhost", server.getPort());

        logger.info("Calling Client.getStore");
        client.getStore();

        logger.info("Cleaning up");

        client.getStore().disconnect();
        server.close();
    }

    public void run() {
        TestBase test = null;
        int score = 0;
        int total = 0;

        logger.info("Checking loaded classes");

        this.checkLoadedClasses();

        logger.info("Starting test(s)");

        int i = 0;
        for(Class testClass : Tester.tests) {
            try {
                test = (TestBase) testClass.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            logger.info("Running test #" + (++i));

            test.run();
            System.out.print(test.getReport());
            System.out.println(test.getSuccessCount() + " / " + test.getTotalCount());

            score += test.getScore();
            total += test.getTotalCount();
        }

        logger.fine("Finished test(s)");

        if(!Tester.isRelease) {
            System.out.println("Score: " + score + " / " + total);
        }
    }
}
