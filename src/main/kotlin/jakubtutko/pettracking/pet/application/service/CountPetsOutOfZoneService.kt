package jakubtutko.pettracking.pet.application.service

import jakubtutko.pettracking.pet.application.port.`in`.CountPetsOutOfZoneUseCase
import jakubtutko.pettracking.pet.application.port.out.CountPetsOutOfZonePort
import jakubtutko.pettracking.pet.domain.PetCount
import org.springframework.stereotype.Service

@Service
class CountPetsOutOfZoneService(
    private val countPetsOutOfZonePort: CountPetsOutOfZonePort,
) : CountPetsOutOfZoneUseCase {

    override fun countPetsOutOfZone(): PetCount = countPetsOutOfZonePort.countPetsOutOfZone()
}
