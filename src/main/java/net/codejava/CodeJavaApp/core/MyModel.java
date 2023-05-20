package net.codejava.CodeJavaApp.core;

import net.codejava.CodeJavaApp.configs.Connect;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class MyModel {
    public static String collectionTB = "";
    public static String private_key = "_id";

    protected MyModel() {
    }

    MyModel(String collection) {
        setCollection(collection);
    }

    public static void setCollection(String collection) {
        collectionTB = collection;
    }

    public MongoCollection<Document> collection(String col) {
        MongoCollection<Document> collection = Connect.db().getCollection(col.isEmpty() ? collectionTB : col);
        return collection;
    }

    public boolean insertOne(Document document) {
        try {
            MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
            InsertOneResult result = collection.insertOne(document);
            if (result.wasAcknowledged()) {
                return true;
            } else {
                return false;
            }
        } catch (MongoException me) {
            return false;
        }
    }

    public List<Document> select() {
        try {
            MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
            List<Document> documents = new ArrayList<>();
            collection.find().into(documents);
            return documents;
        } catch (MongoException me) {
            return null;
        }
    }

    public static Document findOne(String field, String value) {
        MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
        Document query = new Document(field, value);
        return collection.find(query).first();
    }

    public static List<Document> findMultiple(String field, String value) {
        MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
        Document query = new Document(field, value);
        List<Document> results = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(query).iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
        return results;
    }

    public boolean update(String id, Document document) {
        MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
        Document filter = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", document);
        UpdateResult result = collection.updateOne(filter, update);
        if (result.getModifiedCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean delete(String id) {
        MongoCollection<Document> collection = Connect.db().getCollection(collectionTB);
        DeleteResult result = collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
        if (result.getDeletedCount() == 1) {
            return true;
        }
        return false;
    }
}
