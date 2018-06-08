package com.rvfs.challenge.mcc.login.repository;


import com.rvfs.challenge.mcc.login.model.Account;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AccountRepository extends Repository<Account, Long> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findById(Long id);

    Account save(Account account);

    Integer countByUsername(String username);


}
