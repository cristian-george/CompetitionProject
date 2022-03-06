package database.dao;

import database.DatabaseConnection;
import database.model.AdminEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull AdminEntity adminEntity) {
        adminEntity.setId(getLastId() + 1);
        connection.executeTransaction(entityManager -> entityManager.persist(adminEntity));
    }

    //read
    public AdminEntity getById(int id) {
        return connection.getEntityManager().find(AdminEntity.class, id);
    }
    public AdminEntity getByIdPerson(int idPerson) {
        return connection.getEntityManager().find(AdminEntity.class, idPerson);
    }

    public List<AdminEntity> getAll() {
        TypedQuery<AdminEntity> query = connection.getEntityManager().createQuery("SELECT a FROM AdminEntity a", AdminEntity.class);
        return query.getResultList();
    }

    //update
    public void updateIdPerson(int id, int idPerson) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE AdminEntity a SET a.idPerson = :idPerson WHERE a.id = :id", AdminEntity.class)
                .setParameter("id", id)
                .setParameter("idPerson", idPerson)
                .executeUpdate());
    }

    //delete
    public void removeById(int id) {
        AdminEntity adminEntity = getById(id);
        connection.executeTransaction(entityManager -> entityManager.remove(adminEntity));
    }

    public void removeByIdPerson(int idPerson) {
        AdminEntity adminEntity = getByIdPerson(idPerson);
        connection.executeTransaction(entityManager -> entityManager.remove(adminEntity));
    }

    public int getLastId() {
        Query query = connection.getEntityManager().createQuery("SELECT MAX(a.id) FROM AdminEntity a", AdminEntity.class);
        if (query.getSingleResult() == null)
            return 0;

        return (int) query.getSingleResult();
    }
}
