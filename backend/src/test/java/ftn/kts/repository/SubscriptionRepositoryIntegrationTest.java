package ftn.kts.repository;

import ftn.kts.model.CulturalOffer;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subcategory;
import ftn.kts.model.Subscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static ftn.kts.constants.SubscriptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscriptionRepositoryIntegrationTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void findAll_ReturnsAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, subscriptions.size());
    }

    @Test
    public void findByUser_ExistingUser_ReturnsSubscriptions()
    {
        RegisteredUser registeredUser = subscriptionRepository.findById(DB_EXISTING_SUBSCRIPTION_ID_1).get().getUser();
        List<Subscription> subscriptions = subscriptionRepository.findByUser(registeredUser);
        assertEquals(NUM_OF_SUBS_FOR_USER_1, subscriptions.size());
    }

    @Test
    public void findBySubcategory_ExistingSubcategory_ReturnsSubscriptions()
    {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Subcategory subcategory = subscriptionRepository.findById(DB_EXISTING_SUBSCRIPTION_ID_2).get().getSubcategory();
        Page<Subscription> subscriptions = subscriptionRepository.findBySubcategory(subcategory, pageable);
        assertEquals(NUM_OF_SUBS_FOR_SUBCATEGORY_2, subscriptions.getNumberOfElements());
    }

    @Test
    public void findByCulturalOffer_ExistingCulturalOffer_ReturnsSubscriptions()
    {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        CulturalOffer culturalOffer = subscriptionRepository.findById(DB_EXISTING_SUBSCRIPTION_ID_1).get().getCulturalOffer();
        Page<Subscription> subscriptions = subscriptionRepository.findByCulturalOffer(culturalOffer, pageable);
        assertEquals(NUM_OF_SUBS_FOR_CULTOFFER_2, subscriptions.getNumberOfElements());
    }
}
