package test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BigBangTest {

    private TemaXMLRepo assignmnetRepo;
    private StudentXMLRepo studentRepo;
    private NotaXMLRepo gradesRepo;

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

        File xml2 = new File("fisiere/test-studenti.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml2))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        File xml3 = new File("fisiere/test-note.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml3))) {
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
        this.assignmnetRepo = new TemaXMLRepo("fisiere/test-teme.xml");
        this.studentRepo = new StudentXMLRepo("fisiere/test-studenti.xml");
        this.gradesRepo = new NotaXMLRepo("fisiere/test-note.xml");

        this.service = new Service(
                this.studentRepo,
                new StudentValidator(),
                this.assignmnetRepo,
                new TemaValidator(),
                this.gradesRepo,
                new NotaValidator(this.studentRepo, this.assignmnetRepo)
        );
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/test-studenti.xml").delete();
        new File("fisiere/test-note.xml").delete();
        new File("fisiere/test-teme.xml").delete();
    }


    @Test
    public void testAddStudent() {
        Student student = new Student("1", "A", 1, "a@a");

        try {
            service.addStudent(student);

            // Get the student from the repository
            Student newStudent = this.studentRepo.findOne("1");

            assertEquals(student, newStudent);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddAssignment() {
        Tema tema = new Tema("1", "Test", 7, 1);

        try {
            service.addTema(tema);

            // Get the assignment from the repo
            Tema temaRepo = this.assignmnetRepo.findOne("1");

            assertEquals(tema, temaRepo);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testAddGrade() {
        Nota nota = new Nota("1", "1", "1", 1, LocalDate.now());

        try {
            service.addNota(nota, "Good");

            // Get the grade from the repo
            Nota notaRepo = this.gradesRepo.findOne("1");

            assertEquals(nota, notaRepo);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBigBang() {
        Student student = new Student("2", "B", 2, "b@b");
        Tema tema = new Tema("2", "Test", 7, 1);
        Nota nota = new Nota("2", "2", "2", 1, LocalDate.now());

        try {
            service.addStudent(student);
            service.addTema(tema);
            service.addNota(nota, "Good");

            Student newStudent = this.studentRepo.findOne("2");
            Tema newTema = this.assignmnetRepo.findOne("2");
            Nota newNota = this.gradesRepo.findOne("2");

            assertEquals(student, newStudent);
            assertEquals(tema, newTema);
            assertEquals(nota, newNota);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
