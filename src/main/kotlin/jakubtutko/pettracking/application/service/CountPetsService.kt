package jakubtutko.pettracking.application.service

import jakubtutko.pettracking.application.port.`in`.CountPetsUseCase
import jakubtutko.pettracking.application.port.out.CountPetsPort
import jakubtutko.pettracking.pet.domain.PetCount
import org.springframework.stereotype.Service

@Service
class CountPetsService(
    private val countPetsPort: CountPetsPort,
) : CountPetsUseCase {

    override fun countPets(): PetCount = countPetsPort.countPets()
}
