package perso.discordbots.caupanharm.controllers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import perso.discordbots.caupanharm.databases.CaupanharmUser;

import java.util.List;

public class UserController extends MongoController{
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(String dbName){
        super(dbName);
    }

    public void insertUser(String discordID, String riotId, String riotUsername){
        super.openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        try {
            InsertOneResult result = collection.insertOne(new CaupanharmUser(discordID,riotId, riotUsername));
            logger.info("Created user: "+result);
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
        mongoClient.close();
    }

    /*
    public void insertUsers(List<CaupanharmUser> users){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        try {
            InsertManyResult result = collection.insertMany(users);
            logger.info("Created " + result.getInsertedIds().size() + " users.");
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
        mongoClient.close();
    }

     */

    public void getUsers(){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        try(MongoCursor<CaupanharmUser> cursor = collection.find().iterator()){
            while(cursor.hasNext()){
                CaupanharmUser currentUser = cursor.next();
                logger.info(currentUser.toString());
            }
        }catch(MongoException me){
            logger.error("Unable to find the list of users due to an error: "+me);
        }
        mongoClient.close();
    }

    public CaupanharmUser getUser(String key, String value){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        Bson filter = Filters.eq(key, value);
        CaupanharmUser result = null;
        try{
            result = collection.find(filter).first();
        }catch(MongoException me){
            logger.error("Unable to find any user due to an error: "+me);
        }
        mongoClient.close();
        return result;
    }


    public void updateUser(String oldKey, String oldValue, String newKey, String newValue){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        Bson updateFilter = Updates.set(newKey, newValue);
        // The following FindOneAndUpdateOptions specify that we want it to return
        // the *updated* document to us. By default, we get the document as it was *before*
        // the update.
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
        // The updatedDocument object is an object that reflects the changes we just made.
        try {
            CaupanharmUser updatedUser = collection.findOneAndUpdate(Filters.eq(oldKey, oldValue), updateFilter, options);
            if (updatedUser == null) {
                logger.warn("Couldn't update the document. Did someone (or something) delete it?");
            } else {
                logger.info("Updated user: " + updatedUser);
            }
        } catch (MongoException me) {
            logger.error("Unable to update any document due to an error: " + me);
        }
        mongoClient.close();
    }

    public void deleteUser(String key, String value){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        Bson deleteFilter = Filters.eq(key, value);
        try {
            CaupanharmUser deletedUser = collection.findOneAndDelete(deleteFilter);
            logger.info("Deleted user: "+deletedUser);
        } catch (MongoException me) {
            logger.error("Unable to delete a document due to an error: " + me);
        }
        mongoClient.close();
    }
}
