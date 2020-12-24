package ftn.kts.constants;

public class CulturalOfferConstants {
	
	public static final String DB_CULTURAL_OFFER_NAME = "cultural_offer1";
	public static final String DB_CULTURAL_OFFER_NAME_UPPERCASE = "CuLTuRAL_OffER1";
	public static final String DB_CULTURAL_OFFER_NO_SUCH_NAME = "no_cultural_offer";
	public static final String DB_CULTURAL_OFFER_PART_NAME = "cultural";
	public static final Long DB_CULTURAL_OFFER_ID = 1L;
	

	public static final String DB_CITY_NAME = "Novi Sad";
	public static final String DB_CITY_NAME_LOWERCASE = "novi sad";
	public static final String DB_CITY_PART_NAME = "novi";
	public static final String DB_NO_SUCH_CITY = "no-city";
	
	public static final String DB_PART_DESCRIPTION = "festival";
	public static final String DB_PART_DESCRIPTION_UPPERCASE = "fEsTiVaL";
	public static final String DB_NO_SUCH_DESCRIPTION = "no-festival";
	
	
	public static final long FIND_ALL_NUMBER_OF_ITEMS = 2;
	public static final long FIND_ALL_NUMBER_OF_ITEMS_BY_SUBCATEGORY = 2;
	public static final long FIND_ALL_NUMBER_OF_ITEMS_BY_CITY = 1;
	public static final long FIND_ALL_NUMBER_OF_ITEMS_BY_DESCRIPTION = 2;
    public static final int PAGEABLE_PAGE = 0;
    public static final int PAGEABLE_SIZE = 2;
    public static final int PAGEABLE_TOTAL_ELEMENTS = 1;
    public static final int PAGEABLE_FIRST_PAGE = 1;

    public static final int PAGEABLE_ONE_ELEMENTS = 1;
    public static final int PAGEABLE_TWO_ELEMENTS = 2;
    public static final int PAGEABLE_NO_ELEMENTS = 0;

    public static final Long CATEGORY_ONE_ID = 1L;
    public static final Long CATEGORY_TWO_ID = 2L;
    public static final Long CATEGORY_NULL_ID = 10L;
    
    public static final int CATEGORY_ONE_RESULTS = 2;
    public static final int CATEGORY_TWO_RESULTS = 0;
    
    public static final Long PAGEABLE_SORTBYID_ASC_FIRST = 1L;
    public static final Long PAGEABLE_SORTBYID_ASC_SECOND = 2L;
    
    public static final Long PAGEABLE_SORTBYID_DESC_FIRST = 2L;
    public static final Long PAGEABLE_SORTBYID_DESC_SECOND = 1L;
    
    public static final Long FIND_NOT_EXIST_ID = 5L;

    public static final String CREATE_NAME = "new cultural offer somewhere";
    public static final String CREATE_DESCRIPTION = "Testing description";
    public static final String CREATE_CITY = "Novi Sad";
    public static final String UPDATE_NEW_DESCRIPTION = "new description!";
    public static final String UPDATE_NEW_CITY = "Beograd";
    
    public static final String UNIT_TEST_NAME_FIRST = "First Unit Test Cultural Offer";
    public static final String UNIT_TEST_NAME_SECOND = "Second Unit Test Cultural Offer";
    public static final String UNIT_TEST_NAME_CHANGED = "Changed Name Passing Unique Check";
    public static final String UNIT_TEST_DESCRIPTION = "Unit Test Description";
    public static final String UNIT_TEST_ADDRESS = "Fruska gora 41";
    public static final String UNIT_TEST_CITY = "Novi Sad";
    public static final String UNIT_TEST_REGION = "Vojvodina";
    public static final Long UNIT_TEST_ID_FIRST = 1L;
    public static final Long UNIT_TEST_ID_SECOND = 2L;
    
}
