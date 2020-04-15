package com.fast.repository;

import com.fast.model.record.root.TranRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranRecordRepository extends JpaRepository<TranRecord, Integer> {


}
