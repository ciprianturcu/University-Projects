from src.domain.assignments import AssignmentValidator
from src.domain.grade import GradeValidator
from src.domain.student import StudentValidator
from src.repository.repository import Repository
from src.services.assignments_service import AssignmentService
from src.services.grade_service import GradeService
from src.services.student_service import StudentService
from src.services.undo_service import UndoRedoService
from src.ui.ui import Ui

if __name__ == "__main__":
    student_validator = StudentValidator()
    student_repository = Repository()

    assignment_validator = AssignmentValidator()
    assignment_repository = Repository()

    grade_validator = GradeValidator()
    grade_repository = Repository()

    undo_redo_service = UndoRedoService()

    grade_service = GradeService(undo_redo_service, grade_repository, grade_validator, student_repository,
                                 assignment_repository)
    assignment_services = AssignmentService(undo_redo_service, assignment_repository, assignment_validator,
                                            grade_service,grade_repository)
    student_services = StudentService(undo_redo_service, student_repository, student_validator, grade_service,
                                      grade_repository)

    console = Ui(undo_redo_service, student_services, assignment_services, grade_service)
    console.start()
