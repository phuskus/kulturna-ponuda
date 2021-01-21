package ftn.kts;

import com.github.javafaker.Faker;
import ftn.kts.dto.*;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.Category;
import ftn.kts.model.CulturalOffer;
import ftn.kts.model.Subcategory;
import ftn.kts.repository.*;
import ftn.kts.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class MockDataGenerator {

    private static final int ADMIN_COUNT = 5;
    private static final int REGISTERED_USER_COUNT = 100;

    private static final String[] CATEGORIES = {
            "Institution",
            "Manifestation",
            "Cultural good"
    };


    private static final String[][] SUBCATEGORIES = {
            { "Museum", "Gallery" },
            { "Festival", "Fair" },
            { "Monument", "Landmark" }
    };
    
    private static final String[][] ICONS = {
    		{ "\\pictures\\mus.png", "Museum placeholder" },
    		{ "\\pictures\\gal.png", "Gallery placeholder" },
    		{ "\\pictures\\fest.png", "Festival placeholder" },
    		{ "\\pictures\\fair.png", "Fair placeholder" },
    		{ "\\pictures\\mon.png", "Monument placeholder" },
    		{ "\\pictures\\lmark.png", "Landmark placeholder" }
    };
    
    private static final String[][] LOCATIONS = {
    	    { "Belgrade", "44.80401", "20.46513", "Central Serbia"},
    	    { "Novi Sad", "45.25167", "19.83694", "Vojvodina"},
    	    { "Kragujevac", "44.01667", "20.91667", "Central Serbia"},
    	    { "Leskovac", "42.99806", "21.94611", "Southern Serbia"},
    	    { "Novi Pazar", "43.13667", "20.51222", "Southern Serbia"},
    	    { "Niš", "43.32472", "21.90333", "Southern Serbia"},
    	    { "Kraljevo", "43.72583", "20.68944", "Central Serbia"},
    	    { "Zrenjanin", "45.38361", "20.38194", "Vojvodina"},
    	    { "Kruševac", "43.58", "21.33389", "Central Serbia"},
    	    { "Subotica", "46.1", "19.66667", "Vojvodina"},
    	    { "Smederevo", "44.66436", "20.92763", "Central Serbia"},
    	    { "Valjevo", "44.27513", "19.89821", "Western Serbia"},
    	    { "Bor", "44.07488", "22.09591", "Eastern Serbia"},
    	    { "Čačak", "43.89139", "20.34972", "Western Serbia"},
    	    { "Sombor", "45.77417", "19.11222", "Vojvodina"},
    	    { "Vranje", "42.55139", "21.90028", "Southern Serbia"},
    	    { "Zaječar", "43.90358", "22.26405", "Eastern Serbia"},
    	    { "Negotin", "44.22639", "22.53083", "Eastern Serbia"},
    	    { "Užice", "43.85861", "19.84878", "Western Serbia"}
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
        
        System.out.println("Adding pictures for subcategories...");
        ArrayList<PictureDTO> icons = GeneratePicturesSubcategories(applicationContext, ICONS);

        System.out.println("Creating categories...");
        ArrayList<CategoryDTO> categoryList = GenerateCategories(applicationContext, CATEGORIES);

        System.out.println("Creating subcategories...");
        ArrayList<SubcategoryDTO> subcategoryList = GenerateSubcategories(applicationContext, categoryList, SUBCATEGORIES, icons);

        System.out.println("Creating cultural offers...");
        ArrayList<CulturalOfferDTO> culturalOfferList = GenerateCulturalOffers(applicationContext, subcategoryList, adminList, icons, LOCATIONS);

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
        subcategoryRepository.deleteAll();
        pictureRepository.deleteAll();
        categoryRepository.deleteAll();
        registeredUserRepository.deleteAll();
        adminRepository.deleteAll();
        authorityRepository.deleteAll();
        userRepository.deleteAll();
    }

    private static ArrayList<AdminDTO> GenerateAdmins(ApplicationContext applicationContext) {
        AdminService adminService = applicationContext.getBean(AdminService.class);
        ArrayList<AdminDTO> adminList = new ArrayList<>();
        AdminDTO staticAdmin = new AdminDTO("Amin", "Aminovic", "covid19.clinic.llc@gmail.com", "12345");
        adminList.add(adminService.create(staticAdmin));
        for (int i = 0; i < ADMIN_COUNT; i++) {
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
        for (int i = 0; i < REGISTERED_USER_COUNT; i++) {
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
    
    private static ArrayList<PictureDTO> GeneratePicturesSubcategories(ApplicationContext applicationContext, String[][] icons) {
    	PictureService pictureService = applicationContext.getBean(PictureService.class);
    	ArrayList<PictureDTO> iconList = new ArrayList<>();
    	
    	for (int i = 0; i < icons.length; i++) 
    	{
    		PictureDTO dto = new PictureDTO(icons[i][1], icons[i][0]);
    		try {
    			iconList.add(pictureService.save(dto));
			} catch (IOException e) {
				System.out.println("Something went wrong with generating image...");
				e.printStackTrace();
			}
    	}
    	return iconList;
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

    private static ArrayList<SubcategoryDTO> GenerateSubcategories(ApplicationContext applicationContext, List<CategoryDTO> categoryList, String[][] subcategories, List<PictureDTO> iconList) throws UniqueConstraintViolationException {
        SubcategoryService subcategoryService = applicationContext.getBean(SubcategoryService.class);
        ArrayList<SubcategoryDTO> subcategoryList = new ArrayList<>();
        int iconCounter = 0;
        for (int i = 0; i < categoryList.size(); i++)
        {
            CategoryDTO category = categoryList.get(i);

            for (int j = 0; j < subcategories[i].length; j++)
            {
            	PictureDTO icon = iconList.get(iconCounter);
                SubcategoryDTO dto = new SubcategoryDTO(subcategories[i][j], category.getId(), icon);
                subcategoryList.add(subcategoryService.create(dto));
                iconCounter++;
            }
        }
        return subcategoryList;
    }

    private static ArrayList<CulturalOfferDTO> GenerateCulturalOffers(ApplicationContext applicationContext, List<SubcategoryDTO> subcategoryList, List<AdminDTO> adminList, ArrayList<PictureDTO> icons, String[][] locations) {
        CulturalOfferService culturalOfferService = applicationContext.getBean(CulturalOfferService.class);
        ArrayList<CulturalOfferDTO> culturalOfferList = new ArrayList<>();
        int iconCounter = 0;
        for (SubcategoryDTO subcategory : subcategoryList) {
            int randomCount = random.nextInt(CULTURAL_OFFERS_PER_SUBCATEGORY_MAX - CULTURAL_OFFERS_PER_SUBCATEGORY_MIN);
            int culturalOfferRandomCount = CULTURAL_OFFERS_PER_SUBCATEGORY_MIN + randomCount;

            for (int i = 0; i < culturalOfferRandomCount; i++) {
                while (true) {
                    try {
                    	String[] location = locations[random.nextInt(locations.length)];
                    	// range je -0.5 do 0.5 podeljeno sa 20, tj -0.025 do 0.025
                    	float latitude = Float.parseFloat(location[1]) + (random.nextFloat() - 0.5f)/20;
                        float longitude = Float.parseFloat(location[2]) + (random.nextFloat() - 0.5f)/20;
                        CulturalOfferDTO dto = new CulturalOfferDTO(subcategory.getName() + "_" + i,
                                faker.lorem().paragraph(5),
                                latitude,
                                longitude,
                                faker.address().streetAddress(),
                                location[0],
                                location[3],
                                adminList.get(0).getId(),
                                subcategory.getId());
                        Set<PictureDTO> pictures = new HashSet<>();
                        pictures.add(icons.get(iconCounter));
                        dto.setPictures(pictures);
                        culturalOfferList.add(culturalOfferService.create(dto));
                        break;
                    } catch (Exception e) {
                        System.out.println("Faker cultural offer create failed, probably duplicate, trying again...");
                    }
                }
            }
            iconCounter++;
        }
        return culturalOfferList;
    }

    private static void GenerateSubscriptions(ApplicationContext applicationContext, List<UserDTO> userList, List<CulturalOfferDTO> culturalOfferList, List<SubcategoryDTO> subcategoryList) {
        SubscriptionService subscriptionService = applicationContext.getBean(SubscriptionService.class);
        for (UserDTO user : userList) {
            int randomCount = random.nextInt(SUBSCRIPTIONS_PER_USER_MAX - SUBSCRIPTIONS_PER_USER_MIN);
            int subscriptionsRandomCount = SUBSCRIPTIONS_PER_USER_MIN + randomCount;

            for (int i = 0; i < subscriptionsRandomCount; i++) {
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

            for (int i = 0; i < postsRandomCount; i++) {
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

            for (int i = 0; i < reviewsRandomCount; i++) {
                while (true) {
                    try {
                        CulturalOfferDTO offer = culturalOfferList.get(random.nextInt(culturalOfferList.size()));
                        ReviewDTO dto = new ReviewDTO((long) (random.nextInt(5) + 1),
                                faker.rickAndMorty().quote(),
                                user,
                                offer.getId(), offer.getName());
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
