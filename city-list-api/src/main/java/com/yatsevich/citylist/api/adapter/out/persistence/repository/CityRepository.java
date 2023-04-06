package com.yatsevich.citylist.api.adapter.out.persistence.repository;

import com.yatsevich.citylist.api.adapter.out.persistence.model.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends MongoRepository<CityEntity, String> {

  Page<CityEntity>  findAll(Pageable pageable);

  Page<CityEntity> findByNameLikeIgnoreCase(String name, Pageable pageable);

}
