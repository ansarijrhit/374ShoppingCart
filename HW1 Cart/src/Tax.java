import java.util.Random;

public class Tax {
	double taxPercent = 0;
	double flatTax = 0;
	State state;
	
	public Tax(State state) {
		this.state = state;
		calculateTax();
	}
	
	public void calculateTax() {
		String stateStr = state.toString();
		Random r = new Random(42);
		for(int i = 65; i <= 90; i++) {
			for (int j = 0; j < 2; j++) {
				r.nextDouble();
				if (stateStr.charAt(j) == i) {
					taxPercent = r.nextDouble()/4;
					if (r.nextDouble() > .5) {
						flatTax = r.nextInt(25);
					}
				}
			}
		}
	}
	
	public double getTaxPercent() {
		return taxPercent;
	}
	public double getFlatTax() {
		return flatTax;
	}
}
