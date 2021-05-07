package net.ion.sst.api_sample.security;

import net.ion.sst.api_sample.entity.Admin;
import net.ion.sst.api_sample.entity.Member;
import net.ion.sst.api_sample.service.AdminService;
import net.ion.sst.api_sample.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    AdminService adminService;
    @Autowired
    MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Admin admin = adminService.getAdminById(username);

        if (admin != null)
        {
            if (admin.valid == null || !admin.valid.equals("Y"))
                throw new DisabledException("권한이 종료된 사용자입니다.");

            return createUser(admin);
        }

        if (username.contains("@"))
        {
            String[] parts = username.split("@");
            int clientId = Integer.parseInt(parts[1]);

            Member member = memberService.getMember(clientId, parts[0]);
            if (member == null)
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

            if (member.valid == null || !member.valid.equalsIgnoreCase("Y"))
                throw new DisabledException("권한이 중지된 사용자입니다.");

            return createUser(member);
        }

        return null;
    }

    private ServiceUser createUser(Admin admin)
    {
        List<UserRole> authorities = new ArrayList<>();
        UserRole role = new UserRole("ROLE_" + admin.getUserRole());
        authorities.add(role);

        ServiceUser user = new ServiceUser(admin.userId, admin.password, authorities);
        user.clientId = admin.clientId;
        user.mobile = admin.mobile;
        user.profileImg = admin.profileImg;
        user.displayName = admin.userName;

        return user;
    }

    private ServiceUser createUser(Member member)
    {
        List<UserRole> authorities = new ArrayList<>();
        UserRole role = new UserRole("ROLE_member");
        authorities.add(role);

        String username = member.mobile + "@" + member.clientId;
        ServiceUser user = new ServiceUser(username, member.password, authorities);
        user.clientId = member.clientId;
        user.mobile = member.mobile;
        user.profileImg = member.profileImg;
        user.displayName = member.memName;

        return user;
    }
}
