package net.ion.sst.api_sample.mapper;

import net.ion.sst.api_sample.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdminMapper
{
    Admin getAdminById(String userId);
    int insertAdmin(Admin admin);
    int updateAdmin(Admin admin);
    int deleteAdmin(String userId);

    List<Admin> findAdmins(Map<String, Object> params);
    List<Admin> getCheckersByClientId(int clientId);
    List<Admin> getAdminsByClientId(int clientId);
}
