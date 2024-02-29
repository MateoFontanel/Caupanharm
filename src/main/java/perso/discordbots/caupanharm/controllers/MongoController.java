package perso.discordbots.caupanharm.controllers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import jakarta.annotation.PostConstruct;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Controller
public class MongoController {
    private final static Logger logger = LoggerFactory.getLogger(MongoController.class);

    MongoClientSettings settings;
    ConnectionString connectionString;
    String dbName;
    MongoDatabase database;
    MongoClient mongoClient;

    public MongoController() {
        connectionString = null;
        settings = null;
        dbName = null;
    }

    @Value("${mongodb_admin_usr}")
    String mongodb_admin_usr;

    @Value("${mongodb_admin_pwd}")
    String mongodb_admin_pwd;

    public MongoController(String dbName) {
        this.dbName = dbName;
    }


    @PostConstruct
    void init() {
        connectionString = new ConnectionString("mongodb+srv://" + mongodb_admin_usr + ":" + mongodb_admin_pwd + "@caupanharmcluster.2lh0nde.mongodb.net/?retryWrites=true&w=majority");

        // a CodecRegistry tells the Driver how to move data between Java POJOs (Plain Old Java Objects) and MongoDB documents
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // The MongoClient defines the connection to our MongoDB datastore instance (Atlas) using MongoClientSettings
        // You can create a MongoClientSettings with a Builder to configure codecRegistries, connection strings, and more
        settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(connectionString).build();
    }

    void openClient(MongoClientSettings settings, String dbName) {
        try {
            mongoClient = MongoClients.create(settings);
            // MongoDatabase defines a connection to a specific MongoDB database
            database = mongoClient.getDatabase(dbName);
        } catch (MongoException me) {
            logger.error("Unable to connect to the MongoDB instance due to an error: " + me);
        }
    }


}