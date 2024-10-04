package xyz.kbws.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kbws
 * @date 2024/10/1
 * @description:
 */
public class OpenCodeReview {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("测试执行");
        // 1. 代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("gir", "diff", "HEAD~1", "HEAD");
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
        System.out.println("评审代码：" + diffCode);
    }
}
