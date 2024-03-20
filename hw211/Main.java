package hw211;

//IREM ONARAN
//201101045

public class Main {
	public static void main(String[] args) {
    	Trader trader = new Trader();
 
    	
    	Order order1 = new Order("A","sell",150.0,170,1,"user1",false);
    	Order order2 = new Order("A","sell",120.0,100,2,"user2",true);
    	Order order3 = new Order("A","buy",130.0,350,3,"user3",false);
    	Order order4 = new Order("A","sell",110.0,100,4,"user4",true);
    	Order order5 = new Order("A","sell",115.0,50,5,"user5",false);
    	Order order6 = new Order("A","buy",150.0,150,6,"user6",true);
    	Order order7 = new Order("A","sell",100.0,50,7,"user7",false);
    	Order order8 = new Order("B","buy",200.0,150,8,"user8",false);
    	Order order9 = new Order("D","buy",120.0,50,9,"user9",false);
    	Order order10 = new Order("D","Cancel",0.0,0,9,"user9",false);
    	Order order11 = new Order("A","Cancel",0.0,0,3,"user3",false);
 
    	
    	trader.pushOrder(order1);
    	trader.pushOrder(order2);
    	trader.pushOrder(order3);
     	trader.pushOrder(order4);
     	trader.pushOrder(order5);
     	trader.pushOrder(order6);
     	trader.pushOrder(order7);
     	trader.pushOrder(order8);
     	trader.pushOrder(order9);
    	
    	trader.execute();
 
    	
    	trader.printExecutedOrders();
	
    	
    	trader.pushOrder(order10);
    	trader.pushOrder(order11);
    	 
    	
    	trader.execute();
 
    	
    	trader.printExecutedOrders(); 

    	
    	trader.printOrderQueue();
	
	
	}
}

