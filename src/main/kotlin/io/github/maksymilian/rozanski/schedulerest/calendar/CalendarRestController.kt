package io.github.maksymilian.rozanski.schedulerest.calendar

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CalendarRestController {

    @GetMapping("/test")
    fun getHelloWorld(): ResponseEntity<String> {
        val body = "Hello World!!"
        return ResponseEntity.ok().contentLength(body.length.toLong())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(body)
    }


    @PostMapping("/cal")
    fun getCalendar(@RequestBody string: String): ResponseEntity<Resource> {
        val resource = ByteArrayResource(("TestResponse").toByteArray())
        return ResponseEntity.ok().contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource)
    }
}
