package dto;

import enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TransactionDto {
//    4. Transaction (card_number,amount,terminal_code,type,created_date)
private String cardNumber;
private double amount;
private String terminalId;
private TransactionType transactionType;
private LocalDateTime created_date;
}
