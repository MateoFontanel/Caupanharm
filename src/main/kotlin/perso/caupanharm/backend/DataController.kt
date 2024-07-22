package perso.caupanharm.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import perso.caupanharm.backend.models.BracketMatchData
import perso.caupanharm.backend.models.TestData
import mu.KotlinLogging
import org.springframework.web.bind.annotation.CrossOrigin

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api")
class DataController(private val dataService: DataService) {
    private val logger = KotlinLogging.logger {}

    @CrossOrigin(origins = ["*"])
    @GetMapping("/test")
    fun getData(): List<TestData> {
        logger.info("Endpoint fetched: test")
        return dataService.getTestData()
    }
    @CrossOrigin(origins = ["http://localhost:8080"])
    @GetMapping("/bracket")
    fun getBracket(): List<BracketMatchData> {
        logger.info("Endpoint fetched: bracket")
        return dataService.getBracketData()
    }
}