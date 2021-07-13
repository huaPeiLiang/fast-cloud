package com.fast.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.fast.mapper.RecordMapper;
import com.fast.model.record.root.TranRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;

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

    public List<TranRecord> query(){
        return recordMapper.selectList(new QueryWrapper<TranRecord>());
    }

}
