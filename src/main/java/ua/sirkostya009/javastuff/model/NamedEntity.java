package ua.sirkostya009.javastuff.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NamedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedEntity entity)) return false;
        return id.equals(entity.id) && name.equals(entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
