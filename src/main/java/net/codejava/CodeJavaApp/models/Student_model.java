package net.codejava.CodeJavaApp.models;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.codejava.CodeJavaApp.core.MyModel;

public class Student_model extends MyModel {
    public Student_model() {
        super();
        setCollection("students");
    }

    public List<Document> getList() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.lookup("class_rooms", "class_room_id", "_id", "class_rooms"),
                Aggregates.unwind("$class_rooms"),
                Aggregates.lookup("majors", "class_rooms.major", "_id", "majors"),
                Aggregates.unwind("$majors"),
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.computed("_id", "$_id"),
                        Projections.computed("name", "$name"),
                        Projections.computed("gender", "$gender"),
                        Projections.computed("date_of_birth", "$date_of_birth"),
                        Projections.computed("created_at", "$created_at"),
                        Projections.computed("class_room_id", "$class_rooms._id"),
                        Projections.computed("class_room_name", "$class_rooms.name"),
                        Projections.computed("major_name", "$majors.name"))));
        List<Document> results = collection("").aggregate(pipeline).into(new ArrayList<>());
        return results;
    }

}
