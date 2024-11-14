package Database;

import com.nichga.proj97.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends GenericRepository {
    public UserRepository() {
        super("users");
    }

    enum Columns {
        ID(1, "id"),
        USERNAME(2, "username"),
        PASSWORD(4, "password"),
        FULLNAME(8, "fullname"),;

        final int index;

        final String name;

        Columns(int col, String name) {
            index = col;
            this.name = name;
        }

        private static boolean validate(int attr) {
            for (Columns col : Columns.values()) {
                if ((col.index & attr) != 0) {
                    return true;
                }
            }
            return false;
        }

        public static List<String> getName(int attr) {
            List<String> rs = new ArrayList<String>();
            if (!validate(attr)) {
                return rs;
            }
            for (Columns col : Columns.values()) {
                if ((col.index & attr) != 0) {
                    rs.add(col.name);
                }
            }
            return rs;
        }
    }

    private PreparedStatement createStatement(String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stmt;
    }

    public List<User> getUserByName(String name, int attr) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(
            String.join(", ", Columns.getName(attr))
        );
        sql.append(" FROM " + tableName + " WHERE ").;
        PreparedStatement stmt = createStatement(sql.toString());
        ResultSet rs = executeQuery(stmt, name);
    }

    @Override
    protected ResultSet executeQuery(PreparedStatement stmt, String... args) {
        ResultSet rs = null;
        try {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    @Override
    protected int executeUpdate(PreparedStatement stmt, String... args) {
        return 0;
    }
}
