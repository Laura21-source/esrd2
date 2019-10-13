package ru.gbuac.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserTo extends BaseTo {
    @SafeHtml
    private String fullName;
    @SafeHtml
    private String phone;

    public UserTo(Integer id, @SafeHtml String fullName, @SafeHtml String phone) {
        super(id);
        this.fullName = fullName;
        this.phone = phone;
    }
}
