package com.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend.entities.Instrumentos;
import com.app.backend.services.InstrumentosService;
@Controller
@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/crud/instrumento")
public class InstrumentosController {
	@Autowired
	protected InstrumentosService service;
	
	@GetMapping(path = "/")
	public ResponseEntity<?> getAll() throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	
	@GetMapping(path = "/pagina")
	public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	
	@GetMapping(path = "/uploads/img/{id}")
	public ResponseEntity<?> verImg(@PathVariable int id)throws Exception{
		try {
			Instrumentos Instrumentos = service.getOne(id);
			Resource img = new ByteArrayResource(Instrumentos.getImagen());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception();
		}
	}

	@PostMapping(path = "/")
	public ResponseEntity<?> save(@RequestBody Instrumentos entity) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> saveWithFoto(Instrumentos entity, @RequestParam MultipartFile img) throws Exception {
		try {
			if(!img.isEmpty()) {
				entity.setImagen(img.getBytes());
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	
	@PutMapping(path = "/editar-con-foto/{id}")
	public ResponseEntity<?> updateWithFoto(Instrumentos entity, @PathVariable int id, @RequestParam MultipartFile img) throws Exception {
		try {
			if(!img.isEmpty()) {
				entity.setImagen(img.getBytes());
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(service.update(entity, id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> update(@RequestBody Instrumentos entity, @PathVariable int id) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.update(entity, id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws Exception {
		try {
			boolean isDet = this.service.delete(id);
			if (isDet) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"Message\":\"Deleted\"");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"Error\":\"Bad Request\"");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"Error\":\""+e.getMessage()+"\"");
		}
	}

}
