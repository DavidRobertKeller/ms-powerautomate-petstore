package drkeller.petstore.model;

import org.springframework.stereotype.Component;

import drkeller.petstore.dto.Pet;
import drkeller.petstore.dto.PetCreation;

@Component
public class PetMapper {

	public Pet toDTO(DbPet dbPet) {
		Pet pet = new Pet();
		pet.setId(dbPet.getId());
		pet.setName(dbPet.getName());
		return pet;
	}

	public DbPet toEntity(PetCreation petCreation) {
		DbPet dbPet = new DbPet();
//		dbsc.setId(sc.getId()); managed by database
		dbPet.setName(petCreation.getName());
		return dbPet;
	}
	
	public DbPet toEntity(Pet pet) {
		DbPet dbPet = new DbPet();
//		dbsc.setId(sc.getId()); managed by database
		dbPet.setName(pet.getName());
		return dbPet;
	}
	
}
