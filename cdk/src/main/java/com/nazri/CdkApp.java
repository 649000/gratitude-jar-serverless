package com.nazri;

import com.nazri.config.StackConfig;
import com.nazri.stack.APIGatewayStack;
import com.nazri.stack.LambdaStack;
import com.nazri.util.Constant;
import software.amazon.awscdk.App;

public class CdkApp {
    public static void main(final String[] args) {
        App app = new App();

        String environment = Constant.DEV;

        StackConfig stackConfig = getStackConfig(environment);

        LambdaStack apiLambdaStack = new LambdaStack(app, "gratitudejar-lambda-stack", stackConfig.getStackProps()
                .stackName("gratitudejar-lambda-stack")
                .description("Lambda Stack for Gratitude Jar")
                .build(),
                stackConfig
        );

        new APIGatewayStack(app, "gratitudejar-api-stack", stackConfig.getStackProps()
                .stackName("gratitudejar-api-stack")
                .description("HTTP API Gateway Stack for Gratitude Jar")
                .build(),
                stackConfig,
                apiLambdaStack.getApiFunction());

        app.synth();
    }

    public static StackConfig getStackConfig(String environment) {
        StackConfig stackConfig;
        switch (environment) {
            case Constant.SIT:
                stackConfig = new StackConfig.Builder()
                        .withEnvironment(Constant.SIT)
                        .build();
                break;
            case Constant.UAT:
                stackConfig = new StackConfig.Builder()
                        .withEnvironment(Constant.UAT)
                        .build();
                break;
            default:
                stackConfig = new StackConfig.Builder()
                        .withEnvironment(Constant.DEV)
                        .build();
        }
        return stackConfig;
    }
}

