package club.inq.team1.dto;

import club.inq.team1.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO {

    @NotNull
    private Long followId;

    @NotNull
    private User followerId;

    @NotNull
    private User followeeId;

    @NotNull
    private Boolean alarm;

}
