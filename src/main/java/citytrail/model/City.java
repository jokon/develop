package citytrail.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaok on 2017-02-21.
 */
public enum City {

    BYDGOSZCZ(467),
    KATOWICE(485),
    LUBLIN(497),
    LODZ(515),
    OLSZTYN(479),
    POZNAN(461),
    SZCZECIN(509),
    TROJMIASTO(473),
    WARSZAWA(503),
    WROCLAW(491);

    private final List<Integer> eventIds = new ArrayList<>();

    City(int firstSingleEventId) {
        int next = 0;
        for (int i = firstSingleEventId; i < firstSingleEventId + GeneralResult.EVENT_COUNT; i ++) {
            eventIds.add(firstSingleEventId + next++);
        }
    }

    public Integer getEventId(Integer eventNumber) {
        return eventIds.get(eventNumber);
    }

    public String getName() {
        return name().toLowerCase();
    }
}
