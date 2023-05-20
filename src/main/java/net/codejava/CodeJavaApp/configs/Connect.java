package net.codejava.CodeJavaApp.configs;

import com.mongodb.*;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connect {
    private static String host = "localhost";
    private static int port = 27017;
    private static String username = "";
    private static String password = "";
    private static String databaseName = "iuhdb";

    public static MongoClient client() {
        try {
            String auth = (!username.isEmpty() && !password.isEmpty()) ? (username + ":" + password) : "";
            String uri = "mongodb:// " + auth + host + ":" + port + "/?retryWrites=true&w=majority";

            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(uri))
                    .serverApi(serverApi)
                    .build();

            MongoClient mongoClient = MongoClients.create(settings);
            return mongoClient;

        } catch (MongoException me) {
            System.err.println("Failed to connect to MongoDB: " + me.getMessage());
            return null;
        }
    }

    public static MongoDatabase db() {
        try {
            MongoDatabase database = client().getDatabase(databaseName);
            return database;
        } catch (MongoException me) {
            System.err.println("Failed to connect to MongoDB: " + me.getMessage());
            return null;
        }
    }

    public static boolean ping() {
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = db().runCommand(command);
            double okValue = commandResult.getDouble("ok");
            if (okValue > 0) {
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                return true;
            }
            return false;
        } catch (MongoException me) {
            System.err.println("Failed to connect to MongoDB: " + me.getMessage());
            return false;
        }
    }
}