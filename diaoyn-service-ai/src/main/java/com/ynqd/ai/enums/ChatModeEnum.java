package com.ynqd.ai.enums;

/**
 * Query: Will not use LLM unless there are relevant sources from vectorDB & does not recall chat history.
 * Chat: Uses LLM general knowledge w/custom embeddings to produce output, uses rolling chat history.
 *
 * @author diaoyn
 * @ClassName ChatModeEnum
 * @Date 2024/12/4 16:45
 */
public enum ChatModeEnum {

    query, chat
}
