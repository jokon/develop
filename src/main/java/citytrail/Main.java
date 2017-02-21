package citytrail;

import citytrail.model.City;
import citytrail.model.Competitor;
import citytrail.model.GeneralResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

/**
 * Created by jaok on 2017-02-20.
 */
public class Main {

    static String GENERAL_RESULTS_URL = "http://citytrail.pl/zawody/klasyfikacja_bg/miasto/%s";
    static String SINGLE_EVENT_RESULT_URL = "http://citytrail.pl/zawody/wyniki/miasto/%s/id/%s";

    public static void main (String[] args) throws IOException {
        List<GeneralResult> globalResults = new ArrayList<>();


        System.out.println("-----------|");

        for (City city : City.values()) {

            String generalResultsUrl = String.format(GENERAL_RESULTS_URL, city.getName());
            String eventResultUrl = String.format(SINGLE_EVENT_RESULT_URL, city.getName(), city.getEventId(4));

            Map<Integer, GeneralResult> generalResults = getGeneralResults(generalResultsUrl);
            Collection<GeneralResult> updatedGeneralResults = updateGeneralResultsBySingleResult(generalResults, eventResultUrl);

            List<GeneralResult> r = new ArrayList<>(updatedGeneralResults);

            Collections.sort(r);
            printResults(r, city.getName());

            globalResults.addAll(r);

            System.out.print("-");
        }

        Collections.sort(globalResults);
        printResults(globalResults, "global");
        System.out.print("-|");
    }

    public static void printResults(List<GeneralResult> results, String fileName) {
        try {
            PrintWriter writer = new PrintWriter("results/" + fileName + ".txt" , "UTF-8");
            for (int i = 0; i < results.size(); i++) {
                writer.println((i + 1) + ". " + results.get(i));
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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
                    Integer number = getIntValueFrom(rawRow, 1);
                    Integer place = getIntValueFrom(rawRow, 0);

                    GeneralResult generalResult = generalResults.get(number);
                    if (generalResult != null) {
                        generalResult.addPlace(place);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return generalResults.values();
    }

    private static Integer getIntValueFrom(Elements rawRow, int i) {

            String[] v = rawRow.get(i).text().split(" ");
            return Integer.parseInt(v[0]);

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
            String[] placeField = text.split(" ");
            if (text.length() > 0 && placeField.length > 0) {
                String place = placeField[0];
                places.add(Integer.parseInt(place));
            }
        } catch (NumberFormatException ex) {

            ex.printStackTrace();
        }
    }
}
