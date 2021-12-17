import java.util.HashMap;

public class Total {
	private double totalItem = 0;
	private double totalDiscount = 0;
	private double totalTax = 0;
	private int consecutiveCouponFails = 0;
	private Tax tax;
	private HashMap<String, Coupon> coupons = new HashMap<String, Coupon>();
	private HashMap<String, Integer> items;
	private Database database;
	
	public Total(HashMap<String, Integer> items, State state) {
		this.items = items;
		this.tax = new Tax(state);
	}
	
	public void setDatabase(Database d) { 
		this.database = d;
	}
	
	public void addItem(String itemID, int count) {
		items.put(itemID, count);
	}
	
	public void removeItem(String itemID) {
		items.remove(itemID);
	}
	
	public String applyCoupon(Coupon coupon) {
		if (consecutiveCouponFails >= 5) {
			return "Silently rejected";
		}
		if(coupon.isExpired()) {
			consecutiveCouponFails++;
			return "Coupon is expired";
		}
		else if(coupons.containsKey(coupon.getID())) {
			
		}
		String i = coupon.getApplicableItemID();
		if (items.containsKey(i)) {
			if (items.get(i) >= coupon.getMinimumQuantity()) {
				for (String c : coupons.keySet()) {
					if(coupons.get(c).testInapplicable(coupon.getID())) {
						consecutiveCouponFails++;
						return "Coupon code " + coupon.getID() + " cannot be applied with " + coupons.get(c).getID();
					}
				}
				consecutiveCouponFails = 0;
				return addCoupon(coupon.getID(), coupon);
			}
		}
		
		consecutiveCouponFails++;
		return "Failed to meet coupon requirements: " + coupon.getMinimumQuantity() + " of " + database.getItem(i);
	}
	
	public void updateItemTotal() {
		totalItem = 0;
		for(String i : items.keySet()) {
			totalItem += database.getItem(i).getCost() * items.get(i);
		}
	}
	
	public void updateDiscount() {
		totalDiscount = 0;
		checkCoupons();
		for (String i : coupons.keySet()) {
			String appID = coupons.get(i).getApplicableItemID();
			totalDiscount += database.getItem(appID).getCost()*items.get(appID)*coupons.get(i).getPercentOff();
		}
	}
	
	public void updateTax() {
		totalTax = 0;
		if(this.totalItem + this.totalDiscount > 0) {
			this.totalTax = (this.totalItem + this.totalDiscount)*this.tax.getTaxPercent()+this.tax.getFlatTax();
		}
	}
	
	public double getTotalDiscount() {
		updateDiscount();
		return totalDiscount*100.0/100.0;
	}
	
	public double getTax() {
		updateItemTotal();
		updateDiscount();
		updateTax();
		return Math.round(totalTax*100.0)/100.0;
	}
	
	public double getTotal() {
		updateItemTotal();
		updateDiscount();
		updateTax();
		return Math.round((totalItem-totalDiscount+totalTax)*100.0)/100.0;
	}
	
	public String addCoupon(String cID, Coupon c) {
		if (coupons.containsKey(cID)) {
			return "Coupon " + cID + " already applied";
		}
		coupons.put(cID, c);
		return "Coupon " + cID + " applied";
	}
	
	public void removeCoupon(String cID, Coupon c) {
		coupons.remove(cID);
	}
	
	public void checkCoupons() {
		for(String i : coupons.keySet()) {
			Coupon c = coupons.get(i);
			String item = c.getApplicableItemID();
			if (!items.containsKey(item) || items.get(item) < c.getMinimumQuantity()) {
				coupons.remove(i);
			}
		}
	}
	
	public String getCoupons() {
		String couponString = "";
		for(String i : coupons.keySet()) {
			couponString += i + ", ";
		}
		if (couponString.length() <= 2) {
			return "";
		}
		return couponString.substring(0, couponString.length()-2);
	}
}
