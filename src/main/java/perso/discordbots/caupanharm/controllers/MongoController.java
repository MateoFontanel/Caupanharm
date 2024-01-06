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

    final MongoClientSettings settings;
    final ConnectionString connectionString;
    final String dbName;
    MongoDatabase database;
    MongoClient mongoClient;


    public MongoController(){
        connectionString = null;
        settings = null;
        dbName = null;
    }

    public MongoController(String dbName) {
        PropertyController propertyController = new PropertyController();
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


    void openClient(MongoClientSettings settings, String dbName){
        try {
            mongoClient = MongoClients.create(settings);
            // MongoDatabase defines a connection to a specific MongoDB database
            database = mongoClient.getDatabase(dbName);
        } catch (MongoException me) {
            logger.error("Unable to connect to the MongoDB instance due to an error: " + me);
        }
    }




}