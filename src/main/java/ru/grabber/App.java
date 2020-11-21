package ru.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import ru.grabber.grabber.Grabber;
import ru.grabber.parser.Parser;
import ru.grabber.parser.SqlRuParser;
import ru.grabber.storage.PsqlStore;
import ru.grabber.storage.Store;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    private Properties config;

    public void run() {
        try {
            loadConfig();
            Grabber grab = new Grabber(
                    getParser(),
                    getStore(),
                    getScheduler(),
                    config
            );
            grab.startGrabbing();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void loadConfig() throws IOException {
        if (config == null) {
            config = new Properties();
            try (InputStream in = ClassLoader.getSystemResourceAsStream("app.properties")) {
                config.load(in);
            }
        }
    }

    public Parser getParser() {
        return new SqlRuParser();
    }

    public Store getStore(){
        return new PsqlStore(config);
    }

    public Scheduler getScheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }
}
