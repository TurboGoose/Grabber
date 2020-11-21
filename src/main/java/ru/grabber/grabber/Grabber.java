package ru.grabber.grabber;

import org.quartz.*;
import ru.grabber.parser.Parser;
import ru.grabber.storage.Store;

import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {
    private final Parser parser;
    private final Store store;
    private final Scheduler scheduler;
    private final Properties config;

    public Grabber(Parser parser, Store store, Scheduler scheduler, Properties config) {
        this.parser = parser;
        this.store = store;
        this.scheduler = scheduler;
        this.config = config;
    }

    @Override
    public void startGrabbing() throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parser", parser);
        data.put("port", Integer.parseInt(config.getProperty("port")));
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInHours(Integer.parseInt(config.getProperty("time")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
