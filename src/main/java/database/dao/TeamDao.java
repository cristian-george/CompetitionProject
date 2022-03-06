package database.dao;

import database.DatabaseConnection;
import database.model.TeamEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeamDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull TeamEntity teamEntity) {
        teamEntity.setId(getLastId() + 1);
        connection.executeTransaction(entityManager -> entityManager.persist(teamEntity));
    }

    //read
    public TeamEntity getById(int id) {
        return connection.getEntityManager().find(TeamEntity.class, id);
    }

    public TeamEntity getByName(String name) {
        TypedQuery<TeamEntity> query = connection.getEntityManager().createQuery
                        ("SELECT t FROM TeamEntity t WHERE t.name = :name", TeamEntity.class).setParameter("name", name);

        return query.getSingleResult();
    }

    public List<TeamEntity> getAll() {
        TypedQuery<TeamEntity> query = connection.getEntityManager().createQuery("SELECT t FROM TeamEntity t", TeamEntity.class);
        return query.getResultList();
    }

    //update
    public void updateName(int id, String name) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE TeamEntity t SET t.name = :name WHERE t.id = :id", TeamEntity.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate());
    }

    //delete
    public void removeById(int id) {
        TeamEntity teamEntity = getById(id);
        connection.executeTransaction(entityManager -> entityManager.remove(teamEntity));
    }

    public int getLastId() {
        Query query = connection.getEntityManager().createQuery("SELECT MAX(t.id) FROM TeamEntity t", TeamEntity.class);
        if (query.getSingleResult() == null)
            return 0;

        return (int) query.getSingleResult();
    }
}
