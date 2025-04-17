package com.springboot.admin.repository;

import com.springboot.admin.domain.UserAccount;
import com.springboot.admin.domain.constant.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("회원 정보 SELECT")
    @Test
    void whenSelecting_thenOK() {
        // when
        List<UserAccount> userAccounts = userAccountRepository.findAll();

        // then
        assertThat(userAccounts).isNotNull().hasSize(4);
    }

    @DisplayName("회원 정보 INSERT")
    @Test
    void givenUserAccount_whenInserting_thenOK() {
        // Given
        UserAccount userAccount = UserAccount.of("test", "pw", Set.of(RoleType.DEVELOPER), null, null, "Set= 중복허용 불가");

        // When
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);

        // Then
        assertThat(userAccountRepository.findById(savedUserAccount.getUserId()))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("userId", "test")
                .hasFieldOrPropertyWithValue("userPassword", "pw");
    }

    @DisplayName("회원 정보 UPDATE")
    @Test
    void givenUserAccountANDRollType_whenUpdating_thenOK(){
        //Given
        UserAccount userAccount = userAccountRepository.getReferenceById("MJ");
        userAccount.addRoleType(RoleType.DEVELOPER);
        userAccount.addRoleTypes(List.of(RoleType.USER,RoleType.USER));
        userAccount.removeRoleType(RoleType.ADMIN);

        // when
        UserAccount updatedAccount = userAccountRepository.saveAndFlush(userAccount);

        // then
        assertThat(updatedAccount).hasFieldOrPropertyWithValue("userId","MJ");
        assertThat(updatedAccount).hasFieldOrPropertyWithValue("roleTypes",Set.of(RoleType.DEVELOPER,RoleType.USER));
    }

    @DisplayName("회원 정보 DELETE")
    @Test
    void givenUserAccount_whenDeleting_thenOK() {
        //Given
        UserAccount userAccount = userAccountRepository.getReferenceById("MJ");

        // when
        userAccountRepository.delete(userAccount);

        // then
        assertThat(userAccountRepository.findById("MJ")).isEmpty();
    }

    @EnableJpaAuditing
    @TestConfiguration
    static class TestJpaConfig {
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("MJ");
        }
    }
}
