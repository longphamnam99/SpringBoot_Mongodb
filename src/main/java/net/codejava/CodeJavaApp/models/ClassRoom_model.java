package net.codejava.CodeJavaApp.models;

import com.mongodb.client.model.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.codejava.CodeJavaApp.core.MyModel;

public class ClassRoom_model extends MyModel {
    public ClassRoom_model() {
        super();
        setCollection("class_rooms");
    }

    public List<Document> getList() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.lookup("majors", "major", "_id", "majors"),
                Aggregates.unwind("$majors"),
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.computed("_id", "$_id"),
                        Projections.computed("class_room_name", "$name"),
                        Projections.computed("class_room_created_at", "$created_at"),
                        Projections.computed("major_name", "$majors.name"),
                        Projections.computed("major_id", "$majors._id"))));

        List<Document> results = collection("").aggregate(pipeline).into(new ArrayList<>());
        return results;
    }
}
