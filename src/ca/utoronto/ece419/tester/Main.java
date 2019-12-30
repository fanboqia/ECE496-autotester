package ca.utoronto.ece419.tester;

import ca.utoronto.ece419.tester.util.LogFormatter;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
    private static void init() {
        Logger root = Logger.getLogger("");
        for(Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }

        Handler h = new ConsoleHandler();
        h.setLevel(Level.ALL);
        h.setFormatter(new LogFormatter());
        root.addHandler(h);
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Usage: <jar>");
            return;
        }
        Main.init();

        if(!(new File(args[0]).exists())) {
            System.err.println("File `" + args[0] + "` is not a valid file");
            return;
        }

        Logger.getLogger("Autotest").setLevel(args.length >= 2 && args[1].equals("-debug") ? Level.ALL : Level.INFO);

        new Tester(args[0]).run();
    }
}