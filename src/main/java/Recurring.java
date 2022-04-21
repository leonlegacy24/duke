import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Recurring extends Task {

    protected String Recurring;

    public Recurring(String description, String recurring) {
        super(description);
        this.Recurring = recurring;
    }

    @Override
    public String toString() {
        return "[R]" + super.toString() + " (Every: " + Recurring+ ")";
    }
}