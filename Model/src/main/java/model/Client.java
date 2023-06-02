package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client implements Enity<String>{
    @Id
    @Column(name = "cnp", nullable = false)
    private String cnp;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "prenume", nullable = false)
    private String prenume;

    @Column(name = "gen", nullable = false)
    private String gen;

    public Client(String cnp, String nume, String prenume, String gen) {
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
        this.gen = gen;
    }

    public Client() {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String s) {
        this.cnp = cnp;
    }

    public String getCnp() {
        return cnp;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getGen() {
        return gen;
    }

    @Override
    public String toString() {
        return "Client{" +
                "cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", gen='" + gen + '\'' +
                '}';
    }
}
