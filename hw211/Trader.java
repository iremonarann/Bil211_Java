package hw211;

//IREM ONARAN
//201101045

import java.util.*;

public class Trader {

	private Queue<Order> siparisler;
	private Queue<Order> execute_edilenler; //daha once execute edilmis order'lari depolar
	private Queue<Order> execute_edilmeyenler; //daha once hic execute edilmeyen order'lari depolar
	private Queue<String> log;
	
	
	
	public Trader() {
		siparisler = new LinkedList<>();
		execute_edilenler = new LinkedList<>();
		execute_edilmeyenler = new LinkedList<>();
		log = new LinkedList<>();
		
	}
	

	
	public void pushOrder(Order obj) {
		//cancel order'i gelirse yapilacak islem
		if(obj.getType().equalsIgnoreCase("Cancel")) {
			for(Order o: siparisler) {
				if (obj.getUserId().equalsIgnoreCase(o.getUserId()) && obj.getTimeStamp() == o.getTimeStamp()) {
					cancel(o);
					break;
				}
			}
		}
		else {
			if(!(obj.getQuantity() == 0)) {
				siparisler.add(obj);
				exe_kontrol();
			}
		}
	}
	
	
	
	public void execute() {
			
		Queue<Order> buy = new LinkedList<>();
		Queue<Order> sell = new LinkedList<>();
		Queue<Order> sell_holder = new LinkedList<>();
		double deger = 0;
		Order holder_s = null;
		Order holder_b = null;
		Order alis = null;
		Order satis = null;
		double price = 0;
		int quantity = 0;
		int temp = 0;	
			
		//siparisleri buyer ve seller olma durumlarina gore iki listeye ayirir
		for(Order o: siparisler) {
			if(o.getType().equalsIgnoreCase("buy")) {
				buy.add(o);
			}
			if(o.getType().equalsIgnoreCase("sell")) {
				sell.add(o);
			}
		}
			
			
		for(Order b: buy) {
			deger = b.getPrice();
			if(b.isAllOrNone() == true) {
				for(Order s: sell) {
					if(b.getSymbol().equalsIgnoreCase(s.getSymbol()) && (b.getPrice() >= s.getPrice())){
						holder_b = b;
						if(s.isAllOrNone() == true && s.getQuantity() == b.getQuantity()) {
							sell_holder.add(s);
						}
						if(s.isAllOrNone() == false && s.getQuantity() >= b.getQuantity()) {
							sell_holder.add(s);
						}
					}
				}
				for(Order sh: sell_holder) {
					if(sh.getPrice() < deger) {
						holder_s = sh;
						deger = sh.getPrice();
					}
				}
				if(holder_b != null && holder_s != null) {
					alis = holder_b;
					satis = holder_s;
					price = satis.getPrice();
					quantity = Math.min(alis.getQuantity(), satis.getQuantity());
					if(!(quantity == 0)) {
						log.add(satis.getUserId() + ", " + alis.getUserId() + ", " + price + ", " + quantity);
						alis.setQuantity(alis.getQuantity()-quantity);
						satis.setQuantity(satis.getQuantity()-quantity);
					}
					//quantity sifira dustuyse order'i listeden sil
					if(alis.getQuantity() == 0) {
						cancel2(alis);
					}
					if(satis.getQuantity() == 0) {
						cancel2(satis);
					}
					//buyer ve seller orderlarinde degisiklik olduysa execute_edilenler listesine ekle
					for(Order x: execute_edilenler) {
						if(alis.getUserId().equalsIgnoreCase(x.getUserId())) {
							temp++;
						}
					}
					if(temp == 0) {
						execute_edilenler.add(alis);
					}
					temp = 0;
					for(Order x: execute_edilenler) {
						if(satis.getUserId().equalsIgnoreCase(x.getUserId())) {
							temp++;
						}
					}
					if(temp == 0) {
						execute_edilenler.add(satis);
					}
					temp = 0;
				}
			}
				
			else {
				for(Order s: sell) {
					if(b.getSymbol().equalsIgnoreCase(s.getSymbol()) && (b.getPrice() >= s.getPrice())) {
						holder_b = b;
						if(s.isAllOrNone() == true && b.getQuantity() >= s.getQuantity()) {
							sell_holder.add(s);
						}
						if(s.isAllOrNone() == false) {
							sell_holder.add(s);
						}
					}
				}
					
				for(Order sh: sell_holder) {
					if(sh.getPrice() < deger) {
						holder_s = sh;
						deger = sh.getPrice();
					}
				}
					
				if(holder_s == null) {
					for(Order sh: sell_holder) {
						if(sh.getPrice() == deger) {
							holder_s = sh;
							deger = sh.getPrice();
							break;
						}
					}
				}
						
				if(holder_b != null && holder_s != null) {
					alis = holder_b;
					satis = holder_s;
					price = satis.getPrice();
					quantity = Math.min(alis.getQuantity(), satis.getQuantity());
					if(!(quantity == 0)) {
						log.add(satis.getUserId() + ", " + alis.getUserId() + ", " + price + ", " + quantity);
						alis.setQuantity(alis.getQuantity()-quantity);
						satis.setQuantity(satis.getQuantity()-quantity);
					}
					//quantity sifira dustuyse order'i listeden sil
					if(alis.getQuantity() == 0) {
						cancel2(alis);
					}
					if(satis.getQuantity() == 0) {
						cancel2(satis);
					}
					//buyer ve seller orderlarinde degisiklik olduysa execute_edilenler listesine ekle
					for(Order x: execute_edilenler) {
						if(alis.getUserId().equalsIgnoreCase(x.getUserId())) {
							temp++;
						}
					}
					if(temp == 0) {
						execute_edilenler.add(alis);
					}
					temp = 0;
					for(Order x: execute_edilenler) {
						if(satis.getUserId().equalsIgnoreCase(x.getUserId())) {
							temp++;
						}
					}
					if(temp == 0) {
						execute_edilenler.add(satis);
					}
					temp = 0;
					
				}
				//buyer execute edildikten sonra hala uygun bir seller olabilir diye metod tekrar cagirilir
				if(holder_s != null && holder_b.getQuantity() != 0) {
					execute();
				}
			}
		}

		exe_kontrol();
	}
	
	
	//hic execute edilmeyen order'ları execute_edilmeyen listesine koyar
	public void exe_kontrol() {
		Queue<Order> yeni = new LinkedList<>(); 
		int temp = 0;
		execute_edilmeyenler = yeni;
		for(Order o: siparisler) {
			for(Order e: execute_edilenler) {
				if(o.getUserId().equalsIgnoreCase(e.getUserId())) {
					temp++;
				}
			}
			
			if(temp == 0) {
				execute_edilmeyenler.add(o);
			}
			temp = 0;
		}
		
	}
	
	
	//pushOrder ile cancel gelmesi durumunda kullanilan cancel metodu
	public void cancel(Order obj) {
		Order hold = null;
		Queue<Order> yeni = new LinkedList<>(); 
		int temp = 0;
		for(Order o: siparisler) {
			if(o.getUserId().equalsIgnoreCase(obj.getUserId()) && o.getTimeStamp() == obj.getTimeStamp()) {
				hold = obj;
				for(Order e: execute_edilmeyenler) {
					if(hold.getUserId().equalsIgnoreCase(e.getUserId())) {
						temp++;
					}
				}
				if(temp == 0) {
					yeni.add(hold);
				}
			}
			else {
				yeni.add(o);
			}
		}
		siparisler = yeni;
	}
	
	
	//quantity 0 oldugunda order'i listeden silmek icin kullanilan cancel metodu
	public void cancel2(Order obj) {
		Queue<Order> yeni = new LinkedList<>(); 
		for(Order o: siparisler) {
			if(!(o.getUserId().equalsIgnoreCase(obj.getUserId()) && o.getTimeStamp() == obj.getTimeStamp())) {
				yeni.add(o);
			}
		}
		siparisler = yeni;
	}
	
	
    public void printExecutedOrders() {
	   	System.out.println("Executed Orders:");
		for (String o : log) {
			System.out.println(o);
		}
	    System.out.println();
	    Queue<String> temp = log = new LinkedList<>();
		log = temp;
    }
    
    
    
    public void printOrderQueue() {
    	System.out.println("Order Queue:");
        for (Order order : siparisler) {
            System.out.println(order.getSymbol() + ", " + order.getType() + ", " + order.getPrice() + ", " + order.getQuantity() 
            					+ ", " + order.getTimeStamp() + ", " + order.getUserId() + ", " + order.isAllOrNone());
        }
        System.out.println();
    }
    

    
}
