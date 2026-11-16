
package com.tka.library;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Book.class);

        System.out.println("1. cfg ready...");

        SessionFactory sf = cfg.buildSessionFactory();

        System.out.println("2. factory ready...");

        Session session = sf.openSession();

        System.out.println("3. session ready...");

        try {

            // TASK 1: Insert Book
            Transaction tx1 = session.beginTransaction();

            Book b1 = new Book(1, "Java Programming",
                    "James Gosling", 500, "Pearson", 10);

            session.persist(b1);
            tx1.commit();

            System.out.println("Task 1: Book Inserted");


            // TASK 2: Fetch All Books
            List<Book> list = session.createQuery(
                    "from Book", Book.class).list();

            System.out.println("Task 2: All Books");

            for (Book b : list) {
                System.out.println(b);
            }


            // TASK 3: Fetch Book By ID
            Book b2 = session.get(Book.class, 1);

            System.out.println("Task 3: Book By ID");
            System.out.println(b2);


            // TASK 4: Update Book Price
            Transaction tx2 = session.beginTransaction();

            Book b3 = session.get(Book.class, 1);

            if (b3 != null) {
                b3.setPrice(700);
                tx2.commit();
                System.out.println("Task 4: Price Updated");
            } else {
                tx2.rollback();
                System.out.println("Book Not Found");
            }


            // TASK 5: Delete Book
            Transaction tx3 = session.beginTransaction();

            Book b4 = session.get(Book.class, 1);

            if (b4 != null) {
                session.remove(b4);
                tx3.commit();
                System.out.println("Task 5: Book Deleted");
            } else {
                tx3.rollback();
                System.out.println("Book Not Found");
            }


            // TASK 6: Insert 10 Books
            Transaction tx4 = session.beginTransaction();

            try {

                session.persist(new Book(101, "C Programming",
                        "Dennis Ritchie", 400, "Pearson", 10));

                session.persist(new Book(102, "Python Basics",
                        "Guido", 550, "Oxford", 15));

                session.persist(new Book(103, "Java Advanced",
                        "James Gosling", 800, "Pearson", 5));

                session.persist(new Book(104, "HTML Guide",
                        "Tim Berners", 300, "Oxford", 20));

                session.persist(new Book(105, "Android Development",
                        "John", 900, "McGraw", 8));

                session.persist(new Book(106, "Database System",
                        "Korth", 750, "Pearson", 12));

                session.persist(new Book(107, "Artificial Intelligence",
                        "Russell", 1000, "McGraw", 7));

                session.persist(new Book(108, "Data Structures",
                        "Mark Allen", 600, "Oxford", 10));

                session.persist(new Book(109, "Operating System",
                        "Galvin", 850, "Pearson", 0));

                session.persist(new Book(110, "Networking",
                        "Tanenbaum", 650, "McGraw", 0));

                tx4.commit();

                System.out.println("Task 6: 10 Books Inserted");

            } catch (Exception ex) {

                if (tx4.isActive()) {
                    tx4.rollback();
                }

                throw ex;
            }


            // TASK 7: Update Publisher Using session.update()
            Transaction tx5 = session.beginTransaction();

            Book b5 = session.get(Book.class, 102);

            if (b5 != null) {
                session.evict(b5);
                b5.setPublisher("Pearson");
                session.update(b5);

                tx5.commit();
                System.out.println("Task 7: Publisher Updated");
            } else {
                tx5.rollback();
                System.out.println("Book Not Found");
            }


            // TASK 8: Delete Out Of Stock Books
            Transaction tx6 = session.beginTransaction();

            int deleted = session.createQuery(
                    "delete from Book where quantity = 0")
                    .executeUpdate();

            tx6.commit();

            System.out.println("Task 8: Out Of Stock Books Deleted = "
                    + deleted);


            // TASK 9: Price Greater Than 500
            List<Book> l9 = session.createNativeQuery(
                    "SELECT * FROM book WHERE price > 500",
                    Book.class).list();

            System.out.println("Task 9: Price Greater Than 500");

            for (Book b : l9) {
                System.out.println(b);
            }


            // TASK 10: Price Descending
            List<Book> l10 = session.createNativeQuery(
                    "SELECT * FROM book ORDER BY price DESC",
                    Book.class).list();

            System.out.println("Task 10: Price Descending");

            for (Book b : l10) {
                System.out.println(b);
            }


            // TASK 11: Highest Price
            Object max = session.createNativeQuery(
                    "SELECT MAX(price) FROM book").uniqueResult();

            System.out.println("Task 11: Highest Price = " + max);


            // TASK 12: Total Price
            Object total = session.createNativeQuery(
                    "SELECT SUM(price) FROM book").uniqueResult();

            System.out.println("Task 12: Total Price = " + total);


            // TASK 13: Publisher Wise Count
            List<Object[]> l13 = session.createNativeQuery(
                    "SELECT publisher, COUNT(*) FROM book "
                    + "GROUP BY publisher").list();

            System.out.println("Task 13: Publisher Wise Count");

            for (Object[] row : l13) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 14: Price Between 400 and 800
            List<Book> l14 = session.createNativeQuery(
                    "SELECT * FROM book WHERE price "
                    + "BETWEEN 400 AND 800", Book.class).list();

            System.out.println("Task 14: Price Between 400 and 800");

            for (Book b : l14) {
                System.out.println(b);
            }


            // TASK 15: Book Names Starting With A
            List<Book> l15 = session.createQuery(
                    "from Book where bookName LIKE 'A%'",
                    Book.class).list();

            System.out.println("Task 15: Names Starting With A");

            for (Book b : l15) {
                System.out.println(b);
            }


            // TASK 16: Price Greater Than Average
            List<Book> l16 = session.createQuery(
                    "from Book where price > "
                    + "(select AVG(price) from Book)",
                    Book.class).list();

            System.out.println("Task 16: Above Average Price");

            for (Book b : l16) {
                System.out.println(b);
            }


            // TASK 17: Second Highest Price
            Object second = session.createQuery(
                    "select max(b.price) from Book b "
                    + "where b.price < "
                    + "(select max(b2.price) from Book b2)")
                    .uniqueResult();

            System.out.println("Task 17: Second Highest Price = "
                    + second);


            // TASK 18: Publisher IN
            List<Book> l18 = session.createQuery(
                    "from Book where publisher IN "
                    + "('Pearson','Oxford','McGraw')",
                    Book.class).list();

            System.out.println("Task 18: Books By Publishers");

            for (Book b : l18) {
                System.out.println(b);
            }


            // TASK 19: Publisher Wise Average Price
            List<Object[]> l19 = session.createQuery(
                    "select publisher, AVG(price) "
                    + "from Book group by publisher").list();

            System.out.println("Task 19: Publisher Wise Average");

            for (Object[] row : l19) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 20: Increase Pearson Price By 10%
            Transaction tx7 = session.beginTransaction();

            int count = session.createQuery(
                    "update Book set price = price * 1.10 "
                    + "where publisher = 'Pearson'")
                    .executeUpdate();

            tx7.commit();

            System.out.println("Task 20: Pearson Price Increased = "
                    + count);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            session.close();
            sf.close();

            System.out.println("Hibernate Closed Successfully");
        }
    }
}
