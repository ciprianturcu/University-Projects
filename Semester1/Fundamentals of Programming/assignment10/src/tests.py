import unittest

from src.my_stuff.collection import Collection
from src.domain.assignments import Assignment, AssignmentValidator
from src.domain.grade import GradeValidator, Grade
from src.domain.student import Student, StudentValidator
from src.domain.validator_exception import ValidatorException
from src.repository.repository import Repository, RepositoryException
from src.services.assignments_service import AssignmentService
from src.services.grade_service import GradeService
from src.services.student_service import StudentService
from src.services.undo_service import UndoRedoService, UndoServiceException


class StudentDomainTest(unittest.TestCase):
    def setUp(self) -> None:
        self._test_assignment = Assignment("14", "lab", "23/05/2021")
        self._student_id = "18"
        self._student_name = "George"
        self._student_group = 99
        self._test_student = Student(self._student_id, self._student_name, self._student_group)
        self._validator = StudentValidator()
        self._student_invalid_id_1 = "-1"
        self._student_invalid_id_2 = "4.0"
        self._student_invalid_group_1 = -1
        self._student_invalid_group_2 = 4.0
        self._test_student_invalid_id_1 = Student(self._student_invalid_id_1, self._student_name, self._student_group)
        self._test_student_invalid_id_2 = Student(self._student_invalid_id_2, self._student_name, self._student_group)
        self._test_student_invalid_group_1 = Student(self._student_id, self._student_name,
                                                     self._student_invalid_group_1)
        self._test_student_invalid_group_2 = Student(self._student_id, self._student_name,
                                                     self._student_invalid_group_2)

    def test__creationOfStudent__validInput(self):
        assert self._test_student.id == self._student_id
        assert self._test_student.name == self._student_name
        assert self._test_student.group == self._student_group

    def test__strOfStudent__validInput(self):
        assert str(self._test_student) == "Id:18 Name:George Group:99"

    def test__validationOfStudent__validInput__returnTrue(self):
        assert self._validator.validate(self._test_student) == True

    def test__validationOfStudent__invalidInputWrongType__throwsException(self):
        try:
            self._validator.validate(self._test_assignment)
        except TypeError as te:
            assert str(te) == "Can only validate Student objects!"

    def test__validationOfStudent__invalidInputId1__throwsException(self):
        try:
            self._validator.validate(self._test_student_invalid_id_1)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer id. \n"

    def test__validationOfStudent__invalidInputId2__throwsException(self):
        try:
            assert self._validator.validate(self._test_student_invalid_id_2)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer id. \n"

    def test__validationOfStudent__invalidInputGroup1__throwsException(self):
        try:
            assert self._validator.validate(self._test_student_invalid_group_1)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer group. \n"

    def test__validationOfStudent__invalidInputGroup2__throwsException(self):
        try:
            assert self._validator.validate(self._test_student_invalid_group_2)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer group. \n"

    def tearDown(self) -> None:
        pass


