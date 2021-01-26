package ftn.kts.e2e.tests;

import static ftn.kts.e2e.constants.AppConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ftn.kts.e2e.pages.DashboardPage;

public class DashboardPostTests extends AbstractDashboard {

	DashboardPage offerPage;
	
    @Before
    public void setUp() {
        super.setUp("post");

        offerPage = PageFactory.initElements(driver, DashboardPage.class);
        offerPage.setTable("offer");
    }

    @Test
    public void AddSearchUpdateDeletePost_NonExistentName_Success() {
        int totalElements = page.getTable().getTotalElements();

        offerPage.ensureAddPostsIsDisplayed();
        
        List<WebElement> addButtons = offerPage.getAddPostButtons();
        
        assertTrue(addButtons.size() != 0);
        
        addButtons.get(0).click();
        
        offerPage.ensureDialogIsDisplayed();
        
        offerPage.writeToDialogInput("title", NEW_POST_TITLE);
        offerPage.writeToDialogInput("content", NEW_POST_DESCRIPTION);
        offerPage.clickConfirmDialog();

        // number of elements is old + 1
        assertEquals(totalElements + 1, page.getTable().getTotalElements());

        // search for category
        page.getTable().writeToSearch(NEW_POST_TITLE);

        String postTitle = page.getTable().getRowColumnText(0, 2);
        assertEquals(NEW_POST_TITLE, postTitle);

        // update
        String nameSuffix = "UPDATE";
        String updatedTitle = NEW_POST_TITLE + nameSuffix;
        page.openActionDialog(0, 0);

        page.writeToDialogInput("title", nameSuffix);
        page.clickConfirmDialog();

        postTitle = page.getTable().getRowColumnText(0, 2);
        assertNotEquals(NEW_POST_TITLE, postTitle);
        assertEquals(updatedTitle, postTitle);

        // DELETE
        page.openActionDialog(0, 1);

        page.clickConfirmDialog();

        // Reset search
        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
    }
}
