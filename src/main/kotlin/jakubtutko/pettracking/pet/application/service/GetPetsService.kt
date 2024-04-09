package jakubtutko.pettracking.pet.application.service

import jakubtutko.pettracking.pet.application.port.`in`.GetPetsUseCase
import jakubtutko.pettracking.pet.application.port.out.GetPetsPort
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetFilter
import org.springframework.stereotype.Service

@Service
class GetPetsService(
    private val getPetsPort: GetPetsPort,
) : GetPetsUseCase {

    override fun getPets(petFilter: PetFilter): List<Pet> = getPetsPort.getPets(petFilter)
}
