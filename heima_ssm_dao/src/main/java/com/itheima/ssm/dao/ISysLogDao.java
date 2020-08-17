package com.itheima.ssm.dao;

import com.itheima.ssm.domain.SysLog;
import org.apache.ibatis.annotations.Insert;

public interface ISysLogDao {

    @Insert("insert into syslog(id,visitTime,username,ip,url,executionTime,method) values(uuid(),#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    void save(SysLog sysLog) throws Exception;
}
