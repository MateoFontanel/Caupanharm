package perso.discordbots.caupanharm.controllers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import perso.discordbots.caupanharm.SpringBot;
import perso.discordbots.caupanharm.databases.CaupanharmUser;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Controller
public class MongoController {
    private final static Logger logger = LoggerFactory.getLogger(MongoController.class);

    private final MongoClientSettings settings;
    private final ConnectionString connectionString;
    private final String dbName;
    private MongoDatabase database;
    private MongoClient mongoClient;


    public MongoController(){
        connectionString = null;
        settings = null;
        dbName = null;
    }

    public MongoController(String dbName) {
        PropertyController propertyController = new PropertyController();
        logger.warn(propertyController.getRiotApiKey());
        connectionString = new ConnectionString("mongodb+srv://" + propertyController.getMongodbAdminUsr() + ":" + propertyController.getMongodbAdminPwd() + "@cluster0.ttjwtey.mongodb.net/?retryWrites=true&w=majority");

        // a CodecRegistry tells the Driver how to move data between Java POJOs (Plain Old Java Objects) and MongoDB documents
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // The MongoClient defines the connection to our MongoDB datastore instance (Atlas) using MongoClientSettings
        // You can create a MongoClientSettings with a Builder to configure codecRegistries, connection strings, and more
        this.settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(connectionString).build();

        this.dbName = dbName;

    }


    private void openClient(MongoClientSettings settings, String dbName){
        try {
            mongoClient = MongoClients.create(settings);
            // MongoDatabase defines a connection to a specific MongoDB database
            database = mongoClient.getDatabase(dbName);
        } catch (MongoException me) {
            logger.error("Unable to connect to the MongoDB instance due to an error: " + me);
        }
    }


    public void insertUser(String discordID, String riotId, String riotUsername){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        try {
            InsertOneResult result = collection.insertOne(new CaupanharmUser(discordID,riotId, riotUsername));
            logger.info("Inserted " + result.getInsertedId() + " documents.");
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
        mongoClient.close();
    }

    public void insertUsers(List<CaupanharmUser> users){
        openClient(settings, dbName);
        MongoCollection<CaupanharmUser> collection = database.getCollection("users",CaupanharmUser.class);
        try {
            InsertManyResult result = collection.insertMany(users);
            logger.info("Inserted " + result.getInsertedIds().size() + " documents.");
        } catch (MongoException me) {
            logger.error("Unable to insert any data into MongoDB due to an error: " + me);
        }
        mongoClient.close();
    }

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
                logger.info("Updated the recipe to: " + updatedUser);
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
            collection.deleteOne(deleteFilter);
            logger.info("Document deleted");
        } catch (MongoException me) {
            logger.error("Unable to delete a document due to an error: " + me);
        }
        mongoClient.close();
    }

}