package club.inq.team1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "userId")
    private UserInfo userInfoId;

    @OneToMany(mappedBy = "followeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followerId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Follow> followings = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        });

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
