package com.kata.vending_machine;

import java.io.Console;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.kata.vending_machine.exceptions.UnrecognizedCoinInserted;

public final class VendingMachineInteractor {

	private static PrintStream out;
	private static Scanner in;

	private static List<Product> createProducts() {
		return createProducts(new Product("Cola", 1, 10), new Product("Chips", 0.5, 10),
				new Product("Candy", 0.65, 10));
	}

	private static List<Product> createProducts(Product... products) {
		return Arrays.asList(products);
	}

	private static List<Coin> createCoins(int quarters, int dimes, int nickels) {
		List<Coin> coins = new ArrayList<>();
		for (int i = 0; i < quarters; i++) {
			coins.add(Coin.Quarter);
		}
		for (int i = 0; i < dimes; i++) {
			coins.add(Coin.Dime);
		}
		for (int i = 0; i < nickels; i++) {
			coins.add(Coin.Nickel);
		}

		return coins;
	}

	public static void main(String[] args) {
		out = System.out;
		in = new Scanner(System.in);

		VendingMachine vendingMachine = new VendingMachine(createProducts(), createCoins(10, 10, 10));
		HashMap<String, String> menu = getMenu();

		// show the menu till end chosen
		while (runMenu(menu, vendingMachine))
			;

		// Display termination message
		out.println("Thank you for using the Vending Machine. Goodbye!");
	}

	private static HashMap<String, String> getMenu() {
		HashMap<String, String> menu = new HashMap<>();

		menu.put("1", "Read the display");
		menu.put("2", "Insert a coin");
		menu.put("3", "Select a product");
		menu.put("4", "Return coins");
		menu.put("5", "Take dispenser items");
		menu.put("6", "Take return tray coins");
		menu.put("9", "Leave the Vending Machine area (Exit)");

		return menu;
	}

	public static boolean runMenu(HashMap<String, String> menu, VendingMachine vendingMachine) {
		out.println("------------------------------------------------------------");
		out.println("*\t\t\t VENDING MACHINE \t\t   *");
		out.println("------------------------------------------------------------");
		out.printf("\t%-20s: %s\n", "Display peek", vendingMachine.peekDisplayText());
		out.printf("\t%-20s: $%.2f\n", "Amount peek", vendingMachine.getCurrentAmount());
		out.printf("\t%-20s: %s\n", "Dispenser Tray", aggregate(vendingMachine.getDispenserTray()));
		out.printf("\t%-20s: %s\n", "Coin Return Tray", aggregate(vendingMachine.getReturnTray()));

		out.println("=========================== MENU ===========================");
		out.print(getMenuString(menu));

		out.print("Enter your action code: ");
		return performActionOnVendingMachine(in.nextLine(), vendingMachine);
	}

	private static <T> String aggregate(List<T> things) {
		return things.size() == 0 ? "<empty>"
				: String.join(", ", things.stream().map(s -> s.toString()).collect(Collectors.toList()));
	}

	private static boolean performActionOnVendingMachine(String actionCode, VendingMachine vm) {
		switch (actionCode) {
		case "1": // Read display
			out.printf("Display reads: [%s]\n", vm.readDisplay());
			return true;
		case "2": // Insert a coin
			out.printf("Enter the coin to insert: ");
			String coinName = in.nextLine();
			try {
				vm.insertCoin(coinName);
			} catch (UnrecognizedCoinInserted e) {
				out.printf("Returned: %s\n", e.getMessage());
			}
			return true;
		case "3":
			// print the products
			HashMap<String, Product> products = vm.getMappedProducts();
			for (Entry<String, Product> product : products.entrySet()) {
				out.printf("\t%s: %s [$%.2f]\n", product.getKey(), product.getValue().getName(), product.getValue().getUnitPrice().doubleValue());
			}
			out.printf("Enter product number: ");
			String productNumber = in.nextLine();
			if (products.containsKey(productNumber)) {
				vm.SelectProduct(productNumber);
			} else {
				out.printf("Product number %s is invalid\n", productNumber);
			}
			return true;
		case "4": // return coins
			vm.returnMoney();
			return true;
		case "5": // clear dispenser
			if (vm.getDispenserTray().size() == 0) {
				out.println("Nothing to take in the dispenser tray!");
			} else {
				out.printf("You are taking out <%s> from the dispenser.\n", aggregate(vm.getDispenserTray()));
				vm.clearDispenserTray();
			}
			return true;
		case "6": // clear return tray
			if (vm.getReturnTray().size() ==0) {
				out.println("Nothing to take in the coins return tray!");
			} else {
				out.printf("You are taking out <%s> from the coin return tray.\n", aggregate(vm.getReturnTray()));
				vm.clearReturnTray();
			}
			
			return true;
		case "9":
			out.println("Leaving the vending machine...");
			return false;
		default:
			out.printf(">> %s is not a valid menu choice\n\n", actionCode);
			return true;
		}
	}

	private static String getMenuString(HashMap<String, String> menu) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> menuEntry : menu.entrySet()) {
			sb.append(String.format("[%s] %s\n", menuEntry.getKey(), menuEntry.getValue()));
		}
		return sb.toString();
	}

}
