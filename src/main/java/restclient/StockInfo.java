package restclient;

public class StockInfo {
    private final String symbol;
    private final Double current;

    public StockInfo(String symbol, Double current) {
        this.symbol = symbol;
        this.current = current;
        invariants();
    }

    public StockInfo(String symbol, StockResponse response) {
        this.symbol = symbol;
        this.current = response.getC();
        invariants();
    }


    private void invariants() {
        if (symbol.isEmpty() || current == null || current.isNaN() || current.isInfinite() || current < 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getCurrent() {
        return current;
    }


}
