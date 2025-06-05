package com.diaoyn.aichat.controller.service;

import reactor.core.publisher.Flux;

/**
 * @author diaoyn
 * @ClassName ChatService
 * @Date 2025/6/5 17:54
 */
public interface ChatService {

    String chat(String message);

    Flux<String> streamChat(String message);
}
