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

    private Integer id;
    private Integer cardId;
    private String cardNumber;
    private Double amount;
    private String terminalCode;
    private TransactionType transactionType;
    private String phone;
    private LocalDateTime createdDate;

}
