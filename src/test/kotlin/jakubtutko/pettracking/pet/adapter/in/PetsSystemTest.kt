package jakubtutko.pettracking.pet.adapter.`in`

import jakubtutko.pettracking.pet.adapter.out.persistence.InMemoryPetStorage
import jakubtutko.pettracking.pet.domain.PetType
import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType
import jakubtutko.pettracking.pet.domain.TrackerType.MEDIUM
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestConstructor(autowireMode = ALL)
class PetsSystemTest(
    private val storage: InMemoryPetStorage,
    private val webTestClient: WebTestClient,
) {

    @BeforeEach
    fun setup() {
        storage.clear()
    }

    @Test
    fun `dog pet is created and returned`() {
        webTestClient.post()
            .uri { uriBuilder -> uriBuilder.path("/pets").build() }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .bodyValue(petPrototype(DOG))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    fun `cat pet is created and returned`() {
        webTestClient.post()
            .uri { uriBuilder -> uriBuilder.path("/pets").build() }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .bodyValue(petPrototype(CAT))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    @Test
    fun `pets are returned`() {
        createPet(DOG)
        createPet(CAT)

        webTestClient.get()
            .uri { uriBuilder -> uriBuilder.path("/pets").build() }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.pets.length()").isEqualTo(2)
    }

    @Test
    fun `only dogs are returned`() {
        createPet(DOG)
        createPet(CAT)

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/pets")
                    .queryParam("petType", DOG.name)
                    .build()
            }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.pets.length()").isEqualTo(1)
            .jsonPath("$.pets[0].type").isEqualTo(DOG.name)
    }

    @Test
    fun `only cats are returned`() {
        createPet(DOG)
        createPet(CAT)

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/pets")
                    .queryParam("petType", CAT.name)
                    .build()
            }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.pets.length()").isEqualTo(1)
            .jsonPath("$.pets[0].type").isEqualTo(CAT.name)
    }

    @Test
    fun `pets are counted`() {
        createPet(DOG, SMALL)
        createPet(CAT, MEDIUM)

        webTestClient.get()
            .uri { uriBuilder -> uriBuilder.path("/pets/counts").build() }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.dogs['SMALL']").isEqualTo(1)
            .jsonPath("$.dogs['MEDIUM']").isEqualTo(0)
            .jsonPath("$.dogs['BIG']").isEqualTo(0)
            .jsonPath("$.cats['SMALL']").isEqualTo(0)
            .jsonPath("$.cats['MEDIUM']").isEqualTo(1)
            .jsonPath("$.cats['BIG']").isEqualTo(0) // should not be returned
    }

    private fun petPrototype(petType: PetType, trackerType: TrackerType = ANY_TRACKER_TYPE) =
        PetController.PetPrototypeResource(
            type = petType,
            trackerType = trackerType,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = if (petType == DOG) null else ANY_LOST_TRACKER,
        )

    private fun createPet(petType: PetType, trackerType: TrackerType = ANY_TRACKER_TYPE) {
        webTestClient.post()
            .uri { uriBuilder -> uriBuilder.path("/pets").build() }
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .bodyValue(petPrototype(petType, trackerType))
            .exchange()
            .expectStatus().isOk
            .expectBody()
    }
}

private val ANY_TRACKER_TYPE = SMALL
private const val ANY_OWNER_ID = "ownerId"
private const val ANY_IN_ZONE = true
private const val ANY_LOST_TRACKER = false
