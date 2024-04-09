package jakubtutko.pettracking.application.port.`in`

import jakubtutko.pettracking.pet.domain.PetCount

interface CountPetsUseCase {

    fun countPets(): PetCount
}
