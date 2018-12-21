package mathpuzzles.logics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mathpuzzles.dao.RecordDao;
import mathpuzzles.domain.Record;
import mathpuzzles.domain.User;

/**
 * The logic for records
 */
public class RecordLogic {

    private int amountSolved = 0;
    private User currentUser;
    private RecordDao recordDao;

    public RecordLogic(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    /**
     * Increases amountSolved by one
     */
    public void incrementAmount() {
        amountSolved++;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Sorts the given list of records by amount of solved problems
     * @param records the list to be sorted
     * @return a list sorted by amount of solved problems
     */
    public List<Record> sort(List<Record> records) {
        Collections.sort(records, (Record r1, Record r2) -> {
            if (r1.getSolved() > r2.getSolved()) {
                return -1;
            } else if (r1.getSolved() < r2.getSolved()) {
                return +1;
            } else {
                return 0;
            }
        });

        return records;
    }

    /**
     * Fetches all records from the database for given time
     * @param time the time that all the records have
     * @return List of records in string format
     * @throws SQLException if something fails at database level
     */
    public List<String> getRecords(String time) throws SQLException {
        List<Record> records = recordDao.findAllForTime(time);
        sort(records);
        List<String> recordsString = new ArrayList<>();
        if (!records.isEmpty()) {
            if (records.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    recordsString.add(records.get(i).toString());
                }
            } else {
                for (int i = 0; i < records.size(); i++) {
                    recordsString.add(records.get(i).toString());
                }
            }
        }
        return recordsString;
    }

    /**
     * Saves a new recored to database
     * @param time for the record
     * @throws SQLException if something fails at database level
     */
    public void setRecord(String time) throws SQLException {
        Record record = new Record(time, amountSolved, this.currentUser);

        if (!recordDao.findOneForUserWithTimeAndSolved(currentUser, time, amountSolved)) {
            recordDao.save(record);
        }

        amountSolved = 0;
    }

    public int getAmountSolved() {
        return amountSolved;
    }
}
