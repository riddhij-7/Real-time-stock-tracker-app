package org.example;

import yahoofinance.YahooFinance;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class App {
    public static void main(String[] args) {
        // Queue to store stock data with a limit of 10 entries (for demonstration purposes)
        Queue<String> stockDataQueue = new LinkedList<>();
        int queueLimit = 10; // Maximum number of stock data entries to store

        while (true) {
            try {
                // Get DJIA stock info
                Stock stock = YahooFinance.get("^DJI");

                if (stock != null && stock.getQuote() != null) {
                    Double price = stock.getQuote().getPrice().doubleValue();
                    
                    // Check if the price is available and valid
                    if (price != null) {
                        long timestamp = System.currentTimeMillis();
                        String data = "Timestamp: " + timestamp + " Price: " + price;

                        // Add stock data to queue and manage the size
                        if (stockDataQueue.size() >= queueLimit) {
                            stockDataQueue.poll();  // Remove oldest entry
                        }
                        stockDataQueue.add(data);

                        // Print stock data
                        System.out.println(data);
                    } else {
                        System.out.println("Error: Could not fetch valid stock price.");
                    }
                } else {
                    System.out.println("Error: Could not fetch stock data.");
                }

                // Optional: Print the queue size for debugging
                System.out.println("Queue Size: " + stockDataQueue.size());

                // Sleep 5 seconds before getting the next stock data
                Thread.sleep(30000);
                
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
