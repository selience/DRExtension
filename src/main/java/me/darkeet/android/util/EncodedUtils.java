package me.darkeet.android.util;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

/**
 * Name: EncodedUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/8 15:49
 * Desc: 处理URL编码类
 */
public class EncodedUtils {

    private static final String DEFAULT_CONTENT_CHARSET = "UTF-8";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    /**
     * Returns a list of {@link HashMap<String, String>} as built from the
     * URI's query portion. For example, a URI of
     * http://example.org/path/to/file?a=1&b=2&c=3 would return a list of three
     * string, one for a=1, one for b=2, and one for c=3.
     * <p/>
     * This is typically useful while parsing an HTTP PUT.
     *
     * @param uri      uri to parse
     * @param encoding encoding to use while parsing the query
     */
    public static Map<String, String> parse(final URI uri, final String encoding) {
        Map<String, String> result = Collections.emptyMap();
        final String query = uri.getRawQuery();
        if (query != null && query.length() > 0) {
            result = new HashMap<String, String>();
            parse(result, new Scanner(query), encoding);
        }
        return result;
    }

    /**
     * Adds all parameters within the Scanner to the list of
     * <code>parameters</code>, as encoded by <code>encoding</code>. For
     * example, a scanner containing the string <code>a=1&b=2&c=3</code> would
     * add the {@link HashMap<String, String>} a=1, b=2, and c=3 to the
     * list of parameters.
     *
     * @param parameters List to add parameters to.
     * @param scanner    Input that contains the parameters to parse.
     * @param encoding   Encoding to use when decoding the parameters.
     */
    public static void parse(
            final Map<String, String> parameters,
            final Scanner scanner,
            final String encoding) {
        scanner.useDelimiter(PARAMETER_SEPARATOR);
        while (scanner.hasNext()) {
            final String[] nameValue = scanner.next().split(NAME_VALUE_SEPARATOR);
            if (nameValue.length == 0 || nameValue.length > 2)
                throw new IllegalArgumentException("bad parameter");

            final String name = decode(nameValue[0], encoding);
            String value = null;
            if (nameValue.length == 2)
                value = decode(nameValue[1], encoding);
            parameters.put(name, value);
        }
    }

    /**
     * Returns a String that is suitable for use as an list of parameters in an HTTP PUT or HTTP POST.
     *
     * @param parameters The parameters to include.
     * @param encoding   The encoding to use.
     */
    public static String format(
            final Map<String, String> parameters,
            final String encoding) {
        final StringBuilder result = new StringBuilder();
        for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
            final String encodedName = encode(parameter.getKey(), encoding);
            final String value = parameter.getValue();
            final String encodedValue = value != null ? encode(value, encoding) : "";
            if (result.length() > 0)
                result.append(PARAMETER_SEPARATOR);
            result.append(encodedName);
            result.append(NAME_VALUE_SEPARATOR);
            result.append(encodedValue);
        }
        return result.toString();
    }

    public static String decode(final String content, final String encoding) {
        try {
            return URLDecoder.decode(content,
                    encoding != null ? encoding : DEFAULT_CONTENT_CHARSET);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    public static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(content,
                    encoding != null ? encoding : DEFAULT_CONTENT_CHARSET);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }
}

