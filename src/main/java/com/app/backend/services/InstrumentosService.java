package com.app.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.backend.entities.Instrumentos;
import com.app.backend.repositories.InstrumentosRepository;

@Service
public class InstrumentosService {

	@Autowired
	private InstrumentosRepository repository;

	@Transactional(readOnly = true)
	public Iterable<Instrumentos> findAll()throws Exception {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public Page<Instrumentos> findAll(Pageable pageable) throws Exception {
		try {
			return repository.findAll(pageable);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	public Instrumentos getOne(int id) throws Exception {
		try {
			Optional<Instrumentos> optIns = repository.findById(id);
			return optIns.get();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public Instrumentos save(Instrumentos entity) throws Exception{
		try {
			return repository.save(entity);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public Instrumentos update(Instrumentos entity,int id) throws Exception{
		Optional<Instrumentos> optInstrumentos = repository.findById(id);
		Instrumentos temp = new Instrumentos();
		try {
			temp = optInstrumentos.get();
			temp.setInstrumento(entity.getInstrumento());
			temp.setMarca(entity.getMarca());
			temp.setModelo(entity.getModelo());
			temp.setCostoEnvio(entity.getCostoEnvio());
			temp.setCantidadVendida(entity.getCantidadVendida());
			temp.setDescripcion(entity.getDescripcion());
			temp.setPrecio(entity.getPrecio());
			temp.setImagen(entity.getImagen());
			
			temp = repository.save(temp);
			entity.setId(temp.getId());
			return entity;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public boolean delete(int id) throws Exception{
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


}
