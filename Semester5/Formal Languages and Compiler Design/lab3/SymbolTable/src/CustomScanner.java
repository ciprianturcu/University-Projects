import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomScanner {
    //1b - 2 instances of the symbol table for identifiers and constants

    private SymbolTable<String> symbolTableIdentifiers;
    private SymbolTable<String> symbolTableConstants;
    private ProgramInternalForm programInternalForm;

    public CustomScanner() {
        this.symbolTableConstants = new SymbolTable<String>();
        this.symbolTableIdentifiers = new SymbolTable<>();
        this.programInternalForm = new ProgramInternalForm();
    }

    public void compileProgram(String filename) {

    }

    public void readFile(String filename) {
        try (FileReader fileReader = new FileReader(filename); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processLine(String line) {

    }
}
