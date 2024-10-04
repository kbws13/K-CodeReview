package xyz.kbws.sdk;

import com.alibaba.fastjson2.JSON;
import xyz.kbws.sdk.domain.model.ChatCompletionSyncResponse;
import xyz.kbws.sdk.types.utils.BearerTokenUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author kbws
 * @date 2024/10/1
 * @description:
 */
public class OpenCodeReview {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("测试执行");
        // 1. 代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", "HEAD");
        processBuilder.directory(new File("."));
        Process start = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String line;
        StringBuilder diffCode = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            diffCode.append(line);
        }
        int exitCode = start.waitFor();
        System.out.println("Exited with code:" + exitCode);
        System.out.println("diff code：" + diffCode);

        // 2. ChatGLM 代码评审
        String log = codeReview(diffCode.toString());
        System.out.println("code review：" + log);
    }

    private static String codeReview(String diffCode) throws IOException {
        String apiKeySecret = "364c966589833d3514a0ab02dadb7f29.TyONa2QIVDAIEx0t";
        String token = BearerTokenUtils.getToken(apiKeySecret);

        URL url = new URL("https://open.bigmodel.cn/api/paas/v4/chat/completions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);

        String code = "1+1";

        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"user\","
                + "        \"content\": \"你是一个高级编程架构师，精通各类场景方案、架构设计和编程语言请，请您根据git diff记录，对代码做出评审。代码为: " + code + "\""
                + "    }"
                + "]"
                + "}";


        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInpuString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        ChatCompletionSyncResponse response = JSON.parseObject(content.toString(), ChatCompletionSyncResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
