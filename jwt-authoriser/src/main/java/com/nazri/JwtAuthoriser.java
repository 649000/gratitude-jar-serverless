package com.nazri;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

public class JwtAuthoriser implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final String FIREBASE_CREDENTIALS_ENV = "FIREBASE_CREDENTIALS";
    static {
        // Initialize Firebase Admin SDK
        String firebaseCredentials = System.getenv(FIREBASE_CREDENTIALS_ENV);
        if (firebaseCredentials == null) {
            throw new RuntimeException("Firebase credentials not found in environment variables.");
        }

        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(Base64.getDecoder().decode(firebaseCredentials))
            );
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase Admin SDK", e);
        }
    }
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        String token = input.getHeaders().get("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(401)
                    .withBody("Unauthorized: Missing or invalid token.");
        }

        String jwt = token.substring(7);
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            // Pass user details to the Quarkus app
            Map<String, String> userDetails = Map.of(
                    "uid", uid,
                    "email", email
            );

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody("Authorized")
                    .withHeaders(Collections.singletonMap("User-Details", userDetails.toString()));
        } catch (FirebaseAuthException e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(401)
                    .withBody("Unauthorized: Invalid token.");
        }

    }
}
