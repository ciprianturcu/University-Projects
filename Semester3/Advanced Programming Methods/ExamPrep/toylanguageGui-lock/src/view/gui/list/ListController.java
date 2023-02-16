package view.gui.list;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
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
import repository.InterfaceRepository;
import repository.Repository;
import view.gui.program.ProgramController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListController {

    private ProgramController programController;

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
                PrgState programState = new PrgState(new StackClass<>(), new DictionaryClass<>(), new ListClass<>(), selectedStatement, new DictionaryClass<>(), new HeapClass(), new LockTableClass());
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

        IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new VarDeclStmt("q", new IntType()),
                                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new NewLockStmt("x"),
                                                                new CompStmt(new ForkStmt(
                                                                        new CompStmt(new ForkStmt(
                                                                                new CompStmt(new LockStmt("x"),
                                                                                        new CompStmt(new HeapWriteStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)),"-")),
                                                                                                new UnlockStmt("x")))
                                                                        ),
                                                                                new CompStmt(new LockStmt("x"),
                                                                                        new CompStmt(new HeapWriteStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)),"*")),
                                                                                                new UnlockStmt("x"))))
                                                                ),
                                                                        new CompStmt( new NewLockStmt("q"),
                                                                                new CompStmt(new ForkStmt(
                                                                                        new CompStmt( new ForkStmt(
                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                        new CompStmt(new HeapWriteStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)),"+")),
                                                                                                                new UnlockStmt("q")))
                                                                                        ),
                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                        new CompStmt(new HeapWriteStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)),"*")),
                                                                                                                new UnlockStmt("q"))))
                                                                                ),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new LockStmt("x"),
                                                                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                        new CompStmt(new UnlockStmt("x"),
                                                                                                                                                new CompStmt(new LockStmt("q"),
                                                                                                                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                new UnlockStmt("q"))))))))))))))))))));

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
