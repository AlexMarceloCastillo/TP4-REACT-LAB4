package com.app.backend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.backend.entities.Instrumentos;

@Repository
public interface InstrumentosRepository extends PagingAndSortingRepository<Instrumentos,Integer> {

}
