package dto;

import enums.ProfileRole;
import enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProfileDto {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDateTime createdDate;
    private Status status;
    private ProfileRole profileRole;

}
