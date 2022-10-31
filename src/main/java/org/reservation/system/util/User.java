package org.reservation.system.util;

import java.security.Principal;
import java.util.List;

public class User implements Principal {
    private final String name;
    private final List<String> roles;
    protected User(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }
    @Override
    public String getName() {
        return name;
    }
    public boolean isInRole(String role) {
        return roles.contains(role);
    }
}
