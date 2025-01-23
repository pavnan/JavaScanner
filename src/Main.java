import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int balance = 0; // Inicjalizacja salda
        Vector<String> myStocks = new Vector<>(); // Lista moich akcji

        // Lista dostępnych akcji z cenami (cena za 1%)
        Map<Integer, String> stockList = new LinkedHashMap<>();
        Map<Integer, Integer> stockPrices = new LinkedHashMap<>();

        stockList.put(1, "Apple'");
        stockList.put(2, "Tesla");
        stockList.put(3, "Google");
        stockList.put(4, "Amazon");
        stockList.put(5, "Microsoft");

        stockPrices.put(1, 150);
        stockPrices.put(2, 200);
        stockPrices.put(3, 180);
        stockPrices.put(4, 170);
        stockPrices.put(5, 160);

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Doładować saldo");
            System.out.println("2. Zobaczyć dostępne akcje");
            System.out.println("3. Przeskoczyć dzień");
            System.out.println("4. Moje akcje");
            System.out.println("5. Wyjście");
            System.out.print("Wybierz opcję (1-5): ");

            int choice = scanner.nextInt(); // Odbieranie wyboru od użytkownika

            switch (choice) {
                case 1:
                    boolean rechargeRunning = true;
                    while (rechargeRunning) {
                        System.out.println("\nNa twoim koncie: " + balance + "$");
                        System.out.println("1. Doładować o 500");
                        System.out.println("2. Doładować o 100");
                        System.out.println("3. Wprowadzić własną kwotę");
                        System.out.println("4. Wróć");
                        System.out.print("Wybierz opcję (1-4): ");

                        int rechargeChoice = scanner.nextInt();

                        switch (rechargeChoice) {
                            case 1:
                                balance += 500;
                                System.out.println("Twoje saldo zostało doładowane o 500. Aktualne saldo: " + balance);
                                break;
                            case 2:
                                balance += 100;
                                System.out.println("Twoje saldo zostało doładowane o 100. Aktualne saldo: " + balance);
                                break;
                            case 3:
                                System.out.print("Wprowadź kwotę do doładowania: ");
                                int customAmount = scanner.nextInt();
                                if (customAmount > 0) {
                                    balance += customAmount;
                                    System.out.println("Twoje saldo zostało doładowane o " + customAmount + ". Aktualne saldo: " + balance);
                                } else {
                                    System.out.println("Kwota musi być większa niż zero.");
                                }
                                break;
                            case 4:
                                System.out.println("Powrót do menu głównego.");
                                rechargeRunning = false;
                                break;
                            default:
                                System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
                        }
                    }
                    break;
                case 2:
                    boolean subMenuRunning = true;
                    while (subMenuRunning) {
                        System.out.println("\nDostępne akcje:");
                        for (Map.Entry<Integer, String> entry : stockList.entrySet()) {
                            System.out.println(entry.getKey() + ". " + entry.getValue() + " - Cena za 1%: " + stockPrices.get(entry.getKey()));
                        }
                        System.out.println("\nDziałania:");
                        System.out.println("1. Kup akcje");
                        System.out.println("2. Wróć");
                        System.out.print("Wybierz opcję (1-2): ");

                        int subChoice = scanner.nextInt();

                        switch (subChoice) {
                            case 1:
                                System.out.print("Wprowadź numer akcji, którą chcesz kupić: ");
                                int stockNumber = scanner.nextInt();
                                if (stockList.containsKey(stockNumber)) {
                                    System.out.println("Wybrałeś: " + stockList.get(stockNumber) + ". Cena za 1%: " + stockPrices.get(stockNumber));
                                    System.out.print("Ile procent chcesz kupić? ");
                                    int percent = scanner.nextInt();
                                    int cost = stockPrices.get(stockNumber) * percent;
                                    if (percent > 0 && hasEnoughFunds(balance, cost)) {
                                        balance -= cost;
                                        myStocks.add(percent + "% akcji " + stockList.get(stockNumber));
                                        System.out.println("Pomyślnie kupiłeś " + percent + "% akcji " + stockList.get(stockNumber) + ". Wydano: " + cost + ". Aktualne saldo: " + balance);
                                    } else {
                                        System.out.println("Niewystarczająca ilość środków lub niepoprawny procent.");
                                    }
                                } else {
                                    System.out.println("Niepoprawny numer akcji.");
                                }
                                break;
                            case 2:
                                System.out.println("Powrót do menu głównego.");
                                subMenuRunning = false;
                                break;
                            default:
                                System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Przeskoczenie dnia: aktualizacja cen akcji.");
                    updateStockPrices(stockPrices);
                    System.out.println("Ceny zostały zaktualizowane!");
                    break;
                case 4:
                    System.out.println("Moje akcje:");
                    if (myStocks.isEmpty()) {
                        System.out.println("Nie masz żadnych zakupionych akcji.");
                    } else {
                        for (String stock : myStocks) {
                            System.out.println("- " + stock);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Wyjście z programu. Do zobaczenia!");
                    running = false;
                    break;
                default:
                    System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        }

        scanner.close();
    }

    public static boolean hasEnoughFunds(int balance, int cost) {
        return balance >= cost;
    }

    public static void updateStockPrices(Map<Integer, Integer> stockPrices) {
        Random random = new Random();
        for (Map.Entry<Integer, Integer> entry : stockPrices.entrySet()) {
            int change = random.nextInt(20) + 1; // Losowa liczba od 1 do 20
            if (random.nextBoolean()) {
                entry.setValue(entry.getValue() + change); // Zwiększamy cenę
            } else {
                entry.setValue(Math.max(1, entry.getValue() - change)); // Zmniejszamy cenę, ale nie poniżej 1
            }
        }
    }
}