package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Filters.*;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import static com.mongodb.client.model.Projections.*;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;


    public void saveAssociation(PersistentObject source, PersistentObject destination, String associationName) {
        Association association = new Association(source.getObjectId(), destination.getObjectId());
        this.getDb()
                .getCollection(associationName, Association.class)
                .insertOne(association);
    }

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo13");
    }

    public <T extends PersistentObject> List<T> getAssociatedObjects(
            PersistentObject source, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("source", source.getObjectId())),
                                        lookup(destCollection, "destination", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
/*--  agregado de la etapa 2 --*/

    public <T extends PersistentObject> List<T> getObjectsAssociatedWith(
            ObjectId objectId, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("destination", objectId)),
                                        lookup(destCollection, "source", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    /*-- hasta acá --*/
    
    public void createUser(User usuario) {
    	MongoCollection<User> collection = this.getDb().getCollection("user", User.class);
        collection.insertOne(usuario);
    }

    public void persistProduct(Product product){
    	MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        collection.insertOne(product);
    }  
    
	public void persistSupplier(Supplier supplier){
		MongoCollection<Supplier> collection = this.getDb().getCollection("supplier", Supplier.class);
        collection.insertOne(supplier);
    }	

    public void persistOrder(Order orden){
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        collection.insertOne(orden);
    }


    public Optional getUserById(ObjectId id){
    	MongoCollection collection = this.getDb().getCollection("user", User.class);
    	return Optional.ofNullable(collection.find(eq("_id",id)).first());
    }
    
    public Optional getProductById(ObjectId id){
    	MongoCollection collection = this.getDb().getCollection("product", Product.class);
    	return Optional.ofNullable(collection.find(eq("_id",id)).first());
    }
    
    public Optional getUserByEmail(String email){
    	MongoCollection collection = this.getDb().getCollection("user", User.class);
    	return Optional.ofNullable(collection.find(eq("email",email)).first());
    }
    
    public Optional getUserByUsername(String username) {
    	MongoCollection collection = this.getDb().getCollection("user", User.class);
    	return Optional.ofNullable(collection.find(eq("username",username)).first());
    }
    
    public Optional getOrderById(ObjectId id){
    	MongoCollection collection = this.getDb().getCollection("order", Order.class);
    	return Optional.ofNullable(collection.find(eq("_id",id)).first());
    }
    
    /* ------------------------ */
    
    public List<Product> getProductsByName(String name){
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        ArrayList<Product> list = new ArrayList<Product>();
        return collection.find(regex("name", name)).into(list);
    }
    
    public void updateProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        collection.replaceOne(eq("_id", product.getObjectId()), product);
    }
    
    public void updateOrder(Order order) {
    	MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
    	collection.replaceOne(eq("_id", order.getObjectId()), order);
    }
    
    /*-- etapa 2: Consultas --*/
    public List <Order> getPendingOrders(){
    	MongoCollection<Order> collection = this.getDb().getCollection("order",Order.class);
    	ArrayList<Order> list = new ArrayList<Order>();
        return collection.find(regex("myState.status","Pending")).into(list);
    }
    
    public List<Order> getSentOrders() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        return collection.find(eq("myState.status", "Sent")).into(list);
    }
    
    public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        return collection.aggregate(Arrays.asList(
                match(eq("myState.status", "Delivered")),
                match(gt("myState.startDate", startDate)),
                match(lt("myState.startDate", endDate)) )).into(list);
    }


    public List<Order> getAllOrdersMadeByUser(String username) {
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        ArrayList<Order> list = new ArrayList<>();
        return collection.find(regex("client.username", username)).into(list);
    }
    
    public List<Product> getSoldProductsOn(Date day) {
        ArrayList<Product> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        for (Order dbObject : collection.find(eq("dateOfOrder", day)))
        {
        	for (ProductOrder Products: dbObject.getProducts())
            list.add(Products.getProduct());
        }
        return list;
    }
    
    
    public List<Order> getOrderNearPlazaMoreno() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        Point myPoint = new Point(new Position(-34.921236,-57.954571));
        return collection.find(Filters.near("position", myPoint, 400.0, 0.0)).into(list);
    }
    
    public List<Product> getProductsOnePrice(){
    	ArrayList<Product> list = new ArrayList<>();
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        return collection.find(size("prices",1)).into(list);
    }
    
    public Product getMaxWeigth() {
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        return collection.find().sort(new Document("weight",-1)).first();
    }   
    
    /*-- Obtiene el producto con más demanda . agrupo por producto y sumo quantity--*/
    public Product getBestSellingProduct() {
    		MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
    		collection.aggregate(Arrays.asList(
    				Aggregates.unwind("$products"),
    				Aggregates.group("$products.product", 
    						Accumulators.sum("quantity", "$products.quantity")),
    				Aggregates.sort(
    						Sorts.orderBy(Sorts.descending("quantity")) ),
    				limit(1), replaceRoot("$_id"), out("bestProduct") )).toCollection();
    		Product best = this.getDb().getCollection("bestProduct", Product.class).find().first();
    		this.getDb().getCollection("bestProduct").drop();
    		return best;
    } //unwind deconstruye un arreglo en documentos. No pude hacer para directamente quedarme con el producto	  
    //Aggregates.project(  )
    
    
    /*-- Obtiene los n proveedores que más productos tienen en ordenes que están siendo enviadas --*/
    public List<Supplier> getTopNSuppliersInSentOrders(int n){
    	List<Supplier> list = new ArrayList<>();
		MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);

		collection.aggregate(Arrays.asList(
				match(eq("myState.status", "Sent")),
				unwind("$products"), 
				lookup("productSupplier", "products.product._id", "destination", "productSupplier"), 
				lookup("supplier", "productSupplier.source", "_id", "supplier"),
				unwind("$supplier"),
				unwind("$productSupplier"),
				group("$supplier", Accumulators.sum("quantity", "$products.quantity")),
				sort(Sorts.orderBy(Sorts.descending("quantity"))),
				replaceRoot("$_id"),limit(n),out("topSuppliers"))).toCollection();

		this.getDb().getCollection("topSuppliers", Supplier.class).find().into(list);
		this.getDb().getCollection("topSuppliers").drop();
		return list;
    }
    //source = supplier y destination = product
    
    /*-- Obtiene todas las órdenes entregadas para el cliente con username username --*/
    public List <Order> getDeliveredOrdersForUser(String username){
    	MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
    	 ArrayList<Order> list = new ArrayList<>();
         return collection.aggregate(Arrays.asList(
                 match(eq("myState.status", "Delivered")),
                 match(eq("client.username", username)) )).into(list);
    }
}
