import java.util.Scanner;

public class ATM {
    private int atmBalance = 15000;
    private final int[] cardNumbers = {12345, 23456, 34567, 45678, 56789, 67890, 78901, 89012, 90123, 1234};
    private final String[] accountNames = {"Deepak", "Ankit", "Harshit", "Umesh", "Lokesh", "Rohit", "Omkar", "Hitesh", "Ishant", "Tushar"};
    private final int[] pins = {123, 234, 345, 456, 567, 678, 789, 890, 901, 12};
    private final int[] accountBalances = {5000, 7000, 9000, 11000, 13000, 15000, 17000, 19000, 20000, 22000};

    private int currentUserIndex = -1;
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        selectBank();
    }

    private void selectBank() {
        System.out.println("Choose Bank:");
        System.out.println("1. SBI Bank");
        System.out.println("2. Paytm Bank");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Welcome to SBI Bank...");
                authenticateCard();
                break;
            case 2:
                System.out.println("Welcome to Paytm Payments Bank...");
                authenticateCard();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                selectBank();
                break;
        }
    }

    private void authenticateCard() {
        System.out.print("Enter Your Card Number: ");
        int cardNumber = sc.nextInt();
        boolean found = false;

        for (int i = 0; i < cardNumbers.length; i++) {
            if (cardNumbers[i] == cardNumber) {
                currentUserIndex = i;
                found = true;
                authenticatePin();
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid Card Number!");
            retryPrompt(this::authenticateCard);
        }
    }

    private void authenticatePin() {
        System.out.print("Enter Your Card PIN: ");
        int enteredPin = sc.nextInt();
        if (pins[currentUserIndex] == enteredPin) {
            showMenu();
        } else {
            System.out.println("Incorrect PIN!");
            retryPrompt(this::authenticatePin);
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nWelcome, " + accountNames[currentUserIndex]);
            System.out.println("Choose an option:");
            System.out.println("1. Cash Withdrawal");
            System.out.println("2. Cash Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");

            int option = sc.nextInt();
            switch (option) {
                case 1:
                    withdrawCash();
                    break;
                case 2:
                    depositCash();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void withdrawCash() {
        System.out.print("Enter amount to withdraw: ");
        int amount = sc.nextInt();

        if (amount > atmBalance) {
            System.out.println("ATM has insufficient funds.");
        } else if (accountBalances[currentUserIndex] >= amount) {
            accountBalances[currentUserIndex] -= amount;
            atmBalance -= amount;
            System.out.println("Withdrawal successful. Please collect your money: ₹" + amount);
        } else {
            System.out.println("Insufficient balance in your account.");
        }

        postTransactionPrompt();
    }

    private void depositCash() {
        System.out.print("Enter amount to deposit: ");
        int amount = sc.nextInt();
        accountBalances[currentUserIndex] += amount;
        System.out.println("Amount ₹" + amount + " deposited successfully.");
        postTransactionPrompt();
    }

    private void checkBalance() {
        System.out.println("Your account balance is: ₹" + accountBalances[currentUserIndex]);
        postTransactionPrompt();
    }

    private void postTransactionPrompt() {
        System.out.println("\nPress 1 to return to Menu or any other key to Exit.");
        int choice = sc.nextInt();
        if (choice != 1) {
            System.out.println("Thank you for using the ATM. Goodbye!");
            System.exit(0);
        }
    }

    private void retryPrompt(Runnable retryAction) {
        System.out.println("Press 1 to try again or any other key to exit.");
        int retry = sc.nextInt();
        if (retry == 1) {
            retryAction.run();
        } else {
            System.out.println("Exiting...");
            System.exit(0);
        }
    }
}
