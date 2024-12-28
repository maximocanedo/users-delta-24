package ar.com.colectiva.models;

public enum Action {

    CHANGE_USER_BASIC_INFORMATION,
    CHANGE_PASSWORD,
    GRANT_PERMISSIONS,
    REVOKE_PERMISSIONS;

    @Override public String toString() {
        return name();
    }
}
