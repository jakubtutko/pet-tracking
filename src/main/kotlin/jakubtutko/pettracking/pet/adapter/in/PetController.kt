package jakubtutko.pettracking.pet.adapter.`in`

import jakubtutko.pettracking.pet.application.port.`in`.CountPetsUseCase
import jakubtutko.pettracking.pet.application.port.`in`.CreatePetUseCase
import jakubtutko.pettracking.pet.application.port.`in`.GetPetsUseCase
import jakubtutko.pettracking.pet.domain.OwnerId
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetCount
import jakubtutko.pettracking.pet.domain.PetFilter
import jakubtutko.pettracking.pet.domain.PetPrototype
import jakubtutko.pettracking.pet.domain.PetType
import jakubtutko.pettracking.pet.domain.TrackerType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PetController(
    private val createPetUseCase: CreatePetUseCase,
    private val getPetsUseCase: GetPetsUseCase,
    private val countPetsUseCase: CountPetsUseCase,
) {

    @PostMapping("/pets")
    fun createPet(
        @RequestBody request: PetPrototypeResource,
    ): ResponseEntity<PetResource> = ok(createPetUseCase.create(request.toDomain()).toResource())

    @GetMapping("/pets")
    fun createPet(
        @RequestParam petType: PetType? = null,
    ): ResponseEntity<PetCollectionResource> {
        val filter = PetFilter(petType)
        val resource = PetCollectionResource(
            pets = getPetsUseCase.getPets(filter).map { it.toResource() }
        )
        return ok(resource)
    }

    @GetMapping("/pets/counts")
    fun createPet(): ResponseEntity<PetCount> = ok(countPetsUseCase.countPets())

    data class PetResource(
        val id: UUID,
        val type: PetType,
        val trackerType: TrackerType,
        val ownerId: String,
        val inZone: Boolean,
        val lostTracker: Boolean? = null,
    )

    private fun Pet.toResource() = PetResource(
        id = id.id,
        type = type,
        trackerType = trackerType,
        ownerId = ownerId.id,
        inZone = inZone,
        lostTracker = lostTracker,
    )

    data class PetPrototypeResource(
        val type: PetType,
        val trackerType: TrackerType,
        val ownerId: String,
        val inZone: Boolean,
        val lostTracker: Boolean? = null,
    ) {
        fun toDomain() = PetPrototype(
            type = type,
            trackerType = trackerType,
            ownerId = OwnerId(ownerId),
            inZone = inZone,
            lostTracker = lostTracker,
        )
    }

    data class PetCollectionResource(
        val pets: List<PetResource>,
    )
}
