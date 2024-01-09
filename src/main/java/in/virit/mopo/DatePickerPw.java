package in.virit.mopo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A helper class to work with vaadin-date-picker component.
 */
public class DatePickerPw {

    private final Locator root;
    private final Page page;
    private String dateFormat;

    /**
     * Creates a DatePicker page object for the specified Page and element ID.
     *
     * @param page
     *            The Page to which the date picker belongs.
     * @param id
     *            The ID of the date picker element.
     */
    public DatePickerPw(Page page, String id) {
        this.page = page;
        this.root = page.locator("#" + id + " > input");
    }
    
    /**
     * Creates a DatePicker page object for the given locator.
     *
     * @param gridLocator the Playwright locator for the vaadin-date-picker to
     * be interacted with
     */
    public DatePickerPw(Locator gridLocator) {
        this.root = gridLocator;
        this.page = gridLocator.page();
    }

    /**
     * Returns the value from the client side and parses it as
     * {@link LocalDate}.
     *
     * @return the current value of the field
     */
    public LocalDate getValue() {
        String str = (String) root.evaluate("db => db.value");
        try {
            return LocalDate.parse(str);
        } catch (java.time.format.DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Sets the value of the field.
     *
     * @param value
     *            the value to be set
     */
    public void setValue(LocalDate value) {
        String formattedDate = (dateFormat == null ? value.toString()
                : DateTimeFormatter.ofPattern(dateFormat).format(value));

        root.fill(formattedDate);
        root.press("Enter");

        // Wait for the date picker overlay to be hidden
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions();
        options.setState(options.state.HIDDEN);
        page.waitForSelector("vaadin-date-picker-overlay", options);
    }

    /**
     * Sets the format to be used when setting the date value.
     *
     * @param format
     *            The format to be set.
     */
    public void setDateFormat(String format) {
        dateFormat = format;
    }

    /**
     * Returns the raw string value in the field.
     *
     * @return the string value as it is formatted in the field. Note, this may
     *         be locale dependent.
     */
    public String getInputString() {
        return root.locator("input").inputValue();
    }
}
