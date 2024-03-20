package hw211;

//IREM ONARAN
//201101045

public class Order {
	
	private String symbol;
	private String type;
	private double price;
	private int quantity;
	private long timeStamp;
	private String userId;
	private boolean allOrNone;
	
	
	public Order(String symbol, String type, double price, int quantity, long timeStamp, String userId,
			boolean allOrNone) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.timeStamp = timeStamp;
		this.userId = userId;
		this.allOrNone = allOrNone;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public long getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public boolean isAllOrNone() {
		return allOrNone;
	}


	public void setAllOrNone(boolean allOrNone) {
		this.allOrNone = allOrNone;
	}
	
	
}
