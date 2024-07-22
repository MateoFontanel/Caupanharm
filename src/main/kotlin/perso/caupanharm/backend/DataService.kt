package perso.caupanharm.backend

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import perso.caupanharm.backend.models.BracketMatchData
import perso.caupanharm.backend.models.TestData
import java.io.File

@Repository
class DataService {
    private val mapper = jacksonObjectMapper()
    private val logger = KotlinLogging.logger {}


    fun getTestData(): List<TestData> {
        try{
            val file = File("src/main/resources/data/testData.json")
            val testDataLists: List<TestData> = mapper.readValue(file, object : TypeReference<List<TestData>>() {})
            return testDataLists

        }catch(exception: Exception){
            return emptyList()
        }
    }

    fun getBracketData(): List<BracketMatchData> {
        try{
            val file = File("src/main/resources/data/bracket.json")
            val bracket: List<BracketMatchData> = mapper.readValue(file, object : TypeReference<List<BracketMatchData>>() {})
            return bracket

        }catch(exception: Exception){
            logger.error(exception.toString())
            return emptyList()
        }
    }
}