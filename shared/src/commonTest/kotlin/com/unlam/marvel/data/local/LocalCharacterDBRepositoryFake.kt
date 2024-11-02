import com.unlam.marvel.data.local.ILocalCharacterRepository
import com.unlam.marvel.data.local.LocalCharacter

class LocalCharacterDBRepositoryFake : ILocalCharacterRepository {

    private val characters = ArrayList<LocalCharacter>()

    override suspend fun getAll(): List<LocalCharacter> {
        return characters
    }

    override suspend fun getBy(id: Long): LocalCharacter {
        return characters.first { it.id == id }
    }

    override suspend fun insert(character: LocalCharacter) {
        val existingCharacter = characters.find { it.id == character.id }
        if (existingCharacter == null) {
            characters.add(character)
        } else {
            characters.remove(existingCharacter)
            characters.add(character)
        }
    }

    override suspend fun deleteAll() {
        characters.clear()
    }

    override suspend fun delete(id: Long) {
        characters.removeAll { it.id == id }
    }
}