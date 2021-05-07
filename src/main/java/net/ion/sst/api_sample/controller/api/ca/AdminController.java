package net.ion.sst.api_sample.controller.api.ca;

import net.ion.sst.api_sample.controller.api.ApiResult;
import net.ion.sst.api_sample.entity.Admin;
import net.ion.sst.api_sample.entity.Member;
import net.ion.sst.api_sample.security.ServiceUser;
import net.ion.sst.api_sample.service.AdminService;
import net.ion.sst.api_sample.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CaAdminController")
@RequestMapping("/ca/admin")
public class AdminController
{
    AdminService adminService;
    MemberService memberService;

    public AdminController(AdminService adminService,
                           MemberService memberService)
    {
        this.adminService = adminService;
        this.memberService = memberService;
    }

    @GetMapping("/test")
    public ApiResult test()
    {
        return new ApiResult(0, "test");
    }

    @GetMapping("/{userId}")
    public ApiResult getAdminById(@PathVariable("userId") String userId,
                                  Authentication authentication)
    {
        Admin admin = adminService.getAdminById(userId);
        if (admin != null)
            return new ApiResult(0, "success", admin);

//        ServiceUser user = (ServiceUser) authentication.getPrincipal();
//
//        Member member = memberService.getMember(user.clientId, userId);
//        if (member != null)
//            return new ApiResult(0, "success", member);

        return new ApiResult(400, "No Admin");
    }
}
