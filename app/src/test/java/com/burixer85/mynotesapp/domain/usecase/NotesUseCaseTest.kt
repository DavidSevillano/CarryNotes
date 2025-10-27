import com.burixer85.mynotesapp.data.entity.NoteEntity
import com.burixer85.mynotesapp.domain.model.Note
import com.burixer85.mynotesapp.domain.repository.INoteRepository
import com.burixer85.mynotesapp.domain.usecase.NotesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FakeNoteRepository : INoteRepository {
    private val notes = mutableListOf<NoteEntity>()

    override suspend fun getNotes(): List<NoteEntity> = notes
    override suspend fun addNote(note: NoteEntity) { notes.add(note) }
    override suspend fun removeNote(note: NoteEntity) { notes.remove(note) }
}

class NotesUseCaseTest {
    private lateinit var useCase: NotesUseCase
    private lateinit var repository: FakeNoteRepository

    @Before
    fun setup() {
        repository = FakeNoteRepository()
        useCase = NotesUseCase(repository)
    }

    @Test
    fun testGetNotesInitiallyEmpty() = runBlocking {
        val notes = useCase.getNotes()
        assertTrue(notes.isEmpty())
    }

    @Test
    fun testAddNote() = runBlocking {
        val note = Note(1, "Título", "Contenido")
        useCase.addNote(note)
        val notes = useCase.getNotes()
        assertEquals(1, notes.size)
        assertEquals("Título", notes[0].title)
    }

    @Test
    fun testDeleteNote() = runBlocking {
        val note = Note(2, "Borrar", "Texto")
        useCase.addNote(note)
        useCase.deleteNote(note)
        val notes = useCase.getNotes()
        assertTrue(notes.isEmpty())
    }
}