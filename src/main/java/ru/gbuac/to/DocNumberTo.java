package ru.gbuac.to;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;

@Data
@NoArgsConstructor
public class DocNumberTo {
    @SafeHtml
    private String regNum;

    public DocNumberTo(@SafeHtml String regNum) {
        this.regNum = regNum;
    }
}
