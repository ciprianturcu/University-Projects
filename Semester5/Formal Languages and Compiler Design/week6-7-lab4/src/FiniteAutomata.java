import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteAutomata {

    private List<String> stateSet;
    private List<String> alphabet;
    private Map<Pair<String, String>, Set<String>> transitions;
    private List<String> finalStates;
    private String initialState;

    public FiniteAutomata() {
        stateSet = new ArrayList<>();
        alphabet = new ArrayList<>();
        transitions = new HashMap<>();
        finalStates = new ArrayList<>();
    }

    public void scanFa(String filename) {
        try (FileReader fileReader = new FileReader(filename); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                switch (line) {
                    case "~states" -> stateSet.addAll(List.of(bufferedReader.readLine().split(" ")));
                    case "~alphabet" -> alphabet.addAll(List.of(bufferedReader.readLine().split(" ")));
                    case "~initialState" -> initialState = bufferedReader.readLine();
                    case "~finalStates" -> finalStates.addAll(List.of(bufferedReader.readLine().split(" ")));
                    case "~transitions" -> readTransitions(bufferedReader);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readTransitions(BufferedReader bufferedReader) throws IOException {
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] elements = line.split(" ");
            if(stateSet.contains(elements[0]) && alphabet.contains(elements[1]) && stateSet.contains(elements[2]))
            {
                Pair<String, String> startStateOfTransitionAndPath = new Pair<>(elements[0], elements[1]);

                if(transitions.containsKey(startStateOfTransitionAndPath))
                    transitions.get(startStateOfTransitionAndPath).add(elements[2]);
                else {
                    Set<String> endStatesOfTransition = new HashSet<>();
                    endStatesOfTransition.add(elements[2]);
                    transitions.put(startStateOfTransitionAndPath, endStatesOfTransition);
                }
            }
        }
    }

    public List<String> getStateSet() {
        return stateSet;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public Map<Pair<String, String>, Set<String>> getTransitions() {
        return transitions;
    }

    public boolean isDeterministic(){
        for (Pair<String, String > key : transitions.keySet()) {
            if(transitions.get(key).size() > 1)
                return false;
        }
        return true;
    }

    public boolean isSequenceAccepted(String sequence) {
        if(!isDeterministic())
            return false;

        String currentState = this.initialState;

        while(!sequence.isEmpty())
        {
            Pair<String, String > startStateOfTransitionAndPath = new Pair<>(currentState, String.valueOf(sequence.charAt(0)));
            if(!transitions.containsKey(startStateOfTransitionAndPath))
                return false;
            currentState = this.transitions.get(startStateOfTransitionAndPath).iterator().next();
            sequence = sequence.substring(1);
        }
    
        return finalStates.contains(currentState);
    }
}



