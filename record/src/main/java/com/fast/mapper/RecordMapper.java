package com.fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.model.record.root.TranRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<TranRecord> {

}
