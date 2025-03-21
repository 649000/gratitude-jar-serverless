package com.nazri.stack;

import com.nazri.config.StackConfig;
import com.nazri.util.Constant;
import com.nazri.util.TagUtil;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.aws_apigatewayv2_authorizers.HttpJwtAuthorizer;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegration;
import software.amazon.awscdk.services.apigatewayv2.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.HttpMethod;
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
        addGratitudeRoute(function);
        addUserRoute(function);
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

    private void addGratitudeRoute(Function function) {
        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/gratitude")
                .methods(List.of(HttpMethod.GET))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-gratitude-getAll-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/gratitude/{gratitudeId}")
                .methods(List.of(HttpMethod.GET))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-gratitude-get-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/gratitude/{gratitudeId}")
                .methods(List.of(HttpMethod.PUT))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-gratitude-put-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/gratitude/{gratitudeId}")
                .methods(List.of(HttpMethod.DELETE))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-gratitude-delete-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/gratitude")
                .methods(List.of(HttpMethod.POST))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-gratitude-post-integration", function)
                        .build())
                .build());
    }

    private void addUserRoute(Function function) {
        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/user")
                .methods(List.of(HttpMethod.GET))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-user-getAll-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/user/{userUID}")
                .methods(List.of(HttpMethod.GET))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-user-get-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/user/{userUID}")
                .methods(List.of(HttpMethod.DELETE))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-user-delete-integration", function)
                        .build())
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/api/user")
                .methods(List.of(HttpMethod.POST))
                .integration(HttpLambdaIntegration.Builder
                        .create("gratitudejar-user-post-integration", function)
                        .build())
                .build());
    }
}
