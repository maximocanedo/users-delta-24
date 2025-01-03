package ar.com.colectiva.services;

import ar.com.colectiva.models.AccountActivity;
import ar.com.colectiva.models.AccountEvent;
import ar.com.colectiva.models.User;

public interface AccountTrackingService {

    AccountActivity logActivity(AccountEvent event, String payload, User user);

    AccountActivity logActivity(AccountEvent event, String payload);

    AccountActivity logActivity(AccountEvent event);

}
