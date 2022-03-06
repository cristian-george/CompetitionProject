package database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin", schema = "public", catalog = "competition")
public class AdminEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "id_person", nullable = false)
    private int idPerson;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminEntity that = (AdminEntity) o;

        if (id != that.id) return false;
        if (idPerson != that.idPerson) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idPerson;
        return result;
    }

    @Override
    public String toString() {
        return "AdminEntity{" +
                "id=" + id +
                ", idPerson=" + idPerson +
                '}';
    }
}