class AssignmentDomainTest(unittest.TestCase):
    def setUp(self) -> None:
        self._test_student = Student('15', 'George', 90)
        self._assignment_id = "10"
        self._assignment_description = "lab test"
        self._assignment_deadline = "12/12/2021"
        self._test_assignment = Assignment(self._assignment_id, self._assignment_description, self._assignment_deadline)
        self._validator = AssignmentValidator()
        self._assignment_invalid_id_1 = "-1"
        self._assignment_invalid_id_2 = "4.0"
        self._assignment_invalid_deadline_1 = "32/1000/0"
        self._assignment_invalid_deadline_2 = "random/string/random"
        self._test_assignment_invalid_id_1 = Assignment(self._assignment_invalid_id_1, self._assignment_description,
                                                        self._assignment_deadline)
        self._test_assignment_invalid_id_2 = Assignment(self._assignment_invalid_id_2, self._assignment_description,
                                                        self._assignment_deadline)
        self._test_assignment_invalid_deadline_1 = Assignment(self._assignment_id, self._assignment_description,
                                                              self._assignment_invalid_deadline_1)
        self._test_assignment_invalid_deadline_2 = Assignment(self._assignment_id, self._assignment_description,
                                                              self._assignment_invalid_deadline_2)

    def test__creationOfAssignment__validInput(self):
        assert self._test_assignment.id == self._assignment_id
        assert self._test_assignment.description == self._assignment_description
        assert self._test_assignment.deadline == self._assignment_deadline

    def test__strOfAssignment__validInput(self):
        assert str(self._test_assignment) == "Id:10 Description:lab test Deadline:12/12/2021"

    def test__validationOfAssignment__validInput__returnTrue(self):
        assert self._validator.validate(self._test_assignment) == True

    def test__validationOfAssignment__invalidInputWrongType__throwsTypeError(self):
        try:
            self._validator.validate(self._test_student)
        except TypeError as te:
            assert str(te) == "Can only validate Assignment objects!"

    def test__validationOfAssignment__invalidInputId1__throwsException(self):
        try:
            self._validator.validate(self._test_assignment_invalid_id_1)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have an integer id!\n"

    def test__validationOfAssignment__invalidInputId2__throwsException(self):
        try:
            self._validator.validate(self._test_assignment_invalid_id_2)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have an integer id!\n"

    def test__validationOfAssignment__invalidInputDeadline1__throwsException(self):
        try:
            self._validator.validate(self._test_assignment_invalid_deadline_1)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have a valid deadline!\n"

    def test__validationOfAssignment__invalidInputDeadline2__throwsException(self):
        try:
            self._validator.validate(self._test_assignment_invalid_deadline_2)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have a valid deadline!\n"

    def tearDown(self) -> None:
        pass


class GradeDomainTests(unittest.TestCase):
    def setUp(self) -> None:
        self._grade_validator = GradeValidator()

    def test__validateGrade__validInput__validatesTrue(self):
        assert self._grade_validator.validate(Grade(('10', '15'), '0')) == True

    def test__validateGrade__invalidId__throwsException(self):
        try:
            self._grade_validator.validate(Grade(('abc', '15'), '0'))
        except ValidatorException as ve:
            assert str(
                ve) == "Validation error!\nGrade must have a positive integer tuple id formed from the Assignment id and Student id.\n"

    def test__validateGrade__invalidType__throwsTypeException(self):
        try:
            self._grade_validator.validate(Assignment('15', 'ceva', '29/09/2020'))
        except TypeError as te:
            assert str(te) == "Can only validate Grade objects!"

    def tearDown(self) -> None:
        pass


class RepositoryTests(unittest.TestCase):
    def setUp(self) -> None:
        self._repository = Repository()
        self._entity1 = Student("15", "George", 60)
        self._entity2 = Assignment("16", "Cristi", 50)

    def test__addToRepository__validInput__addsStudentToRepository(self):
        self._repository.add_entity(Student("15", "George", 60))
        assert self._repository['15'].id == self._entity1.id
        assert self._repository['15'].name == self._entity1.name
        assert self._repository['15'].group == self._entity1.group

    def test__addToRepository__invalidInputDuplicateEntity__throwsExceptionWhenAddingSameStudentTwice(self):
        self._repository.add_entity(self._entity1)
        try:
            self._repository.add_entity(self._entity1)
        except RepositoryException as re:
            assert str(re) == "Entity with Id " + str(self._entity1.id) + " already in repo"

    def test__repositoryHasElement__ValidInput__returnsTrue(self):
        self._repository.add_entity(self._entity2)
        assert self._repository.has_element(self._entity2.id) == True

    def test__repositoryHasElement__ValidInput__returnsFalse(self):
        self._repository.add_entity(self._entity1)
        assert self._repository.has_element(self._entity2) == False

    def test__getAllFromRepository__validInput__returnsListWithObjects(self):
        _test_list = list()
        _test_list.append(self._entity1)
        _test_list.append(self._entity2)
        self._repository.add_entity(self._entity1)
        self._repository.add_entity(self._entity2)
        _list_from_repository = self._repository.get_all()
        assert type(_test_list) == type(_list_from_repository)
        assert len(_test_list) == len(_list_from_repository)
        self.assertEqual(_test_list, _list_from_repository)

    def test__getItemFromRepository__validInput__returnItem(self):
        self._repository.add_entity(self._entity1)
        Object = self._repository['15']
        assert Object == self._repository['15']

    def test__setItemFromRepository__validInput__setItem(self):
        self._repository.add_entity(self._entity1)
        self._repository['15'] = Student('15', 'Grigore', 45)
        assert self._repository['15'].name == 'Grigore'
        assert self._repository['15'].group == 45

    def test__deleteItemFromRepository__validInput__deletesItem(self):
        self._repository.add_entity(self._entity1)
        self._repository.delete('15')
        assert len(self._repository.get_all()) == 0

    def tearDown(self) -> None:
        pass


