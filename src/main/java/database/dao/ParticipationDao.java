package database.dao;

import database.DatabaseConnection;
import database.model.ParticipationEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParticipationDao {
    DatabaseConnection connection = new DatabaseConnection();

    //create
    public void add(@NotNull ParticipationEntity participationEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(participationEntity));
    }

    //read
    public ParticipationEntity getById(int idCompetitor, int idStage) {
        Query query = connection.getEntityManager().createQuery
                ("SELECT p FROM ParticipationEntity p WHERE p.idCompetitor = :idCompetitor AND p.idStage = :idStage", ParticipationEntity.class)
                .setParameter("idCompetitor", idCompetitor)
                .setParameter("idStage", idStage);

        return (ParticipationEntity) query.getSingleResult();
    }

    public List<ParticipationEntity> getAll() {
        TypedQuery<ParticipationEntity> query = connection.getEntityManager().createQuery("SELECT p FROM ParticipationEntity p", ParticipationEntity.class);
        return query.getResultList();
    }

    public List<ParticipationEntity> getByScore(int score) {
        TypedQuery<ParticipationEntity> query = connection.getEntityManager().createQuery
                ("SELECT p FROM ParticipationEntity p WHERE p.score = :score", ParticipationEntity.class)
                .setParameter("score", score);

        return query.getResultList();
    }

    //update
    public void updateScore(int idCompetitor, int idStage, int score) {
        connection.executeTransaction(entityManager -> entityManager.createQuery
                        ("UPDATE ParticipationEntity p SET p.score = :score WHERE p.idCompetitor = :idCompetitor AND p.idStage = :idStage")
                .setParameter("idCompetitor", idCompetitor)
                .setParameter("idStage", idStage)
                .setParameter("score", score)
                .executeUpdate());
    }

    //delete
    public void removeById(int idCompetitor, int idStage) {
        ParticipationEntity participationEntity = getById(idCompetitor, idStage);
        connection.executeTransaction(entityManager -> entityManager.remove(participationEntity));
    }
}
