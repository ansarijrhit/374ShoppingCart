import java.util.HashMap;

public class Cart {
	@SuppressWarnings("unused")
	private String id;
	private Total total;
	
	private HashMap<String, Integer> items = new HashMap<String, Integer>(); // Items and their quantity in cart
	private Database database;
	
	public Cart(String id, State customerState) {
		this.id = id;
		this.total = new Total(items, customerState);
	}
	
	public void setDatabase(Database d) {
		this.database = d;
		this.total.setDatabase(d);
	}

	// Already tested
	public String addItem(String itemID, int count) {
		if (items.containsKey(itemID)) {
			items.put(itemID, items.get(itemID) + count);
			total.addItem(itemID, items.get(itemID) + count);
			if (items.get(itemID) <= 0) {
				items.remove(itemID);
				total.removeItem(itemID);
			}
			checkCoupons();
			return "Item " + itemID + " count increased to " + items.get(itemID);
		}
		else if (count > 0) {
			items.put(itemID, count);
			total.addItem(itemID, count);
			checkCoupons();
			return count + " of item " + itemID + " added";
		}
		return "Item count cannot be negative";
	}

	// Already tested
	public String changeItemCount(String itemID, int count) {
		if (items.containsKey(itemID)) {
			if(items.get(itemID) == count) {
				return "Item " + itemID + " count already equal to " + count;
			}
			items.put(itemID, count);
			if (count <= 0) {
				items.remove(itemID);
				checkCoupons();
				return "Item " + itemID + " removed";
			}
			checkCoupons();
			return "Item " + itemID + " count modified to " + count;
		}
		return "Item not found in cart";
	}

	// Already tested
	public String removeItem(String itemID) {
		if (items.containsKey(itemID)) {
			items.remove(itemID);
			total.removeItem(itemID);
			return "Item " + itemID + " removed";
		}
		return "Item " + itemID + " does not exist in cart";
	}

	// Already tested
	public String applyCoupon(Coupon coupon) {
		return this.total.applyCoupon(coupon);
	}
	
	public void checkCoupons() {
		total.checkCoupons();
	}
	
	// Testing complete
	public String getCoupons() {
		return total.getCoupons();
	}

	// Testing complete
	public String getItems() {
		String itemList = "[";
		for (String i : items.keySet()) {
			itemList += "{" + i + ": " + database.getItem(i).getName() + "}, ";
		}
		if (itemList.length() <= 2) {
			return "";
		}
		return itemList.substring(0, itemList.length() - 2) + "]";
	}
	
	// Testing complete
	public double getTotal() {
		return total.getTotal();
	}

	// Testing complete
	public double getTotalDiscounts() {
		return total.getTotalDiscount();
	}

	// Testing complete
	public double getTotalTaxes() {
		return total.getTax();
	}
}