package com.paul.bankapi.Account.Repository;

import com.paul.bankapi.Account.Model.Account;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Api(tags = "Account Repository")
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findAccountById(Integer accountId);
}
