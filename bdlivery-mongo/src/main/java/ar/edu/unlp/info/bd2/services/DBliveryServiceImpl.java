package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.repositories.*;

public class DBliveryServiceImpl implements DBliveryService {

	private DBliveryMongoRepository repository;
	
	public DBliveryServiceImpl(DBliveryMongoRepository repository) {
		this.repository = repository;
	}
	
	
}
