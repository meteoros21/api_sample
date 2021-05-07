package net.ion.sst.api_sample.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ServiceUser extends User
{
    public int clientId;
    public String role;
    public String profileImg;
    public String displayName;
    public String mobile;

    public ServiceUser(String username, String password, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);
    }
}
