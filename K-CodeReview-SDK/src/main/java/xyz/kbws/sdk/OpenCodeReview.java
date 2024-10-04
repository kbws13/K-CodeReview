package xyz.kbws.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kbws.sdk.domain.service.impl.OpenAiCodeReviewService;
import xyz.kbws.sdk.infrastructure.git.GitCommand;
import xyz.kbws.sdk.infrastructure.openai.IOpenAI;
import xyz.kbws.sdk.infrastructure.openai.impl.ChatGLM;
import xyz.kbws.sdk.infrastructure.weixin.WeiXin;

/**
 * @author kbws
 * @date 2024/10/1
 * @description:
 */
public class OpenCodeReview {
    private static final Logger logger = LoggerFactory.getLogger(OpenCodeReview.class);

    // 配置配置
    private String weixin_appid = "wx193be5610ef8813e";
    private String weixin_secret = "c3134b3095e60a6d4b21dbd5b8d76609";
    private String weixin_touser = "oBSeU6bMG1JcNKS69f95-R5lbu8U";
    private String weixin_template_id = "VBtESQRtEI2K_07ymU1v4OgNjVHBKE8DBJmd6sx1DF0";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private String chatglm_apiKeySecret = "";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        /**
         * 项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        WeiXin weiXin = new WeiXin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );


        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        OpenAiCodeReviewService openAiCodeReviewService = new OpenAiCodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }


}
