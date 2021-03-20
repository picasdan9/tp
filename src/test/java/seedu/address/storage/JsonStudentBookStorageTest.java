package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.StudentBook;
import seedu.address.model.ReadOnlyStudentBook;

public class JsonStudentBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudentBook(null));
    }

    private java.util.Optional<ReadOnlyStudentBook> readStudentBook(String filePath) throws Exception {
        return new JsonStudentBookStorage(Paths.get(filePath)).readStudentBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStudentBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudentBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readInvalidStudentBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudentBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readStudentBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudentBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveStudentBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempStudentBook.json");
        StudentBook original = getTypicalAddressBook();
        JsonStudentBookStorage jsonAddressBookStorage = new JsonStudentBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveStudentBook(original, filePath);
        ReadOnlyStudentBook readBack = jsonAddressBookStorage.readStudentBook(filePath).get();
        assertEquals(original, new StudentBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveStudentBook(original, filePath);
        readBack = jsonAddressBookStorage.readStudentBook(filePath).get();
        assertEquals(original, new StudentBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveStudentBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readStudentBook().get(); // file path not specified
        assertEquals(original, new StudentBook(readBack));

    }

    @Test
    public void saveStudentBook_nullStudentBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code studentBook} at the specified {@code filePath}.
     */
    private void saveStudentBook(ReadOnlyStudentBook addressBook, String filePath) {
        try {
            new JsonStudentBookStorage(Paths.get(filePath))
                    .saveStudentBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStudentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentBook(new StudentBook(), null));
    }
}
