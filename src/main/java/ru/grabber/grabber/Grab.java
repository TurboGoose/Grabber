package ru.grabber.grabber;

import org.quartz.SchedulerException;

public interface Grab {
    void startGrabbing() throws SchedulerException;
}
