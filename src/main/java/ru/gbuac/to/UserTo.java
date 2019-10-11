package ru.gbuac.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserTo extends BaseTo {
    private String fullName;
    private String phone;

    public UserTo(Integer id, String fullName, String phone) {
        super(id);
        this.fullName = fullName;
        this.phone = phone;
    }
}
