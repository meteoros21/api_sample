package net.ion.sst.api_sample.service;

import net.ion.sst.api_sample.entity.Admin;
import net.ion.sst.api_sample.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService
{
    @Autowired
    AdminMapper adminMapper;

    public Admin getAdminById(String userId)
    {
        return adminMapper.getAdminById(userId);
    }

    public int insertAdmin(Admin admin)
    {
        return adminMapper.insertAdmin(admin);
    }

    public int updateAdmin(Admin admin)
    {
        return adminMapper.updateAdmin(admin);
    }

    public List<Admin> getAdminsByClientId(int clientId)
    {
        return adminMapper.getAdminsByClientId(clientId);
    }

    public List<Admin> getCheckersByClientId(int clientId)
    {
        return adminMapper.getCheckersByClientId(clientId);
    }

    public int deleteAdmin(String userId)
    {
        return adminMapper.deleteAdmin(userId);
    }
}
