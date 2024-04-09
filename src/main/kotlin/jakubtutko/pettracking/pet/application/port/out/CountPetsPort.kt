package jakubtutko.pettracking.pet.application.port.out

import jakubtutko.pettracking.pet.domain.PetCount

interface CountPetsPort {

    fun countPets(): PetCount
}
