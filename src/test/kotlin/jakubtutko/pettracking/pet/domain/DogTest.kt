package jakubtutko.pettracking.pet.domain

import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class DogTest {

    @Test
    fun `type of the dog is DOG`() {
        val dog = Dog(
            id = ANY_PET_ID,
            trackerType = ANY_TRACKER_TYPE,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE
        )

        val type = dog.type

        assertThat(type).isEqualTo(DOG)
    }
}

private val ANY_PET_ID = PetId(UUID.fromString("6599afb6-5452-458f-98f2-a92f119ef70a"))
private val ANY_OWNER_ID = OwnerId("ownerId")
private val ANY_TRACKER_TYPE = SMALL
private const val ANY_IN_ZONE = true
