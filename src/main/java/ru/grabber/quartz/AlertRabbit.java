package ru.grabber.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {

    public static void main(String[] args) {
        try {
            Properties config = readProperties();
            try (Connection connection = DriverManager.getConnection(config.getProperty("url"), config)) {
                System.out.println("Connection created");

                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                JobDataMap data = new JobDataMap();
                data.put("connection", connection);

                JobDetail job = newJob(Rabbit.class)
                        .setJobData(data)
                        .build();

                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(getInterval(config))
                        .repeatForever();

                Trigger trigger = newTrigger()
                        .withSchedule(times)
                        .startNow()
                        .build();
                scheduler.scheduleJob(job, trigger);

                System.out.println("Start");
                scheduler.start();
                Thread.sleep(10000);
                scheduler.shutdown();
                System.out.println("Stop");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static Properties readProperties() throws IOException {
        Properties config = new Properties();
        try (InputStream is = ClassLoader.getSystemResourceAsStream("rabbit.properties")) {
            config.load(is);
        }
        return config;
    }

    private static int getInterval(Properties config) {
        return Integer.parseInt(config.getProperty("rabbit.interval"));
    }
}
