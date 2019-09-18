package ru.gbuac.to;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter

public class FileTo {
    @SafeHtml
    private
    String fileUrl;

    public FileTo(@SafeHtml String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
