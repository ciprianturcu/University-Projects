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

public class IncrementalTest {

    private TemaXMLRepo assignmnetRepo;
    private StudentXMLRepo studentRepo;
    private NotaXMLRepo gradesRepo;

    private Service service;

    @BeforeAll
    static void createXML() {
        createSingleXMLFile("fisiere/test-teme.xml");
        createSingleXMLFile("fisiere/test-studenti.xml");
        createSingleXMLFile("fisiere/test-note.xml");
    }

    private static void createSingleXMLFile(String fileName) {
        File xmlFile = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<inbox>\n\n</inbox>");
            writer.flush();
        } catch (IOException e) {
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
    public void testAddStudentAndAssignment() {

        Student student = new Student("2", "A", 1, "a@a");
        Tema tema = new Tema("1", "Test", 7, 1);

        try {
            service.addStudent(student);
            service.addTema(tema);

            // Get the assignment from the repo
            Tema temaRepo = this.assignmnetRepo.findOne("1");
            assertEquals(tema, temaRepo);

            Student newStudent = this.studentRepo.findOne("2");
            assertEquals(student, newStudent);
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testAddStudentAndAssignmentAndGrade() {
        Student student = new Student("3", "B", 2, "b@b");
        Tema tema = new Tema("3", "Test", 7, 1);
        Nota nota = new Nota("3", "3", "3", 1, LocalDate.now());

        try {
            service.addStudent(student);
            service.addTema(tema);
            service.addNota(nota, "Good");

            Student newStudent = this.studentRepo.findOne("3");
            Tema newTema = this.assignmnetRepo.findOne("3");
            Nota newNota = this.gradesRepo.findOne("3");

            assertEquals(student, newStudent);
            assertEquals(tema, newTema);
            assertEquals(nota, newNota);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}