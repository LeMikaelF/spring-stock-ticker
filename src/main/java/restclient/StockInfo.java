package restclient;

public class StockInfo {
    private String symbol;
    private Double current;

    public StockInfo(String symbol, Double current) {
        this.symbol = symbol;
        this.current = current;
    }

    public StockInfo(String symbol, StockResponse response) {
        this.symbol = symbol;
        this.current = response.getC();
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }
}
