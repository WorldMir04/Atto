package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String number;
    private String exp_date;
    private Double balance;
    private String status;
    private String phone;
    private LocalDate created_date;

}
