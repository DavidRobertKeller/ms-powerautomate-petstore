package drkeller.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import drkeller.petstore.model.DbApiKeyAuth;


@Repository
public interface ApiKeyAuthRepository extends JpaRepository <DbApiKeyAuth, String>{
	List<DbApiKeyAuth> findByApiKey(String apiKey);
}
