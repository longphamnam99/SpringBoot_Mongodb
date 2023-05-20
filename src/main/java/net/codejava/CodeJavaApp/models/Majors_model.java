package net.codejava.CodeJavaApp.models;

import net.codejava.CodeJavaApp.core.MyModel;
import org.bson.Document;

public class Majors_model extends MyModel {
    public Majors_model() {
        super();
        setCollection("majors");
    }

    public boolean add_majors(Document document) {
        boolean result = insertOne(document);
        return result;
    }
}
