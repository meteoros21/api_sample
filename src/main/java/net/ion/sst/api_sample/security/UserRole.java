package net.ion.sst.api_sample.security;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority
{
    private static final long serialVersionUID = 1L;

    private String roleName;

    public UserRole()
    {
        this.roleName = "ROLE_USER";
    }
    public UserRole(String roleName)
    {
        this.roleName = roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority()
    {
        return roleName;
    }
}
