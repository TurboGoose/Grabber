package ru.grabber.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class SqlRuParser {
    public static void main(String[] args) {
        try {
            final int NUMBER_OF_PAGES = 1;
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
            String href = row.child(1).child(0).attr("href");
            printDescription(href);
        }
    }

    private static void printPageSeparator(int pageNumber) {
        System.out.printf(
                "------------------ Page %d ------------------%n",
                pageNumber
        );
    }

    private static void printDescription(String href) throws IOException {
        Document doc = Jsoup.connect(href).get();
        System.out.printf("%s%n%s%n%s%n%n",
                extractName(doc),
                extractDescription(doc),
                extractCreationDate(doc)
        );
    }

    private static String extractName(Document doc) {
        String unformattedName = doc.selectFirst(".messageHeader").text();
        return unformattedName.substring(0, unformattedName.length() - 6);
    }

    private static String extractDescription(Document doc) {
        return doc.select(".msgBody").get(1).text();
    }

    private static String extractCreationDate(Document doc) {
        String unformattedDate = doc.selectFirst(".msgFooter").ownText();
        return unformattedDate.substring(0, unformattedDate.length() - 5);
    }
}
