package com.fast.repository;

import com.fast.model.root.Phone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends ElasticsearchRepository<Phone,Long> {

}
