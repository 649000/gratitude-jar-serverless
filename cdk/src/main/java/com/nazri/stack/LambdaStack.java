package com.nazri.stack;

import com.nazri.config.StackConfig;
import com.nazri.util.Constant;
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
        return Function.Builder.create(this, "gratitudejar-api-lambda")
                .description("Gratitude Jar: REST API")
                .code(Code.fromAsset("../api/target/function.zip"))
                .timeout(Duration.seconds(15))
                .memorySize(512)
                .runtime(Runtime.JAVA_21)
                .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
                .environment(Map.of(
                        "quarkus_lambda_handler", "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest",
                        "quarkus_datasource_jdbc_url", Constant.SUPABASE_DB_URL,
                        "quarkus_datasource_username", Constant.SUPABASE_DB_USER,
                        "quarkus_datasource_password", Constant.SUPABASE_DB_PASSWORD,
                        // For JVM only
                        "JAVA_TOOL_OPTIONS", "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
                ))
                .build();
    }

    public Function getApiFunction() {
        return apiFunction;
    }
}
