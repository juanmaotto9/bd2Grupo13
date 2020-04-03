package ar.edu.unlp.info.bd2.repositories;

//import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryRepository {
/*    estas dos lineas las agrega el profe, ver por qué tira error   */
	@Autowired
	private SessionFactory sessionFactory;
}
