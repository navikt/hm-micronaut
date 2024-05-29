package no.nav.hm.micronaut.leaderelection

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Singleton
import org.junit.jupiter.api.Test

@MicronautTest
class LeaderElectionTest(private val leaderComponent: LeaderComponent) {

    @Test
    fun testLeaderElection() {
        leaderComponent.leaderMethod().shouldNotBeNull() shouldBeEqual 1

    }
}

@Singleton
open class LeaderComponent {

    @LeaderOnly
    open fun leaderMethod(): Int? {
        println("I am the leader")
        return 1
    }

}