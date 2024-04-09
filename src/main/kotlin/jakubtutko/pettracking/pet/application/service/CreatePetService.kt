package jakubtutko.pettracking.pet.application.service

import jakubtutko.pettracking.pet.application.port.`in`.CreatePetUseCase
import jakubtutko.pettracking.pet.application.port.out.CreatePetPort
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetPrototype
import org.springframework.stereotype.Service

@Service
class CreatePetService(
    private val createPetPort: CreatePetPort,
) : CreatePetUseCase {

    override fun create(petPrototype: PetPrototype): Pet {
        petPrototype.validate()
        return createPetPort.create(petPrototype)
    }
}
