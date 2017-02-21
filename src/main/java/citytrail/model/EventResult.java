package citytrail.model;

/**
 * Created by jaok on 2017-02-20.
 */
public class EventResult {
    private Integer place;
    private Competitor competitor;

    public EventResult(Competitor competitor, Integer place) {
        this.place = place;
        this.competitor = competitor;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }
}
