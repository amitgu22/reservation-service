package org.reservation.system.util;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class UserPassAuthenticator implements io.muserver.rest.UserPassAuthenticator {
    private final Map<String, Map<String, List<String>>> usersToPasswordToRoles;

    public UserPassAuthenticator(Map<String, Map<String, List<String>>> usersToPasswordToRoles) {
        this.usersToPasswordToRoles = usersToPasswordToRoles;
    }

    @Override
    public Principal authenticate(String username, String password) {
        Principal principal = null;
        Map<String, List<String>> user = usersToPasswordToRoles.get(username);
        if (user != null) {
            List<String> roles = user.get(password);
            if (roles != null) {
                principal = new User(username, roles);
            }
        }
        return principal;
    }
}

