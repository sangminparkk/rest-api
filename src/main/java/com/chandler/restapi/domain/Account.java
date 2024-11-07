package com.chandler.restapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    @ElementCollection(fetch = EAGER)
    @Enumerated(STRING)
    private Set<AccountRole> roles; //TODO: Set을 사용한 목적?

}
