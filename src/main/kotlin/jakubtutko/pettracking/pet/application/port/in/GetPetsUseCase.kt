package jakubtutko.pettracking.pet.application.port.`in`

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetFilter

interface GetPetsUseCase {

    fun getPets(petFilter: PetFilter): List<Pet>
}
