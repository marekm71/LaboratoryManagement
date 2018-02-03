package pl.wat.wcy.prz.i5b4s1.database.model;

import javax.persistence.*;

@Entity
@Table(name="sprzet")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nazwa")
    private String name;
    @Column(name="numer_seryjny")
    private String serialNumber;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="laboratorium", referencedColumnName = "numer_sali_id")
    private Lab lab;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }
}
