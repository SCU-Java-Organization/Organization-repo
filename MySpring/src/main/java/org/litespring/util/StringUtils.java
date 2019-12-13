package org.litespring.util;

/**
 * Description: A tool class for String
 * We can judge if a String or CharSequence is null or empty
 * @author ShaoJiale
 * date 2019/12/12
 */
public abstract class StringUtils {

    public static boolean hasLength(String str){
        return hasLength((CharSequence)str);
    }

    public static boolean hasLength(CharSequence str){
        return str != null && str.length() > 0;
    }

    public static boolean hasText(String str){
        return hasText((CharSequence)str);
    }

    public static boolean hasText(CharSequence str) {
        if(!hasLength(str))
            return false;

        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i)))
                return true;
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if(!hasLength(str))
            return str;

        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index){
            if (Character.isWhitespace(sb.charAt(index)))
                sb.deleteCharAt(index);
            else
                index++;
        }
        return sb.toString();
    }
}