class StudentServiceTests(unittest.TestCase):
    def setUp(self) -> None:
        self._student_repository = Repository()
        self._assignment_repository = Repository()
        self._grade_repository = Repository()
        self._grade_validator = GradeValidator()
        self._undo_redo_service = UndoRedoService()
        self._grade_service = GradeService(self._undo_redo_service, self._grade_repository, self._grade_validator,
                                           self._student_repository,
                                           self._assignment_repository)
        self._validator = StudentValidator()
        self._service = StudentService(self._undo_redo_service, self._student_repository, self._validator,
                                       self._grade_service, self._grade_repository)
        self._student_1 = Student('15', 'Mircea', 30)
        self._student_2 = Student('16', 'Cristi', 30)
        self._assignment_1 = Assignment('15', 'lab32', '25/09/2021')
        self._assignment_2 = Assignment('16', 'lab33', '25/09/2021')
        self._grade_1 = Grade(('15', '15'), '0')
        self._grade_2 = Grade(('15', '16'), '0')
        self._student_with_invalid_id = Student('-1', 'Gabi', 80)
        self._student_with_invalid_group = Student('1', 'Mirciulica', -100)
        self._updated_student_1 = Student('15', 'Andrei', 60)

    def test__getAllStudentsFromRepository__validInput__returnsListOfStudentObjects(self):
        self._student_repository.add_entity(self._student_1)
        student_list = self._service.get_values()
        assert len(student_list) == 1
        assert student_list[0] == self._student_1

    def test__generateStudents__validInput__creates20Students(self):
        self._service.generate_students()
        assert len(self._student_repository.get_all()) == 20

    def test__addStudentToRepository__validStudent__addsStudentToRepository(self):
        self._service.add(self._student_1)
        assert len(self._student_repository.get_all()) == 1
        assert self._student_repository['15'] == self._student_1

    def test__addStudentToRepository__invalidStudentId__throwsException(self):
        try:
            self._service.add(self._student_with_invalid_id)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer id. \n"

    def test__addStudentToRepository__invalidStudentGroup__throwsException(self):
        try:
            self._service.add(self._student_with_invalid_group)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nStudent must have an integer group. \n"

    def test_addStudentToRepository__validStudent__throwsExceptionDuplicateStudent(self):
        self._service.add(self._student_1)
        try:
            self._service.add(self._student_1)
        except RepositoryException as re:
            assert str(re) == "Entity with Id " + str(self._student_1.id) + " already in repo"

    def test_removeStudentFromRepository__validInput__removesStudentFromRepository(self):
        self._student_repository.add_entity(self._student_1)
        self._student_repository.add_entity(self._student_2)
        self._assignment_repository.add_entity(self._assignment_1)
        self._assignment_repository.add_entity(self._assignment_2)
        self._grade_repository.add_entity(self._grade_1)
        self._grade_repository.add_entity(self._grade_2)
        self._service.remove(self._student_1.id)
        assert len(self._service.get_values()) == 1
        assert self._service.get_values()[0] == self._student_2
        assert len(self._grade_repository.get_all()) == 1

    def test__removeStudentFromRepository__validStudentId__throwsExceptionStudentToRemoveNotInRepository(self):
        self._service.add(self._student_1)
        try:
            self._service.remove(self._student_2.id)
        except ValueError as ve:
            assert str(ve) == "Student not found!"

    def test__removeStudentFromRepository__invalidStudentId__throwsExceptionStudentIdToRemoveNotInteger(self):
        self._service.add(self._student_1)
        try:
            self._service.remove("random_test_string")
        except ValueError as ve:
            assert str(ve) == "ID should be an integer!"

    def test__updateStudentFromRepository__validStudent__updatesStudentInRepository(self):
        self._service.add(self._student_1)
        self._service.update(self._updated_student_1)
        assert self._service.get_values()[0] == self._updated_student_1

    def test__updateStudentFromRepository__validStudent__throwsExceptionUpdatedStudentNotInRepository(self):
        self._service.add(self._student_2)
        try:
            self._service.update(self._updated_student_1)
        except ValueError as ve:
            assert str(ve) == "Object not found! Cannot update."

    def tearDown(self) -> None:
        pass


