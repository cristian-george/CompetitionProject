package database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "competitor", schema = "public", catalog = "competition")
public class CompetitorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "id_person", nullable = false)
    private int idPerson;
    @Basic
    @Column(name = "id_team", nullable = false)
    private int idTeam;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitorEntity that = (CompetitorEntity) o;

        if (id != that.id) return false;
        if (idPerson != that.idPerson) return false;
        if (idTeam != that.idTeam) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idPerson;
        result = 31 * result + idTeam;
        return result;
    }

    @Override
    public String toString() {
        return "CompetitorEntity{" +
                "id=" + id +
                ", idPerson=" + idPerson +
                ", idTeam=" + idTeam +
                '}';
    }
}
