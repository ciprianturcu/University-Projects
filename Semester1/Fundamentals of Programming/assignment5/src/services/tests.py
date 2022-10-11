from src.services.services import StudentServices, Student


def tests_for_add_student_to_repository():
    addStudentInRepository_correctInput_studentAddedToRepository()
    addStudentInRepository_existingId_throwsException()
    addStudentInRepository_idInputString_throwsException()
    addStudentInRepository_groupInputString_throwsException()
    addStudentInRepository_idAndGroupInputString_throwsException()
    addStudentInRepository_withExistingName_studentAddedWithSameName()
    addStudentInRepository_negativeIdInput_throwsException()
    addStudentInRepository_negativeGroupInput_throwsException()
    addStudentInRepository_inExistingGroup_newStudentAddedInExistingGroup()
    addStudentInRepository_floatIdInput_throwsException()
    addStudentInRepository_floatGroupInput_throwsException()


def addStudentInRepository_correctInput_studentAddedToRepository():
    test = StudentServices()
    test.add_student_to_repository(Student(10, "Mircea", 14))
    list_students = test.return_repository()
    assert list_students[0].id == 10
    assert list_students[0].name == "Mircea"
    assert list_students[0].group == 14
    assert len(list_students) == 1


def addStudentInRepository_existingId_throwsException():
    test = StudentServices()
    test._student_repository = [Student(10, "Mircea", 14)]
    try:
        test.add_student_to_repository(Student(10, "Gabi", 10))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_idInputString_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student("option", "Gabi", 10))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_groupInputString_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student(10, "Gabi", "command"))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_idAndGroupInputString_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student("id", "Gabi", "group"))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_withExistingName_studentAddedWithSameName():
    test = StudentServices()
    test._student_repository = [Student(10, "Mircea", 14)]
    test.add_student_to_repository(Student(18, "Mircea", 19))
    list_students = test.return_repository()
    assert len(list_students) == 2


def addStudentInRepository_negativeIdInput_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student(-100, "Andrei", 80))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_negativeGroupInput_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student(100, "Andrei", -180))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_inExistingGroup_newStudentAddedInExistingGroup():
    test = StudentServices()
    test._student_repository = [Student(10, "Mircea", 14)]
    test.add_student_to_repository(Student(18, "Mircea", 14))
    list_students = test.return_repository()
    assert len(list_students) == 2


def addStudentInRepository_floatIdInput_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student(1.00, "Andrei", 40))
        assert False
    except ValueError:
        assert True


def addStudentInRepository_floatGroupInput_throwsException():
    test = StudentServices()
    try:
        test.add_student_to_repository(Student(1, "Andrei", 420.69))
        assert False
    except ValueError:
        assert True


def tests_for_filter_by_group():
    filterByGroup_correctInputCase_leavesInListAGroupOfTwoStudents()
    filterByGroup_nonExistantInputGroupCase_throwsException()
    filterByGroup_inputGroupIsFloatCase_throwsException()
    filterByGroup_inputGroupIsListCase_throwsException()
    filterByGroup_inputGroupIsNegativeCase_throwsException()
    filterByGroup_inputGroupIsStringCase_throwsException()
    filterByGroup_inputGroupIsDictioanryCase_throwsException()


def filterByGroup_correctInputCase_leavesInListAGroupOfTwoStudents():
    test = StudentServices()
    test.generate_initial_student_list()
    test.filter_by_group(10)
    test.filter_by_group(20)
    test.filter_by_group(15)
    list_of_students = test.return_repository()
    student = list_of_students[0]
    assert student.id == 144
    assert student.name == "Edi"
    assert student.group == 60
    student = list_of_students[1]
    assert student.id == 1023
    assert student.name == "Cipri"
    assert student.group == 60


def filterByGroup_nonExistantInputGroupCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    try:
        test.filter_by_group(69)
        assert False
    except ValueError:
        assert True


def filterByGroup_inputGroupIsFloatCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    try:
        test.filter_by_group(6.9)
        assert False
    except ValueError:
        assert True


def filterByGroup_inputGroupIsNegativeCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    try:
        test.filter_by_group(-1000)
        assert False
    except ValueError:
        assert True


def filterByGroup_inputGroupIsStringCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    try:
        test.filter_by_group("55")
        assert False
    except ValueError:
        assert True


def filterByGroup_inputGroupIsListCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    try:
        test.filter_by_group(['45', '95'])
        assert False
    except ValueError:
        assert True


def filterByGroup_inputGroupIsDictioanryCase_throwsException():
    test = StudentServices()
    test.generate_initial_student_list()
    dictionary = {1: 'option1', 2: 'option2'}
    try:
        test.filter_by_group(dictionary)
        assert False
    except ValueError:
        assert True
