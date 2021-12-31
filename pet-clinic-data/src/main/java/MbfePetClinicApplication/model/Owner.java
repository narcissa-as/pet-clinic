package MbfePetClinicApplication.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();
    @Builder
    public Owner(Long id,String firstName, String lastName, String telephone, String address, String city, Set<Pet> pets) {
        super(id,firstName, lastName);
        this.telephone = telephone;
        this.address = address;
        this.city = city;
        this.pets = pets;
    }
}
