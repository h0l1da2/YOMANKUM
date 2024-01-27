package com.account.yomankum.user.domain;

import com.account.yomankum.user.domain.type.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "ROLES")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }
}
