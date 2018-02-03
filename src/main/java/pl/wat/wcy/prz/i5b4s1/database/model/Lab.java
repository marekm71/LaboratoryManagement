package pl.wat.wcy.prz.i5b4s1.database.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="laboratorium")
public class Lab {
    @Id
    @Column(name="numer_sali_id")
    private int numberId;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "lab")
    Set<Equipment> equipment;

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }
}
