package constants;

public enum EventTime {
    UPCOMING(0),
    PAST(1);
    private final int id;

    EventTime(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
