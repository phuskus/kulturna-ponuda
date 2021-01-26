package ftn.kts.e2e.tests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static ftn.kts.e2e.constants.AppConstants.*;
import static org.junit.Assert.*;

public class DashboardCategoryTests extends AbstractDashboard {

    @Before
    public void setUp() {
        super.setUp("category");
    }

    @Test
    public void AddSearchUpdateDeleteCategory_NonExistentName_Success() {
        int totalElements = page.getTable().getTotalElements();

        page.openNewItemDialog();
        page.writeToDialogInput("name", NONEXISTENT_CATEGORY);
        page.clickConfirmDialog();

        // number of elements is old + 1
        assertEquals(totalElements + 1, page.getTable().getTotalElements());

        // search for category
        page.getTable().writeToSearch(NONEXISTENT_CATEGORY);

        String categoryName = page.getTable().getRowColumnText(0, 0);
        assertEquals(NONEXISTENT_CATEGORY, categoryName);

        // update
        String nameSuffix = "xD";
        String updatedName = NONEXISTENT_CATEGORY + nameSuffix;
        page.openActionDialog(0, 0);

        page.writeToDialogInput("name", nameSuffix);
        page.clickConfirmDialog();

        categoryName = page.getTable().getRowColumnText(0, 0);
        assertNotEquals(NONEXISTENT_CATEGORY, categoryName);
        assertEquals(updatedName, categoryName);

        // DELETE
        page.openActionDialog(0, 1);

        page.clickConfirmDialog();

        // Reset search
        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void AddCategory_ExistentName_Fails() {
        int totalElements = page.getTable().getTotalElements();

        page.openNewItemDialog();

        page.writeToDialogInput("name", EXISTENT_CATEOGORY);
        page.clickConfirmDialog();

        // number of elements stays the same
        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void DeleteCategory_ContainsSubcategories_Fails() {
        int totalElements = page.getTable().getTotalElements();

        page.openActionDialog(0, 1);

        page.clickConfirmDialog();

        // number of elements stays the same
        assertEquals(totalElements, page.getTable().getTotalElements());
    }
}
