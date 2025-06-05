package com.diaoyn.aichat.controller;

import com.diaoyn.aichat.controller.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author diaoyn
 * @ClassName ChatController
 * @Date 2025/5/26 15:23
 */
@RestController
@RequestMapping("/model")
@Tag(name = "聊天模型")
public class ChatController {

    @Resource
    ChatService chatService;


    @Operation(summary = "聊天_一次性返回")
    @GetMapping("/chat")
    public String chat(@RequestParam(required = false) String message) {
        return chatService.chat(message);
    }


    @Operation(summary = "聊天_流式返回")
    @GetMapping("/stream/chat")
    public Flux<String> streamChat(@RequestParam(required = false) String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatService.streamChat(message);


    }

}
