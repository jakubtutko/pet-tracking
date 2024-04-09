package jakubtutko.pettracking.application.service

import io.mockk.every
import io.mockk.mockk
import jakubtutko.pettracking.pet.application.port.out.GetPetsPort
import jakubtutko.pettracking.pet.application.service.GetPetsService
import jakubtutko.pettracking.pet.domain.Dog
import jakubtutko.pettracking.pet.domain.OwnerId
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetFilter
import jakubtutko.pettracking.pet.domain.PetId
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class GetPetsServiceTest {

    private val getPetsPort: GetPetsPort = mockk()
    private val sut = GetPetsService(getPetsPort)

    @Test
    fun `returns pets`() {
        every { getPetsPort.getPets(ANY_PET_FILTER) } returns listOf(ANY_PET)

        val pets = sut.getPets(ANY_PET_FILTER)

        assertThat(pets).containsOnly(ANY_PET)
    }
}

private val ANY_PET_FILTER = PetFilter(null)
private val ANY_PET: Pet = Dog(
    id = PetId(UUID.fromString("ddd13e2c-d3ac-4623-bbec-c75d7be237b2")),
    trackerType = SMALL,
    ownerId = OwnerId("ownerId"),
    inZone = true,
)
