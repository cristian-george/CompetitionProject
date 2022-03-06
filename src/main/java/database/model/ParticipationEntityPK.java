package database.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class ParticipationEntityPK implements Serializable {
    @Column(name = "id_competitor", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompetitor;
    @Column(name = "id_stage", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStage;

    public int getIdCompetitor() {
        return idCompetitor;
    }

    public void setIdCompetitor(int idCompetitor) {
        this.idCompetitor = idCompetitor;
    }

    public int getIdStage() {
        return idStage;
    }

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipationEntityPK that = (ParticipationEntityPK) o;

        if (idCompetitor != that.idCompetitor) return false;
        if (idStage != that.idStage) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCompetitor;
        result = 31 * result + idStage;
        return result;
    }
}
