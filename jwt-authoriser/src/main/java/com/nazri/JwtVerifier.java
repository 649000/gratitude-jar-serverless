package com.nazri;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class JwtVerifier {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    public static void initializeFirebase() {
        if (!initialized.getAndSet(true)) {
            try (InputStream serviceAccount = JwtVerifier.class.getResourceAsStream("/firebase-service-account.json")) {
                if (serviceAccount == null) {
                    throw new RuntimeException("Firebase service account file not found.");
                }

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize Firebase", e);
            }
        }
    }

    public static FirebaseToken verifyToken(String jwtToken) throws Exception {
        initializeFirebase();
        return FirebaseAuth.getInstance().verifyIdToken(jwtToken);
    }
}
