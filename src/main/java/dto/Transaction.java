package dto;

import enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String card_number;
    private Double amount;
    private String terminal_code;
    private TransactionType type;
    private LocalDate created_date;
}
