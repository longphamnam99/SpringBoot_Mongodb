package net.codejava.CodeJavaApp.models;
import org.bson.Document;
import net.codejava.CodeJavaApp.core.MyModel;

public class User_model extends MyModel {
    public User_model() {
        super();
        setCollection("users");
    }

    public boolean authenticate(String email, String password) {
        Document query = new Document("email", email);
        Document user = collection("").find(query).first();
        if (user != null) {
            String storedPassword = user.getString("password");
            if (storedPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
}
