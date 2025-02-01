package com.nazri.util;

public final class Constant {

    private Constant() {
        // restrict instantiation
    }

    public static final String PROJECT = "project";
    public static final String GRATITUDEJAR = "gratitudejar";
    public static final String ENVIRONMENT = "environment";

    public static final String DEV = "dev";
    public static final String SIT = "sit";
    public static final String UAT = "uat";
    public static final String PRD = "prd";


    public static final String FIREBASE_PROJECT_ID = System.getenv("GRATITUDEJAR_FIREBASE_PROJECT_ID");
    public static final String SUPABASE_DB_URL = System.getenv("SUPABASE_DB_URL");
    public static final String SUPABASE_DB_USER = System.getenv("SUPABASE_DB_USER");
    public static final String SUPABASE_DB_PASSWORD = System.getenv("SUPABASE_DB_PASSWORD");

}
