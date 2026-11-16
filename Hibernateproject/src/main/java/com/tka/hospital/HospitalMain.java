
package com.tka.hospital;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HospitalMain {

    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Patient.class);

        System.out.println("1. cfg ready...");

        SessionFactory sf = cfg.buildSessionFactory();

        System.out.println("2. factory ready...");

        Session session = sf.openSession();

        System.out.println("3. session ready...");

        try {

            // TASK 1: Insert Patient
            Transaction tx1 = session.beginTransaction();

            Patient p1 = new Patient(101, "Amit", 25,
                    "Male", "Fever", "Dr. Amit", 500);

            session.persist(p1);
            tx1.commit();

            System.out.println("Task 1: Patient Inserted");


            // TASK 2: Fetch All Patients
            List<Patient> list = session.createQuery(
                    "from Patient", Patient.class).list();

            System.out.println("Task 2: All Patients");

            for (Patient p : list) {
                System.out.println(p);
            }


            // TASK 3: Fetch Patient By ID
            Patient p2 = session.get(Patient.class, 101);

            System.out.println("Task 3: Patient By ID");
            System.out.println(p2);


            // TASK 4: Update Patient Fees
            Transaction tx2 = session.beginTransaction();

            Patient p3 = session.get(Patient.class, 101);

            if (p3 != null) {
                p3.setFees(750);
                tx2.commit();
                System.out.println("Task 4: Patient Fees Updated");
            } else {
                tx2.rollback();
                System.out.println("Patient Not Found");
            }


            // TASK 5: Delete Patient
            Transaction tx3 = session.beginTransaction();

            Patient p4 = session.get(Patient.class, 101);

            if (p4 != null) {
                session.remove(p4);
                tx3.commit();
                System.out.println("Task 5: Patient Deleted");
            } else {
                tx3.rollback();
                System.out.println("Patient Not Found");
            }


            // TASK 6: Insert 10 Patients
            Transaction tx4 = session.beginTransaction();

            try {

                session.persist(new Patient(102, "Rahul", 30,
                        "Male", "Fever", "Dr. Amit", 400));

                session.persist(new Patient(103, "Sneha", 28,
                        "Female", "Cold", "Dr. Neha", 550));

                session.persist(new Patient(104, "Priya", 35,
                        "Female", "Diabetes", "Dr. Amit", 700));

                session.persist(new Patient(105, "Akash", 40,
                        "Male", "Fever", "Dr. Raj", 350));

                session.persist(new Patient(106, "Rohit", 32,
                        "Male", "Headache", "Dr. Neha", 800));

                session.persist(new Patient(107, "Neha", 26,
                        "Female", "Cold", "Dr. Amit", 650));

                session.persist(new Patient(108, "Kiran", 45,
                        "Male", "Diabetes", "Dr. Raj", 450));

                session.persist(new Patient(109, "Pooja", 29,
                        "Female", "Fever", "Dr. Neha", 500));

                session.persist(new Patient(110, "Anil", 50,
                        "Male", "Heart Problem", "Dr. Amit", 900));

                session.persist(new Patient(111, "Ajay", 38,
                        "Male", "Cold", "Dr. Raj", 600));

                tx4.commit();

                System.out.println("Task 6: 10 Patients Inserted");

            } catch (Exception ex) {

                if (tx4.isActive()) {
                    tx4.rollback();
                }

                throw ex;
            }


            // TASK 7: Update Doctor Name Using session.update()
            Transaction tx5 = session.beginTransaction();

            session.clear();

            Patient p5 = new Patient(102, "Rahul", 30,
                    "Male", "Fever", "Dr. New Amit", 400);

            session.update(p5);

            tx5.commit();

            System.out.println("Task 7: Doctor Name Updated");


            // TASK 8: Delete All Fever Patients
            Transaction tx6 = session.beginTransaction();

            int deleted = session.createQuery(
                    "delete from Patient where disease = :disease")
                    .setParameter("disease", "Fever")
                    .executeUpdate();

            tx6.commit();
            session.clear();

            System.out.println("Task 8: Fever Patients Deleted = " + deleted);


            // TASK 9: Fees Greater Than 500
            List<Patient> l9 = session.createNativeQuery(
                    "SELECT * FROM patient WHERE patient_fees > 500",
                    Patient.class).list();

            System.out.println("Task 9: Fees Greater Than 500");

            for (Patient p : l9) {
                System.out.println(p);
            }


            // TASK 10: Fees Descending
            List<Patient> l10 = session.createNativeQuery(
                    "SELECT * FROM patient ORDER BY patient_fees DESC",
                    Patient.class).list();

            System.out.println("Task 10: Fees Descending");

            for (Patient p : l10) {
                System.out.println(p);
            }


            // TASK 11: Highest Fees
            Object max = session.createNativeQuery(
                    "SELECT MAX(patient_fees) FROM patient")
                    .uniqueResult();

            System.out.println("Task 11: Highest Fees = " + max);


            // TASK 12: Total Fees
            Object total = session.createNativeQuery(
                    "SELECT SUM(patient_fees) FROM patient")
                    .uniqueResult();

            System.out.println("Task 12: Total Fees = " + total);


            // TASK 13: Doctor Wise Patient Count
            List<Object[]> l13 = session.createNativeQuery(
                    "SELECT doctor_name, COUNT(*) FROM patient GROUP BY doctor_name")
                    .list();

            System.out.println("Task 13: Doctor Wise Patient Count");

            for (Object[] row : l13) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 14: Fees Between 400 and 800
            List<Patient> l14 = session.createNativeQuery(
                    "SELECT * FROM patient WHERE patient_fees BETWEEN 400 AND 800",
                    Patient.class).list();

            System.out.println("Task 14: Fees Between 400 and 800");

            for (Patient p : l14) {
                System.out.println(p);
            }


            // TASK 15: Patient Name Starts With A
            List<Patient> l15 = session.createQuery(
                    "from Patient where patientName LIKE 'A%'",
                    Patient.class).list();

            System.out.println("Task 15: Names Starting With A");

            for (Patient p : l15) {
                System.out.println(p);
            }


            // TASK 16: Fees Greater Than Average Fees
            List<Patient> l16 = session.createQuery(
                    "from Patient where fees > (select AVG(fees) from Patient)",
                    Patient.class).list();

            System.out.println("Task 16: Above Average Fees");

            for (Patient p : l16) {
                System.out.println(p);
            }


            // TASK 17: Second Highest Fees
            Object second = session.createQuery(
                    "select max(p.fees) from Patient p " +
                    "where p.fees < (select max(p2.fees) from Patient p2)")
                    .uniqueResult();

            System.out.println("Task 17: Second Highest Fees = " + second);


            // TASK 18: Doctor Name IN
            List<Patient> l18 = session.createQuery(
                    "from Patient where doctorName IN " +
                    "('Dr. Amit', 'Dr. Neha', 'Dr. Raj')",
                    Patient.class).list();

            System.out.println("Task 18: Patients By Doctors");

            for (Patient p : l18) {
                System.out.println(p);
            }


            // TASK 19: Doctor Wise Average Fees
            List<Object[]> l19 = session.createQuery(
                    "select doctorName, AVG(fees) " +
                    "from Patient group by doctorName").list();

            System.out.println("Task 19: Doctor Wise Average Fees");

            for (Object[] row : l19) {
                System.out.println(row[0] + " = " + row[1]);
            }


            // TASK 20: Increase Dr. Amit Fees By 10%
            Transaction tx7 = session.beginTransaction();

            int count = session.createQuery(
                    "update Patient set fees = fees * 1.10 " +
                    "where doctorName = 'Dr. Amit'")
                    .executeUpdate();

            tx7.commit();
            session.clear();

            System.out.println("Task 20: Fees Increased = " + count);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            session.close();
            sf.close();

            System.out.println("Hibernate Closed Successfully");
        }
    }
}
