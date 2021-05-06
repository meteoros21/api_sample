package net.ion.sst.api_sample.mapper;

import net.ion.sst.api_sample.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper
{
    Admin getAdminById(String userId);
}
