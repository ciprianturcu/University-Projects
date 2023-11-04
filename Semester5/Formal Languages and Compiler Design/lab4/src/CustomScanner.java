import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomScanner {
    //1b - 2 instances of the symbol table for identifiers and constants

    private SymbolTable<String> symbolTableIdentifiers;
    private SymbolTable<String> symbolTableConstants;
    private ProgramInternalForm programInternalForm;
    private final ArrayList<String> operators = new ArrayList<>(List.of("+", "-", "*", "/", "=", ">", ">=", "<", "<=", "!=", "$", "%", "=="));
    private final ArrayList<String> separators = new ArrayList<>(List.of("{", "}", "(", ")", "[", "]", ",", ";", ".", "\""));
    private final ArrayList<String> reservedWords = new ArrayList<>(List.of("Integer", "String", "Char", "ArrayList", "else", "if", "elif", "while", "for", "return", "out", "in", "return", "add"));
    private int linePosition;
    private int lineCounter;
    private String line;


    public CustomScanner() {
        this.symbolTableConstants = new SymbolTable<>();
        this.symbolTableIdentifiers = new SymbolTable<>();
        this.programInternalForm = new ProgramInternalForm();
    }

    public void scanProgram(String filename) {
        this.linePosition = 0;
        this.lineCounter = 0;
        try (FileReader fileReader = new FileReader(filename); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                processLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine() {
        this.lineCounter++;
        this.linePosition = 0;
        this.line = line.strip();
        while (linePosition < line.length())
            findNextToken();
    }

    private void findNextToken() {
        if (this.linePosition == line.length())
            return;
        treatSpaces();


    }

    private void treatSpaces() {
        while (linePosition < line.length() && Character.isWhitespace(line.charAt(linePosition)))
            this.linePosition++;
    }

    private boolean intConstClassifier() {
        var intConstRegex = Pattern.compile("^(-?[1-9][0-9]*|0)");
        var matcherToFind = intConstRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find())
            return false;

        var invalidateIntConstRegex = Pattern.compile("^(-?[1-9][0-9]*|0)([a-zA-Z0-9_])");
        var matcherToInvalidate = invalidateIntConstRegex.matcher(line.substring(linePosition));
        if (matcherToInvalidate.find()) {
            return false;
        }

        var intConst = matcherToFind.group(1);
        linePosition += intConst.length();
        int position = symbolTableConstants.add(intConst);
        programInternalForm.add("intconst", position);
        return true;
    }

    private boolean stringConstClassifier() {
        var stringConstRegex = Pattern.compile("^\"[a-zA-Z][a-zA-Z_0-9]*\"");
        var matcherToFind = stringConstRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find()) {
//            if(Pattern.compile("^\"[^\"]\"").matcher(line.substring(linePosition)).find()) {
//                return false;
//            }
//            if(Pattern.compile("^\"[^\"]").matcher(line.substring(linePosition)).find()) {
//                return false;
//            }
            return false;
        }

        var stringConst = matcherToFind.group(0);
        linePosition += stringConst.length();
        int position = symbolTableConstants.add(stringConst);
        programInternalForm.add("stringconst", position);
        return true;
    }

    private boolean charConstClassifier() {
        var charConstRegex = Pattern.compile("^\"[a-zA-Z0-9]\"");
        var matcherToFind = charConstRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find()) {
            return false;
        }
        var
        var charConst = matcherToFind.group(0);
        linePosition += charConst.length();
        int position = symbolTableConstants.add(charConst);
        programInternalForm.add("charconst", position);
        return true;
    }

    private boolean identifierClassifier() {
        var identifierRegex = Pattern.compile("^([a-zA-Z][a-zA-Z_0-9]*)");
        var matcherToFind = identifierRegex.matcher(line.substring(linePosition));
        if(!matcherToFind.find())
            return false;

    }

}
