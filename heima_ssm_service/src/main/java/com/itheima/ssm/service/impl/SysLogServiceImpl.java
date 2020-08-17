package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.ISysLogDao;
import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    ISysLogDao iSysLogDao;
    @Override
    public void save(SysLog sysLog) throws Exception {
        iSysLogDao.save(sysLog);
    }
}
