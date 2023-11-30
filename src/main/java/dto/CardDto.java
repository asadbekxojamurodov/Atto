package dto;

import enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CardDto {
    private String cardNumber;
    private LocalDate expireDate;
    private double balance;
    private Status status;
    private String phone;
    private LocalDateTime createdDate;

}


