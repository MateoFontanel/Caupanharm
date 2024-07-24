package perso.caupanharm.backend

import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import perso.caupanharm.backend.models.*
import perso.caupanharm.backend.models.henrik3.Henrik3Errors
import perso.caupanharm.backend.models.henrik3.Henrik3LifetimeMatches
import perso.caupanharm.backend.models.henrik3.Henrik3MatchesV3
import perso.caupanharm.backend.models.henrik3.Henrik3Player
import perso.caupanharm.backend.models.localdata.AdditionalCustomPlayerData
import perso.caupanharm.backend.models.localdata.BracketMatchData
import perso.caupanharm.backend.models.localdata.PlayersMatchData
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
class DataController(private val localDataService: LocalDataService, private val henrikService: HenrikService) {
    private val logger = KotlinLogging.logger {}


    @GetMapping("/test")
    fun getData(): List<TestData> {
        logger.info("Endpoint fetched: test")
        return localDataService.getTestData()
    }

    @GetMapping("/bracket")
    fun getBracket(): List<BracketMatchData> {
        logger.info("Endpoint fetched: bracket")
        return localDataService.getBracketData()
    }

    @GetMapping("/players")
    fun getPlayers(): List<PlayersMatchData> {
        logger.info("Endpoint fetched: players")
        return localDataService.getPlayersMatchesData()
    }

    @GetMapping("/stats")
    fun getStats(): List<AdditionalCustomPlayerData> {
        logger.info("Endpoint fetched: stats")
        return localDataService.getAdditionalPlayerData()
    }

    @GetMapping("/player/{username}/{tag}")
    fun getPlayer(@PathVariable username: String, @PathVariable tag: String): Any {
        logger.info("Endpoint fetched: player")
        return henrikService.getPlayerFromName(username, tag)
    }

    @GetMapping("/matches/light/{username}/{tag}")
    fun getPlayerMatchesLight(@PathVariable username: String, @PathVariable tag: String): Any {
        logger.info("Endpoint fetched: matches light")
        val uuidQuery: Mono<Any> = henrikService.getPlayerFromName(username, tag)
        return uuidQuery.flatMap { result ->
            when (result) {
                is Henrik3Player -> Mono.just(henrikService.getMatchesLightFromUUID(result.data.puuid))
                is Henrik3Errors -> Mono.just(result)
                else -> Mono.error(IllegalArgumentException("Unexpected type"))
            }
        }
    }

    @GetMapping("/matches/full/{name}/{tag}")
    fun getPlayerMatchesFull(@PathVariable name: String, @PathVariable tag: String): Mono<MutableList<Any>> {
        logger.info("Endpoint fetched: matches full")
        return henrikService.getMatchesLightFromName(name, tag)
            .flatMapMany { matches ->
                if (matches is Henrik3LifetimeMatches) {
                    var index = 0
                    Flux.fromIterable(matches.data)
                        .flatMap { match ->
                            index++
                            henrikService.getMatchFromId(match.meta.id, matches.results.returned, index) }
                } else {
                    Flux.error(IllegalStateException("Unexpected result type"))
                }
            }
            .collectList()
    }

}