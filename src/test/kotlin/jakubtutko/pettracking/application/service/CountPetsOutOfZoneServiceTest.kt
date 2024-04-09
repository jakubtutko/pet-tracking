package jakubtutko.pettracking.application.service

import io.mockk.every
import io.mockk.mockk
import jakubtutko.pettracking.pet.application.port.out.CountPetsOutOfZonePort
import jakubtutko.pettracking.pet.application.service.CountPetsOutOfZoneService
import jakubtutko.pettracking.pet.domain.PetCount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CountPetsOutOfZoneServiceTest {

    private val countPetsOutOfZonePort: CountPetsOutOfZonePort = mockk()
    private val sut = CountPetsOutOfZoneService(countPetsOutOfZonePort)

    @Test
    fun `returns out of zone pets counts`() {
        every { countPetsOutOfZonePort.countPetsOutOfZone() } returns ANY_PET_COUNT

        val count = sut.countPetsOutOfZone()

        assertThat(count).isEqualTo(ANY_PET_COUNT)
    }
}

private val ANY_PET_COUNT = PetCount(dogs = emptyMap(), cats = emptyMap())
