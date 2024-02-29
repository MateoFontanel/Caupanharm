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
import perso.discordbots.caupanharm.models.CaupanharmTeam;

public class TeamController extends MongoController{
    private final static Logger logger = LoggerFactory.getLogger(TeamController.class);

    String dbName;

    public TeamController(String dbName){
        this.dbName = dbName;
    }

    public void insertTeam(CaupanharmTeam team){
        super.openClient(settings, dbName);
        MongoCollection<CaupanharmTeam> collection = database.getCollection("teams",CaupanharmTeam.class);
        try {
            collection.insertOne(team);
            logger.info("Created team: "+team);
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
        mongoClient.close();
    }

    public CaupanharmTeam getTeamByName(String requestedTeam){
        openClient(settings, dbName);
        MongoCollection<CaupanharmTeam> collection = database.getCollection("teams",CaupanharmTeam.class);
        Bson filter = Filters.eq("name", requestedTeam);
        CaupanharmTeam result = null;
        try{
            result = collection.find(filter).first();
        }catch(MongoException me){
            logger.error("Unable to find any team due to an error: "+me);
        }
        mongoClient.close();
        return result;
    }

    public void updateTeam(String keyFilter, Object valueFilter, String keyToEdit, Object newValue){
        openClient(settings, dbName);
        MongoCollection<CaupanharmTeam> collection = database.getCollection("teams",CaupanharmTeam.class);
        Bson updateFilter = Updates.set(keyToEdit, newValue);
        // The following FindOneAndUpdateOptions specify that we want it to return
        // the *updated* document to us. By default, we get the document as it was *before*
        // the update.
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
        // The updatedDocument object is an object that reflects the changes we just made.
        try {
            CaupanharmTeam updatedTeam = collection.findOneAndUpdate(Filters.eq(keyFilter, valueFilter), updateFilter, options);
            if (updatedTeam == null) {
                logger.error("Couldn't update the document. Did someone (or something) delete it?");
            } else {
                logger.info("Updated team: " + updatedTeam);
            }

        } catch (MongoException me) {
            logger.error("Unable to update any document due to an error: " + me);
        }
        mongoClient.close();
    }

    public void replaceTeam(String name, CaupanharmTeam replacement){
        openClient(settings, dbName);
        MongoCollection<CaupanharmTeam> collection = database.getCollection("teams",CaupanharmTeam.class);
        Bson query = Filters.eq("name",name);
        UpdateResult result = collection.replaceOne(query, replacement);
        logger.info("Replaced "+result.getModifiedCount()+" document in teams collection");
    }

    public void deleteTeam(String key, String value){
        openClient(settings, dbName);
        try {
            MongoCollection<CaupanharmTeam> collection = database.getCollection("teams",CaupanharmTeam.class);
            Bson deleteFilter = Filters.eq(key, value);
            CaupanharmTeam deletedTeam = collection.findOneAndDelete(deleteFilter);
            logger.info("Deleted user: "+deletedTeam);
        } catch (MongoException me) {
            logger.error("Unable to delete a document due to an error: " + me);
        }
        mongoClient.close();
    }

}
