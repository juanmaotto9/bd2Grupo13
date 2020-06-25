package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
	
}
