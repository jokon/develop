package citytrail;

import citytrail.model.City;
import citytrail.model.Competitor;
import citytrail.model.GeneralResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by jaok on 2017-02-20.
 */
public class Main {

    static String GENERAL_RESULTS_URL = "http://citytrail.pl/zawody/klasyfikacja_bg/miasto/%s";
    static String SINGLE_EVENT_RESULT = "http://citytrail.pl/zawody/wyniki/miasto/%s/id/%s";

    public static void main (String[] args) throws IOException {
        List<GeneralResult> globalResults = new ArrayList<>();

        for (City city : City.values()) {
            System.out.println(String.format("\n\n ---- %s ---- \n", city.name().substring(0,1) + city.name().substring(1).toLowerCase()));

            String generalResultsUrl = String.format(GENERAL_RESULTS_URL, city.getName());
            String eventResultUrl = String.format(SINGLE_EVENT_RESULT, city.getName(), city.getEventId(4));

            Map<Integer, GeneralResult> generalResults = getGeneralResults(generalResultsUrl);
            Collection<GeneralResult> updatedGeneralResults = updateGeneralResultsBySingleResult(generalResults, eventResultUrl);

            List<GeneralResult> r = new ArrayList<>(updatedGeneralResults);

            Collections.sort(r);
            printResults(r, city.getName());

            globalResults.addAll(r);
        }

        System.out.println(String.format("\n\n ---- %s ---- \n", "GLOBALNE"));
        Collections.sort(globalResults);
        printResults(globalResults, "global");
    }

    public static void printResults(List<GeneralResult> results, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName + ".txt" , "UTF-8");
            for (int i = 0; i < results.size(); i++) {
                writer.println((i + 1) + ". " + results.get(i));
            }
            writer.close();
        } catch (IOException ex) {

        }
    }

    private static Collection<GeneralResult> updateGeneralResultsBySingleResult(Map<Integer, GeneralResult> generalResults, String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements tables = doc.getElementsByClass("main-scores-table");

        if (tables.isEmpty())
            return generalResults.values();

        Element table = tables.get(0);

        Elements rawResults = table.select("tr");

        for (Element rawResult : rawResults) {

            Elements rawRow = rawResult.select("td");

            if (rawRow.size() > 9) {
                try {
                    Integer number = Integer.parseInt(rawRow.get(1).text());
                    Integer place = Integer.parseInt(rawRow.get(0).text());

                    GeneralResult generalResult = generalResults.get(number);
                    if (generalResult != null) {
                        generalResult.addPlace(place);
                    }
                } catch (NumberFormatException ex) {

                }
            }
        }
        return generalResults.values();
    }

    private static Map<Integer, GeneralResult> getGeneralResults(String url) throws IOException {
        Map<Integer, GeneralResult> results = new HashMap<>();

        Document doc = Jsoup.connect(url).get();
        Elements tables = doc.getElementsByClass("main-scores-table");


        for (Element table : tables) {
            Elements rawResults = table.select("tr");

            for (Element rawResult : rawResults) {
                Elements rawCompetitors = rawResult.select("td");

                if (rawCompetitors.size() >= 15) {
                    String firstName = rawCompetitors.get(2).text();
                    String lastName = rawCompetitors.get(3).text();
                    String category = rawCompetitors.get(7).text();

                    Integer number = Integer.parseInt(rawCompetitors.get(1).text());
                    Integer eventCount = Integer.parseInt(rawCompetitors.get(14).text());

                    List<Integer> places = new ArrayList<>();
                    for (int i = 8; i <= 13; i++) {
                        addPlaceToListIfExist(places, rawCompetitors.get(i).text());
                    }

                    Competitor competitor = new Competitor(firstName, lastName, number, category);
                    GeneralResult result = new GeneralResult(competitor, places);

                    results.put(number, result);
                }
            }
        }

        return results;
    }

    private static void addPlaceToListIfExist(List<Integer> places, String text) {
        try {
            String place = text.split(" ")[0];
            places.add(Integer.parseInt(place));
        } catch (NumberFormatException ex) {

        }
    }
}
