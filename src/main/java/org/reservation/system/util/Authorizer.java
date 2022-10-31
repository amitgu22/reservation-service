package org.reservation.system.util;

import java.security.Principal;

public class Authorizer implements io.muserver.rest.Authorizer {
    @Override
    public boolean isInRole(Principal principal, String role) {
        if (principal == null) {
            return false;
        }
        User user = (User)principal;
        return user.isInRole(role);
    }
}

