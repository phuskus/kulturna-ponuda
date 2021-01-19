package ftn.kts;

import com.github.javafaker.Faker;
import ftn.kts.dto.*;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Category;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.*;
import ftn.kts.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class MockDataGenerator {

    private static final int ADMIN_COUNT = 5;
    private static final int REGISTERED_USER_COUNT = 100;

    private static final String[] CATEGORIES = {
            "Institucija",
            "Manifestacija",
            "Kulturno dobro"
    };


    private static final String[][] SUBCATEGORIES = {
            { "Muzej", "Galerija" },
            { "Festival", "Sajam" },
            { "Spomenik", "Znamenitost" }
    };

    private static final int CULTURAL_OFFERS_PER_SUBCATEGORY_MIN = 1;
    private static final int CULTURAL_OFFERS_PER_SUBCATEGORY_MAX = 30;

    private static final int SUBSCRIPTIONS_PER_USER_MIN = 0;
    private static final int SUBSCRIPTIONS_PER_USER_MAX = 20;
    private static final float SUBSCRIBE_TO_SUBCATEGORY_CHANCE = 0.2f;

    private static final int POSTS_PER_CULTURAL_OFFER_MIN = 0;
    private static final int POSTS_PER_CULTURAL_OFFER_MAX = 30;

    // TODO: Maybe implement random picture generation?
    private static final int PICTURES_PER_POST_MIN = 0;
    private static final int PICTURES_PER_POST_MAX = 5;

    private static final int REVIEWS_PER_USER_MIN = 0;
    private static final int REVIEWS_PER_USER_MAX = 20;

    private static final float LONGITUDE_MIN = 0.0f;
    private static final float LONGITUDE_MAX = 180.0f;

    private static final float LATITUDE_MIN = 0.0f;
    private static final float LATITUDE_MAX = 0.0f;

    private static Faker faker;
    private static Random random;

    public static void GenerateMockData(ApplicationContext applicationContext) throws UniqueConstraintViolationException {
        System.out.println("-!!!- Mock data generation initiated. This will DELETE ALL DATA FROM THE DATABASE! Proceeed? ('n' to abort, anything else to proceed) -!!!-");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("n")) {
            System.out.println("Mock data generation aborted, no worries, data is safu.");
            return;
        }
        System.out.println("Proceeding with mock data generation");


        System.out.println("Purging database...");
        PurgeDatabase(applicationContext);

        System.out.println("Database purged, creating mock data via each service...");


        faker = new Faker();
        random = new Random();


        System.out.println("Creating admins...");
        ArrayList<AdminDTO> adminList = GenerateAdmins(applicationContext);

        System.out.println("Creating users...");
        ArrayList<UserDTO> userList = GenerateUsers(applicationContext);


        if (CATEGORIES.length != SUBCATEGORIES.length)
        {
            throw new IllegalArgumentException("There must be an array of subcategories for each category");
        }

        System.out.println("Creating categories...");
        ArrayList<CategoryDTO> categoryList = GenerateCategories(applicationContext, CATEGORIES);

        System.out.println("Creating subcategories...");
        ArrayList<SubcategoryDTO> subcategoryList = GenerateSubcategories(applicationContext, categoryList, SUBCATEGORIES);

        System.out.println("Creating cultural offers...");
        ArrayList<CulturalOfferDTO> culturalOfferList = GenerateCulturalOffers(applicationContext, subcategoryList, adminList);

        System.out.println("Creating subscriptions...");
        GenerateSubscriptions(applicationContext, userList, culturalOfferList, subcategoryList);

        System.out.println("Creating posts...");
        GeneratePosts(applicationContext, culturalOfferList);

        System.out.println("Creating reviews...");
        GenerateReviews(applicationContext, userList, culturalOfferList);

        System.out.println("Mock data generation finished!");
    }

    private static void PurgeDatabase(ApplicationContext applicationContext) {
        AdminRepository adminRepository = applicationContext.getBean(AdminRepository.class);
        AuthorityRepository authorityRepository = applicationContext.getBean(AuthorityRepository.class);
        CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);
        CulturalOfferRepository culturalOfferRepository = applicationContext.getBean(CulturalOfferRepository.class);
        PictureRepository pictureRepository = applicationContext.getBean(PictureRepository.class);
        PostRepository postRepository = applicationContext.getBean(PostRepository.class);
        RegisteredUserRepository registeredUserRepository = applicationContext.getBean(RegisteredUserRepository.class);
        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        SubcategoryRepository subcategoryRepository = applicationContext.getBean(SubcategoryRepository.class);
        SubscriptionRepository subscriptionRepository = applicationContext.getBean(SubscriptionRepository.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);

        reviewRepository.deleteAll();
        subscriptionRepository.deleteAll();
        postRepository.deleteAll();
        culturalOfferRepository.deleteAll();
        pictureRepository.deleteAll();
        subcategoryRepository.deleteAll();
        categoryRepository.deleteAll();
        registeredUserRepository.deleteAll();
        adminRepository.deleteAll();
        authorityRepository.deleteAll();
        userRepository.deleteAll();
    }

    private static ArrayList<AdminDTO> GenerateAdmins(ApplicationContext applicationContext) {
        AdminService adminService = applicationContext.getBean(AdminService.class);
        ArrayList<AdminDTO> adminList = new ArrayList<>();
        for (int i = 0; i < ADMIN_COUNT; i++)
        {
            while (true) {
                try {
                    AdminDTO dto = new AdminDTO(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.internet().password(8, 16));
                    adminList.add(adminService.create(dto));
                    break;
                } catch (Exception e) {
                    System.out.println("Faker admin create failed, probably duplicate, trying again...");
                }
            }
        }
        return adminList;
    }

    private static ArrayList<UserDTO> GenerateUsers(ApplicationContext applicationContext) {
        UserService userService = applicationContext.getBean(UserService.class);
        ArrayList<UserDTO> userList = new ArrayList<>();
        for (int i = 0; i < REGISTERED_USER_COUNT; i++)
        {
            while (true) {
                try {
                    //UserDTO dto1 = new UserDTO(faker.rickAndMorty().character(), faker.internet().emailAddress(), faker.internet().password());
                	UserDTO dto = new UserDTO(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(), faker.internet().password());
                    userList.add(userService.create(dto));
                    break;
                } catch (Exception e) {
                    System.out.println("Faker user create failed, probably duplicate, trying again...");
                }
            }
        }
        return userList;
    }

    private static ArrayList<CategoryDTO> GenerateCategories(ApplicationContext applicationContext, String[] categories) throws UniqueConstraintViolationException {
        CategoryService categoryService = applicationContext.getBean(CategoryService.class);
        ArrayList<CategoryDTO> categoryList = new ArrayList<CategoryDTO>();

        for (int i = 0; i < categories.length; i++)
        {
            CategoryDTO dto = new CategoryDTO(categories[i]);
            categoryList.add(categoryService.create(dto));
        }
        return categoryList;
    }

    private static ArrayList<SubcategoryDTO> GenerateSubcategories(ApplicationContext applicationContext, List<CategoryDTO> categoryList, String[][] subcategories) throws UniqueConstraintViolationException {
        SubcategoryService subcategoryService = applicationContext.getBean(SubcategoryService.class);
        ArrayList<SubcategoryDTO> subcategoryList = new ArrayList<>();

        for (int i = 0; i < categoryList.size(); i++)
        {
            CategoryDTO category = categoryList.get(i);

            for (int j = 0; j < subcategories[i].length; j++)
            {
                SubcategoryDTO dto = new SubcategoryDTO(subcategories[i][j], category.getId());
                subcategoryList.add(subcategoryService.create(dto));
            }
        }
        return subcategoryList;
    }

    private static ArrayList<CulturalOfferDTO> GenerateCulturalOffers(ApplicationContext applicationContext, List<SubcategoryDTO> subcategoryList, List<AdminDTO> adminList) {
        CulturalOfferService culturalOfferService = applicationContext.getBean(CulturalOfferService.class);
        ArrayList<CulturalOfferDTO> culturalOfferList = new ArrayList<>();
        for (SubcategoryDTO subcategory : subcategoryList)
        {
            int randomCount = random.nextInt(CULTURAL_OFFERS_PER_SUBCATEGORY_MAX - CULTURAL_OFFERS_PER_SUBCATEGORY_MIN);
            int culturalOfferRandomCount = CULTURAL_OFFERS_PER_SUBCATEGORY_MIN + randomCount;

            for (int i = 0; i < culturalOfferRandomCount; i++)
            {
                while (true) {
                    try {
                        float longitude = LONGITUDE_MIN + random.nextFloat() * (LONGITUDE_MAX - LONGITUDE_MIN);
                        float latitude = LATITUDE_MIN + random.nextFloat() * (LATITUDE_MAX - LATITUDE_MIN);
                        CulturalOfferDTO dto = new CulturalOfferDTO(subcategory.getName() + "_" + i,
                                faker.lorem().paragraph(5),
                                latitude,
                                longitude,
                                faker.address().streetAddress(),
                                faker.address().cityName(),
                                faker.address().country(),
                                adminList.get(0).getId(),
                                subcategory.getId());
                        culturalOfferList.add(culturalOfferService.create(dto));
                        break;
                    } catch (Exception e) {
                        System.out.println("Faker cultural offer create failed, probably duplicate, trying again...");
                    }
                }
            }
        }
        return culturalOfferList;
    }

    private static void GenerateSubscriptions(ApplicationContext applicationContext, List<UserDTO> userList, List<CulturalOfferDTO> culturalOfferList, List<SubcategoryDTO> subcategoryList) {
        SubscriptionService subscriptionService = applicationContext.getBean(SubscriptionService.class);
        for (UserDTO user : userList) {
            int randomCount = random.nextInt(SUBSCRIPTIONS_PER_USER_MAX - SUBSCRIPTIONS_PER_USER_MIN);
            int subscriptionsRandomCount = SUBSCRIPTIONS_PER_USER_MIN + randomCount;

            for (int i = 0; i < subscriptionsRandomCount; i++)
            {
                while (true) {
                    try {
                        SubscriptionDTO dto;
                        if (random.nextFloat() > SUBSCRIBE_TO_SUBCATEGORY_CHANCE) {
                            dto = new SubscriptionDTO(faker.date().past(365, TimeUnit.DAYS),
                                    user.getId(),
                                    null,
                                    culturalOfferList.get(random.nextInt(culturalOfferList.size())).getId());
                        } else {
                            dto = new SubscriptionDTO(faker.date().past(365, TimeUnit.DAYS),
                                    user.getId(),
                                    subcategoryList.get(random.nextInt(subcategoryList.size())).getId(),
                                    null);
                        }
                        subscriptionService.create(dto);
                        break;
                    } catch (Exception e) {
                        System.out.println("Faker subscription create failed, probably duplicate, trying again...");
                    }
                }
            }
        }
    }

    private static void GeneratePosts(ApplicationContext applicationContext, List<CulturalOfferDTO> culturalOfferList) {
        PostService postService = applicationContext.getBean(PostService.class);
        for (CulturalOfferDTO culturalOffer : culturalOfferList) {
            int randomCount = random.nextInt(POSTS_PER_CULTURAL_OFFER_MAX - POSTS_PER_CULTURAL_OFFER_MIN);
            int postsRandomCount = POSTS_PER_CULTURAL_OFFER_MIN + randomCount;

            for (int i = 0; i < postsRandomCount; i++)
            {
                while (true) {
                    try {
                        Set<PictureDTO> pictureSet = new HashSet<>();
                        //int pictureRandomCount = PICTURES_PER_POST_MIN + random.nextInt(PICTURES_PER_POST_MAX - PICTURES_PER_POST_MIN);
                        //TODO: Implement picture generation

                        String postContent;
                        float randomFloat = random.nextFloat();
                        if (randomFloat < 0.33f) {
                            postContent = faker.friends().quote();
                        } else if (randomFloat < 0.66f) {
                            postContent = faker.elderScrolls().quote();
                        } else {
                            postContent = faker.gameOfThrones().quote();
                        }
                        PostDTO dto = new PostDTO(postContent,
                                culturalOffer.getId(),
                                pictureSet);
                        postService.create(dto);
                        break;
                    } catch (Exception e) {
                        System.out.println("Faker post create failed, probably duplicate, trying again...");
                    }
                }
            }
        }
    }

    private static void GenerateReviews(ApplicationContext applicationContext, List<UserDTO> userList, List<CulturalOfferDTO> culturalOfferList) {
        ReviewService reviewService = applicationContext.getBean(ReviewService.class);
        for (UserDTO user : userList) {
            int randomCount = random.nextInt(REVIEWS_PER_USER_MAX - REVIEWS_PER_USER_MIN);
            int reviewsRandomCount = REVIEWS_PER_USER_MIN + randomCount;

            for (int i = 0; i < reviewsRandomCount; i++)
            {
                while (true) {
                    try {
                        ReviewDTO dto = new ReviewDTO((long) (random.nextInt(5) + 1),
                                faker.rickAndMorty().quote(),
                                user.getId(),
                                culturalOfferList.get(random.nextInt(culturalOfferList.size())).getId());
                        reviewService.create(dto);
                        break;
                    } catch (Exception e) {
                        System.out.println("Faker review create failed, probably duplicate, trying again...");
                    }
                }
            }
        }
    }

}
