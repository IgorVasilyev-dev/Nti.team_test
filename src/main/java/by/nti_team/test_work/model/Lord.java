package by.nti_team.test_work.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "lords_data")
public class Lord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private Integer age;

    @JsonManagedReference
    @OneToMany(mappedBy = "lord", cascade = CascadeType.ALL)
    private Set<Planet> planets = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lord otherObj = (Lord) o;
        return (this.id.equals(otherObj.id) || this.id.equals(otherObj.getId()))
                && (this.name.equals(otherObj.name) || this.name.equals(otherObj.getName())
                && this.planets.equals(otherObj.planets) || this.planets.equals(otherObj.getPlanets()));
    }

    @Override
    public int hashCode() {
        int seed = 2341;
        int result = seed  + ((id == null ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.isEmpty() ? 0 : name.hashCode())
                + (age == null ? 0 : age.hashCode()) + (planets == null ? 0 : planets.hashCode()));
        result ^= result >>> 31;
        result ^= result << 15;
        result ^= result >> 7;

        return result;
    }

}
