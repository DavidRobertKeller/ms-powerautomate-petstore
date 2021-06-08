package drkeller.petstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import drkeller.petstore.model.DbPet;

@Repository
public interface PetRepository extends JpaRepository <DbPet, String>{
}
