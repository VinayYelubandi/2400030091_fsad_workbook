package com.inventory;

import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class AdvancedHqlApp {

    public static void main(String[] args) {

        // Build the SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            // 2. Add 5-8 additional Product records into the database.
            System.out.println("--- Task 2: Adding 5-8 additional Product records ---");
            Product p1 = new Product("Headphones", "Noise cancelling headphones", 150.00, 45);
            Product p2 = new Product("Smartphone", "Latest model smartphone", 999.99, 15);
            Product p3 = new Product("Tablet", "10-inch screen tablet", 450.00, 25);
            Product p4 = new Product("Smartwatch", "Fitness tracking smartwatch", 199.50, 60);
            Product p5 = new Product("Webcam", "1080p HD webcam", 55.00, 100);
            Product p6 = new Product("Microphone", "USB condenser microphone", 85.00, 40);
            Product p7 = new Product("External HDD", "2TB portable hard drive", 65.00, 80);
            Product p8 = new Product("Flash Drive", "64GB USB 3.0 flash drive", 15.00, 200);

            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(p4);
            session.persist(p5);
            session.persist(p6);
            session.persist(p7);
            session.persist(p8);
            System.out.println("Additional products added.");


            // 3. Write HQL queries to retrieve all products sorted by price:
            System.out.println("\n--- Task 3(a): Retrieve all products sorted by price (Ascending) ---");
            List<Product> ascProducts = session.createQuery("from Product p order by p.price asc", Product.class).getResultList();
            for (Product p : ascProducts) {
                System.out.println(p.getName() + " - $" + p.price);
            }

            System.out.println("\n--- Task 3(b): Retrieve all products sorted by price (Descending) ---");
            List<Product> descProducts = session.createQuery("from Product p order by p.price desc", Product.class).getResultList();
            for (Product p : descProducts) {
                System.out.println(p.getName() + " - $" + p.price);
            }

            // 4. Write an HQL query to sort products by quantity (highest first).
            System.out.println("\n--- Task 4: Sort products by quantity (Highest first) ---");
            List<Product> qtyProducts = session.createQuery("from Product p order by p.quantity desc", Product.class).getResultList();
            for (Product p : qtyProducts) {
                System.out.println(p.getName() + " - Qty: " + p.quantity);
            }

            // 5. Implement pagination using HQL to display:
            System.out.println("\n--- Task 5(a): Pagination - First 3 products ---");
            Query<Product> page1Query = session.createQuery("from Product", Product.class);
            page1Query.setFirstResult(0);
            page1Query.setMaxResults(3);
            List<Product> page1Products = page1Query.getResultList();
            for (Product p : page1Products) {
                System.out.println(p);
            }

            System.out.println("\n--- Task 5(b): Pagination - Next 3 products ---");
            Query<Product> page2Query = session.createQuery("from Product", Product.class);
            page2Query.setFirstResult(3);
            page2Query.setMaxResults(3);
            List<Product> page2Products = page2Query.getResultList();
            for (Product p : page2Products) {
                System.out.println(p);
            }

            // 6. Write HQL queries for aggregate operations:
            System.out.println("\n--- Task 6: Aggregate Operations ---");
            
            // a. Count total number of products
            Long totalProducts = session.createQuery("select count(p) from Product p", Long.class).uniqueResult();
            System.out.println("Total number of products: " + totalProducts);

            // b. Count products where quantity > 50 (wait, prompt says > 0, let's do > 0)
            Long qtyGreaterThanZero = session.createQuery("select count(p) from Product p where p.quantity > 0", Long.class).uniqueResult();
            System.out.println("Products with quantity > 0: " + qtyGreaterThanZero);

            // c. Count products grouped by description
            System.out.println("Count products grouped by description:");
            List<Object[]> groupCounts = session.createQuery("select p.description, count(p) from Product p group by p.description", Object[].class).getResultList();
            for (Object[] row : groupCounts) {
                System.out.println("Description: '" + row[0] + "' -> Count: " + row[1]);
            }

            // d. Find minimum and maximum price
            Object[] minMaxPrice = session.createQuery("select min(p.price), max(p.price) from Product p", Object[].class).uniqueResult();
            System.out.println("Minimum Price: $" + minMaxPrice[0] + ", Maximum Price: $" + minMaxPrice[1]);

            // 7. Write an HQL query using GROUP BY to group products by description.
            System.out.println("\n--- Task 7: GROUP BY - Group products by description ---");
            List<Object[]> groupByDesc = session.createQuery("select p.description, sum(p.quantity) from Product p group by p.description", Object[].class).getResultList();
            for (Object[] row : groupByDesc) {
                System.out.println("Description: '" + row[0] + "' -> Total Quantity: " + row[1]);
            }

            // 8. Write an HQL query using WHERE to filter products within a price range.
            System.out.println("\n--- Task 8: WHERE - Filter products within a price range (e.g., $50 to $200) ---");
            List<Product> priceRangeProducts = session.createQuery("from Product p where p.price between 50 and 200", Product.class).getResultList();
            for (Product p : priceRangeProducts) {
                System.out.println(p.getName() + " - $" + p.price);
            }

            // 9. Write HQL queries using LIKE such as:
            System.out.println("\n--- Task 9: LIKE Queries ---");
            
            // a. Names starting with certain letters
            System.out.println("a. Names starting with 'S':");
            List<Product> startS = session.createQuery("from Product p where p.name like 'S%'", Product.class).getResultList();
            for (Product p : startS) {
                System.out.println(p.getName());
            }

            // b. Names ending with certain letters
            System.out.println("b. Names ending with 'e':");
            List<Product> endsWithE = session.createQuery("from Product p where p.name like '%e'", Product.class).getResultList();
            for (Product p : endsWithE) {
                System.out.println(p.getName());
            }

            // c. Names containing a pattern anywhere (substring)
            System.out.println("c. Names containing 'book' anywhere:");
            List<Product> containsMac = session.createQuery("from Product p where p.name like '%book%'", Product.class).getResultList();
            for (Product p : containsMac) {
                System.out.println(p.getName());
            }

            // d. Names with an exact character length (e.g., exactly 6 characters using 6 underscores)
            System.out.println("d. Names with exactly 6 characters (using '______'):");
            List<Product> exactLength = session.createQuery("from Product p where p.name like '______'", Product.class).getResultList();
            for (Product p : exactLength) {
                System.out.println(p.getName());
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            factory.close();
            System.out.println("\nDone!");
        }
    }
}
