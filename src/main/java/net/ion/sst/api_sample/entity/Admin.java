package net.ion.sst.api_sample.entity;

import lombok.Data;

@Data
public class Admin
{
    public String userId;
    public String password;
    public String userName;
    public String mobile;
    public String email;
    public String profileImg;

    public String valid;

    public int clientId;
    public String clientName;

    public String userRole;
    public String roleDisplayName;

    public String getProfileImage()
    {
        if (this.profileImg == null || this.profileImg.length() == 0)
            return "/img/no-profile.png";
        else
            return this.profileImg;
    }
}
