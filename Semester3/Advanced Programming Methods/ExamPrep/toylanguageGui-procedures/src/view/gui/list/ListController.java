package view.gui.list;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import model.ADT.*;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.MyException;
import model.exceptions.StatementException;
import model.expressions.*;
import model.statements.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.InterfaceRepository;
import repository.Repository;
import view.gui.program.ProgramController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListController {

    private ProgramController programController;
    private InterfaceProcedureTable procedureTable;

    public void setProgramController (ProgramController programController)
    {
        this.programController = programController;
    }

    @FXML
    private ListView<IStmt> programListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        this.procedureTable = new ProcedureTable();
        programListView.setItems(getStatements());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStatement = programListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typeCheck(new DictionaryClass<>());
                InterfaceStack<InterfaceDictionary<String, Value>> symbolTableStack = new StackClass<>();
                symbolTableStack.push(new DictionaryClass<>());
                PrgState programState = new PrgState(new StackClass<>(), symbolTableStack, new ListClass<>(), selectedStatement, new DictionaryClass<>(), new HeapClass(), procedureTable);
                InterfaceRepository repository = new Repository(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programController.setController(controller);
            } catch (MyException | StatementException | ADTException | IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    private ObservableList<IStmt> getStatements()
    {
        List<IStmt> statements = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp(
                new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(
                new IntValue(5)), "*"), "+")), new CompStmt(new AssignStmt("b", new ArithExp(
                new VarExp("a"), new ValueExp(new IntValue(1)), "+")), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                        new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",
                                new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenReadFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFile(new VarExp("varf"))))))))));


        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType( new RefType( new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt( new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)), "+")))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType( new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)),"+"))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new BoolType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),"-")))),
                                new PrintStmt(new VarExp("v")))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt( new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a",new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt( new CompStmt( new HeapWriteStmt("a",new ValueExp(new IntValue(30))),new CompStmt( new AssignStmt("v",new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));


        IStmt sumProc = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"), "+")),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varSum = new ArrayList<>();
        varSum.add("a");
        varSum.add("b");
        procedureTable.put("sum", new Pair<>(varSum, sumProc));

        IStmt prodProc = new CompStmt(
                new VarDeclStmt("v" , new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"),"*")),
                        new PrintStmt(new VarExp("v"))
                )
        );

        List<String> varProd = new ArrayList<>();
        varProd.add("a");
        varProd.add("b");
        procedureTable.put("product", new Pair<>(varProd, prodProc));

        IStmt ex11 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("w", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new CompStmt(
                                        new AssignStmt("w", new ValueExp(new IntValue(5))),
                                        new CompStmt(
                                                new CallProcedureStmt("sum", new ArrayList<>(Arrays.asList(new ArithExp(new VarExp("v"), new ValueExp(new IntValue(10)),"*"), new VarExp("w")))),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new CallProcedureStmt("product", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w")))),
                                                                        new ForkStmt(
                                                                                new CallProcedureStmt("sum", new ArrayList<>(Arrays.asList(new VarExp("v"), new VarExp("w"))))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        statements.add(ex1);
        statements.add(ex2);
        statements.add(ex3);
        statements.add(ex4);
        statements.add(ex5);
        statements.add(ex6);
        statements.add(ex7);
        statements.add(ex8);
        statements.add(ex9);
        statements.add(ex10);
        statements.add(ex11);
        return FXCollections.observableArrayList(statements);
    }

}
