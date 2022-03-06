package database;

import jakarta.persistence.*;
import java.util.function.Consumer;

public class DatabaseConnection {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public DatabaseConnection() {
        this.initializeTransaction();
    }

    public void initializeTransaction() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("CompetitionPersistence");
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            System.err.println("Error at initializing DatabaseManager " + e.getMessage());
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean executeTransaction(Consumer<EntityManager> action) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            action.accept(entityManager);
            entityTransaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Transaction error " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}