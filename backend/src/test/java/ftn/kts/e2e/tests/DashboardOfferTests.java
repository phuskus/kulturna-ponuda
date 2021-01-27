package ftn.kts.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class DashboardOfferTests extends AbstractDashboard {
	
	@Before
	public void setUp() {
		super.setUp("offer");
	}
	
	@Test
	public void AddSearchUpdateDeleteOffer_NonExistentName_Success() {
		int totalElements = page.getTable().getTotalElements();
		String name = "Cat Land Love Meow";
		page.openNewItemDialog();
		page.writeToDialogInput("name", name);
		page.writeToDialogInput("description", "I love cats");
		page.selectFirstSubcategory();
		page.selectFirstRegion();
		page.selectFirstAddress();
		page.clickConfirmDialog();
		
		page.ensureTableIsLoaded();
		// number of elements is old + 1
     	assertEquals(totalElements + 1, page.getTable().getTotalElements());
		
        // search for offer
        page.getTable().writeToSearch(name);
        
        String offerName = page.getTable().getRowColumnText(0, 0);
        assertEquals(name, offerName);
               
        // update
        String nameSuffix = "UPDATE";
        String updatedName = name + nameSuffix;
        page.openActionDialog(0, 0);
        
        page.writeToDialogInput("name", nameSuffix);
        page.clickConfirmDialog();
        
        page.ensureTableIsLoaded();
        offerName = page.getTable().getRowColumnText(0, 0);
        assertNotEquals(name, offerName);
        assertEquals(updatedName, offerName);
        
        // delete
        page.openActionDialog(0, 1);
        page.clickConfirmDialog();
        
        // reset search
        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
	
	}
	
	@Test
	public void AddOffer_ExistentName_MessageDisplayed() {
		int totalElements = page.getTable().getTotalElements();
		String name = "Museum_1";
		page.openNewItemDialog();
		page.writeToDialogInput("name", name);
		page.writeToDialogInput("description", "I love cats");
		page.selectFirstSubcategory();
		page.selectFirstRegion();
		page.selectFirstAddress();
		page.clickConfirmDialogEnsureSnackbarSays("The name of the cultural offer should be unique!");
		
		page.clickCancelDialog();
		assertEquals(totalElements, page.getTable().getTotalElements());
	}

}