class AssignmentServiceTests(unittest.TestCase):
    def setUp(self) -> None:
        self._student_repository = Repository()
        self._assignment_repository = Repository()
        self._grade_repository = Repository()
        self._grade_validator = GradeValidator()
        self._undo_redo_service = UndoRedoService()
        self._grade_service = GradeService(self._undo_redo_service, self._grade_repository, self._grade_validator,
                                           self._student_repository,
                                           self._assignment_repository)
        self._validator = AssignmentValidator()
        self._service = AssignmentService(self._undo_redo_service, self._assignment_repository, self._validator,
                                          self._grade_service, self._grade_repository)
        self._student_1 = Student('15', 'mircea', '80')
        self._student_2 = Student('16', 'cristi', '90')
        self._assignment_1 = Assignment('15', 'lab6', '17/10/1999')
        self._assignment_2 = Assignment('14', 'lab7', '15/9/2020')
        self._grade_1 = Grade(('15', '15'), '0')
        self._grade_2 = Grade(('14', '16'), '8')
        self._grade_3 = Grade(('15', '16'), '6')
        self._updated_assignment_1 = Assignment('15', 'lab8', '15/10/2022')
        self._assignment_invalid_id = Assignment("-1", "lab1", "21/2/2019")
        self._assignment_invalid_deadline = Assignment('15', 'lab2', '300/10/2020')

    def test__getAllValuesFromRepository__validInput__returnsListOfObjects(self):
        self._assignment_repository.add_entity(self._assignment_1)
        assert len(self._service.get_values()) == 1
        assert self._service.get_values()[0] == self._assignment_1

    def test__generateAssignments__validInput__generates20Assignments(self):
        self._service.generate_assignments_data()
        assert len(self._service.get_values()) == 20

    def test__addAssignmentToRepository__validInput__addsAssignmentToRepository(self):
        self._service.add(self._assignment_1)
        assert len(self._service.get_values()) == 1
        assert self._assignment_repository['15'] == self._assignment_1

    def test__addAssignmentToRepository__invalidAssignmentId__throwsException(self):
        try:
            self._service.add(self._assignment_invalid_id)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have an integer id!\n"

    def test__addAssignmentToRepository__invalidAssignmentDeadline__throwsException(self):
        try:
            self._service.add(self._assignment_invalid_deadline)
        except ValidatorException as ve:
            assert str(ve) == "Validation error!\nAssignment must have a valid deadline!\n"

    def test__removeAssignmentFromRepository__validInput__removesAssignmentFromRepository(self):
        self._service.add(self._assignment_1)
        self._service.add(self._assignment_2)
        self._student_repository.add_entity(self._student_1)
        self._student_repository.add_entity(self._student_2)
        self._grade_service.add(self._grade_1)
        self._grade_service.add(self._grade_2)
        self._grade_service.add(self._grade_3)
        self._service.remove(self._assignment_1.id)
        assert len(self._service.get_values()) == 1
        assert self._service.get_values()[0] == self._assignment_2
        assert len(self._grade_repository.get_all()) == 1

    def test__removeAssignmentFromRepository__validAssignmentId__throwsExceptionAssignmentNotInRepository(self):
        self._service.add(self._assignment_2)
        try:
            self._service.remove(self._assignment_1.id)
        except ValueError as ve:
            assert str(ve) == "Assignment not found!"

    def test__removeAssignmentFromRepository__invalidAssignmentId__throwsExceptionAssignmentIdToRemoveNotInteger(self):
        self._service.add(self._assignment_1)
        try:
            self._service.remove("random_test_string")
        except ValueError as ve:
            assert str(ve) == "ID should be an integer!"

    def test__updateAssignmentFromRepository__validInput__updatesAssignmentInRepository(self):
        self._service.add(self._assignment_1)
        self._service.update(self._updated_assignment_1)
        assert self._service.get_values()[0] == self._updated_assignment_1

    def test__updateAssignmentFromRepository__validAssignment__throwsExceptionUpdatedAssignmentNotInRepository(self):
        self._service.add(self._assignment_2)
        try:
            self._service.update(self._updated_assignment_1)
        except ValueError as ve:
            assert str(ve) == "Object not found! Cannot update."

    def tearDown(self) -> None:
        pass


