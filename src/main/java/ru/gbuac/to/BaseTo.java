package ru.gbuac.to;

import lombok.*;
import ru.gbuac.HasId;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter

public abstract class BaseTo implements HasId {

    protected Integer id;


    public BaseTo(Integer id) {
        this.id = id;
    }
}
