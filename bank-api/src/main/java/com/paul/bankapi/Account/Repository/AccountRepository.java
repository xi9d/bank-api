package com.paul.bankapi.Account.Repository;

import com.paul.bankapi.Account.Model.Account;
import io.swagger.annotations.Api;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Api(tags = "Account Repository")
public interface AccountRepository extends CrudRepository<Account,Integer> {
}
