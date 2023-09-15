package com.springboot.shootformoney.chatting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class ChattingController {

    private final ChattingService chattingService;

    @Autowired
    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @PostMapping("/{matchId}/{userId}")
    public ResponseEntity<Void> addComment(@PathVariable Long matchId,
                                           @PathVariable Long userId,
                                           @RequestBody String message) {
        try {
            chattingService.addComment(userId, matchId, message);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<List<Chatting>> getCommentsForMatch(@PathVariable Long matchId) {
        try {
            List<Chatting> comments = chattingService.getCommentsForMatch(gno);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{matchId}/since/{time}")
    public ResponseEntity<List<Chatting>> getNewCommentsForMatch(@PathVariable Long match_id,
                                                                @PathVariable String time) { // ISO-8601 format: "yyyy-MM-dd'T'HH:mm:ss"
        try {
            LocalDateTime since = LocalDateTime.parse(time); // parse the time string into a LocalDateTime object
            List<Chatting> comments = chattingService.getNewCommentsForMatch(match_id, since);

            return new ResponseEntity<>(comments, HttpStatus.OK);

        } catch (Exception e) { // catching parsing exceptions and IllegalArgumentExceptions
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
