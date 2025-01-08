package su.morevq.pkmn.models.card;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttackSkill implements Serializable {
    public static final long serialVersionUID = 1L;
    String name;
    String description;
    String cost;
    int damage;
}
