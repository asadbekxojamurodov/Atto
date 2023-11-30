package dto;

import enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TerminalDto {
    private String code;
    private String address;
    private Status statusTerminal;
    private LocalDateTime createdDate;

}
