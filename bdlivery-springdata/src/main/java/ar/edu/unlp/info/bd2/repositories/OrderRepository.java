package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.Status;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByUser(User user);
	List<Order> findByMyState(Status myState);
}