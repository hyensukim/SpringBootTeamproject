package com.springboot.shootformoney.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/add")
    public ResponseEntity<Chat> addComment(@RequestParam String mId, @RequestParam Integer matchId, @RequestParam String content) {
        Chat comment = chatService.addComment(mId, matchId, content);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/member/{mNo}")
    public ResponseEntity<List<Chat>> getCommentsByMemberId(@PathVariable Long mNo) {
        List<Chat> comments = chatService.getCommentsByMemberId(mNo);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getCommentById(@PathVariable Long id) {
        return chatService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
