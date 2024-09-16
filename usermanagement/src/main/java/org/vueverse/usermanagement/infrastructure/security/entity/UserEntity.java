package org.vueverse.usermanagement.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vueverse.usermanagement.infrastructure.security.entity.base.BaseEntity;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phonenumber", unique = true)
    private PhoneNumber phoneNumber;


}
