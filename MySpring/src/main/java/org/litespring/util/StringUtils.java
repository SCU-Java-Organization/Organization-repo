package org.litespring.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Description: A tool class for String
 * We can judge if a String or CharSequence is null or empty
 *
 * @author ShaoJiale
 * date 2019/12/12
 */
public abstract class StringUtils {

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str))
            return false;

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return true;
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str))
            return str;

        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index)))
                sb.deleteCharAt(index);
            else
                index++;
        }
        return sb.toString();
    }

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (str == null)
            return null;

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens)
                token = token.trim();
            if (!ignoreEmptyTokens || token.length() > 0)
                tokens.add(token);
        }
        return toStringArray(tokens);
    }

    public static String[] toStringArray(List<String> collection) {
        if (collection == null)
            return null;
        return collection.toArray(new String[collection.size()]);
    }
}
