package by.nti_team.test_work.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "planets_data")
public class Planet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lord_id", referencedColumnName = "id")
    private Lord lord;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Planet otherObj = (Planet) obj;
        return (this.id.equals(otherObj.id) || this.id.equals(otherObj.getId()))
                && (this.name.equals(otherObj.name) || this.name.equals(otherObj.getName())
                && this.lord.getId().equals(otherObj.lord.getId()) || this.lord.getId().equals(otherObj.getLord().getId()));
    }

    @Override
    public int hashCode() {
        int seed = 123;
        int result = seed  + ((id == null ? 0 : id.hashCode())
                + ((name == null) ? 0 : name.isEmpty() ? 0 : name.hashCode())
                + (lord == null ? 0 : lord.getId().hashCode()));
        result ^= result >>> 31;
        result ^= result << 15;
        result ^= result >> 7;

        return result;
    }

}
