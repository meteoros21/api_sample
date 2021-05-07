package net.ion.sst.api_sample.mapper;

import net.ion.sst.api_sample.entity.CarInfo;
import net.ion.sst.api_sample.entity.CounterAndLabel;
import net.ion.sst.api_sample.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper
{
    List<Member> getMembersByClientId(int clientId);
    List<Member> getMembersByClientIdAndStatus(int clientId, int status);
    Member getMember(int clientId, String mobile);

    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(int clientId, String mobile);
    int updateMemberValid(int clientId, String mobile, String valid);

    int getAllMemberCount(int clientId);
    int getApprovedMemberCount(int clientId);

    List<CounterAndLabel> getMemberCounts(int clientId);

    int updateCarInfo(CarInfo carInfo);
}
