import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Integer>> array;

    public ProgramInternalForm() {
        this.array = new ArrayList<>();
    }

    public void add(String token, Integer position) {
        array.add(new Pair<>(token, position));
    }

    @Override
    public String toString() {
        return "ProgramInternalForm : { " + array + " }";
    }
}
