package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Terminal {
    private String code;
    private String adress;
    private String status;
    private LocalDate created_date;
}
