package ftn.kts.e2e.constants;

public class AppConstants {
    public static final String BASE_URL = "http://localhost:4200";
    public static final String RESULTS_URL = BASE_URL + "/offers/search";
    public static final String SINGLE_OFFER_URL = BASE_URL + "/offers/";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String REGISTER_URL = BASE_URL + "/register";
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "/forgot-password";
    
    public static final String DASHBOARD_URL = BASE_URL + "/admin";

    public static final String NONEXISTENT_CATEGORY = "FLKJD:Ldf;sal";
    public static final String EXISTENT_CATEOGORY = "Institution";

    public static final String NONEXISTENT_SUBCATEGORY = "Ne postoji, sine";
    public static final String EXISTENT_SUBCATEGORY = "Gallery";

    public static final String EXISTENT_USER_MAIL = "yahoo@yahoo.com";
    public static final String EXISTENT_USER_PASS = "12345";
    public static final String EXISTENT_USER_NAME = "User Useric";

    public static final String EXISTENT_ADMIN_MAIL = "covid19.clinic.llc@gmail.com";
    public static final String EXISTENT_ADMIN_PASS = "12345";
    public static final String NONEXISTENT_ADMIN_MAIL = "abvgddjezz@gmail.com";
    
    public static final String NEW_POST_TITLE = "New post title wee";
    public static final String NEW_POST_DESCRIPTION = "New post descirption!";
    

    public static final String EXISTENT_IMAGE_PATH = "src/test/resources/test_image.jpg";
    public static final String REVIEW_STAR_NO_COLOR = "rgba(128, 128, 128, 1)";

}
