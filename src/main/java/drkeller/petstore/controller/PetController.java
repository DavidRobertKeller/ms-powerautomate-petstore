package drkeller.petstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import drkeller.petstore.dto.Pet;
import drkeller.petstore.model.DbPet;
import drkeller.petstore.model.PetMapper;
import drkeller.petstore.repository.PetRepository;
import drkeller.petstore.security.ApiKeyAuthConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin("*")
@RestController
@Api( tags = "pets", description = "Everything about your Pets")
@RequestMapping("/api/v1/pet")
public class PetController {
	
    private static final Logger LOG = LoggerFactory.getLogger(PetController.class);
	
	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PetMapper petMapper;

	@ApiOperation(
		value = "This method is used to get all pets.",
		nickname = "Pet_FindAll",
		authorizations = {@Authorization(value = ApiKeyAuthConfiguration.AUTH_HEADER_NAME)},
		notes = "TODO description")
	@RequestMapping(value = "/", 
		produces = MediaType.APPLICATION_JSON_VALUE, 
		method = RequestMethod.GET) 
	public ResponseEntity<List<Pet>> findAll() {
		LOG.info("get all pets");
		List<DbPet> entityList = petRepository.findAll();
		// TODO use https://modelmapper.org
		List<Pet> dtoList = new ArrayList<Pet>();
		for (DbPet dbPet : entityList) {
			dtoList.add(petMapper.toDTO(dbPet));
		}
		return ResponseEntity.ok().body(dtoList);
	}

	@ApiOperation(
		value = "This method is used to find a pet by id.",
		nickname = "Pet_FindById",
		authorizations = {@Authorization(value = ApiKeyAuthConfiguration.AUTH_HEADER_NAME)},
		notes = "TODO description")
	@RequestMapping(value = "/{id}", 
	produces = MediaType.APPLICATION_JSON_VALUE, 
	method = RequestMethod.GET) 
    public ResponseEntity<Pet> findById(
			@PathVariable(value = "id") String id) 
	throws IOException {
		LOG.info("find pet by id :: " + id);
		DbPet dbPet 
			= petRepository.findById(id)
					.orElseThrow(() -> new IOException("Pet not found for this id :: " + id));
		
		Pet pet = petMapper.toDTO(dbPet);

    	return ResponseEntity.ok().body(pet);
    }
}
