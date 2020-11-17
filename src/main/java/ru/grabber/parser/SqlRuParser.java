package ru.grabber.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SqlRuParser {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
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
}
