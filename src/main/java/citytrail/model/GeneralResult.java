package citytrail.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jaok on 2017-02-20.
 */
public class GeneralResult implements Comparable<GeneralResult> {
    private Competitor competitor;
    private List<Integer> places;

    public GeneralResult(Competitor competitor, List<Integer> places) {
        this.competitor = competitor;
        this.places = places;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public Integer getEventCount() {
        return places.size();
    }

    public Integer getPoinstCount() {
        Collections.sort(places, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        int sum = places.subList(0, places.size() > 3 ? 4 : places.size()).stream().mapToInt(Integer::intValue).sum();

        return sum;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return competitor + " [" + getEventCount() + " | " + getPoinstCount() + "]";
    }

    @Override
    public int compareTo(GeneralResult o) {
        Integer thisEventCount = getEventCount();
        Integer otherEventCount = o.getEventCount();

        Integer thisPointCount = getPoinstCount();
        Integer otherPointCount = o.getPoinstCount();

        if (thisEventCount < 4 || otherEventCount < 4) {
            if (thisEventCount == otherEventCount) {
                return thisPointCount.compareTo(otherPointCount);
            } else {
                return otherEventCount.compareTo(thisEventCount);
            }
        } else {
            return thisPointCount.compareTo(otherPointCount);
        }

    }

    public void addPlace(Integer place) {
        places.add(place);
    }
}
