import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CustomScanner {
    //1b - 2 instances of the symbol table for identifiers and constants

    private SymbolTable<String> symbolTableIdentifiers;
    private SymbolTable<String> symbolTableConstants;
    private ProgramInternalForm programInternalForm;
    private final ArrayList<String> operators = new ArrayList<>(List.of( "&&","||","==", ">=", "<=", "!=", "+", "-", "*", "/", "=", ">", "<",  "$", "%"));
    private final ArrayList<String> separators = new ArrayList<>(List.of("{", "}", "(", ")", "[", "]", ",", ";", ".", "\""));
    private final ArrayList<String> reservedWords = new ArrayList<>(List.of("Integer", "String", "Char", "ArrayList", "else", "if", "elif", "while", "for", "return", "out", "in", "add", "len", "and", "or"));
    private int linePosition;
    private int lineCounter;
    private String line;


    public CustomScanner() {
        this.symbolTableConstants = new SymbolTable<>();
        this.symbolTableIdentifiers = new SymbolTable<>();
        this.programInternalForm = new ProgramInternalForm();
        this.linePosition = 0;
        this.lineCounter = 0;
    }

    public void scanProgram(String filename) {
        this.linePosition = 0;
        this.lineCounter = 0;
        try (FileReader fileReader = new FileReader(filename); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                processLine();
            }
            createOutputOfScan(filename);
        } catch (IOException | ScannerException e) {
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
        if (stringConstClassifier())
            return;
        if (intConstClassifier())
            return;
        if (charConstClassifier())
            return;
        if (identifierClassifier())
            return;
        if (tokenClassifier())
            return;
        throw new ScannerException("Lexical error at line: " + lineCounter + " on position : " + linePosition);
    }

    private void treatSpaces() {
        while (linePosition < line.length() && Character.isWhitespace(line.charAt(linePosition)))
            this.linePosition++;
    }

    private boolean intConstClassifier() {
        var intConstRegex = Pattern.compile("^((-?[1-9][0-9]*)|0)");
        var matcherToFind = intConstRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find())
            return false;

        var intConst = matcherToFind.group(0);
        linePosition += intConst.length();
        int position = symbolTableConstants.add(intConst);
        programInternalForm.add("intconst", position);
        return true;
    }

    private boolean stringConstClassifier() {
        var stringConstRegex = Pattern.compile("^\"([a-zA-Z][a-zA-Z_0-9]*)\"");
        var matcherToFind = stringConstRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find()) {
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
        var charConst = matcherToFind.group(0);
        linePosition += charConst.length();
        int position = symbolTableConstants.add(charConst);
        programInternalForm.add("charconst", position);
        return true;
    }

    private boolean identifierClassifier() {
        var identifierRegex = Pattern.compile("^([a-zA-Z][a-zA-Z_0-9]*)");
        var matcherToFind = identifierRegex.matcher(line.substring(linePosition));
        if (!matcherToFind.find())
            return false;

        var identifier = matcherToFind.group(0);
        if (reservedWords.contains(identifier))
            return false;

        linePosition += identifier.length();
        var position = symbolTableIdentifiers.add(identifier);
        programInternalForm.add("identifier", position);
        return true;
    }


    private boolean tokenClassifier() {
        String possibleToken = line.substring(linePosition).split(" ")[0];
        return checkTokenForOperators(possibleToken) || checkTokenForSeparators(possibleToken) || checkTokenForReservedWords(possibleToken);
    }

    private boolean checkTokenForOperators(final String token) {
        for (var operator : operators) {
            if (Objects.equals(operator, token) || token.startsWith(operator)) {
                linePosition += operator.length();
                programInternalForm.add(operator, -1);
                return true;
            }
        }
        return false;
    }

    private boolean checkTokenForSeparators(final String token) {
        for (var separator : separators) {
            if (Objects.equals(separator, token) || token.startsWith(separator)) {
                linePosition += separator.length();
                programInternalForm.add(separator, -1);
                return true;
            }
        }
        return false;
    }

    private boolean checkTokenForReservedWords(final String token) {
        for (var reservedWord : reservedWords) {
            if (token.startsWith(reservedWord)) {
                var invalidReservedWordRegex = Pattern.compile("^[a-zA-Z0-9_]* + res + [a-zA-Z0-9_]+");
                var matcherToInvalidate = invalidReservedWordRegex.matcher(line.substring(linePosition));
                if (matcherToInvalidate.find())
                    return false;
                linePosition += reservedWord.length();
                programInternalForm.add(reservedWord, -1);
                return true;
            }
        }
        return false;
    }

    private void createOutputOfScan(final String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(new File("src/outpifst", "ST-"+filename.split("\\.")[0]+".out"));
        fileWriter.write("------------------------------------------------------------\n");
        fileWriter.write("Symbol Table - constants\n");
        fileWriter.write("------------------------------------------------------------\n");
        var symbolTableConstantsElementsWithPositions = symbolTableConstants.getTable().getElementsWithPositions();
        for (Pair<String, Integer> pair : symbolTableConstantsElementsWithPositions) {
            fileWriter.write(createPaddedString(pair.getFirst(), pair.getSecond().toString())+"\n");
        }
        fileWriter.write("\n\n\n------------------------------------------------------------\n");
        fileWriter.write("Symbol Table - identifiers\n");
        fileWriter.write("------------------------------------------------------------\n");
        var symbolTableIdentifiersElementsWithPositions = symbolTableIdentifiers.getTable().getElementsWithPositions();
        for (Pair<String, Integer> pair : symbolTableIdentifiersElementsWithPositions) {
            fileWriter.write(createPaddedString(pair.getFirst(), pair.getSecond().toString())+"\n");
        }
        fileWriter.close();

        fileWriter = new FileWriter(new File("src/outpifst", "PIF-"+filename.split("\\.")[0]+".out"));
        fileWriter.write("------------------------------------------------------------\n");
        fileWriter.write("Program Internal Form\n");
        fileWriter.write("------------------------------------------------------------\n");
        for (Pair<String, Integer> pair : programInternalForm.getArray()) {
            fileWriter.write(createPaddedString(pair.getFirst(), pair.getSecond().toString())+"\n");
        }
        fileWriter.close();
    }
    private static String createPaddedString(String firstString, String secondString) {
        StringBuilder paddedString = new StringBuilder();

        // Append the first string
        paddedString.append(firstString);

        // Calculate the number of spaces needed
        int spacesToAdd = 40 - firstString.length() - secondString.length();

        // Append the required spaces
        if (spacesToAdd > 0) {
            for (int i = 0; i < spacesToAdd; i++) {
                paddedString.append(' ');
            }
        }

        // Append the second string
        paddedString.append(secondString);

        return paddedString.toString();
    }
}
