package club.inq.team1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "mail")
@JsonIgnoreProperties(value = {"user","post"})
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id")
    private Long mailId;

    @Column(name = "saw", nullable = false)
    @ColumnDefault("false")
    private Boolean saw;
    /**
     * 수신자
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    todo
//    @ManyToOne
//    @JoinColumn(name = "post_id")
}
