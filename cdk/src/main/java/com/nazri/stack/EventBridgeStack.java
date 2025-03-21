package com.nazri.stack;

import com.nazri.config.StackConfig;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Tags;
import software.amazon.awscdk.services.events.CronOptions;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.lambda.Function;
import software.constructs.Construct;

import java.util.List;

public class EventBridgeStack extends Stack {
    private final Rule rule;
    private final StackConfig stackConfig;

    public EventBridgeStack(final Construct scope, final String id, final StackProps props, StackConfig stackConfig, Function emailFunction) {
        super(scope, id, props);
        this.stackConfig = stackConfig;

        this.rule = Rule.Builder.create(this, "gratitude-jar-scheduler")
                .description("Gratitude Jar: Scheduler")
                .ruleName("gratitude-jar-scheduler")
                .enabled(true)
                .schedule(
                        // Hourly
//                        Schedule.rate(Duration.hours(1))
//                        Schedule.expression("0 0,18-23 * * *")
                        Schedule.cron(CronOptions.builder()
                                //Daily: 6 PM ~ 11PM
                                .minute("0")
                                .hour("10-15")
                                .day("*")
                                .month("*")
                                .year("*")
                                .build())
                )
                .targets(List.of(
                        LambdaFunction.Builder.create(emailFunction)
                                // Optional: set the maxEventAge retry policy
                                .maxEventAge(Duration.minutes(30))
                                .retryAttempts(2)
                                .build()))
                .build();

//        Tags.of(this.rule).add(Constants.ENVIRONMENT, stackConfig.getTags().get(Constants.ENVIRONMENT));
//        Tags.of(this.rule).add(Constants.PROJECT, Constants.NOTESNEST);
    }
}
