package com.fast.service;

import com.fast.mapper.RecordMapper;
import com.fast.model.record.root.TranRecord;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranRecordServiceImpl {

    @Autowired
    private RecordMapper recordMapper;

    @Transactional
    public TranRecord add(Integer accountId, Double changeAmount, String changeType){
        TranRecord tranRecord = new TranRecord();
        tranRecord.setAccountId(accountId);
        tranRecord.setChangeAmount(changeAmount);
        tranRecord.setChangeType(changeType);
        recordMapper.insert(tranRecord);
        return tranRecord;
    }

}
