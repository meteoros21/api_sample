package net.ion.sst.api_sample.service;

import net.ion.sst.api_sample.entity.CarInfo;
import net.ion.sst.api_sample.entity.Member;
import net.ion.sst.api_sample.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService
{
    @Autowired
    MemberMapper memberMapper;

    public List<Member> getMembersByClientIdAndStatus(int clientId, int status)
    {
        return memberMapper.getMembersByClientIdAndStatus(clientId, status);
    }

    public Member getMember(int clientId, String mobile)
    {
        return memberMapper.getMember(clientId, mobile);
    }

    public int insertMember(Member member)
    {
        return memberMapper.insertMember(member);
    }

    public int updateMember(Member member)
    {
        return memberMapper.updateMember(member);
    }

    public int deleteMember(int clientId, String mobile)
    {
        return memberMapper.deleteMember(clientId, mobile);
    }

    public int invalidateMembers(int clientId, String[] mobiles)
    {
        int total = 0;
        for (String mobile : mobiles)
        {
            total += memberMapper.updateMemberValid(clientId, mobile, "N");
        }
        return total;
    }

    public int validateMembers(int clientId, String[] mobiles)
    {
        int total = 0;
        for (String mobile : mobiles)
        {
            total += memberMapper.updateMemberValid(clientId, mobile, "Y");
        }
        return total;
    }

    public List<Member> getMembersByClientId(int clientId)
    {
        return memberMapper.getMembersByClientId(clientId);
    }

    public Map<String, Integer> getMemberCounts(int clientId)
    {
        int allCount =  memberMapper.getAllMemberCount(clientId);
        int approvedCount = memberMapper.getApprovedMemberCount(clientId);

        Map<String, Integer> result = new HashMap<>();
        result.put("all", allCount);
        result.put("approved", approvedCount);
        result.put("notApproved", (allCount - approvedCount));

        return result;
    }

    public int getAllMemberCount(int clientId)
    {
        return memberMapper.getAllMemberCount(clientId);
    }

    @Transactional
    public void updateCarInfos(List<CarInfo> carInfos)
    {
        for (CarInfo carInfo : carInfos)
        {
            memberMapper.updateCarInfo(carInfo);
        }
    }

    public int updateCarNumber(int clientId, String mobile, String carNumber)
    {
        CarInfo carInfo = new CarInfo();
        carInfo.clientId = clientId;
        carInfo.mobile = mobile;
        carInfo.carNumber = carNumber;
        return memberMapper.updateCarInfo(carInfo);
    }
}
