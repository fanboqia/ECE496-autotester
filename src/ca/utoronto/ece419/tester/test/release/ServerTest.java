package ca.utoronto.ece419.tester.test.release;

import ca.utoronto.ece419.tester.test.TestBase;
import ca.utoronto.ece419.tester.test.TestCase;
import ca.utoronto.ece419.tester.wrapper.Server;

public class ServerTest extends TestBase{
    public ServerTest() {
        super(ServerTest.class, "Client/Server Test");
    }

    @Override
    protected void before(String methodName) {
    }

    @Override
    protected void after(String methodName) {
    }

    @Override
    protected void start() {
        this.hijackIO();
    }

    @Override
    protected void finish() {
        this.releaseIO();
    }

    private static Server makeServer(int port, int cacheSize, Server.CacheStrategy strategy) {
        Server server = new Server(port, cacheSize, strategy.name());
        server.run();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return server;
    }

    @TestCase(name="Storage", details="server.put('test', 'test string');server.inStorage('test')", score=1)
    public boolean testInStorage() {
        Server server = makeServer(0, 5, Server.CacheStrategy.LRU);
        server.clearStorage();

        server.putKV("test", "test string");
        boolean res = server.inStorage("test");

        server.close();

        return res;
    }

    @TestCase(name="Persistence", details="server.put('test', 'test string');restart(server);server.inStorage('test')", score=1)
    public boolean testPersistence() {
        Server server = makeServer(0, 5, Server.CacheStrategy.LRU);
        server.clearStorage();

        server.putKV("test", "test string");

        server.close();
        server = makeServer(0, 5, Server.CacheStrategy.LRU);

        boolean res = server.inStorage("test");

        server.close();

        return res;
    }

    @TestCase(name="Basic Cache", details="server.put('test', 'test string');server.inCache('test')", score=1)
    public boolean testInCache() {
        Server server = makeServer(0, 5, Server.CacheStrategy.FIFO);
        server.clearStorage();

        server.putKV("test", "test string");
        boolean res = server.inCache("test");

        server.close();

        return res;
    }

    //m2-added functions
    @TestCase(name="getCacheSize", details="server.put('test', 'test string');server.inCache('test')", score=1)
    public boolean testGetCacheSize() {
        Server server = makeServer(0, 5, Server.CacheStrategy.FIFO);
        server.clearStorage();

        boolean res = server.getCacheSize() == 5;

        server.close();

        return res;
    }

}