class GradeServiceTests(unittest.TestCase):
    def setUp(self) -> None:
        self._grade_validator = GradeValidator()
        self._student_validator = StudentValidator()
        self._assignments_validator = AssignmentValidator()
        self._grade_repository = Repository()
        self._student_repository = Repository()
        self._assignments_repository = Repository()
        self._undo_redo_service = UndoRedoService()
        self._grade_service = GradeService(self._undo_redo_service, self._grade_repository, self._grade_validator,
                                           self._student_repository,
                                           self._assignments_repository)
        self._student_service = StudentService(self._undo_redo_service, self._student_repository,
                                               self._student_validator,
                                               self._grade_service, self._grade_repository)
        self._assignments_service = AssignmentService(self._undo_redo_service, self._assignments_repository,
                                                      self._assignments_validator,
                                                      self._grade_service, self._grade_repository)
        self._test_grade_1 = Grade(('10', '10'), "4")
        self._test_grade_2 = Grade(('14', '15'), '10')
        self._test_grade_3 = Grade(('14', '10'), '0')

    def test__getValues__validInput__getListOfAllObjectsFromRepository(self):
        self._grade_repository.add_entity(self._test_grade_1)
        assert len(self._grade_service.get_values()) == 1
        self.assertEqual(self._grade_service.get_values()[0], self._test_grade_1)

    def test__generateUngradedAssignments__validInput__populateRepositoryWith40Objects(self):
        self._student_service.generate_students()
        self._assignments_service.generate_assignments_data()
        self._grade_service.generate_ungraded_assignments()
        assert len(self._grade_service.get_values()) == 20

    def test__generateGradedAssignments__validInput__grade20Assignments(self):
        self._student_service.generate_students()
        self._assignments_service.generate_assignments_data()
        self._grade_service.generate_ungraded_assignments()
        self._grade_service.generate_graded_assignments()

    def test__giveGroupOfStudentsAssignments__validInput__computeGradeObjects(self):
        self._student_repository.add_entity(Student('10', 'Mircea', '40'))
        self._student_repository.add_entity(Student('15', 'Cristi', '40'))
        self._assignments_repository.add_entity(Assignment('14', 'lab2', '23/05/2021'))
        self._assignments_repository.add_entity(Assignment('10', 'lab8', '23/4/2020'))
        self._grade_service.add(self._test_grade_1)
        self._grade_service.add(self._test_grade_2)
        self._grade_service.give_group_of_students_assignment('14', '40')
        assert len(self._grade_service.get_values()) == 3
        assert str(self._grade_service.get_values()[2]) == str(self._test_grade_3)

    def test__getUngradedAssignmentsOfStudents_validInput__returnObjectList(self):
        self._student_service.generate_students()
        self._assignments_service.generate_assignments_data()
        self._grade_service.generate_ungraded_assignments()
        self._grade_service.generate_graded_assignments()
        assert len(self._grade_service.get_ungraded_assignments_of_students()) == 20

    def test__addGradeToRepository__validInput__addsGradeToRepository(self):
        self._student_repository.add_entity(Student('14', 'Gica', '40'))
        self._assignments_repository.add_entity(Assignment('15', 'lab4', '24/9/2021'))
        _grade = Grade(('15', '14'), '0')
        self._grade_service.add(_grade)
        assert len(self._grade_service.get_values()) == 1
        self.assertEqual(self._grade_service.get_values()[0], _grade)

    def test__addGradeToRepository__invalidInput__throwsExceptionStudentIdNotInRepository(self):
        self._student_repository.add_entity(Student('14', 'Gica', '40'))
        self._assignments_repository.add_entity(Assignment('15', 'lab4', '24/9/2021'))
        _grade = Grade(('15', '13'), '0')
        try:
            self._grade_service.add(_grade)
        except ValueError as ve:
            assert str(ve) == "Student id does not exist."

    def test_addGradeToRepository__invalidInput__throwsExceptionAssignmentIdNotInRepository(self):
        self._student_repository.add_entity(Student('14', 'Gica', '40'))
        self._assignments_repository.add_entity(Assignment('15', 'lab4', '24/9/2021'))
        _grade = Grade(('14', '14'), '0')
        try:
            self._grade_service.add(_grade)
        except ValueError as ve:
            assert str(ve) == "Assignment id does not exist."

    def test__getAssignmentsDescendingOrder__validInput__returnsList(self):
        self._student_service.generate_students()
        self._assignments_service.generate_assignments_data()
        self._grade_service.generate_ungraded_assignments()
        self._grade_service.generate_graded_assignments()
        assert len(self._grade_service.get_assignments_descending_order()) == 20

    def test__getLateAssignments__validInput__returnsList(self):
        self._assignments_service.add(Assignment('10', 'lab12', '15/4/2021'))
        self._student_service.add(Student('15', 'cristinus', '40'))
        self._grade_service.add(Grade(('10', '15'), '0'))
        assert len(self._grade_service.get_late_assignments()) == 1

    def test__getStudentsTop__validInput__returnsList(self):
        self._assignments_service.add(Assignment('10', 'lab69', '14/12/2021'))
        self._assignments_service.add(Assignment('11', 'lab22', '20/8/2022'))
        self._student_service.add(Student('15', 'paul', '40'))
        self._student_service.add(Student('16', 'mircea', '35'))
        self._grade_service.add(Grade(('10', '15'), '8'))
        self._grade_service.add(Grade(('11', '15'), '10'))
        self._grade_service.add(Grade(('10', '16'), '10'))
        assert len(self._grade_service.get_students_top()) == 2
        assert self._grade_service.get_students_top()[0].id == '16'

    def test__updateGrade__validInput__updatesGradeObject(self):
        self._assignments_service.add(Assignment('10', 'lab69', '14/12/2021'))
        self._student_service.add(Student('15', 'paul', '40'))
        self._grade_service.add(Grade(('10', '15'), '0'))
        self._grade_service.update(Grade(('10', '15'), '8'))
        assert self._grade_repository[('10', '15')].grade == '8'

    def test__updateGrade__invalidIdNotInRepo__throwsException(self):
        self._assignments_service.add(Assignment('10', 'lab69', '14/12/2021'))
        self._student_service.add(Student('15', 'paul', '40'))
        self._grade_service.add(Grade(('10', '15'), '0'))
        try:
            self._grade_service.update(Grade(('11', '16'), '7'))
        except ValueError as ve:
            assert str(ve) == "Object not found! Cannot update."

    def test__removeGrade__validInput__removesGradeFromRepository(self):
        self._assignments_service.add(Assignment('10', 'lab69', '14/12/2021'))
        self._student_service.add(Student('15', 'paul', '40'))
        self._grade_service.add(Grade(('10', '15'), '0'))
        self._grade_service.remove(('10', '15'))
        assert len(self._grade_repository.get_all()) == 0

    def test__removeGrade__invalidId__throwsException(self):
        self._assignments_service.add(Assignment('10', 'lab69', '14/12/2021'))
        self._student_service.add(Student('15', 'paul', '40'))
        self._grade_service.add(Grade(('10', '15'), '0'))
        try:
            self._grade_service.remove(('11', '16'))
        except ValueError as ve:
            assert str(ve) == "Grade not found!"

    def tearDown(self) -> None:
        pass


