import java.io.*;
import java.util.Scanner;
public class Stocktradingplatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TradingSystem tradingSystem = new TradingSystem();
        User user = new User("U001", "Shray", 100000);
        while (true) {
            System.out.println("\n=========== STOCK TRADING PLATFORM ===========");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    tradingSystem.viewMarketData();
                    break;
                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = sc.nextInt();
                    tradingSystem.buyStock(user, buySymbol, buyQuantity);
                    break;
                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = sc.nextInt();
                    tradingSystem.sellStock(user, sellSymbol, sellQuantity);
                    break;
                case 4:
                    user.viewPortfolio(tradingSystem);
                    break;
                case 5:
                    user.viewTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thanks for using Stock Trading Platform!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
class Stock {
    String symbol;
    String companyName;
    double currentPrice;
    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
    }
}
class TradingSystem {
    Stock[] stocks = new Stock[5];
    public TradingSystem() {
        stocks[0] = new Stock("INFY", "Infosys", 1500);
        stocks[1] = new Stock("TCSX", "Tata Consultancy Services", 3500);
        stocks[2] = new Stock("RELI", "Reliance Industries", 2500);
        stocks[3] = new Stock("HDFC", "HDFC Bank", 1700);
        stocks[4] = new Stock("ITCX", "ITC Limited", 450);
    }
    public void viewMarketData() {
        System.out.println("\n========== MARKET DATA ==========");
        System.out.printf("%-10s %-30s %-15s%n", "Symbol", "Company", "Current Price");
        for (int i = 0; i < stocks.length; i++) {
            System.out.printf("%-10s %-30s ₹%.2f%n", stocks[i].symbol, stocks[i].companyName, stocks[i].currentPrice);
        }
    }
    public Stock findStock(String symbol) {
        for (int i = 0; i < stocks.length; i++) {
            if (stocks[i].symbol.equalsIgnoreCase(symbol)) {
                return stocks[i];
            }
        }
        return null;
    }
    public void buyStock(User user, String symbol, int quantity) {
        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }
        double totalCost = stock.currentPrice * quantity;
        if (user.balance < totalCost) {
            System.out.println("Insufficient balance.");
            return;
        }
        user.balance = user.balance - totalCost;
        user.addToPortfolio(stock.symbol, quantity, stock.currentPrice);
        String transaction = "BOUGHT | " + stock.symbol + " | Quantity: " + quantity + " | Price: ₹" + stock.currentPrice + " | Total: ₹" + totalCost;
        user.addTransaction(transaction);
        System.out.println("Stock purchased successfully.");
        System.out.println("Total amount: ₹" + totalCost);
        System.out.println("Remaining balance: ₹" + user.balance);
    }
    public void sellStock(User user, String symbol, int quantity) {
        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }
        boolean sold = user.sellFromPortfolio(stock.symbol, quantity);
        if (!sold) {
            System.out.println("You do not have enough shares to sell.");
            return;
        }
        double totalAmount = stock.currentPrice * quantity;
        user.balance = user.balance + totalAmount;
        String transaction = "SOLD | " + stock.symbol + " | Quantity: " + quantity + " | Price: ₹" + stock.currentPrice + " | Total: ₹" + totalAmount;
        user.addTransaction(transaction);
        System.out.println("Stock sold successfully.");
        System.out.println("Total received: ₹" + totalAmount);
        System.out.println("Current balance: ₹" + user.balance);
    }
}
class User {
    String userId;
    String name;
    double balance;
    Portfolio[] portfolio = new Portfolio[5];
    int portfolioCount = 0;
    String[] transactions = new String[50];
    int transactionCount = 0;
    public User(String userId, String name, double balance) {
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        loadPortfolio();
        loadTransactions();
    }
    public void addToPortfolio(String stockSymbol, int quantity, double buyPrice) {
        for (int i = 0; i < portfolioCount; i++) {
            if (portfolio[i].stockSymbol.equalsIgnoreCase(stockSymbol)) {
                portfolio[i].quantity = portfolio[i].quantity + quantity;
                savePortfolio();
                return;
            }
        }
        if (portfolioCount < portfolio.length) {
            portfolio[portfolioCount] = new Portfolio(stockSymbol, quantity, buyPrice);
            portfolioCount++;
            savePortfolio();
        }
    }
    public boolean sellFromPortfolio(String stockSymbol, int quantity) {
        for (int i = 0; i < portfolioCount; i++) {
            if (portfolio[i].stockSymbol.equalsIgnoreCase(stockSymbol)) {
                if (portfolio[i].quantity < quantity) {
                    return false;
                }
                portfolio[i].quantity = portfolio[i].quantity - quantity;
                if (portfolio[i].quantity == 0) {
                    for (int j = i; j < portfolioCount - 1; j++) {
                        portfolio[j] = portfolio[j + 1];
                    }
                    portfolioCount--;
                }
                savePortfolio();
                return true;
            }
        }
        return false;
    }
    public void viewPortfolio(TradingSystem tradingSystem) {
        System.out.println("\n========== MY PORTFOLIO ==========");
        if (portfolioCount == 0) {
            System.out.println("Portfolio is empty.");
            System.out.println("Current Balance: ₹" + balance);
            return;
        }
        System.out.printf("%-10s %-10s %-15s %-15s%n", "Symbol", "Quantity", "Buy Price", "Current Price");
        double totalInvestment = 0;
        double currentValue = 0;
        for (int i = 0; i < portfolioCount; i++) {
            Stock stock = tradingSystem.findStock(portfolio[i].stockSymbol);
            double investment = portfolio[i].quantity * portfolio[i].buyPrice;
            double value = portfolio[i].quantity * stock.currentPrice;
            totalInvestment = totalInvestment + investment;
            currentValue = currentValue + value;
            System.out.printf("%-10s %-10d ₹%-14.2f ₹%.2f%n", portfolio[i].stockSymbol, portfolio[i].quantity, portfolio[i].buyPrice, stock.currentPrice);
        }
        double profitLoss = currentValue - totalInvestment;
        System.out.println("\nTotal Investment: ₹" + totalInvestment);
        System.out.println("Current Portfolio Value: ₹" + currentValue);
        System.out.println("Profit/Loss: ₹" + profitLoss);
        System.out.println("Available Balance: ₹" + balance);
    }
    public void addTransaction(String transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            saveTransactions();
        }
    }
    public void viewTransactionHistory() {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        if (transactionCount == 0) {
            System.out.println("No transactions found.");
            return;
        }
        for (int i = 0; i < transactionCount; i++) {
            System.out.println((i + 1) + ". " + transactions[i]);
        }
    }
    public void savePortfolio() {
        try {
            FileWriter writer = new FileWriter("portfolio.txt");
            for (int i = 0; i < portfolioCount; i++) {
                writer.write(portfolio[i].stockSymbol + "," + portfolio[i].quantity + "," + portfolio[i].buyPrice + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving portfolio.");
        }
    }
    public void loadPortfolio() {
        try {
            File file = new File("portfolio.txt");
            if (!file.exists()) {
                return;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");
                portfolio[portfolioCount] = new Portfolio(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]));
                portfolioCount++;
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading portfolio.");
        }
    }
    public void saveTransactions() {
        try {
            FileWriter writer = new FileWriter("transactions.txt");
            for (int i = 0; i < transactionCount; i++) {
                writer.write(transactions[i] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving transactions.");
        }
    }
    public void loadTransactions() {
        try {
            File file = new File("transactions.txt");
            if (!file.exists()) {
                return;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                if (transactionCount < transactions.length) {
                    transactions[transactionCount] = fileScanner.nextLine();
                    transactionCount++;
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading transactions.");
        }
    }
}
class Portfolio {
    String stockSymbol;
    int quantity;
    double buyPrice;
    public Portfolio(String stockSymbol, int quantity, double buyPrice) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }
}