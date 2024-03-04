package perso.discordbots.caupanharm.controllers;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

import perso.discordbots.caupanharm.models.CaupanharmUser;

import java.util.ArrayList;
import java.util.List;


public class UserController extends MongoController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(String dbName) {
        super();
    }

    public void insertUser(String discordId, String riotId, String riotPuuid, String riotUsername) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        try {
            CaupanharmUser newUser = new CaupanharmUser(discordId, riotId, riotPuuid, riotUsername);
            collection.insertOne(newUser);
            logger.info("Created user: " + newUser);
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
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

    public List<CaupanharmUser> getUsers(String key, Object value) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        Bson filter = Filters.eq(key, value);
        List<CaupanharmUser> result = new ArrayList<>();
        try {
            if (collection.countDocuments(filter) == 0) return result;
            for (CaupanharmUser currentUser : collection.find()) {
                result.add(currentUser);
            }
        } catch (MongoException me) {
            logger.error("Unable to find the list of users due to an error: " + me);
        }
        return result;
    }

    public CaupanharmUser getUser(String key, String value) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        Bson filter = Filters.eq(key, value);
        CaupanharmUser result = null;
        try {
            result = collection.find(filter).first();
        } catch (MongoException me) {
            logger.error("Unable to find any user due to an error: " + me);
        }
        return result;
    }


    public void updateUser(String keyFilter, Object valueFilter, String keyToEdit, Object newValue) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        Bson updateFilter = Updates.set(keyToEdit, newValue);
        // The following FindOneAndUpdateOptions specify that we want it to return
        // the *updated* document to us. By default, we get the document as it was *before*
        // the update.
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
        // The updatedDocument object is an object that reflects the changes we just made.
        try {
            CaupanharmUser updatedUser = collection.findOneAndUpdate(Filters.eq(keyFilter, valueFilter), updateFilter, options);
            if (updatedUser == null) {
                logger.error("Couldn't update the document. Did someone (or something) delete it?");
            } else {
                logger.info("Updated user: " + updatedUser);
            }
        } catch (MongoException me) {
            logger.error("Unable to update any document due to an error: " + me);
        }
    }

    public void updateUsers(String keyFilter, Object valueFilter, String keyToEdit, Object newValue) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        Bson query = eq(keyFilter,valueFilter);
        Bson updateFilter = Updates.set(keyToEdit, newValue);

        try {
            UpdateResult result = collection.updateMany(query, updateFilter);
            logger.info("Modified document count: "+result.getModifiedCount());
        } catch (MongoException me) {
            logger.error("Unable to update any document due to an error: " + me);
        }
    }

    public void deleteUser(String key, String value) {
        MongoCollection<CaupanharmUser> collection = database.getCollection("users", CaupanharmUser.class);
        Bson deleteFilter = Filters.eq(key, value);
        try {
            CaupanharmUser deletedUser = collection.findOneAndDelete(deleteFilter);
            logger.info("Deleted user: " + deletedUser);
        } catch (MongoException me) {
            logger.error("Unable to delete a document due to an error: " + me);
        }
    }

    public List<CaupanharmUser> getUsersFromDiscordId(List<String> ids){
        List<CaupanharmUser> result = new ArrayList<>();
        for(String discordId : ids){
            result.add(getUser("discordId",discordId));
        }
        return result;
    }

}
