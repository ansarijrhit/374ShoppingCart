public class Item {
	private String id;
	private String name;
	private String description;
	private String imageURL;
	private double cost;
	private int count;
	
	public Item(String id, String name, String description, String imageURL, double cost, int count) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageURL = imageURL;
		this.cost = cost;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getPicture() {
		return imageURL;
	}
	public double getCost() {
		return cost;
	}
	public int getCount() {
		return count;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPicture(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void updateCount(int adjust) {
		count += adjust;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString() {
		return name;
	}
	
	public String getID() {
		return id;
	}
}