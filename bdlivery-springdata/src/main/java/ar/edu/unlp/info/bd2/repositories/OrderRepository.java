package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.Status;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByUser(User user);
	
	@Query("select o from Order o join o.myState s where s.status = 'Pending'")
	List<Order> findPendingOrders();
	
	@Query("select s.orden from Status s join s.orden as o "
			+ "where s.status = 'Sent' and "
			+ "s.orden not in (select o1 from Status s1 join s1.orden as o1 "
			+ "where s1.status = 'Delivered')")
	List<Order> findSentOrders();
	
	@Query("select o from Order o join o.myState as s "
			+ "where s.status = 'Delivered' and (s.startDate BETWEEN :startDate AND :endDate)")
	List<Order>findDeliveredOrdersInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select o from Order o where o.myState.status = 'Delivered' "
			+ "and o.user.username = :username")
	List<Order> findDeliveredOrdersForUser(@Param("username") String username);
}