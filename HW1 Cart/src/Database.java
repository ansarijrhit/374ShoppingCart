import java.util.HashMap;

public class Database {
	HashMap<String, Cart> carts;
	HashMap<String, Item> items;
	HashMap<String, Coupon> discounts;
	
	public Database(HashMap<String, Cart> carts, HashMap<String, Item> items, HashMap<String, Coupon> discounts) {
		this.carts = carts;
		this.items = items;
		this.discounts = discounts;
	}
	
	public Item getItem(String itemID) {
		return items.get(itemID);
	}
	
	public Coupon getDiscount(String discountID) {
		return discounts.get(discountID);
	}
	
	public Cart getCart(String cartID) {
		return carts.get(cartID);
	}
}
