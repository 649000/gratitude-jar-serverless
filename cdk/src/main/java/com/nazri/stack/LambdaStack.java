package com.nazri.stack;

import com.nazri.config.StackConfig;
import com.nazri.util.TagUtil;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;

import java.util.Map;

public class LambdaStack extends Stack {


    private final Function apiFunction;
    private final StackConfig stackConfig;

    public LambdaStack(final Construct scope, final String id, final StackProps props, StackConfig stackConfig) {
        super(scope, id, props);
        this.stackConfig = stackConfig;
        this.apiFunction = createNonNativeApiFunction();
        TagUtil.addTags(this.apiFunction, stackConfig);

    }

    private Function createNonNativeApiFunction() {
        return Function.Builder.create(this, "gratitudejar-jwt-auth")
                .functionName("gratitudejar-jwt-auth")
                .description("Gratitude Jar: JWT Authoriser")
                .code(Code.fromAsset("../quarkus-lambda/target/function.zip"))
                .timeout(Duration.seconds(300))
                .memorySize(512)
                .runtime(Runtime.JAVA_21)
                .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
                .environment(Map.of(
                        // For JVM only
                        "JAVA_TOOL_OPTIONS", "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
                ))
                .build();
    }

    public Function getApiFunction() {
        return apiFunction;
    }
}
