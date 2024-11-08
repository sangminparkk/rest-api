package com.chandler.restapi.service;

import com.chandler.restapi.domain.Account;
import com.chandler.restapi.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

import static com.chandler.restapi.domain.AccountRole.ADMIN;
import static com.chandler.restapi.domain.AccountRole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("유저 네임으로 user 확인")
    public void findByUsername() throws Exception {
        // given
        String username = "chandler@gmail.com";
        String password = "chandler";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(ADMIN, USER))
                .build();
        this.accountRepository.save(account);

        // when
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertEquals(userDetails.getPassword(), password);
    }


    @Test
    @DisplayName("유저 정보가 없는 경우 NotFoundException")
    public void findByUsername_NotFound() throws Exception {
        //Expected
        String username = "username@naver.com";
        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername(username)); // ExpectedException deprecated
    }

}