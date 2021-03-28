package seedu.student.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.student.commons.core.GuiSettings;
import seedu.student.logic.commands.exceptions.CommandException;
import seedu.student.model.appointment.Appointment;
import seedu.student.model.appointment.SameDateAppointmentList;
import seedu.student.model.student.MatriculationNumber;
import seedu.student.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<SameDateAppointmentList> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     *
     *
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' student book file path.
     */
    Path getStudentBookFilePath();

    /**
     * Sets the user prefs' student book file path.
     */
    void setStudentBookFilePath(Path studentBookFilePath);

    /**
     * Replaces student book data with the data in {@code studentBook}.
     */
    void setStudentBook(ReadOnlyStudentBook studentBook);

    /** Returns the studentBook */
    ReadOnlyStudentBook getStudentBook();

    /** Returns a list of students in the studentBook */
    ObservableList<Student> getStudentList();

    /** Returns true if matriculation number exists in the records. */
    boolean isExistingMatricNumber(MatriculationNumber matricNum);

    /**
     * Returns true if a student with the same identity as {@code student} exists in the records.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the records.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the records.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the student book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in
     * the records.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    void updateFilteredAppointmentList(Predicate<SameDateAppointmentList> predicate);

    boolean hasAppointment(Appointment appointment);

    boolean hasOverlappingAppointment(Appointment appointment);

    void addAppointment(Appointment appointment);

    void setAppointment(Appointment target, Appointment editedAppointment) throws CommandException;

    ObservableList<SameDateAppointmentList> getFilteredAppointmentList();
}
