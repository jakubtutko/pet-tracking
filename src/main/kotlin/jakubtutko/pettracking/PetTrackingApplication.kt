package jakubtutko.pettracking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetTrackingApplication

fun main(args: Array<String>) {
	runApplication<PetTrackingApplication>(*args)
}
