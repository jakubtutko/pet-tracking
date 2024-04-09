package jakubtutko.pettracking.application.port.out

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetFilter

interface GetPetsPort {

    fun getPets(petFilter: PetFilter): List<Pet>
}
