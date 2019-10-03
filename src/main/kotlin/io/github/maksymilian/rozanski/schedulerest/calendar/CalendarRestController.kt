package io.github.maksymilian.rozanski.schedulerest.calendar

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Clock
import java.util.*
import java.util.stream.Stream

@RestController
@RequestMapping("/api")
class CalendarRestController {

    val calendar = GregorianCalendar()
    val clock: Clock = Clock.systemUTC()

    @GetMapping("/test")
    fun getHelloWorld(): String {
        return "Hello World!!"
    }

    @CrossOrigin(origins = ["https://wsei-schedule.herokuapp.com"])
    @PostMapping("/cal")
    fun getCalendar(@RequestBody string: String): ResponseEntity<Resource> {
        val stream: Stream<String> = string.lines().stream()
        val responseHeader = HttpHeaders()
        responseHeader.set("Access-Control-Allow-Origin", "http://localhost:8080")
        val resource: ByteArrayResource
        try {
            resource = ByteArrayResource(generateICalSchedule(stream, calendar, clock).toByteArray())
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok().headers(responseHeader).contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource)
    }
}
