package ftn.kts.e2e.tests;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static ftn.kts.e2e.constants.AppConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DashboardSubcategoryTests extends AbstractDashboard {

    @Before
    public void setUp() {
        super.setUp("subcategory");
    }

    @Test
    public void AddSearchDeleteSubcategory_NonExistentName_Success() {
        int totalElements = page.getTable().getTotalElements();

        page.openNewItemDialog();
        page.writeToDialogInput("name", NONEXISTENT_SUBCATEGORY);
        page.selectFirstCategory();
        File file = new File(EXISTENT_IMAGE_PATH);
        page.uploadImageFromPath(file.getAbsolutePath());
        page.clickConfirmDialog();

        // number of elements is old + 1
        assertEquals(totalElements + 1, page.getTable().getTotalElements());

        // search for subcategory
        page.getTable().writeToSearch(NONEXISTENT_SUBCATEGORY);

        String subcategoryName = page.getTable().getRowColumnText(0, 0);
        assertEquals(NONEXISTENT_SUBCATEGORY, subcategoryName);

        // DELETE
        page.openActionDialog(0, 0);

        page.clickConfirmDialog();

        // Reset search
        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void AddSubcategory_ExistentName_Fails() {
        int totalElements = page.getTable().getTotalElements();

        page.openNewItemDialog();

        page.writeToDialogInput("name", EXISTENT_SUBCATEGORY);
        page.selectFirstCategory();
        File file = new File(EXISTENT_IMAGE_PATH);
        page.uploadImageFromPath(file.getAbsolutePath());

        // number of elements stays the same
        assertEquals(totalElements, page.getTable().getTotalElements());
    }
}