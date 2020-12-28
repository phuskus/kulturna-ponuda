package ftn.kts.service;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.dto.SubscriptionDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.model.RegisteredUser;
import ftn.kts.model.Subscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

import static ftn.kts.constants.SubscriptionConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SubscriptionServiceIntegrationTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void getAll_ReturnsAllSubscriptionsPageable() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        Page<SubscriptionDTO> list = subscriptionService.getAllDTO(pageable);
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, list.getNumberOfElements());
    }

    @Test
    public void getOne_ExistentId_ReturnsSubscription() {
        SubscriptionDTO dto = subscriptionService.getOneDTO(DB_EXISTING_SUBSCRIPTION_ID_1);
        assertNotNull(dto);
        assertEquals(DB_SUB1_CULT_ID, dto.getCulturalOfferId());
        assertEquals(DB_SUB1_USER_ID, dto.getRegisteredUserId());
    }

    @Test
    public void getOne_NonExistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> subscriptionService.getOneDTO(DB_NONEXISTENT_SUBSCRIPTION_ID));
    }

    @Test
    public void delete_NonexistentId_ThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> subscriptionService.delete(DB_NONEXISTENT_SUBSCRIPTION_ID));
    }

    @Test
    public void createDelete_ValidNewObject_CreatesAndDeletesSuccessfully() {
        // TODO: Unique constraint fail try-catch statement -> Waiting on team consensus: checkUnique or DB composite key?
        Subscription existingSub = subscriptionService.getOne(DB_EXISTING_SUBSCRIPTION_ID_1);

        SubscriptionDTO dto = new SubscriptionDTO(new Date(), existingSub.getUser(), null, existingSub.getCulturalOffer());

        SubscriptionDTO createdDto = subscriptionService.create(dto);
        assertEquals(dto, createdDto);

        subscriptionService.delete(createdDto.getId());
        assertThrows(NoSuchElementException.class, () -> subscriptionService.getOne(createdDto.getId()));
    }

    @Test
    public void update_SetNewContent_ContentChanged() {
        // TODO: Unique constraint fail try-catch statement -> Waiting on team consensus: checkUnique or DB composite key?
        SubscriptionDTO oldSubscription = subscriptionService.getOneDTO(DB_EXISTING_SUBSCRIPTION_ID_1);
        oldSubscription.setDateOfSubscription(DB_SUB_NEW_DATE);

        try {
            subscriptionService.update(oldSubscription, oldSubscription.getId());
            SubscriptionDTO newSub = subscriptionService.getOneDTO(DB_EXISTING_SUBSCRIPTION_ID_1);
            assertEquals(DB_SUB_NEW_DATE, newSub.getDateOfSubscription());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Subscription must be unique");
        }
    }

    @Test
    public void update_ViolateUniqueConstraints_ThrowsUniqueConstraintViolation ()
    {
        // TODO: Assert if unique constraint was thrown, not exception base -> Waiting on team consensus: checkUnique or DB composite key?
        SubscriptionDTO dto = subscriptionService.getOneDTO(DB_EXISTING_SUBSCRIPTION_ID_1);
        //assertThrows(Exception.class, () -> subscriptionService.create(dto));
    }
}
