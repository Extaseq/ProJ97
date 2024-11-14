package Application;

import java.sql.Connection;
import com.nichga.proj97.Documents;

public class DocumentManager {
    private Connection connection = null;

    private static DocumentManager instance = null;

    private DocumentManager() {}

    public static synchronized DocumentManager getInstance() {
        if (instance == null) {
            instance = new DocumentManager();
        }
        return instance;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void addDocument(Documents document, int copies) {
            
    }
}
