package ru.grabber.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class SqlRuParser {
    public static void main(String[] args) {
        try {
            final int NUMBER_OF_PAGES = 5;
            for (int i = 1; i <= NUMBER_OF_PAGES; i++) {
                parsePage(i);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void parsePage(int pageNumber) throws IOException {
        printPageSeparator(pageNumber);
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + pageNumber).get();
        Element table = doc.selectFirst(".forumTable").child(0);
        for (Element row : table.children().subList(4, table.childrenSize())) {
            Element vacancy_name = row.child(1);
            System.out.println(vacancy_name.child(0).attr("href"));
            System.out.println(vacancy_name.text());

            Element publishing_date = row.child(5);
            System.out.println(publishing_date.text());

            System.out.println();
        }
    }

    private static void printPageSeparator(int pageNumber) {
        System.out.printf(
                "------------------------------------------ Page %d ------------------------------------------%n%n",
                pageNumber);
    }
}
