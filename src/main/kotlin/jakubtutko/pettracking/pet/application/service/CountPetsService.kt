package jakubtutko.pettracking.pet.application.service

import jakubtutko.pettracking.pet.application.port.`in`.CountPetsUseCase
import jakubtutko.pettracking.pet.application.port.out.CountPetsPort
import jakubtutko.pettracking.pet.domain.PetCount
import org.springframework.stereotype.Service

@Service
class CountPetsService(
    private val countPetsPort: CountPetsPort,
) : CountPetsUseCase {

    override fun countPets(): PetCount = countPetsPort.countPets()
}
