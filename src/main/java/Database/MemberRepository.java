package Database;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository extends GenericRepository {
    public MemberRepository() {
        super("Members");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column MEMBER_ID = new Column(1,  "member_id");
        public static final Column FULLNAME  = new Column(2,  "fullname");
        public static final Column ADDRESS   = new Column(4,  "address");
        public static final Column EMAIL     = new Column(8,  "email");
        public static final Column PHONE     = new Column(16, "phone");
        public static final Column JOIN_DATE = new Column(32, "join_date");

        private Column(int index, String name) {
            super(index, name);
            columns.add(this);
        }


    }
}
