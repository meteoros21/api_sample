package net.ion.sst.api_sample.entity;

import lombok.Data;

@Data
public class Member
{
    public String mobile;
    public int clientId;
    public String memName;
    public String password;
    public String email;
    public String position;
    public String comment;
    public String valid;
    public String profileImg;
    public String carNumber;
    public String regTime;

    public String getPositionString()
    {
        if (this.position == null || this.position.length() == 0)
            return "";
        else
            return "(" + this.position + ")";
    }

    public String getEmailString()
    {
        if (this.email == null || this.email.length() == 0)
            return "등록안됨";
        else
            return this.email;
    }
    public String getValidText()
    {
        if (valid == null)
            return "정상회원";

        if (valid.equalsIgnoreCase("Y"))
            return "정상회원";
        else
            return "자격정지";
    }

    public String getProfileImage()
    {
        if (this.profileImg == null || this.profileImg.length() == 0)
            return "/img/no-profile.png";
        else
            return this.profileImg;
    }

    public String getCarNumberString()
    {
        if (carNumber == null)
            return "등록안됨";
        else
            return carNumber;
    }
}
