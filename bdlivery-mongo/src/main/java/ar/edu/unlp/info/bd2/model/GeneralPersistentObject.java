package ar.edu.unlp.info.bd2.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public abstract class GeneralPersistentObject implements PersistentObject {
	
	@BsonId
    private ObjectId objectId;
	
	public ObjectId getObjectId() {
        return objectId;
    }
	
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
	
}
