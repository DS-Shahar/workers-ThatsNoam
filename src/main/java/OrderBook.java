public class OrderBook {
    private Queue<Trade> buy;
    private Queue<Trade> sell;

    public Trade tryDeal(Trade buyTrade, Trade sellTrade) {
        // Check for price equality within the threshold
        if (Math.abs(buyTrade.getPrice() - sellTrade.getPrice()) < 0.001) {
            int dealAmount = Math.min(buyTrade.getAmount(), sellTrade.getAmount());
            // Create a new trade for the executed deal
            Trade newTrade = new Trade(buyTrade.getPrice(), dealAmount);
            // Update the amounts of the buy and sell trades
            buyTrade.setAmount(buyTrade.getAmount() - dealAmount);
            sellTrade.setAmount(sellTrade.getAmount() - dealAmount);
            return newTrade;
        }
        return null; // No deal if prices do not match
    }

    public Queue<Trade> doAllDeals() {
        Queue<Trade> deals = new Queue<>();
        while (!buy.isEmpty() && !sell.isEmpty()) {
            Trade buyTrade = buy.head();
            Trade sellTrade = sell.head();
            Trade deal = tryDeal(buyTrade, sellTrade);
            if (deal != null) {
                deals.insert(deal);
                // Remove trades from queues if their amount reaches zero
                if (buyTrade.getAmount() == 0) {
                    buy.remove();
                }
                if (sellTrade.getAmount() == 0) {
                    sell.remove();
                }
            } else {
                break; // Exit if no deal was made
            }
        }
        return deals;
    }
}
