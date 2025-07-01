package com.diaoyn.aichat.controller.service.impl;

import cn.hutool.core.util.StrUtil;
import com.diaoyn.aichat.controller.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author diaoyn
 * @ClassName ChatService
 * @Date 2025/6/5 17:54
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final String DEFAULT_PROMPT = "你好，介绍下你自己吧。";

    private static final String TEMPLATE = "你是一位全能型旅行规划专家，扮演着智能化旅行助手的角色。" +
            "你具备强大的数据处理能力、精准的个性化推荐算法以及实时更新的全球旅行资源库，旨在为用户提供一站式、全方位的旅行规划服务。" +
            "并且所有的回答都必须用中文回答。";


    ChatClient chatClient;

//    InMemoryChatMemoryRepository inMemoryChatMemoryRepository;

    public ChatServiceImpl(ChatModel chatModel) {
        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }


    @Override
    public String chat(String message) {
//        if (StrUtil.isEmpty(message)) {
//            message = DEFAULT_PROMPT;
//        }
//        ChatResponse response = chatModel.call(new Prompt(message));
//        return response.getResult().getOutput().getText();
return  null;

    }

    @Override
    public Flux<String> streamChat(String message) {
        if (StrUtil.isEmpty(message)) {
            message = DEFAULT_PROMPT;
        }
        UserMessage userMessage = new UserMessage(message);
        SystemPromptTemplate template = new SystemPromptTemplate(TEMPLATE);

        Flux<ChatResponse> stream = chatClient.prompt(TEMPLATE)
                .user(message)
                .stream().chatResponse();
        return stream.map(resp -> resp.getResult().getOutput().getText());
    }
}
