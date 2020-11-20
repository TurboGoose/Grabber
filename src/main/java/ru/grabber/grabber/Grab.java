package ru.grabber.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.grabber.parser.Parser;
import ru.grabber.storage.Store;

public interface Grab {
    void init(Parser parser, Store store, Scheduler scheduler) throws SchedulerException;
}
