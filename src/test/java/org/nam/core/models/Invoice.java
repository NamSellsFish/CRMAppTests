package org.nam.core.models;

import lombok.Data;
import lombok.Getter;

@Data
public class Invoice {
    @Getter
    private static String no = "NO";

    @Getter
    private static String billDate = "BILL_DATE";

    @Getter
    private static String dueDate = "DUE_DATE";

    @Getter
    private static String client = "CLIENT";

    @Getter
    private static String tax = "TAX";

    @Getter
    private static String secondTax = "SECOND_TAX";

    @Getter
    private static String tds = "TDS";

    @Getter
    private static String recurring = "RECURRING";

    @Getter
    private static String repeatEvery = "REPEAT_EVERY";

    @Getter
    private static String recurringUnit = "RECURRING_UNIT";

    @Getter
    private static String recurringCycle = "RECURRING_CYCLE";

    @Getter
    private static String attachmentPath = "ATTACHMENT_PATH";

    @Getter
    private static String note = "NOTE";


}
