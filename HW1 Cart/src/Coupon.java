import java.util.ArrayList;

public class Coupon {
	private String id;
	private String applicableItemID;
	private double percentOff;
	private int minimumQuantity;
	private boolean isExpired = false;
	private ArrayList<String> inapplicable;
	
	public Coupon(String id, String applicableItemID, double percentOff, int minimumQuantity, ArrayList<String> inapplicable) {
		this.id = id;
		this.applicableItemID = applicableItemID;
		this.percentOff = percentOff;
		this.minimumQuantity = minimumQuantity;
		this.inapplicable = inapplicable;
	}
	
	public String getApplicableItemID() {
		return applicableItemID;
	}
	public double getPercentOff() {
		return percentOff;
	}
	public int getMinimumQuantity() {
		return minimumQuantity;
	}
	public String toString() {
		return "Applicable Item: " + applicableItemID + ", percent off: " + percentOff + ", min: " + minimumQuantity +", inIDs: " + inapplicable + "\n";
	}
	public void setExpired(boolean e) {
		isExpired = e;
	}
	public boolean isExpired() {
		return isExpired;
	}
	public String getID() {
		return id;
	}
	public boolean testInapplicable(String id) {
		return inapplicable.contains(id);
	}
}