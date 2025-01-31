package com.nazri.stack;

import com.nazri.config.StackConfig;
import com.nazri.util.Constant;
import com.nazri.util.TagUtil;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.aws_apigatewayv2_authorizers.HttpJwtAuthorizer;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.lambda.Function;
import software.constructs.Construct;

import java.util.List;

public class APIGatewayStack extends Stack {
    private final StackConfig stackConfig;
    private final HttpApi httpApi;

    public APIGatewayStack(final Construct scope, final String id, final StackProps props, StackConfig stackConfig, Function function) {
        super(scope, id, props);
        this.stackConfig = stackConfig;
        this.httpApi = createHTTPAPIGateway();
        TagUtil.addTags(this.httpApi, stackConfig);
    }

    private HttpApi createHTTPAPIGateway() {
        final String issuerURL = "https://securetoken.google.com/" + Constant.FIREBASE_PROJECT_ID;
        return HttpApi.Builder.create(this, "gratitudejar-api-gateway")
                .apiName("Gratitude Jar HTTP API Gateway")
                .description("Gratitude Jar: HTTP API Gateway for REST")
                .defaultAuthorizer(HttpJwtAuthorizer.Builder
                        .create("gratitudejar-firebase-authoriser", issuerURL)
                        .authorizerName("gratitudejar-firebase-authoriser")
                        .jwtAudience(List.of(Constant.FIREBASE_PROJECT_ID))
                        .build())
                .build();
    }
}
