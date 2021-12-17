import org.junit.jupiter.api.Test;

class Tests {
	
	// MAINSYSTEM TESTS
	
	@Test
	void testAddItemToCart() {
		MainSystem ms = new MainSystem();
		assert(ms.addItemToCart("c2", "i5", 3).equals("Item i5 count increased to 7"));
		assert(ms.addItemToCart("c6", "i2", 5).equals("5 of item i2 added"));
		assert(ms.addItemToCart("c3", "i3", -2).equals("Item count cannot be negative"));
		assert(ms.addItemToCart("c3", "i8", 3).equals("Item i8 out of stock"));
		assert(ms.addItemToCart("c1", "i4", 100000).equals("Not enough stock of i4 for desired quantity, added as much as possible to cart"));
	}

	@Test
	void testSetItemQuantityCart() {
		MainSystem ms = new MainSystem();
		assert(ms.setItemQuantityCart("c1", "i1", 3).equals("Item i1 count modified to 3"));
		assert(ms.setItemQuantityCart("c5", "i7", 7).equals("Item i7 count already equal to 7"));
		assert(ms.setItemQuantityCart("c1", "i1", 0).equals("Item i1 removed"));
		assert(ms.setItemQuantityCart("c8", "i7", 2).equals("Item not found in cart"));
	}

	// Also tests setting coupons to expired
	@Test
	void testApplyDiscount() {
		MainSystem ms = new MainSystem();
		for(int i = 0; i < 5; i++) {
			assert(ms.applyDiscount("c1", "d2").equals("Failed to meet coupon requirements: 7 of Strawberry Jelly"));
		}
		assert(ms.applyDiscount("c1", "d2").equals("Silently rejected"));
		ms.setCouponExpired("d4"); 
		assert(ms.applyDiscount("c2", "d4").equals("Coupon is expired"));
		assert(ms.applyDiscount("c5", "d3").equals("Coupon d3 applied"));
		assert(ms.applyDiscount("c5", "d3").equals("Coupon d3 already applied"));
	}

	@Test
	void testGetCart() {
		MainSystem ms = new MainSystem();
		String c5 = "Items:[{i1:PeanutButter},{i2:StrawberryJelly},{i3:WheatBread},{i4:ChocolateIceCream},{i5:Raspberries},{i6:PartySizeNachoDoritos},{i7:PoundofFlour}]Total:79.66Discounts:Taxes:22.16";
		assert(c5.equals(ms.getCart("c5").replaceAll("\\s+","")));
		assert(ms.getCart("c6").replaceAll("\\s+", "").equals("Items:Total:0.0Discounts:Taxes:0.0"));
		ms.applyDiscount("c4", "d6");
		assert(ms.getCart("c4").replaceAll("\\s+", "").equals("Items:[{i4:ChocolateIceCream},{i6:PartySizeNachoDoritos}]Total:30.09Discounts:d6Taxes:3.99"));
	}
	
	@Test
	void testRemoveItemFromCart() {
		MainSystem ms = new MainSystem();
		assert(ms.removeItemFromCart("c2", "i4").equals("Item i4 removed"));
		assert(ms.removeItemFromCart("c6", "i4").equals("Item i4 does not exist in cart"));
	}
	
	// CART TESTS

	@Test
	void testGetItems() {
		MainSystem ms = new MainSystem();
		assert(ms.getDatabase().getCart("c1").getItems().equals("[{i1: Peanut Butter}, {i2: Strawberry Jelly}, {i3: Wheat Bread}, {i5: Raspberries}]"));
		assert(ms.getDatabase().getCart("c3").getItems().equals("[{i7: Pound of Flour}]"));
		assert(ms.getDatabase().getCart("c6").getItems().equals(""));
	}

	@Test
	void testGetTotal() {
		MainSystem ms = new MainSystem();
		assert(ms.getDatabase().getCart("c2").getTotal() == 36.4);
		assert(ms.getDatabase().getCart("c6").getTotal() == 0);
	}

	@Test
	void testGetTotalDiscounts() {
		MainSystem ms = new MainSystem();
		ms.applyDiscount("c4", "d6");
		assert(ms.getDatabase().getCart("c4").getTotalDiscounts() == 0.9);
		assert(ms.getDatabase().getCart("c5").getTotalDiscounts() == 0);
	}

	@Test
	void testGetTotalTaxes() {
		MainSystem ms = new MainSystem();
		assert(ms.getDatabase().getCart("c5").getTotalTaxes() == 22.16);
		assert(ms.getDatabase().getCart("c6").getTotalTaxes() == 0);
	}
	
	@Test
	void testGetCoupons() {
		MainSystem ms = new MainSystem();
		ms.applyDiscount("c8", "d6");
		ms.applyDiscount("c8", "d3");
		assert(ms.getDatabase().getCart("c8").getCoupons().equals("d6, d3"));
		assert(ms.getDatabase().getCart("c3").getCoupons().equals(""));
	}
	
	@Test
	void testTaxes() {
		Tax t1 = new Tax(State.FL);
		Tax t2 = new Tax(State.NM);
		assert(t1.getTaxPercent() == 0.0027665809455794665 && t1.getFlatTax() == 9);
		assert(t2.getTaxPercent() == 0.17835156445580727 && t2.getFlatTax() == 0);
	}
}
