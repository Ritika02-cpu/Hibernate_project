
package com.tka.Employee;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(Employee.class);

		System.out.println("1. cfg ready...");

		SessionFactory sf = cfg.buildSessionFactory();

		System.out.println("2. factory ready...");

		Session session = sf.openSession();

		System.out.println("3. session ready...");

		try {

			// TASK 1: Insert Employee
			Transaction tx1 = session.beginTransaction();

			Employee e1 = new Employee("Amit", "IT", 60000);
			session.persist(e1);

			tx1.commit();
			System.out.println("Task 1: Employee Inserted");

			// TASK 2: Fetch All Employees
			List<Employee> list = session.createQuery("from Employee", Employee.class).list();

			System.out.println("Task 2: All Employees");

			for (Employee e : list) {
				System.out.println(e);
			}

			// TASK 3: Fetch Employee By ID
			Employee e2 = session.get(Employee.class, 1);

			System.out.println("Task 3: Employee By ID");

			System.out.println(e2);

			// TASK 4: Update Salary
			Transaction tx2 = session.beginTransaction();

			Employee e3 = session.get(Employee.class, 1);

			if (e3 != null) {
				e3.setSalary(75000);
				tx2.commit();
				System.out.println("Task 4: Salary Updated");
			} else {
				tx2.rollback();
				System.out.println("Employee Not Found");
			}

			// TASK 5: Delete Employee
			Transaction tx3 = session.beginTransaction();

			Employee e4 = session.get(Employee.class, 1);

			if (e4 != null) {
				session.remove(e4);
				tx3.commit();
				System.out.println("Task 5: Employee Deleted");
			} else {
				tx3.rollback();
				System.out.println("Employee Not Found");
			}

			// TASK 6: Insert 10 Employees
			Transaction tx4 = session.beginTransaction();

			try {

				session.persist(new Employee("Rahul", "HR", 40000));
				session.persist(new Employee("Sneha", "Finance", 55000));
				session.persist(new Employee("Priya", "IT", 70000));
				session.persist(new Employee("Akash", "HR", 35000));
				session.persist(new Employee("Rohit", "Finance", 80000));
				session.persist(new Employee("Neha", "IT", 65000));
				session.persist(new Employee("Kiran", "HR", 45000));
				session.persist(new Employee("Pooja", "Finance", 50000));
				session.persist(new Employee("Anil", "IT", 90000));
				session.persist(new Employee("Ajay", "IT", 60000));

				tx4.commit();

				System.out.println("Task 6: 10 Employees Inserted");

			} catch (Exception ex) {
				if (tx4.isActive()) {
					tx4.rollback();
				}
				throw ex;
			}

			// TASK 7: Update Department
			Transaction tx5 = session.beginTransaction();

			Employee e5 = session.get(Employee.class, 2);

			if (e5 != null) {
				e5.setDepartment("IT");
				tx5.commit();
				System.out.println("Task 7: Department Updated");
			} else {
				tx5.rollback();
				System.out.println("Employee Not Found");
			}

			// TASK 8: Delete HR Employees
			Transaction tx6 = session.beginTransaction();

			int deleted = session.createQuery("delete from Employee where department = :dept")
					.setParameter("dept", "HR").executeUpdate();

			tx6.commit();

			System.out.println("Task 8: HR Deleted = " + deleted);

			// TASK 9: Salary Greater Than 50000
			List<Employee> l9 = session.createNativeQuery("SELECT * FROM Employee WHERE salary > 50000", Employee.class)
					.list();

			System.out.println("Task 9: Salary Greater Than 50000");

			for (Employee e : l9) {
				System.out.println(e);
			}

			// TASK 10: Salary Descending
			List<Employee> l10 = session
					.createNativeQuery("SELECT * FROM Employee ORDER BY salary DESC", Employee.class).list();

			System.out.println("Task 10: Salary Descending");

			for (Employee e : l10) {
				System.out.println(e);
			}

			// TASK 11: Highest Salary
			Object max = session.createNativeQuery("SELECT MAX(salary) FROM Employee").uniqueResult();

			System.out.println("Task 11: Highest Salary = " + max);

			// TASK 12: Total Salary
			Object total = session.createNativeQuery("SELECT SUM(salary) FROM Employee").uniqueResult();

			System.out.println("Task 12: Total Salary = " + total);

			// TASK 13: Department Wise Count
			List<Object[]> l13 = session
					.createNativeQuery("SELECT department, COUNT(*) FROM Employee GROUP BY department").list();

			System.out.println("Task 13: Department Count");

			for (Object[] row : l13) {
				System.out.println(row[0] + " = " + row[1]);
			}

			// TASK 14: Salary Between 40000 and 80000
			List<Employee> l14 = session
					.createNativeQuery("SELECT * FROM Employee WHERE salary BETWEEN 40000 AND 80000", Employee.class)
					.list();

			System.out.println("Task 14: Salary Between");

			for (Employee e : l14) {
				System.out.println(e);
			}

			// TASK 15: Name Starts With A
			List<Employee> l15 = session.createQuery("from Employee where name LIKE 'A%'", Employee.class).list();

			System.out.println("Task 15: Names Starting With A");

			for (Employee e : l15) {
				System.out.println(e);
			}

			// TASK 16: Salary Greater Than Average
			List<Employee> l16 = session
					.createQuery("from Employee where salary > " + "(select AVG(salary) from Employee)", Employee.class)
					.list();

			System.out.println("Task 16: Above Average Salary");

			for (Employee e : l16) {
				System.out.println(e);
			}

			// TASK 17: Second Highest Salary
			Object second = session.createQuery("select max(e.salary) from Employee e " + "where e.salary < "
					+ "(select max(e2.salary) from Employee e2)").uniqueResult();

			System.out.println("Task 17: Second Highest Salary = " + second);

			// TASK 18: Department IN
			List<Employee> l18 = session
					.createQuery("from Employee where department IN ('IT','HR','Finance')", Employee.class).list();

			System.out.println("Task 18: IT HR Finance Employees");

			for (Employee e : l18) {
				System.out.println(e);
			}

			// TASK 19: Department Wise Average
			List<Object[]> l19 = session
					.createQuery("select department, AVG(salary) " + "from Employee group by department").list();

			System.out.println("Task 19: Department Average Salary");

			for (Object[] row : l19) {
				System.out.println(row[0] + " = " + row[1]);
			}

			// TASK 20: Increase IT Salary By 10%
			Transaction tx7 = session.beginTransaction();

			int count = session.createQuery("update Employee set salary = salary * 1.10 " + "where department = 'IT'")
					.executeUpdate();

			tx7.commit();

			System.out.println("Task 20: IT Salary Increased = " + count);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			session.close();
			sf.close();

			System.out.println("Hibernate Closed Successfully");
		}
	}
}
