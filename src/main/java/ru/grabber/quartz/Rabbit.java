package ru.grabber.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Rabbit implements Job {

    public Rabbit() {
        System.out.printf("Rabbit with hashcode %d was instantiated\n", hashCode());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Connection connection = (Connection) jobExecutionContext.getJobDetail().getJobDataMap().get("connection");
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO rabbit (name) VALUES ('new_rabbit')"
        )) {
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
