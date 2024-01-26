package in.virit.mopo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
/**
 * A helper class to work with vaadin-time-picker component.
 */
public class TimePickerPw {

    private final Locator root;
    private final Page page;
    private String timeFormat;

    /**
     * Constructs a TimePicker instance with the specified Page and element ID.
     *
     * @param page
     *            The Page to which the time picker belongs.
     * @param id
     *            The ID of the time picker element.
     */
    public TimePickerPw(Page page, String id) {
        this.page = page;
        this.root = page.locator("#" + id + " > input");
    }

    /**
     * Sets the value of the time picker to the specified LocalTime. The
     * formatted time is entered into the input field, and "Enter" is pressed.
     * Additionally, it waits for the time picker overlay to be hidden after
     * setting the value.
     *
     * @param value
     *            The LocalTime to be set.
     */
    public void setValue(LocalTime value) {
        String formattedTime = (timeFormat == null ? value.toString()
                : DateTimeFormatter.ofPattern(timeFormat, Locale.ROOT).format(value));

        root.fill(formattedTime);
        root.press("Enter");

        // Wait for the time picker overlay to be hidden
        Page.WaitForSelectorOptions options = new Page.WaitForSelectorOptions();
        options.setState(options.state.HIDDEN);
        page.waitForSelector("vaadin-time-picker-overlay", options);
    }
    
    /**
     * Returns the value from the client side and parses it as
     * {@link LocalDate} using a {@link DateTimeFormatter}.
     *
     * @return the current value of the field
     */
    public LocalTime getValue() {
        String value = root.inputValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat, Locale.ROOT);
        
        return timeFormat == null ? LocalTime.parse(value)
            : LocalTime.parse(value, formatter);
    }

    /**
     * Sets the format to be used when setting the time value.
     *
     * @param format
     *            The format to be set.
     */
    public void setDateFormat(String format) {
        timeFormat = format;
    }

}
