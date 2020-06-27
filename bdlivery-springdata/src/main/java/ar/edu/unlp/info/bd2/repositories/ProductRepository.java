package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByNameContaining(String name);
	
	@Query("SELECT p FROM Product AS p WHERE p.weight IN (SELECT MAX(pr.weight) FROM Product AS pr)")
	Product findMaxWeight();
	
	@Query("SELECT s.product FROM Price AS s GROUP BY s.product HAVING COUNT(s) = 1")
	List<Product> findProductWithOnePrice();

	@Query("select p from Product p where p in "
			+ "(select distinct po.product from ProductOrder po join"
			+ " po.orden as o where o.dateOfOrder = :day)")
	List<Product> findSoldProductsOn(@Param("day") Date day);
}
