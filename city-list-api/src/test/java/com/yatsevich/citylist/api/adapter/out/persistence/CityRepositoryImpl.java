package com.yatsevich.citylist.api.adapter.out.persistence;

import com.yatsevich.citylist.api.adapter.out.persistence.model.CityEntity;
import com.yatsevich.citylist.api.adapter.out.persistence.repository.CityRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
/*
* This temporary class will be removed after involving MongoDBContainer
* */
@Profile("test")
@Repository
public class CityRepositoryImpl implements CityRepository {

  private final Map<String, CityEntity> storageById = new HashMap<>();
  private final Map<String, CityEntity> storageByName = new HashMap<>();

  @Override
  public Page<CityEntity> findAll(Pageable pageable) {
    return new PageImpl<>(storageById.values().stream().toList(),
        PageRequest.of(0, pageable.getPageSize()), 1);
  }

  @Override
  public Page<CityEntity> findByNameLikeIgnoreCase(String name, Pageable pageable) {
    List<CityEntity> result = new ArrayList<>();
    result.add(storageByName.get(name));
    return new PageImpl<>(result, PageRequest.of(0, pageable.getPageSize()), 1);
  }

  @Override
  public <S extends CityEntity> S insert(S entity) {
    return null;
  }

  @Override
  public <S extends CityEntity> List<S> insert(Iterable<S> entities) {
    return null;
  }

  @Override
  public <S extends CityEntity> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends CityEntity> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends CityEntity> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends CityEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends CityEntity> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends CityEntity> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends CityEntity, R> R findBy(
      Example<S> example,
      Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends CityEntity> S save(S entity) {
    storageById.put(entity.getId(), entity);
    storageByName.put(entity.getName(), entity);
    return entity;
  }

  @Override
  public <S extends CityEntity> List<S> saveAll(Iterable<S> entities) {
    entities.forEach(record -> {
      storageById.put(record.getId(), record);
      storageByName.put(record.getName(), record);
    });
    return null;
  }

  @Override
  public Optional<CityEntity> findById(String s) {
    return Optional.ofNullable(storageById.get(s));
  }

  @Override
  public boolean existsById(String s) {
    return false;
  }

  @Override
  public List<CityEntity> findAll() {
    return null;
  }

  @Override
  public List<CityEntity> findAllById(Iterable<String> strings) {
    return null;
  }

  @Override
  public long count() {
    return storageById.size();
  }

  @Override
  public void deleteById(String s) {

  }

  @Override
  public void delete(CityEntity entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends String> strings) {

  }

  @Override
  public void deleteAll(Iterable<? extends CityEntity> entities) {

  }

  @Override
  public void deleteAll() {
    storageByName.clear();
    storageById.clear();
  }

  @Override
  public List<CityEntity> findAll(Sort sort) {
    return null;
  }
}
