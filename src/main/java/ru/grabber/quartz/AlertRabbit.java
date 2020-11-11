package ru.grabber.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            System.out.println(getInterval());
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .withSchedule(times)
                    .startNow()
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static int getInterval() throws IOException {
        int interval;
        try (InputStream is = ClassLoader.getSystemResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(is);
            interval = Integer.parseInt(config.getProperty("rabbit.interval"));
        }
        return interval;
    }
}

class Rabbit implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Rabbit is here");
    }
}
