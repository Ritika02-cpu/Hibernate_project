
package com.tka.shopping;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(Product.class);

		System.out.println("1. cfg ready...");

		SessionFactory sf = cfg.buildSessionFactory();

		System.out.println("2. factory ready...");

		Session session = sf.openSession();

		System.out.println("3. session ready...");

		try {

			// TASK 1: Insert Product
			Transaction tx1 = session.beginTransaction();

			Product p1 = new Product(1, "Laptop", "Electronics", 55000, "Dell", 10);

			session.persist(p1);

			tx1.commit();

			System.out.println("Task 1: Product Inserted");

			// TASK 2: Fetch All Products
			List<Product> list = session.createQuery("from Product", Product.class).list();

			System.out.println("Task 2: All Products");

			for (Product p : list) {
				System.out.println(p);
			}

			// TASK 3: Fetch Product By ID
			Product p2 = session.get(Product.class, 1);

			System.out.println("Task 3: Product By ID");
			System.out.println(p2);

			// TASK 4: Update Product Price
			Transaction tx2 = session.beginTransaction();

			Product p3 = session.get(Product.class, 1);

			if (p3 != null) {
				p3.setPrice(60000);
				tx2.commit();

				System.out.println("Task 4: Price Updated");
			} else {
				tx2.rollback();
				System.out.println("Product Not Found");
			}

			// TASK 5: Delete Product
			Transaction tx3 = session.beginTransaction();

			Product p4 = session.get(Product.class, 1);

			if (p4 != null) {
				session.remove(p4);
				tx3.commit();

				System.out.println("Task 5: Product Deleted");
			} else {
				tx3.rollback();
				System.out.println("Product Not Found");
			}

			// TASK 6: Insert 10 Products
			Transaction tx4 = session.beginTransaction();

			try {

				session.persist(new Product(101, "Mobile", "Electronics", 25000, "Samsung", 15));

				session.persist(new Product(102, "Headphones", "Electronics", 3000, "Boat", 20));

				session.persist(new Product(103, "Shirt", "Clothing", 1500, "Puma", 10));

				session.persist(new Product(104, "Shoes", "Clothing", 4000, "Nike", 12));

				session.persist(new Product(105, "Washing Machine", "Home Appliances", 30000, "LG", 5));

				session.persist(new Product(106, "Refrigerator", "Home Appliances", 40000, "Samsung", 8));

				session.persist(new Product(107, "Tablet", "Electronics", 18000, "Lenovo", 7));

				session.persist(new Product(108, "Watch", "Accessories", 5000, "Titan", 10));

				session.persist(new Product(109, "Keyboard", "Electronics", 1200, "HP", 0));

				session.persist(new Product(110, "Mouse", "Electronics", 800, "Dell", 0));

				tx4.commit();

				System.out.println("Task 6: 10 Products Inserted");

			} catch (Exception ex) {

				if (tx4.isActive()) {
					tx4.rollback();
				}

				throw ex;
			}

			// TASK 7: Update Category Using session.update()
			Transaction tx5 = session.beginTransaction();

			Product p5 = session.get(Product.class, 102);

			if (p5 != null) {

				session.evict(p5);

				p5.setCategory("Accessories");

				session.update(p5);

				tx5.commit();

				System.out.println("Task 7: Category Updated");

			} else {
				tx5.rollback();
				System.out.println("Product Not Found");
			}

			// TASK 8: Delete Out Of Stock Products
			Transaction tx6 = session.beginTransaction();

			int deleted = session.createQuery("delete from Product where stock = 0").executeUpdate();

			tx6.commit();

			System.out.println("Task 8: Out Of Stock Products Deleted = " + deleted);

			// TASK 9: Price Greater Than 5000
			List<Product> l9 = session.createNativeQuery("SELECT * FROM product WHERE price > 5000", Product.class)
					.list();

			System.out.println("Task 9: Price Greater Than 5000");

			for (Product p : l9) {
				System.out.println(p);
			}

			// TASK 10: Price Descending
			List<Product> l10 = session.createNativeQuery("SELECT * FROM product ORDER BY price DESC", Product.class)
					.list();

			System.out.println("Task 10: Price Descending");

			for (Product p : l10) {
				System.out.println(p);
			}

			// TASK 11: Highest Price
			Object max = session.createNativeQuery("SELECT MAX(price) FROM product").uniqueResult();

			System.out.println("Task 11: Highest Price = " + max);

			// TASK 12: Total Price
			Object total = session.createNativeQuery("SELECT SUM(price) FROM product").uniqueResult();

			System.out.println("Task 12: Total Price = " + total);

			// TASK 13: Category Wise Count
			List<Object[]> l13 = session
					.createNativeQuery("SELECT category, COUNT(*) FROM product " + "GROUP BY category").list();

			System.out.println("Task 13: Category Wise Count");

			for (Object[] row : l13) {
				System.out.println(row[0] + " = " + row[1]);
			}

			// TASK 14: Price Between 4000 and 80000
			List<Product> l14 = session
					.createNativeQuery("SELECT * FROM product WHERE price " + "BETWEEN 4000 AND 80000", Product.class)
					.list();

			System.out.println("Task 14: Price Between 4000 and 80000");

			for (Product p : l14) {
				System.out.println(p);
			}

			// TASK 15: Product Names Starting With A
			List<Product> l15 = session.createQuery("from Product where productName LIKE 'A%'", Product.class).list();

			System.out.println("Task 15: Names Starting With A");

			for (Product p : l15) {
				System.out.println(p);
			}

			// TASK 16: Price Greater Than Average
			List<Product> l16 = session
					.createQuery("from Product where price > " + "(select AVG(price) from Product)", Product.class)
					.list();

			System.out.println("Task 16: Above Average Price");

			for (Product p : l16) {
				System.out.println(p);
			}

			// TASK 17: Second Highest Price
			Object second = session.createQuery("select max(p.price) from Product p " + "where p.price < "
					+ "(select max(p2.price) from Product p2)").uniqueResult();

			System.out.println("Task 17: Second Highest Price = " + second);

			// TASK 18: Category IN
			List<Product> l18 = session
					.createQuery("from Product where category IN " + "('Electronics','Clothing','Accessories')",
							Product.class)
					.list();

			System.out.println("Task 18: Products By Category");

			for (Product p : l18) {
				System.out.println(p);
			}

			// TASK 19: Category Wise Average Price
			List<Object[]> l19 = session.createQuery("select category, AVG(price) " + "from Product group by category")
					.list();

			System.out.println("Task 19: Category Wise Average Price");

			for (Object[] row : l19) {
				System.out.println(row[0] + " = " + row[1]);
			}

			// TASK 20: Increase Electronics Price By 10%
			Transaction tx7 = session.beginTransaction();

			int count = session
					.createQuery("update Product set price = price * 1.10 " + "where category = 'Electronics'")
					.executeUpdate();

			tx7.commit();

			System.out.println("Task 20: Electronics Price Increased = " + count);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			session.close();
			sf.close();

			System.out.println("Hibernate Closed Successfully");
		}
	}
}
