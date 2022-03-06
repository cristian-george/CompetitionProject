package database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "participation", schema = "public", catalog = "competition")
@IdClass(ParticipationEntityPK.class)
public class ParticipationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_competitor", nullable = false)
    private int idCompetitor;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_stage", nullable = false)
    private int idStage;
    @Basic
    @Column(name = "score", nullable = true)
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipationEntity that = (ParticipationEntity) o;

        if (idCompetitor != that.idCompetitor) return false;
        if (idStage != that.idStage) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCompetitor;
        result = 31 * result + idStage;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParticipationEntity{" +
                "idCompetitor=" + idCompetitor +
                ", idStage=" + idStage +
                ", score=" + score +
                '}';
    }
}
