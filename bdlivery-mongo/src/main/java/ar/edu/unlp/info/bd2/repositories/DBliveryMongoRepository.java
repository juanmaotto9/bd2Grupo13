package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;

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

 /*   public void createUser(User usuario) {
    	MongoCollection<User> collection = this.getDb().getCollection("user", User.class);
        collection.insertOne(usuario);
    }
    
    public void createProduct(Product product) {
    	ESTE METODO ES PERSISTPRODUCT !!!
    }
    */
    public void persistProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", product.getName());
        FindIterable<Product> docsIterable = collection.find(whereQuery); 
        //no dar bola al try lo encontre en google pero no es necesario despues lo saco y fue
        try (MongoCursor<Product> iterator = docsIterable.iterator()){
        int count = 0;
        while (iterator.hasNext()) {
        iterator.next();
        count++;
        }
        if( count == 0){
        	collection.insertOne(product);
        }
        }
    }
    
    public void persistSupplier(Supplier supplier){
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        BasicDBObject query = new BasicDBObject();
        query.put("cuil", supplier.getCuil());
        FindIterable<Supplier> docs = collection.find(query); 
        try (MongoCursor<Supplier> iterator = docs.iterator()){
        int count = 0;
        while (iterator.hasNext()) {
        iterator.next();
        count++;
        }
        if( count == 0){
        	collection.insertOne(supplier);
        }
        }
    }
    
    public void UpdateProductPrice(ObjectId product, Price newPrice) {
    	MongoCollection<Supplier> collection = this.getDb().getCollection("Product", Supplier.class);
    	collection.updateOne(eq("_id", product), Updates.addToSet("prices", newPrice));
    	BasicDBObject updateQuery = new BasicDBObject(); updateQuery.append("$set", new BasicDBObject().append("price", newPrice.getPrice()));
    	collection.updateOne(eq("_id", product), updateQuery);
    }

}
