package ru.grabber.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.grabber.storage.Post;

import java.util.ArrayList;
import java.util.List;

public class SqlRuParser implements Parser {
    private Document forumPage;
    private Document vacancyPage;

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        try {
            forumPage = Jsoup.connect(link).get();
            extractVacancyLinks().forEach(l -> result.add(detail(l)));
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    private List<String> extractVacancyLinks() {
        List<String> vacancyLinks = new ArrayList<>();
        Element forumTable = forumPage.selectFirst(".forumTable").child(0);
        for (Element row : forumTable.children().subList(4, forumTable.childrenSize())) {
            vacancyLinks.add(row.child(1).child(0).attr("href"));
        }
        return vacancyLinks;
    }

    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            vacancyPage = Jsoup.connect(link).get();
            post.setId(extractMessageId());
            post.setName(extractName());
            post.setText(extractDescription());
            post.setLink(link);
            post.setCreated(DateTimeParser.parseDateTime(extractCreationDate()));
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return post;
    }

    private int extractMessageId() {
        return Integer.parseInt(vacancyPage.selectFirst(".messageHeader").attr("id").substring(2));
    }

    private String extractName() {
        return vacancyPage.selectFirst(".messageHeader").ownText();
    }

    private String extractDescription() {
        return vacancyPage.select(".msgBody").get(1).text();
    }

    private String extractCreationDate() {
        String unformattedDate = vacancyPage.selectFirst(".msgFooter").ownText();
        return unformattedDate.substring(0, unformattedDate.length() - 5);
    }
}
