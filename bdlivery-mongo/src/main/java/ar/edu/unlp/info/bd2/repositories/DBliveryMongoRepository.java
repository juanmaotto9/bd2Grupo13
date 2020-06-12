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
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.*;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.text.SimpleDateFormat;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;


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
        for (Product dbObject : collection.find(regex("name", name)))
        {
            list.add(dbObject);
        }
        return list;
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
        for (Order dbObject : collection.find(regex("myState.status","Pending")) )
        {
            list.add(dbObject);
        }
        return list;
    }
    
    public List<Order> getSentOrders() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        for (Order dbObject : collection.find(eq("myState.status", "Sent")))
        {
            list.add(dbObject);
        }
        return list;
    }
    
    public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        for (Order order : collection.aggregate(Arrays.asList(
                match(eq("myState.status", "Delivered")),
                match(gt("myState.startDate", startDate)),
                match(lt("myState.startDate", endDate))
        ))) {
            list.add(order);
        }
        return list;
    }


    public List<Order> getAllOrdersMadeByUser(String username) {
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        ArrayList<Order> list = new ArrayList<>();
        for (Order dbObject : collection.find(regex("client.username", username)))
        {
            list.add(dbObject);
        }
        return list;
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
    
    /*
     * crear nuevo date (fecha: new DataTime(day)  //day se recibe por parametro)
     * 
     */
    
    public List<Order> getOrderNearPlazaMoreno() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("order", Order.class);
        Point myPoint = new Point(new Position(-34.921236,-57.954571));
        for (Order order : collection.find(Filters.near("position", myPoint, 400.0, 0.0))) 
        {
            list.add(order);
        }
        return list;
    }
    
    public List<Product> getProductsOnePrice(){
    	ArrayList<Product> list = new ArrayList<>();
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        for (Product dbObject : collection.find(size("prices",1)))
        {
            list.add(dbObject);
        }
        return list;
    }
    
    public Product getMaxWeigth() {
        MongoCollection<Product> collection = this.getDb().getCollection("product", Product.class);
        return collection.find().sort(new Document("weight",-1)).first();
    }   
    
}
