package database.dao;

import database.DatabaseConnection;
import database.model.PersonEntity;
import database.model.StageEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StageDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull StageEntity stageEntity) {
        stageEntity.setId(getLastId() + 1);
        connection.executeTransaction(entityManager -> entityManager.persist(stageEntity));
    }

    //read
    public StageEntity getById(int id) {
        return connection.getEntityManager().find(StageEntity.class, id);
    }

    public StageEntity getByName(String name)  {
        Query query = connection.getEntityManager().createQuery("SELECT s FROM StageEntity s WHERE s.name = :name", StageEntity.class)
                .setParameter("name", name);

        return (StageEntity) query.getSingleResult();
    }

    public List<StageEntity> getAll() {
        TypedQuery<StageEntity> query = connection.getEntityManager().createQuery("SELECT s FROM StageEntity s", StageEntity.class);
        return query.getResultList();
    }

    //update
    public void updateName(int id, String name) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE StageEntity s SET s.name = :name WHERE s.id = :id")
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate());
    }

    public void updateCompleted(int idStage, boolean isCompleted) {
        connection.executeTransaction(entityManager -> entityManager.createQuery
                ("UPDATE StageEntity s SET s.completed = :isCompleted WHERE s.id = :idStage")
                .setParameter("id", idStage)
                .setParameter("completed", isCompleted)
                .executeUpdate());
    }

    //delete
    public void removeById(int id) {
        StageEntity stageEntity = getById(id);
        connection.executeTransaction(entityManager -> entityManager.remove(stageEntity));
    }

    public void removeByName(String name) {
        StageEntity stageEntity = getByName(name);
        connection.executeTransaction(entityManager -> entityManager.remove(stageEntity));
    }

    public int getLastId() {
        Query query = connection.getEntityManager().createQuery("SELECT MAX(s.id) FROM StageEntity s", StageEntity.class);
        if (query.getSingleResult() == null)
            return 0;

        return (int) query.getSingleResult();
    }
}
