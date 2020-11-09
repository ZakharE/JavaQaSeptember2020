public enum Instruments {
    MAKITA("MAKITA"),
    ZUBR("ЗУБР");

    private final String instrument;

    Instruments(String instrument) {
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument;
    }
}
