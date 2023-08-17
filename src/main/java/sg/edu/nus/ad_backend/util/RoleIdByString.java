package sg.edu.nus.ad_backend.util;

import sg.edu.nus.ad_backend.security.common.SecurityConstants;

public class RoleIdByString {
    public static Integer getRoleType(String roles) {
        return switch (roles) {
            case SecurityConstants.SYS_ROLE_ADMIN -> SecurityConstants.SYS_ROLE_ADMIN_KEY;
            case SecurityConstants.SYS_ROLE_MODERATOR -> SecurityConstants.SYS_ROLE_MODERATOR_KEY;
            case SecurityConstants.SYS_ROLE_MEMBER -> SecurityConstants.SYS_ROLE_MEMBER_KEY;
            default -> -1;
        };
    }
}
