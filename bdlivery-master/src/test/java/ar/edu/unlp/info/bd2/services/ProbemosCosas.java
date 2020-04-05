package ar.edu.unlp.info.bd2.services;

import java.util.Date;

import java.util.Calendar;

import java.util.Optional;

import ar.edu.unlp.info.bd2.model.*;

public class ProbemosCosas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

/*		Calendar cal = Calendar.getInstance();
		//System.out.println(cal); //larga muchas cosas feas hehehe
		System.out.println(cal.getTime());
		System.out.println("hola");*/
    	Calendar cal = Calendar.getInstance();
    	Date startDate = cal.getTime();
		Price x = new Price(Float.valueOf(2521.2F));
		/*System.out.println(x.getPrice());
		System.out.println(x.getStartDate());
		System.out.println("hasta aca el precio x");		*/
        Supplier s1 = new Supplier("Burger King", "30710256443", "Av. Corrientes 956", Float.valueOf(-53.45F), Float.valueOf(-60.22F));
		/*System.out.println(s1.getName());
		System.out.println(s1.getCuil());
		System.out.println(s1.getAddress());
		System.out.println(s1.getCoordX());
		System.out.println("hasta aca el suplier");*/
        Product p1 = new Product("Combo Stacker ATR", Float.valueOf(2521.2F), Float.valueOf(2.5F),s1);
     /* System.out.println(p1.getName());
        System.out.println(p1.getActualPrice());
    	System.out.println("lo importante");
    	System.out.println(p1.getPrices().size());
    	System.out.println(p1.getSupplier().equals(s1));
        p1.setPrices();
        System.out.println();
    	System.out.println("vacio?: ");
    	System.out.println(p1.getPrices().isEmpty());*/
    	p1.updateProductPrice(Float.valueOf(2521.2F), startDate);
    	/*System.out.println("se cambio?: ");
    	System.out.println(p1.getPrices().size());
    	System.out.println("sera true?: ");
    	System.out.println(p1.getPrice().equals(Float.valueOf(2521.2F)));*/
    	ProductOrder orden = new ProductOrder(5L,p1);
  /*  	System.out.println("iguales?");
    	System.out.println(orden.getProduct().equals(p1));
    	System.out.println(orden.getQuantity());		*/
    	/*Calendar cal1 = Calendar.getInstance();
    	Date orderDate = cal1.getTime();
    	cal.set(Calendar.YEAR, 1988);
    	cal.set(Calendar.MONTH, Calendar.JUNE);
    	cal.set(Calendar.DAY_OF_MONTH, 23);
    	Date dob2 = cal.getTime();
    	User u1 = new User("hugo.gamarra@testmail.com", "123456", "hgamarra", "Hugo Gamarra", dob2);
    	User u2 = new User("delivery@info.unlp.edu.ar", "123456", "delivery", "Delivery", dob2);
    	Order o1 = new Order(orderDate,"Av. Corrientes 1405 2° B", Float.valueOf(-54.45F), Float.valueOf(-62.22F),u1);
    	System.out.println("vacio?: ");
    	System.out.println(o1.getProducts().isEmpty());
    	o1.addProductOrder(3L, p1);
    	System.out.println("vacio? no debería... : ");
    	System.out.println(o1.getProducts().isEmpty());  	*/
    	

    	

		
	}

}
