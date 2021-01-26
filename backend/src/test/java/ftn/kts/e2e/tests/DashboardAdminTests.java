package ftn.kts.e2e.tests;

import org.junit.Before;
import org.junit.Test;

import static ftn.kts.e2e.constants.AppConstants.*;
import static org.junit.Assert.*;

public class DashboardAdminTests extends AbstractDashboard {

    @Before
    public void setUp() {
        super.setUp("admin");
    }

    @Test
    public void AddAdmin_ExistentEmail_Fail() {
        int totalElements = page.getTable().getTotalElements();

        page.openNewItemDialog();

        page.writeToDialogInput("name", "AAA");
        page.writeToDialogInput("surname", "Battery");
        page.writeToDialogInput("email", EXISTENT_ADMIN_MAIL);

        page.clickConfirmDialog();

        // number of elements stays the same
        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void AddSearchDelete_NotSelfAndNonexistentMail_Success() {
        int totalElements = page.getTable().getTotalElements();

        if (totalElements == 1) {
            fail("Only 1 admin, he can't delete himself");
        }

        // Add new
        page.openNewItemDialog();

        String name = "AAA";
        String surname = "Battery";
        page.writeToDialogInput("name", name);
        page.writeToDialogInput("surname", surname);
        page.writeToDialogInput("email", NONEXISTENT_ADMIN_MAIL);

        page.clickConfirmDialog();
        assertEquals(totalElements + 1, page.getTable().getTotalElements());

        // search for new category
        page.getTable().writeToSearch(name);

        String fullName = page.getTable().getRowColumnText(0, 0);
        assertEquals(name + " " + surname, fullName);

        // delete new admin
        page.openActionDialog(0, 0);
        page.clickConfirmDialog();

        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void DeleteAdmin_Himself_Fail() {
        int totalElements = page.getTable().getTotalElements();

        // search
        page.getTable().writeToSearch(EXISTENT_ADMIN_MAIL);

        // Delete
        page.openActionDialog(0, 0);
        page.clickConfirmDialog();

        // Reset search
        page.getTable().clearSearch();
        assertEquals(totalElements, page.getTable().getTotalElements());
    }
}
