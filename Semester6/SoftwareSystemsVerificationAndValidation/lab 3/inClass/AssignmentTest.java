package test;

import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaXMLRepo;
import service.Service;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class AssignmentTest
{
    private TemaXMLRepo xmlRepo;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/test-teme.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.xmlRepo = new TemaXMLRepo("fisiere/test-teme.xml");
        this.service = new Service(
                null,
                null,
                this.xmlRepo,
                new TemaValidator(),
                null,
                null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/test-teme.xml").delete();
    }

    @Test
    public void testAddAssignmentSuccess() {
        Tema newTema = new Tema("1", "a", 1, 1);

        this.service.addTema(newTema);

        assertEquals(newTema, this.xmlRepo.findOne("1"));
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
        Tema newTema = new Tema("2", "", 1, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Descriere invalida!");
        }
    }

    @Test
    public void testAddAssignmentDeadlineLessThan1() {
        Tema newTema = new Tema("3", "a", 0, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
    }


    @Test
    public void testAddAssignmentDeadlineGreaterThan14() {
        Tema newTema = new Tema("4", "a", 20, 1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Deadlineul trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void testAddAssignmentReceiveGreaterThan14() {
        Tema newTema = new Tema("5", "a", 1, 20);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

    @Test
    public void testAddAssignmentReceiveLessThan1() {
        Tema newTema = new Tema("6", "a", 1, -1);

        try {
            this.service.addTema(newTema);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }

}