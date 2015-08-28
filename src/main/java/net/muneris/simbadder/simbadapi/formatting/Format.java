package net.muneris.simbadder.simbadapi.formatting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Format class allows for simplified generation of an appropriate
 * formatting string to pass to the SIMBAD api along with the desired query.
 * Utilizing this class eliminates the difficulty of ensuring that a formatting
 * string contains the proper SIMBAD value parameters, URI encoding and textual
 * elements to return a machine readable JSON string.
 *
 * Format is primarily intended to accept items from the FormatField enum in
 * order desired response fields. As an alternative, it may be configured to
 * accept format fields as well, although this is not recommended as it defeats
 * the purpose of the class.
 *
 * Refer to FormatField for more details.
 *
 * @see net.muneris.simbadder.simbadapi.formatting.FormatField
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class Format {

    /** Prefix which SIMBAD requires to proceed all formatting instructions. */
    private static final String FORMAT_PREFIX = "format object %s \"{";
    /** Bracket to close JSON formatted return data. */
    private static final String FORMAT_SUFFIX = "}\"";

    public static Format all() {
        Format format = new Format("ALL");
        format.setFields(FormatField.getAll());
        return format;
    }

    public static Format allNonDistance() {
        Format format = new Format("ALL_NON_DISTANCE");
        format.setFields(FormatField.getAllNotDist());
        return format;
    }

    /** Name applied to the format. */
    private final String formatName;

    /** Map of field names with field formatting strings. */
    private Map<String, String> fields;

    /** Primary constructor. */
    public Format(String formatName) {
        this.formatName = formatName;
        this.fields = new HashMap<>();
    }

    /**
     * Add a formatting element in the form of FormatField enumeration.
     *
     * @param field
     *            a pre-configured formatting string
     */
    public void addField(FormatField field) {
        if (!fields.containsKey(field.toString())) {
            fields.put(field.toString(), field.valueOf());
        }
    }

    /**
     * Add an arbitrary formatting string.
     *
     * @param field
     *            specifier name
     * @param value
     *            formatting string
     */
    public void addField(String field, String value) {
        fields.put(field, value);
    }

    /**
     * Get the map of field names and string associated with this instance.
     *
     * @return field name-string map
     */
    public Map<String, String> getFields() {
        return fields;
    }

    /**
     * Returns the string that will be passed to SIMBAD to determine the format
     * of data returned.
     *
     * @return actual formatting string to be inserted into request URI.
     */
    public String getFormatString() {
        String format =
                fields.entrySet().stream().map(fieldEntry -> fieldEntry.getValue())
                        .collect(Collectors.joining(", "));
        return String.format(FORMAT_PREFIX, formatName) + format + FORMAT_SUFFIX;
    }

    /**
     * SIMBAD allows submission of multiple queries in a single script, and it
     * is possible to associate a format string with each one. To so, strings
     * must be named. Although this capability is not currently implemented in
     * this microservice, the format string name is included for a future use
     * case.
     *
     * @return the name of this format specification.
     */
    public String getName() {
        return formatName;
    }

    /**
     * Removes a FormatField entry, if it exists (optional execution).
     *
     * @param field
     *            specifier to be removed
     */
    public void removeField(FormatField field) {
        fields.remove(field.toString());
    }

    /**
     * Removes a formatting entry based on the string field name.
     *
     * @param field
     *            name
     */
    public void removeField(String field) {
        fields.remove(field);
    }

    /**
     * Set a pre-determined list of FormatFields.
     *
     * @param fields
     *            list of FormatFields
     */
    public void setFields(List<FormatField> fields) {
        this.fields = new HashMap<>();
        fields.stream().forEach(field -> this.fields.put(field.toString(), field.valueOf()));
    }

    /**
     * Set a pre-determined mapping of field names and formatting strings.
     *
     * @param fields
     *            name-string map
     */
    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
