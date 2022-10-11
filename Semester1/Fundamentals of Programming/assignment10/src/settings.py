from configparser import ConfigParser

from src.domain.assignments import Assignment
from src.domain.grade import Grade
from src.domain.student import Student
from src.repository.binary_file_repository import BinaryFileRepository
from src.repository.file_repository import FileRepository
from src.repository.repository import Repository


class Settings:
    def __init__(self):
        config = ConfigParser()
        config.read(r'C:\Users\cipri\Documents\GitHub\a10-917-Turcu-Ciprian\src\settings.properties')
        repository_type = config.get("options", "repository")
        if repository_type == "inmemory":
            self._student_repository = Repository()
            self._assignments_repository = Repository()
            self._grade_repository = Repository()
        elif repository_type == "textfiles":
            student_file_name = config.get("options", "student")
            assignment_file_name = config.get("options", "assignment")
            grade_file_name = config.get("options", "grade")
            self._student_repository = FileRepository(student_file_name, Student)
            self._assignments_repository = FileRepository(assignment_file_name, Assignment)
            self._grade_repository = FileRepository(grade_file_name, Grade)
        elif repository_type == "binaryfiles":
            student_file_name = config.get("options", "student")
            assignment_file_name = config.get("options", "assignment")
            grade_file_name = config.get("options", "grade")
            self._student_repository = BinaryFileRepository(student_file_name)
            self._assignments_repository = BinaryFileRepository(assignment_file_name)
            self._grade_repository = BinaryFileRepository(grade_file_name)

    def get_all_repositories(self):
        return self._student_repository, self._assignments_repository, self._grade_repository