class UndoRedoServiceTests(unittest.TestCase):
    def setUp(self) -> None:
        self._grade_validator = GradeValidator()
        self._student_validator = StudentValidator()
        self._assignments_validator = AssignmentValidator()
        self._grade_repository = Repository()
        self._student_repository = Repository()
        self._assignments_repository = Repository()
        self._undo_redo_service = UndoRedoService()
        self._grade_service = GradeService(self._undo_redo_service, self._grade_repository, self._grade_validator,
                                           self._student_repository,
                                           self._assignments_repository)
        self._student_service = StudentService(self._undo_redo_service, self._student_repository,
                                               self._student_validator,
                                               self._grade_service, self._grade_repository)
        self._assignments_service = AssignmentService(self._undo_redo_service, self._assignments_repository,
                                                      self._assignments_validator,
                                                      self._grade_service, self._grade_repository)
        self._student_1 = Student('115', 'Cristi', 70)
        self._student_2 = Student('13', 'Cristi', 69)
        self._student_3 = Student('10', 'mircea', 80)
        self._assignment_1 = Assignment('15', 'lab32', '24/10/2020')
        self._grade_1 = Grade(('15', '13'), '8')

    def test__undoOfAddStudent__validInput__undoTheAddOfAStudent(self):
        self._student_service.add(self._student_1)
        assert len(self._student_service.get_values()) == 1
        self._undo_redo_service.undo()
        assert len(self._student_service.get_values()) == 0

    def test__undoOfRemoveStudent__validInput__undoTheRemovedStudent(self):
        self._student_service.add(self._student_2)
        self._assignments_service.add(self._assignment_1)
        self._grade_service.add(self._grade_1)
        assert len(self._student_service.get_values()) == 1
        assert len(self._grade_service.get_values()) == 1
        self._student_service.remove('13')
        assert len(self._student_service.get_values()) == 0
        assert len(self._grade_service.get_values()) == 0
        self._undo_redo_service.undo()
        assert len(self._student_service.get_values()) == 1
        assert len(self._grade_service.get_values()) == 1

    def test__undoOfUpdatedStudent__validInput__undoTheUpdatedStudent(self):
        self._student_service.add(Student('10', 'cristi', 420))
        assert self._student_service.get_values()[0].name == 'cristi'
        assert self._student_service.get_values()[0].group == 420
        self._student_service.update(self._student_3)
        assert self._student_service.get_values()[0].name == 'mircea'
        assert self._student_service.get_values()[0].group == 80

    def test__undo__noMoreUndo__throwsException(self):
        try:
            self._undo_redo_service.undo()
        except UndoServiceException as us:
            assert str(us) == "No more undo!"

    def test__redo__noMoreRedo__throwsException(self):
        try:
            self._undo_redo_service.redo()
        except UndoServiceException as us:
            assert str(us) == "No more redo!"

    def test__redoAddStudent__validInput__redoTheAddOfStudent(self):
        self._student_service.add(self._student_1)
        assert len(self._student_service.get_values()) == 1
        self._undo_redo_service.undo()
        assert len(self._student_service.get_values()) == 0
        self._undo_redo_service.redo()
        assert len(self._student_service.get_values()) == 1
        assert self._student_service.get_values()[0] == self._student_1

    def tearDown(self) -> None:
        pass


