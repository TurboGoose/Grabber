package ru.grabber.grabber;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import ru.grabber.parser.Parser;
import ru.grabber.storage.Post;
import ru.grabber.storage.Store;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GrabJob implements Job {
    private Store store;
    private Parser parser;
    private int port;

    @Override
    public void execute(JobExecutionContext context) {
        readJobDetails(context);
        execute();
    }

    private void readJobDetails(JobExecutionContext context) {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        store = (Store) map.get("store");
        parser = (Parser) map.get("parser");
        port = (Integer) map.get("port");
    }

    private void execute() {
        System.out.println("Retrieving data...");
        saveFirstPage();
        System.out.println("Displaying data...");
        displayAllPosts();
    }

    private void saveFirstPage() {
        parser.list("https://www.sql.ru/forum/job-offers").forEach(store::save);
    }

    private void displayAllPosts() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(port)) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
                        out.println("HTTP/1.1 200 OK" + System.lineSeparator());
                        for (Post post : store.getAll()) {
                            out.println(post);
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
