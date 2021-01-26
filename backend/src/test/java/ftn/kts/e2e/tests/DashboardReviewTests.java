package ftn.kts.e2e.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DashboardReviewTests extends AbstractDashboard {

    @Before
    public void setUp() {
        super.setUp("review");
    }


    @Test
    public void DeleteReview_ClickConfirm_Success() {
        int totalElements = page.getTable().getTotalElements();

        // open delete dialog
        page.openActionDialog(0, 0);

        page.clickConfirmDialog();

        assertEquals(totalElements - 1, page.getTable().getTotalElements());
    }

    @Test
    public void DeleteReview_ClickCancel_Abort() {
        int totalElements = page.getTable().getTotalElements();

        // open delete dialog
        page.openActionDialog(0, 0);

        page.clickCancelDialog();

        assertEquals(totalElements, page.getTable().getTotalElements());
    }

    @Test
    public void Search_TypeRandomString_NumElementsShrinks() {
        int totalElements = page.getTable().getTotalElements();

        page.getTable().writeToSearch("Fair");
        assertTrue(totalElements > page.getTable().getTotalElements());
    }
}
