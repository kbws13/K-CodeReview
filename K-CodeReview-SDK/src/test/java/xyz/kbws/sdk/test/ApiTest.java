package xyz.kbws.sdk.test;

import xyz.kbws.sdk.types.utils.BearerTokenUtils;

/**
 * @author kbws
 * @date 2024/10/1
 * @description:
 */
public class ApiTest {
    public static void main(String[] args) {
        String apiKey = "364c966589833d3514a0ab02dadb7f29.TyONa2QIVDAIEx0t";
        String token = BearerTokenUtils.getToken(apiKey);
        System.out.println(token);
    }


}
