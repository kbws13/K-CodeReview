package xyz.kbws.sdk.infrastructure.openai;

import xyz.kbws.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import xyz.kbws.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

/**
 * @author kbws
 * @date 2024/10/4
 * @description:
 */
public interface IOpenAI {
    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