class CollectionTests(unittest.TestCase):
    def setUp(self) -> None:
        self._collection = Collection()

    def test__setItem__keyAndInput__elementAddedInCollection(self):
        self._collection.__setitem__(1, "abc")
        self.assertEqual(len(self._collection.values), 1)

    def test__getItem__keyOfWantedItem__gettingTheElement(self):
        self._collection.__setitem__(1, "abc")
        self.assertEqual(self._collection.__getitem__(1), "abc")

    def test__delItem__keyOfElementToDelete__deletesItem(self):
        self._collection.__setitem__(1, "abc")
        self.assertEqual(len(self._collection.values), 1)
        self._collection.__delitem__(1)
        self.assertEqual(len(self._collection.values), 0)

    def test__addItem__validItem__addsItemToCollection(self):
        self._collection.__setitem__(1, "abc")
        self._collection.__setitem__(2, "234")
        self.assertEqual(len(self._collection.values), 2)

    def test__clearCollection__validCollection__clearsCollection(self):
        self._collection.__setitem__(1, "abc")
        self._collection.__setitem__(2, "234")
        self.assertEqual(len(self._collection.values), 2)
        self._collection.clear()
        self.assertEqual(len(self._collection.values), 0)

    def test__iter__NoInput__zero(self):
        self.assertEqual(self._collection.__iter__(), 0)

    def test__next__NoInput__StopIterationWhenPassesThroughAllTheElementsFromTheDictionary(self):
        self._collection.add(1, "abc")
        self._collection.add(2, "abc")
        self._collection.__iter__()
        self._collection.__next__()
        self._collection.__next__()
        with self.assertRaises(StopIteration):
            self._collection.__next__()

    def tearDown(self) -> None:
        pass
