package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carte")
public class Carte implements Enity<Integer>{
    @Id
    @Column(name = "ISBN", nullable = false)
    private Integer ISBN;
    @Column(name = "titlu", nullable = false)
    private String titlu;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "tip", nullable = false)
    private String tip;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "client", nullable = false)
    private String client;


    public Carte(Integer ISBN, String titlu, String autor, String tip, String status, String client) {
        this.ISBN = ISBN;
        this.titlu = titlu;
        this.autor = autor;
        this.tip = tip;
        this.status = status;
        this.client = client;
    }

    public Carte() {

    }


    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public Integer getISBN() {
        return ISBN;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getAutor() {
        return autor;
    }

    public String getTip() {
        return tip;
    }

    public String getClient() {
        return client;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "ISBN=" + ISBN +
                ", titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", tip='" + tip + '\'' +
                ", status='" + status + '\'' +
                ", client='" + client + '\'' +
                '}';
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
