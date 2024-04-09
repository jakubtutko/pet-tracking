package jakubtutko.pettracking.application.service

import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakubtutko.pettracking.pet.application.port.out.CreatePetPort
import jakubtutko.pettracking.pet.application.service.CreatePetService
import jakubtutko.pettracking.pet.domain.Dog
import jakubtutko.pettracking.pet.domain.OwnerId
import jakubtutko.pettracking.pet.domain.PetId
import jakubtutko.pettracking.pet.domain.PetPrototype
import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType.BIG
import jakubtutko.pettracking.pet.domain.TrackerType.MEDIUM
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.UUID

class CreatePetServiceTest {

    private val createPetPort: CreatePetPort = mockk()
    private val sut = CreatePetService(createPetPort)

    @Test
    fun `pet is created`() {
        every { createPetPort.create(ANY_PET_PROTOTYPE) } returns ANY_CREATED_PET

        val createdPet = sut.create(ANY_PET_PROTOTYPE)

        assertThat(createdPet).isEqualTo(ANY_CREATED_PET)
        verify { createPetPort.create(ANY_PET_PROTOTYPE) }
    }

    @Test
    fun `creating invalid pet throws exception`() {
        assertThatThrownBy { sut.create(ANY_INVALID_PET_PROTOTYPE) }.isInstanceOf(IllegalArgumentException::class.java)
        verify { createPetPort.create(ANY_INVALID_PET_PROTOTYPE) wasNot Called }
    }
}

private val ANY_PET_ID = PetId(UUID.fromString("6599afb6-5452-458f-98f2-a92f119ef70a"))

private val ANY_PET_PROTOTYPE = PetPrototype(
    type = DOG,
    trackerType = SMALL,
    ownerId = OwnerId("ownerId"),
    inZone = true,
    lostTracker = null,
)

private val ANY_INVALID_PET_PROTOTYPE = PetPrototype(
    type = CAT,
    trackerType = BIG,
    ownerId = OwnerId("ownerId"),
    inZone = true,
    lostTracker = false,
)

private val ANY_CREATED_PET = Dog(
    id = ANY_PET_ID,
    trackerType = MEDIUM,
    ownerId = OwnerId("ownerId"),
    inZone = false,
)
