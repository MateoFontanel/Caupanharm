package perso.caupanharm.backend

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import perso.caupanharm.backend.models.henrik3.*
import reactor.core.publisher.Mono

@Service
class HenrikService(private val henrik3Client: WebClient) {
    private val logger = KotlinLogging.logger {}

    fun getPlayerFromName(username: String, tag: String): Mono<Any> {
        logger.info("GET  /valorant/v1/account/$username/$tag?force=true")
        return henrik3Client.get()
                            .uri("/valorant/v1/account/$username/$tag?force=true")
                            .exchangeToMono { response ->
                                when (response.statusCode().value()) {
                                    in 200..299 -> response.bodyToMono(Henrik3Player::class.java)
                                    else -> response.bodyToMono(Henrik3Errors::class.java)
                                }
                            }
    }

    fun getMatchesLightFromUUID(uuid: String): Mono<Any> {
        logger.info("GET  /valorant/v1/by-puuid/lifetime/matches/eu/$uuid?mode=competitive")
        return henrik3Client.get()
                            .uri("/valorant/v1/by-puuid/lifetime/matches/eu/$uuid?mode=competitive")
                            .exchangeToMono { response ->
                                when (response.statusCode().value()) {
                                    in 200..299 -> response.bodyToMono(Henrik3MatchesV1::class.java)
                                    else -> response.bodyToMono(Henrik3Errors::class.java)
                                }
                            }
    }

    // Doesn't fetch every match
    fun getMatchesFullFromUUID(uuid: String): Mono<Any> {
        logger.info("GET  /valorant/v3/matches/eu/$uuid?mode=competitive")
        return henrik3Client.get()
                            .uri("valorant/v3/by-puuid/matches/eu/$uuid?mode=competitive")
                            .exchangeToMono { response ->
                                when (response.statusCode().value()) {
                                    in 200..299 -> response.bodyToMono(Henrik3MatchesV3::class.java)
                                    else -> response.bodyToMono(Henrik3Errors::class.java)
                                }
                            }
    }

    fun getMatchesLightFromName(name: String, tag: String): Mono<Any> {
        logger.info("GET  /valorant/v1/lifetime/matches/eu/$name/$tag?mode=competitive")
        return henrik3Client.get()
                            .uri("/valorant/v1/lifetime/matches/eu/$name/$tag?mode=competitive")
                            .exchangeToMono { response ->
                                when (response.statusCode().value()) {
                                    in 200..299 -> response.bodyToMono(Henrik3LifetimeMatches::class.java)
                                    else -> response.bodyToMono(Henrik3Errors::class.java)
                                }
                            }
    }

    fun getMatchFromId(matchId: String, total: Int?, current: Int?): Mono<Any> {
        if((current == null) || (total == null)){
            logger.info("GET  /valorant/v2/match/$matchId")
        }else{
            logger.info("GET  /valorant/v2/match/$matchId ($current/$total)")
        }
        return henrik3Client.get()
                            .uri("/valorant/v2/match/$matchId")
                            .exchangeToMono { response ->
                                when (response.statusCode().value()) {
                                    in 200..299 -> response.bodyToMono(Henrik3MatchV3::class.java)
                                    else -> response.bodyToMono(Henrik3Errors::class.java)
                                }
                            }
    }


}