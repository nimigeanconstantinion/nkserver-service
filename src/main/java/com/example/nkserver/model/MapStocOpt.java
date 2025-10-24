package com.example.nkserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MapStocSrv")

public class MapStocOpt {
    @Id
    private int id;

    @Column(name="id_intern")
    private String idIntern;

    @Column(name = "articol")
    private String articol;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "grupa")
    private String grupa;

    @Column(name = "idFurn")
    private int id_furn;

    @Column(name = "furniz")
    private String furniz;

    @Column(name = "nrZile")
    private int nr_zile;

    @Override
    public String toString() {
        return "MapStocOpt{" +
                "id=" + id +
                ", idIntern='" + idIntern + '\'' +
                ", articol='" + articol + '\'' +
                ", categorie='" + categorie + '\'' +
                ", grupa='" + grupa + '\'' +
                ", id_furn=" + id_furn +
                ", furniz='" + furniz + '\'' +
                ", nr_zile=" + nr_zile +
                '}';
    }
}
