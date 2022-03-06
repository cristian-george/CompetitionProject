package database.dao;

import database.DatabaseConnection;
import database.model.PersonEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PersonDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull PersonEntity personEntity) {
        personEntity.setId(getLastId() + 1);
        connection.executeTransaction(entityManager -> entityManager.persist(personEntity));
    }

    //read
    public PersonEntity getById(int id) {
        return connection.getEntityManager().find(PersonEntity.class, id);
    }

    public PersonEntity getByUsername(String username) {
        Query query = connection.getEntityManager().createQuery("SELECT p FROM PersonEntity p WHERE p.username = :username", PersonEntity.class)
                .setParameter("username", username);

        return (PersonEntity) query.getSingleResult();
    }

    public List<PersonEntity> getByFullname(String fullname) {
        TypedQuery<PersonEntity> query = connection.getEntityManager().createQuery("SELECT p FROM PersonEntity p WHERE p.fullname = :fullname", PersonEntity.class)
                .setParameter("fullname", fullname);

        return query.getResultList();
    }

    public List<PersonEntity> getByType(String type) {
        TypedQuery<PersonEntity> query = connection.getEntityManager().createQuery("SELECT p FROM PersonEntity p WHERE p.type = :type", PersonEntity.class)
                .setParameter("type", type);

        return query.getResultList();
    }

    public List<PersonEntity> getAll() {
        TypedQuery<PersonEntity> query = connection.getEntityManager().createQuery("SELECT p FROM PersonEntity p", PersonEntity.class);
        return query.getResultList();
    }

    //update
    public void updateUsername(int id, String username) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE PersonEntity p SET p.username = :username WHERE p.id = :id", PersonEntity.class)
                .setParameter("id", id)
                .setParameter("username", username)
                .executeUpdate());
    }

    public void updateFullname(int id, String fullname) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE PersonEntity p SET p.fullname = :fullname WHERE p.id = :id", PersonEntity.class)
                .setParameter("id", id)
                .setParameter("fullname", fullname)
                .executeUpdate());
    }
    public void updateType(int id, String type) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE PersonEntity p SET p.type = :type WHERE p.id = :id", PersonEntity.class)
                .setParameter("id", id)
                .setParameter("type", type)
                .executeUpdate());
    }

    //delete
    public void removeById(int id) {
        PersonEntity personEntity = getById(id);
        connection.executeTransaction(entityManager -> entityManager.remove(personEntity));
    }

    public void removeByUsername(String username) {
        PersonEntity personEntity = getByUsername(username);
        connection.executeTransaction(entityManager -> entityManager.remove(personEntity));
    }

    public int getLastId() {
        Query query = connection.getEntityManager().createQuery("SELECT MAX(p.id) FROM PersonEntity p", PersonEntity.class);
        if (query.getSingleResult() == null)
            return 0;

       return (int) query.getSingleResult();
    }
}
