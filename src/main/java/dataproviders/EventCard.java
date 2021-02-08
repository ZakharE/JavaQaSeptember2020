package dataproviders;

public class EventCard {
    private final String eventName;
    private final String dateRange;
    private final String language;
    private final String mode;

    public EventCard(String eventName, String dateRange, String language, String mode) {
        this.eventName = eventName;
        this.dateRange = dateRange;
        this.language = language;
        this.mode = mode;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getLanguage() {
        return language;
    }

    public String getMode() {
        return mode;
    }
}
