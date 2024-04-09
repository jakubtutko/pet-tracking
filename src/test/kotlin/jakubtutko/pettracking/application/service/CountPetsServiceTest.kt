package jakubtutko.pettracking.application.service

import io.mockk.every
import io.mockk.mockk
import jakubtutko.pettracking.pet.application.port.out.CountPetsPort
import jakubtutko.pettracking.pet.application.service.CountPetsService
import jakubtutko.pettracking.pet.domain.PetCount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CountPetsServiceTest {

    private val countPetsPort: CountPetsPort = mockk()
    private val sut = CountPetsService(countPetsPort)

    @Test
    fun `returns pet counts`() {
        every { countPetsPort.countPets() } returns ANY_PET_COUNT

        val count = sut.countPets()

        assertThat(count).isEqualTo(ANY_PET_COUNT)
    }
}

private val ANY_PET_COUNT = PetCount(dogs = emptyMap(), cats = emptyMap())
