
package mathpuzzles.domain;

/**
 * Represents a record set by a user
 */
public class Record {
    Integer id;
    String time;
    Integer solved;
    User user;

    public Record(String time, Integer solved, User user) {
        this.time = time;
        this.solved = solved;
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSolved() {
        return solved;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.getName() + "   " + solved.toString();
    }
}
