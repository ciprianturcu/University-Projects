from src.domain.assignments import AssignmentValidator, Assignment
from src.domain.grade import GradeValidator, Grade
from src.domain.student import StudentValidator, Student
from src.repository.binary_file_repository import BinaryFileRepository
from src.repository.repository import Repository
from src.repository.file_repository import FileRepository
from src.services.assignments_service import AssignmentService
from src.services.grade_service import GradeService
from src.services.student_service import StudentService
from src.services.undo_service import UndoRedoService
from src.ui.ui import Ui
from src.settings import Settings

if __name__ == "__main__":
    student_validator = StudentValidator()
    assignment_validator = AssignmentValidator()
    grade_validator = GradeValidator()
    settings = Settings()
    student_repository, assignment_repository, grade_repository = settings.get_all_repositories()

    undo_redo_service = UndoRedoService()

    grade_service = GradeService(undo_redo_service, grade_repository, grade_validator, student_repository,
                                 assignment_repository)
    assignment_services = AssignmentService(undo_redo_service, assignment_repository, assignment_validator,
                                            grade_service, grade_repository)
    student_services = StudentService(undo_redo_service, student_repository, student_validator, grade_service,
                                      grade_repository)

    console = Ui(undo_redo_service, student_services, assignment_services, grade_service)
    console.start()
