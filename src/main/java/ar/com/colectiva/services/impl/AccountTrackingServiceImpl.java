package ar.com.colectiva.services.impl;

import ar.com.colectiva.models.AccountActivity;
import ar.com.colectiva.models.AccountEvent;
import ar.com.colectiva.models.User;
import ar.com.colectiva.repositories.AccountActivityRepository;
import ar.com.colectiva.services.AccountTrackingService;
import ar.com.colectiva.services.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AccountTrackingServiceImpl implements AccountTrackingService {

    private final AccountActivityRepository accountActivityRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public AccountTrackingServiceImpl(
            AccountActivityRepository accountActivityRepository,
            SecurityContextService securityContextService
    ) {
        this.accountActivityRepository = accountActivityRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public AccountActivity logActivity(AccountEvent event, String payload, User user) {
        var log = new AccountActivity();
        log.setUser(user);
        log.setEvent(event);
        log.setPayload(payload);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return accountActivityRepository.save(log);
    }

    @Override
    public AccountActivity logActivity(AccountEvent event, String payload) {
        return logActivity(event, payload, securityContextService.getCurrentUserOrDie());
    }

    @Override
    public AccountActivity logActivity(AccountEvent event) {
        return logActivity(event, "");
    }

}
