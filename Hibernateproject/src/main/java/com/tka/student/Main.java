
package com.tka.student;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Student.class);

        System.out.println("1. cfg ready...");

        SessionFactory sf = cfg.buildSessionFactory();

        System.out.println("2. factory ready...");

        Session session = sf.openSession();

        System.out.println("3. session ready...");

        try {

            // TASK 1: Insert Student
            Transaction tx1 = session.beginTransaction();

            Student s1 = new Student(1, "Amit",
                    "MCA", 85.50, "ABC College");

            session.persist(s1);
            tx1.commit();

            System.out.println("Task 1: Student Inserted");


            // TASK 2: Fetch All Students
            List<Student> list = session.createQuery(
                    "from Student", Student.class).list();

            System.out.println("Task 2: All Students");

            for (Student s : list) {
                System.out.println(s);
            }


            // TASK 3: Fetch Student By ID
            Student s2 = session.get(Student.class, 1);

            System.out.println("Task 3: Student By ID");
            System.out.println(s2);


            // TASK 4: Update Percentage
            Transaction tx2 = session.beginTransaction();

            Student s3 = session.get(Student.class, 1);

            if (s3 != null) {
                s3.setPercentage(90.00);
                tx2.commit();

                System.out.println("Task 4: Percentage Updated");
            } else {
                tx2.rollback();
                System.out.println("Student Not Found");
            }


            // TASK 5: Delete Student
            Transaction tx3 = session.beginTransaction();

            Student s4 = session.get(Student.class, 1);

            if (s4 != null) {
                session.remove(s4);
                tx3.commit();

                System.out.println("Task 5: Student Deleted");
            } else {
                tx3.rollback();
                System.out.println("Student Not Found");
            }


            // TASK 6: Insert 10 Students
            Transaction tx4 = session.beginTransaction();

            try {

                session.persist(new Student(101, "Rahul",
                        "MCA", 78.50, "ABC College"));

                session.persist(new Student(102, "Sneha",
                        "BCA", 88.00, "XYZ College"));

                session.persist(new Student(103, "Priya",
                        "MCA", 92.00, "ABC College"));

                session.persist(new Student(104, "Akash",
                        "BSc", 65.00, "PQR College"));

                session.persist(new Student(105, "Rohit",
                        "BCA", 72.50, "XYZ College"));

                session.persist(new Student(106, "Neha",
                        "MCA", 95.00, "ABC College"));

                session.persist(new Student(107, "Kiran",
                        "BSc", 55.00, "PQR College"));

                session.persist(new Student(108, "Pooja",
                        "BCA", 82.00, "XYZ College"));

                session.persist(new Student(109, "Anil",
                        "MCA", 68.00, "ABC College"));

                session.persist(new Student(110, "Ajay",
                        "BSc", 75.00, "PQR College"));

                tx4.commit();

                System.out.println("Task 6: 10 Students Inserted");

            } catch (Exception ex) {

                if (tx4.isActive()) {
                    tx4.rollback();
                }

                throw ex;
            }


            // TASK 7: Update Course Using session.update()
            Transaction tx5 = session.beginTransaction();

            Student s5 = session.get(Student.class, 102);

            if (s5 != null) {

                session.evict(s5);
                s5.setCourse("MCA");
                session.update(s5);

                tx5.commit();

                System.out.println("Task 7: Course Updated");

            } else {
                tx5.rollback();
                System.out.println("Student Not Found");
            }


            // TASK 8: Delete BSc Students
            Transaction tx6 = session.beginTransaction();

            int deleted = session.createQuery(
                    "delete from Student where course = :course")
                    .setParameter("course", "BSc")
                    .executeUpdate();

            tx6.commit();
            session.clear();

            System.out.println("Task 8: BSc Students Deleted = "
                    + deleted);


            // TASK 9: Percentage Greater Than 70
            List<Student> l9 = session.createNativeQuery(
                    "SELECT * FROM student WHERE percentage > 70",
                    Student.class).list();

            System.out.println("Task 9: Percentage Greater Than 70");

            for (Student s : l9) {
                System.out.println(s);
            }


            // TASK 10: Percentage Descending
            List<Student> l10 = session.createNativeQuery(
                    "SELECT * FROM student ORDER BY percentage DESC",
                    Student.class).list();

            System.out.println("Task 10: Percentage Descending");

            for (Student s : l10) {
                System.out.println(s);
            }


            // TASK 11: Highest Percentage
            Object max = session.createNativeQuery(
                    "SELECT MAX(percentage) FROM student").uniqueResult();

            System.out.println("Task 11: Highest Percentage = " + max);


            // TASK 12: Total Percentage
            Object total = session.createNativeQuery(
                    "SELECT SUM(percentage) FROM student").uniqueResult();

            System.out.println("Task 12: Total Percentage = " + total);


            // TASK 13: Course Wise Count
            List<Object[]> l13 = session.createNativeQuery(
                    "SELECT course, COUNT(*) FROM student "
                    + "GROUP BY course").list();

            System.out.println("Task 13: Course Wise Count");

            for (Object[] row : l13) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 14: Percentage Between 40 and 80
            List<Student> l14 = session.createNativeQuery(
                    "SELECT * FROM student WHERE percentage "
                    + "BETWEEN 40 AND 80", Student.class).list();

            System.out.println("Task 14: Percentage Between 40 and 80");

            for (Student s : l14) {
                System.out.println(s);
            }


            // TASK 15: Names Starting With A
            List<Student> l15 = session.createQuery(
                    "from Student where studentName LIKE 'A%'",
                    Student.class).list();

            System.out.println("Task 15: Names Starting With A");

            for (Student s : l15) {
                System.out.println(s);
            }


            // TASK 16: Percentage Greater Than Average
            List<Student> l16 = session.createQuery(
                    "from Student where percentage > "
                    + "(select AVG(percentage) from Student)",
                    Student.class).list();

            System.out.println("Task 16: Above Average Percentage");

            for (Student s : l16) {
                System.out.println(s);
            }


            // TASK 17: Second Highest Percentage
            Object second = session.createQuery(
                    "select max(s.percentage) from Student s "
                    + "where s.percentage < "
                    + "(select max(s2.percentage) from Student s2)")
                    .uniqueResult();

            System.out.println("Task 17: Second Highest Percentage = "
                    + second);


            // TASK 18: Course IN
            List<Student> l18 = session.createQuery(
                    "from Student where course IN "
                    + "('MCA','BCA','BSc')",
                    Student.class).list();

            System.out.println("Task 18: Students By Course");

            for (Student s : l18) {
                System.out.println(s);
            }


            // TASK 19: Course Wise Average Percentage
            List<Object[]> l19 = session.createQuery(
                    "select course, AVG(percentage) "
                    + "from Student group by course").list();

            System.out.println("Task 19: Course Wise Average");

            for (Object[] row : l19) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 20: Increase MCA Percentage By 10%
            Transaction tx7 = session.beginTransaction();

            int count = session.createQuery(
                    "update Student set percentage = percentage * 1.10 "
                    + "where course = 'MCA'")
                    .executeUpdate();

            tx7.commit();

            System.out.println("Task 20: MCA Percentage Increased = "
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
