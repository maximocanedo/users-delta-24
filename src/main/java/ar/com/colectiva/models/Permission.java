package ar.com.colectiva.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Permission implements GrantedAuthority {

    @Id
    private String username;

    private Action authority;

    public Permission() {}

    public Permission(Action authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.toString();
    }

    public void setAuthority(Action authority) {
        this.authority = authority;
    }
}

