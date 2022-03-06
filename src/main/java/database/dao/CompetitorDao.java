package database.dao;

import database.DatabaseConnection;
import database.model.CompetitorEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompetitorDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull CompetitorEntity competitorEntity) {
        competitorEntity.setId(getLastId() + 1);
        connection.executeTransaction(entityManager -> entityManager.persist(competitorEntity));
    }

    //read
    public CompetitorEntity getById(int id) {
        return connection.getEntityManager().find(CompetitorEntity.class, id);
    }

    public CompetitorEntity getByIdPerson(int idPerson)  {
        return connection.getEntityManager().find(CompetitorEntity.class, idPerson);
    }

    public List<CompetitorEntity> getAll() {
        TypedQuery<CompetitorEntity> query = connection.getEntityManager().createQuery("SELECT c FROM CompetitorEntity c", CompetitorEntity.class);
        return query.getResultList();
    }

    public List<CompetitorEntity> getCompetitorsByIdTeam(int idTeam) {
        TypedQuery<CompetitorEntity> query = connection.getEntityManager().createQuery
                ("SELECT c FROM CompetitorEntity c WHERE c.idTeam = :idTeam", CompetitorEntity.class)
                .setParameter("idTeam", idTeam);

        return query.getResultList();
    }

    //update
    public void updatePersonId(int id, int idPerson) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE CompetitorEntity c SET c.idPerson = :idPerson WHERE c.id = :id", CompetitorEntity.class)
                .setParameter("id", id)
                .setParameter("idPerson", idPerson)
                .executeUpdate());
    }

    public void updateTeamId(int id, int idTeam) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("UPDATE CompetitorEntity c SET c.idTeam = :idTeam WHERE c.id = :id", CompetitorEntity.class)
                .setParameter("id", id)
                .setParameter("idTeam", idTeam)
                .executeUpdate());
    }

    //delete
    public void removeById(int id) {
        CompetitorEntity competitorEntity = getById(id);
        connection.executeTransaction(entityManager -> entityManager.remove(competitorEntity));
    }

    public void removeByIdPerson(int idPerson) {
        CompetitorEntity competitorEntity = getByIdPerson(idPerson);
        connection.executeTransaction(entityManager -> entityManager.remove(competitorEntity));
    }

    public int getLastId() {
        Query query = connection.getEntityManager().createQuery("SELECT MAX(c.id) FROM CompetitorEntity c", CompetitorEntity.class);
        if (query.getSingleResult() == null)
            return 0;

        return (int) query.getSingleResult();
    }
}
