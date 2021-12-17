import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MainSystem {
	String URL; // Would be URL of API
	
	private Database database;
	
	public MainSystem() {
		HashMap<String, Cart> carts = new HashMap<String, Cart>();
		HashMap<String, Item> items = new HashMap<String, Item>();
		HashMap<String, Coupon> discounts = new HashMap<String, Coupon>();
		try {
			Scanner scItems = new Scanner(new File("Database/items.txt"));
			
			String imageURL = "https://image.shutterstock.com/image-vector/ui-image-placeholder-wireframes-apps-260nw-1037719204.jpg";
			
			while (scItems.hasNext()) {
				String line = scItems.nextLine();
				String[] arr = line.split(" count:");
				int count = Integer.parseInt(arr[1].trim());
				arr = arr[0].split(" cost:");
				double cost = Double.parseDouble(arr[1]);
				arr = arr[0].split(" description:");
				String description = arr[1];
				arr = arr[0].split(" name:");
				String name = arr[1];
				String id = arr[0].substring(0, 2);
				Item i = new Item(id, name, description, imageURL, cost, count);
				items.put(id, i);
			}
			scItems.close(); // Closes the scanner
			
			Scanner scCarts = new Scanner(new File("Database/carts.txt"));
		
			while (scCarts.hasNext()) {
				String line = scCarts.nextLine();
				Cart c = new Cart(line.substring(0, 2), State.valueOf(line.substring(3, 5)));
				String[] itemCounts = line.substring(6).split(",");
				if (itemCounts.length >= 1) {
					for(int i = 0; i < itemCounts.length; i++) {
						String[] itemSplit = itemCounts[i].split("-");
						if (itemSplit.length >= 2) {
							c.addItem(itemSplit[0], Integer.parseInt(itemSplit[1]));
						}
					}
				}
				carts.put(line.substring(0, 2), c);
			}
			scCarts.close(); // Closes the scanner
			
			Scanner scCoupons = new Scanner(new File("Database/discounts.txt"));
			
			while (scCoupons.hasNext()) {
				String line = scCoupons.nextLine();
				String id = line.substring(0, 2);
				String[] couponSplit = line.split(" inID:");
				String[] inIDs = couponSplit[1].split(",");
				ArrayList<String> couponInIDs = new ArrayList<String>();
				for(int i = 0; i < inIDs.length; i++) {
					couponInIDs.add(inIDs[i]);
				}
				couponSplit = couponSplit[0].split(" min:");
				int min = Integer.parseInt(couponSplit[1]);
				couponSplit = couponSplit[0].split(" percent:");
				double percentOff = Double.parseDouble(couponSplit[1]);
				couponSplit = couponSplit[0].split("appID:");
				discounts.put(id, new Coupon(id, couponSplit[1], percentOff/100, min, couponInIDs));
			}
			scCoupons.close(); // Closes the scanner
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.database = new Database(carts, items, discounts);
		
		for (String i : carts.keySet()) {
			carts.get(i).setDatabase(database);
		}
	}

	// Would send call to API
	public boolean sendCall(String URL, String text) {
		return false;
	}

	// Would receive API call and perform required methods accordingly
	public boolean receiveCall(String text) {
		return false;
	}
	
	public String changeItemQuantity(String itemID, int count) {
		database.getItem(itemID).setCount(count);
		return "Item " + itemID + " quantity successfully changed to " + count;
	}
	
	// Testing done
	public String addItemToCart(String cartID, String itemID, int count) {
		int iCount = database.getItem(itemID).getCount();
		if (iCount <= 0) {
			return "Item " + itemID + " out of stock";
		}
		else if (iCount < count) {
			database.getCart(cartID).addItem(itemID, count - iCount);
			return "Not enough stock of " + itemID + " for desired quantity, added as much as possible to cart";
		}
		return database.getCart(cartID).addItem(itemID, count);
	}
	
	// Testing done
	public String setItemQuantityCart(String cartID, String itemID, int count) {
		return database.getCart(cartID).changeItemCount(itemID, count);
	}
	
	// Testing done
	public String applyDiscount(String cartID, String couponID) {
		return database.getCart(cartID).applyCoupon(database.getDiscount(couponID));
	}
	
	// Testing done
	public String getCart(String id) {
		Cart c = database.getCart(id);
		return "Items: " + c.getItems() + "\nTotal: " + c.getTotal() + "\nDiscounts: " + c.getCoupons() + "\nTaxes: " + c.getTotalTaxes();
	}
	
	public String removeItemFromCart(String cartID, String itemID) {
		return database.getCart(cartID).removeItem(itemID);
	}
	
	// Testing done
	public void setCouponExpired(String couponID) {
		database.getDiscount(couponID).setExpired(true);
	}
	
	// For testing purposes
	public Database getDatabase() {
		return database;
	}
	
	public static void main(String[] args) {
		new MainSystem();
	}
}
