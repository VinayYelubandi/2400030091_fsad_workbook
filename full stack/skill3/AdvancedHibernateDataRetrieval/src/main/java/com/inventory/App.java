package com.inventory;

import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {

        // Build the SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // ============================================
            // 3. Insert multiple Product records
            // ============================================
            System.out.println("--- Creating new products ---");
            session = factory.getCurrentSession();
            session.beginTransaction();

            Product product1 = new Product("Laptop", "High end gaming laptop", 1200.00, 10);
            Product product2 = new Product("Mouse", "Wireless optical mouse", 25.50, 50);
            Product product3 = new Product("Keyboard", "Mechanical keyboard", 80.00, 30);
            Product product4 = new Product("Monitor", "27 inch 4K monitor", 300.00, 20);

            session.persist(product1);
            session.persist(product2);
            session.persist(product3);
            session.persist(product4);

            session.getTransaction().commit();
            System.out.println("Products successfully inserted!");

            // ============================================
            // 4. Retrieve a product using its id
            // ============================================
            // Assuming the laptop gets ID 1 (due to auto-increment)
            System.out.println("\n--- Retrieving a product by ID ---");
            session = factory.getCurrentSession();
            session.beginTransaction();

            // We retrieve the product with id 1
            int productIdToRetrieve = product1.getId();
            System.out.println("Retrieving product with id: " + productIdToRetrieve);
            Product retrievedProduct = session.get(Product.class, productIdToRetrieve);
            System.out.println("Retrieved Product: " + retrievedProduct);

            session.getTransaction().commit();


            // ============================================
            // 5. Update the price or quantity of a selected product
            // ============================================
            System.out.println("\n--- Updating a product's price and quantity ---");
            session = factory.getCurrentSession();
            session.beginTransaction();

            int productIdToUpdate = product2.getId();
            Product productToUpdate = session.get(Product.class, productIdToUpdate);
            if (productToUpdate != null) {
                System.out.println("Updating product: " + productToUpdate.getName());
                productToUpdate.setPrice(19.99); // discount the mouse
                productToUpdate.setQuantity(100); // bought more stock
            }

            session.getTransaction().commit();
            System.out.println("Product successfully updated!");

            // ============================================
            // 6. Delete a product record by id if discontinued
            // ============================================
            System.out.println("\n--- Deleting a discontinued product ---");
            session = factory.getCurrentSession();
            session.beginTransaction();

            // Let's assume product4 (Monitor) is discontinued
            int productIdToDelete = product4.getId();
            Product productToDelete = session.get(Product.class, productIdToDelete);
            if (productToDelete != null) {
                System.out.println("Discontinuing and deleting product: " + productToDelete.getName());
                session.remove(productToDelete);
            }

            session.getTransaction().commit();
            System.out.println("Product successfully deleted!");


            // ============================================
            // Retrieve all remaining products to verify
            // ============================================
            System.out.println("\n--- Retrieving all remaining products ---");
            session = factory.getCurrentSession();
            session.beginTransaction();

            java.util.List<Product> products = session.createQuery("from Product", Product.class).getResultList();
            for (Product p : products) {
                System.out.println(p);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            factory.close();
            System.out.println("Done!");
        }
    }
}
