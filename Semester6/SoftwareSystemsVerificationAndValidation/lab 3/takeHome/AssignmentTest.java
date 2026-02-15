package test;

import domain.Tema;
import org.junit.After;
import org.junit.jupiter.api.*;
import repository.TemaXMLRepo;
import service.Service;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AssignmentTest {
    private TemaXMLRepo xmlRepo;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/test-teme.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<inbox>\n" + "\n" + "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.xmlRepo = new TemaXMLRepo("fisiere/test-teme.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null, this.xmlRepo, this.temaValidator, null, null);
    }

    @AfterEach
    void removeXML() {
        new File("fisiere/test-teme.xml").delete();
    }

    @Test
    public void testAddAssignmentDuplicateAssignment() {
        Tema newTema = new Tema("1", "a", 6, 8);
        Tema newTema2 = new Tema("1", "a", 6, 8);
        try {
            this.service.addTema(newTema);
            assertEquals(this.service.addTema(newTema2).getID(), newTema.getID());
        }
        catch (Exception e) {fail();}
    }


    @Test
    public void testAddAssignmentEmptyId() {
        Tema newTema = new Tema("", "a", 1, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Numar tema invalid!");
        }
    }

    @Test
    public void testAddAssignmentNullId() {
        Tema newTema = new Tema(null, "a", 1, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Numar tema invalid!");
        }
    }

    @Test
    public void testAddAssignmentEmptyDescription() {
        Tema newTema = new Tema("1", "", 1, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Descriere invalida!");
        }
    }

    @Test
    public void testAddAssignmentDeadlineGreaterThan14() {
        Tema newTema = new Tema("1", "a", 15, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void testAddAssignmentDeadlineLessThan1() {
        Tema newTema = new Tema("1", "a", 0, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
    }


    @Test
    public void testAddAssignmentReceiveGreaterThan14() {
        Tema newTema = new Tema("1", "a", 1, 15);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void testAddAssignmentReceiveLessThan1() {
        Tema newTema = new Tema("1", "a", 1, 0);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void testAddAssignmentSuccess() {
        Tema newTema = new Tema("1", "a", 6, 7);

        try {
            Tema result = this.service.addTema(newTema);
            assertNull(result);
            assertEquals(newTema, this.service.getAllTeme().iterator().next());
        } catch (Exception e) {
            fail();
        }
    }

}