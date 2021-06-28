package myHibernetproject;

import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FirstClass {

	// Declaring a variable for factory globally
	private static SessionFactory factory;
	static Scanner sc = new Scanner(System.in);

	// Main method
	public static void main(String[] args) {

		// Making the configuration with the configuration file
		try {
			// Getting table details from the student class
			factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
					.buildSessionFactory();

			// Opening session factory for student class
			Session session = factory.getCurrentSession();

			Scanner sc = new Scanner(System.in);
			int validateUser = 0;

			// Type of the user going to access the database table
			do {
				System.out.println();
				System.out.println();
				System.out.println("+----------------------------+");
				System.out.println("|  TechBank Welcome's you !  |");
				System.out.println("+----------------------------+");
				System.out.println();
				System.out.println(" For Admin Login    => 1");
				System.out.println(" For Customer Login => 2");
				System.out.println(" Quit               => 3");
				System.out.println();
				System.out.println(
						"=================================================================================================================================");
				System.out.println();
				System.out.println();

				System.out.print(" Please enter an option here : ");
				validateUser = sc.nextInt();
				System.out.println();
				if (validateUser == 1 || validateUser == 2) {

					// Validating the admin user here and doing the process whatever he needs
					if (validateUser == 1) {
						adminpasswordConfirm();
					}
					// Verifying customer with their accountnumber and password
					else if (validateUser == 2) {
						customervalidation();
					}

				} else {
					System.out.println();
				}
			} while (validateUser != 3);

			// closing session here
			session.close();
			System.out.println();
			System.out.println("+--------------------------------------+");
			System.out.println("|  ThankYou For Visiting our Bank !!!  |");
			System.out.println("+--------------------------------------+");
		} catch (Throwable e) {
			System.err.println("Failed to create sessionFactory object." + e);
			throw new ExceptionInInitializerError(e);
		} finally {

			// Closing the sessionfactory here
			factory.close();
		}
	}

	// Method for validating the admin user and making further processes like create
	// a customer, update, etc.
	private static void customervalidation() {

		Session session = factory.openSession();
		Transaction transaction = null;
		Integer adminid = null;
		int customeroption = 0;
		System.out.println();
		System.out.println();
		System.out.println("+---------------------+");
		System.out.println("| Verify to Continue  |");
		System.out.println("+---------------------+");

		System.out.println();
		System.out.println();
		System.out.print("Enter your AccountNumber : ");
		int Accountnumber = sc.nextInt();
		System.out.println();
		System.out.print("Enter your PIN : ");
		int Password = sc.nextInt();

		transaction = session.beginTransaction();
		Session session1 = factory.openSession();
		Transaction tx = null;

		try {
			Query query1 = session.createQuery("from Student where accountnumber = :fn").setParameter("fn",
					Accountnumber);
			java.util.List<Student> collection1 = query1.getResultList();
			System.out.println();
			System.out.println();
			do {
				for (Student object : collection1) {
					if ((object.getAccountnumber() == Accountnumber) && (object.getPassword() == Password)
							&& (object.getPassword() != 0)) {

						System.out.println("+----------------------------+");
						System.out.println("|  Verified Successfully  !! |");
						System.out.println("+----------------------------+");
						System.out.println();

						System.out.println();
						int num = object.getAccountnumber();
						int oldbalance = object.getBalance();
						System.out.println("Credit Amount    => 1");
						System.out.println("Withdraw Amount  => 2");
						System.out.println("View Balance     => 3");
						System.out.println("Quit             => 4");
						System.out.println();
						System.out.print("Please Enter your choice  : ");
						customeroption = sc.nextInt();
						System.out.println();

						// For crediting amount
						if (customeroption == 1) {
							SessionFactory factory2 = new Configuration().configure("hibernate.cfg.xml")
									.addAnnotatedClass(Customer.class).buildSessionFactory();
							Session session11 = factory2.getCurrentSession();
							Transaction tx11 = null;
							try {
								tx11 = session11.beginTransaction();
								Customer cust = new Customer();

								System.err.println();
								System.out.println();
								System.out.print("Enter your amount to credit : ");
								int creditamt = sc.nextInt();
								int balance = creditamt + oldbalance;
								cust.setCreditedamount(creditamt);
								cust.setBalance(balance);
								System.out.println(cust.getBalance());
								cust.setAccountnumber(num);
								object.setBalance(balance);
								session11.save(cust);
								session11.update(cust);
								session.update(object);
								tx11.commit();
							} catch (HibernateException e) {
								if (tx11 != null)
									tx11.rollback();
								e.printStackTrace();
							} finally {
								session11.close();
							}
						}
						// For withdraw amount
						else if (customeroption == 2) {
							SessionFactory factory2 = new Configuration().configure("hibernate.cfg.xml")
									.addAnnotatedClass(Customer.class).buildSessionFactory();
							Session session11 = factory2.getCurrentSession();
							Transaction tx11 = null;
							try {
								tx11 = session11.beginTransaction();
								Customer cust = new Customer();
								System.out.println();
								System.out.println();
								System.out.println("Enter your amount to withdraw : ");
								int withdrawamt = sc.nextInt();
								int balance = oldbalance - withdrawamt;
								if (balance <= 100) {
									Session session13 = factory.openSession();
									Transaction tx13 = null;
									try {
										tx13 = session13.beginTransaction();
										System.out.println("");
										System.out.println("");
										System.out.println("");
										System.out.println("Due to Low balance your account is removed from our Bank");
										System.out.println();
										System.out.println("You may deal your queries in your respective Brance");
										session13.delete(object);
										tx13.commit();
									} catch (HibernateException e) {
										if (tx11 != null)
											tx11.rollback();
										e.printStackTrace();
									} finally {
										session13.close();
									}

								} else {
									cust.setDebitedAmount(withdrawamt);
									cust.setBalance(balance);
									System.out.println("Available Balance : " + cust.getBalance());
									cust.setAccountnumber(num);
									object.setBalance(balance);
									session11.save(cust);
									session.update(object);
									tx11.commit();
								}
							} catch (HibernateException e) {
								if (tx11 != null)
									tx11.rollback();
								e.printStackTrace();
							} finally {
								session11.close();
							}
						} else if (customeroption == 3) {

							System.out.println();
							System.out.println();

							Session session13 = factory.openSession();
							Transaction tx13 = null;
							try {
								tx13 = session13.beginTransaction();
								Student cust = new Student();
								System.out.println("");
								System.out.println("");
								cust.setAccountnumber(num);
								System.out.println("Your Accountnumber     : " + cust.getAccountnumber());
								System.out.println("Your Available Balance : " + object.getCustomerName());
								System.out.println("Your Available Balance : " + object.getBalance());
								System.out.println();
								tx13.commit();
							} catch (HibernateException e) {
								if (tx13 != null)
									tx13.rollback();
								e.printStackTrace();
							} finally {
								session13.close();
							}
						} else if (customeroption == 4) {
						} else {
							System.out.println("Enter a valid option  !");
						}
					} else {
						System.out.println("Please check userid or password that you have entered!!");
						System.out.println();
						System.out.println();
						System.out.println("To Generate a new PIN  => 1");
						System.out.println("To Change existing PIN => 2");
						System.out.println();
						int userchoice = 0;
						System.out.println("Enter your choice : ");
						userchoice = sc.nextInt();
						if (userchoice == 1) {
							createCustomerPassword();
						}

						else if (userchoice == 2) {
							updateCustomerPassword();
						}
					}
				}
				session.getTransaction().commit();
			} while (customeroption != 4);
		}

		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session1.close();
		}

	}

	private static void updateCustomerPassword() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student syd = new Student();
			System.out.print("Enter your AccountNumber : ");
			int str = sc.nextInt();
			System.out.println();
			System.out.print("Enter your new password  : ");
			int strname = sc.nextInt();
			Student custom = (Student) session.get(Student.class, str);
			custom.setPassword(strname);

			session.save(custom);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	private static void createCustomerPassword() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student syd = new Student();
			System.out.print("Please Enter your AccountNumber  : ");
			int str = sc.nextInt();
			System.out.println();
			System.out.print("Enter your new password          : ");
			int strname = sc.nextInt();
			Student custom = (Student) session.get(Student.class, str);
			custom.setPassword(strname);
			session.update(custom);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	// Method for validating the admin user and making further processes like create
	// a customer, update, etc.
	public static void adminpasswordConfirm() {

		// Opening a new Session Factory for Fetching admin details
		SessionFactory factory1 = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class)
				.buildSessionFactory();

		// opening new session
		Session session = factory1.getCurrentSession();
		Transaction transaction = null;
		Integer adminid = null;
		int adminoption = 0;
		System.out.println();

		System.out.println();
		System.out.println("+----------------------------+");
		System.out.println("|  Please Login to Continue  |");
		System.out.println("+----------------------------+");

		System.out.println();
		System.out.println();
		System.out.print("Enter your UserId   : ");
		String userid = sc.next();
		System.out.print("Enter your password : ");
		String adminPassword = sc.next();

		transaction = session.beginTransaction();
		Session session1 = factory1.openSession();
		Transaction tx = null;

		try {
			session = factory1.openSession();
			session.beginTransaction();
			Query query1 = session.createQuery("from Admin where AdminUserId = :fn").setParameter("fn", userid);

			java.util.List<Admin> collection1 = query1.getResultList();
			System.out.println();
			System.out.println();

			// Checking the admin details for verification
			for (Admin object : collection1) {
				do {
					if ((object.getAdminuserid().equals(userid)) && (object.getAdminPassword().equals(adminPassword))) {
						System.out.println("+------------------------------------+");
						System.out.println("|  You are Successfully Logged In !! |");
						System.out.println("+------------------------------------+");
						System.out.println();

						System.out.println();
						System.out.println("Add a new Customer         => 1");
						System.out.println("Update Customer details    => 2");
						System.out.println("Remove a Customer          => 3");
						System.out.println("View all Customers details => 4");
						System.out.println("Quit                       => 5");
						System.out.println();
						System.out.print("Please Enter your choice    : ");
						adminoption = sc.nextInt();
						System.out.println();

						if (adminoption == 1) {
							// Method for creating new customer
							newcustomer();
						} else if (adminoption == 2) {
							// Method for updating a customer's details
							updatecustomer();
						} else if (adminoption == 3) {
							// Method for deleting customer
							deletecustomer();
						} else if (adminoption == 4) {
							// Method for viewing all customers
							viewAllCustomers();
						} else if (adminoption == 5) {
							// to exit
						} else {
							System.out.println("Enter a valid option  !");
						}
					} else {
						System.out.println("Please check userid or password that you have entered!!");
					}
				} while (adminoption != 5);
			}

			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			// closing the session
			session1.close();
			factory.close();
		}

	}

	// Method for viewing all Customers
	private static void viewAllCustomers() {

		// opening session
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student syd = new Student();

			// Declaring Query here
			Query query11 = session.createQuery("from Student ");

			java.util.List<Student> collection11 = query11.getResultList();
			System.out.println();
			System.out.println();

			// Fetching all customers and displaying here
			for (Student object1 : collection11) {
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.print("AccountNumber : " + object1.getAccountnumber() + " | ");
				System.out.print("CustomerName : " + object1.getCustomerName() + " | ");
				System.out.print("FatherName : " + object1.getFathername() + " | ");
				System.out.print("Address : " + object1.getAddress() + " | ");
				System.out.print("City : " + object1.getCity() + " | ");
				System.out.println("PhoneNumber : " + object1.getPhoneNumber() + " | ");
				System.out.println("Balance : " + object1.getBalance() + " | ");

			}
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			// Closing the session
			session.close();
		}

	}

	// method for Creating new customer
	public static void newcustomer() {
		Session session = factory.openSession();

		try {
			FirstClass obj = new FirstClass();
			int str = obj.generateMyNumber();
			session.beginTransaction();

			Student s2 = new Student();

			System.out.println();
			System.out.print("Name             : ");
			String CustomerName = sc.next();
			s2.setCustomerName(CustomerName);
			System.out.print("Father's name    : ");
			String Fathername = sc.next();
			s2.setFathername(Fathername);
			System.out.print("Age              : ");
			int Age = sc.nextInt();
			s2.setAge(Age);
			System.out.println("Gender=> Male     ");
			System.out.println("      => Female    ");
			System.out.print("      => Other   : ");
			String Gender = sc.next();
			s2.setGender(Gender);
			System.out.print("MaritalStatus    : ");
			String MaritalStatus = sc.next();
			s2.setMaritalStatus(MaritalStatus);
			System.out.print("Address          : ");
			String Address = sc.next();
			s2.setAddress(Address);
			System.out.print("city             : ");
			String city = sc.next();
			s2.setCity(city);
			System.out.print("State            : ");
			String State = sc.next();
			s2.setState(State);
			System.out.print("Country          : ");
			String Country = sc.next();
			s2.setCountry(Country);
			System.out.print("pincode          : ");
			String pincode = sc.next();
			s2.setPincode(pincode);
			System.out.print("phoneNumber      : ");
			String phoneNumber = sc.next();
			s2.setPhoneNumber(phoneNumber);
			System.out.print("Email            : ");
			String Email = sc.next();
			s2.setEmail(Email);
			System.out.print("AccountType      : ");
			String AccountType = sc.next();
			s2.setAccountType(AccountType);
			System.out.println();
			System.out.println();
			System.out.println("Successfully created a new customer");
//saving the customer details in database table
			session.save(s2);
			session.getTransaction().commit();
			session.close();

		} finally {
			session.close();
		}

	}

	// Method for updating a particular customer
	public static void updatecustomer() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student syd = new Student();
			System.out.print("Enter the AccountNumber of the Customer that you want to update :");
			int str = sc.nextInt();
			int adminoption2 = 0;
			System.out.println();
			System.out.print("Enter a field number that you want to update to that Customer:");
			System.out.println();
			System.out.println("For update customer Name        => 1");
			System.out.println("For update customer Email       => 2");
			System.out.println("For update customer Phonenumber => 3");
			System.out.println("For update customer Address     => 4");
			System.out.println("For update customer City        => 5");
			System.out.println("For update customer Pincode     => 6");
			System.out.println("For update customer State       => 7");
			System.out.println();
			System.out.print("Enter your Choice :");
			adminoption2 = sc.nextInt();

			if (adminoption2 == 1) {
				System.out.println();
				System.out.print("Enter Name : ");
				String strname = sc.next();
				Student custom = (Student) session.get(Student.class, str);
				custom.setCustomerName(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 2) {
				System.out.println();
				System.out.print("Enter Email : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setEmail(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 3) {
				System.out.println();
				System.out.print("Enter Phonenumber : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setPhoneNumber(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 4) {
				System.out.println();
				System.out.print("Enter Address : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setAddress(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 5) {
				System.out.println();
				System.out.print("Enter City : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setCity(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 6) {
				System.out.println();
				System.out.print("Enter Pincode : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setPincode(strname);
				session.update(custom);
				tx.commit();
			} else if (adminoption2 == 7) {
				System.out.println();
				System.out.print("Enter State : ");
				Student custom = (Student) session.get(Student.class, str);
				String strname = sc.next();
				custom.setState(strname);
				session.update(custom);
				tx.commit();
			} else {
				System.out.println("Please Enter a valid option !!!");
			}

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

	}

	// Method for generating a random number for accountnumber
	public static int generateMyNumber() {
		int aNumber = 0;
		aNumber = (int) ((Math.random() * 9000000000l) + 1000000000);
		return aNumber;
	}

	// Method for delete a particular customer
	public static void deletecustomer() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student syd = new Student();

			System.out.print("Enter the CustomerId of the Customer that you want to Delete :");
			int str = sc.nextInt();
			System.out.println();

			Student custom = (Student) session.get(Student.class, str);

			session.delete(custom);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

	}

}
