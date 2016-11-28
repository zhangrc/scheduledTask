package com.yinhai.sheduledTask.frame.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by zrc on 2016/11/28.
 */
public class StringUtil extends StringUtils {
    private static Log log = LogFactory.getLog(StringUtil.class);
    private static final String FOLDER_SEPARATOR = "/";
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
    private static final String TOP_PATH = "..";
    private static final String CURRENT_PATH = ".";

    public StringUtil() {
    }

    public static boolean startsWithIgnoreCase(String var0, String var1) {
        if(var0 != null && var1 != null) {
            if(var0.startsWith(var1)) {
                return true;
            } else if(var0.length() < var1.length()) {
                return false;
            } else {
                String var2 = var0.substring(0, var1.length()).toLowerCase();
                String var3 = var1.toLowerCase();
                return var2.equals(var3);
            }
        } else {
            return false;
        }
    }

    public static int countOccurrencesOf(String var0, String var1) {
        if(var0 != null && var1 != null && var0.length() != 0 && var1.length() != 0) {
            int var2 = 0;
            int var3 = 0;

            int var5;
            for(boolean var4 = false; (var5 = var0.indexOf(var1, var3)) != -1; var3 = var5 + var1.length()) {
                ++var2;
            }

            return var2;
        } else {
            return 0;
        }
    }

    public static String delete(String var0, String var1) {
        return replace(var0, var1, "");
    }

    public static String deleteAny(String var0, String var1) {
        if(var0 != null && var1 != null) {
            StringBuffer var2 = new StringBuffer();

            for(int var3 = 0; var3 < var0.length(); ++var3) {
                char var4 = var0.charAt(var3);
                if(var1.indexOf(var4) == -1) {
                    var2.append(var4);
                }
            }

            return var2.toString();
        } else {
            return var0;
        }
    }

    public static String unqualify(String var0) {
        return unqualify(var0, '.');
    }

    public static String unqualify(String var0, char var1) {
        return var0.substring(var0.lastIndexOf(var1) + 1);
    }

    public static String getFilename(String var0) {
        int var1 = var0.lastIndexOf("/");
        return var1 != -1?var0.substring(var1 + 1):var0;
    }

    public static String applyRelativePath(String var0, String var1) {
        int var2 = var0.lastIndexOf("/");
        if(var2 != -1) {
            String var3 = var0.substring(0, var2);
            if(!var1.startsWith("/")) {
                var3 = var3 + "/";
            }

            return var3 + var1;
        } else {
            return var1;
        }
    }

    public static String cleanPath(String var0) {
        String var1 = replace(var0, "\\", "/");
        String[] var2 = delimitedListToStringArray(var1, "/");
        LinkedList var3 = new LinkedList();
        int var4 = 0;

        for(int var5 = var2.length - 1; var5 >= 0; --var5) {
            if(!".".equals(var2[var5])) {
                if("..".equals(var2[var5])) {
                    ++var4;
                } else if(var4 > 0) {
                    --var4;
                } else {
                    var3.add(0, var2[var5]);
                }
            }
        }

        return collectionToDelimitedString(var3, "/");
    }

    public static boolean pathEquals(String var0, String var1) {
        return cleanPath(var0).equals(cleanPath(var1));
    }

    public static Locale parseLocaleString(String var0) {
        String[] var1 = tokenizeToStringArray(var0, "_ ", false, false);
        String var2 = var1.length > 0?var1[0]:"";
        String var3 = var1.length > 1?var1[1]:"";
        String var4 = var1.length > 2?var1[2]:"";
        return var2.length() > 0?new Locale(var2, var3, var4):null;
    }

    public static String[] addStringToArray(String[] var0, String var1) {
        String[] var2 = new String[var0.length + 1];
        System.arraycopy(var0, 0, var2, 0, var0.length);
        var2[var0.length] = var1;
        return var2;
    }

    public static String[] sortStringArray(String[] var0) {
        if(var0 == null) {
            return new String[0];
        } else {
            Arrays.sort(var0);
            return var0;
        }
    }

    public static Properties splitArrayElementsIntoProperties(String[] var0, String var1) {
        return splitArrayElementsIntoProperties(var0, var1, (String)null);
    }

    public static Properties splitArrayElementsIntoProperties(String[] var0, String var1, String var2) {
        if(var0 != null && var0.length != 0) {
            Properties var3 = new Properties();
            String var4 = "";

            for(int var5 = 0; var5 < var0.length; ++var5) {
                var4 = var0[var5];
                if(var2 != null) {
                    var4 = deleteAny(var0[var5], var2);
                }

                String[] var6 = split(var4, var1);
                if(var6 != null) {
                    var3.setProperty(var6[0].trim(), var6[1].trim());
                }
            }

            return var3;
        } else {
            return null;
        }
    }

    public static String[] tokenizeToStringArray(String var0, String var1) {
        return tokenizeToStringArray(var0, var1, true, true);
    }

    public static String[] tokenizeToStringArray(String var0, String var1, boolean var2, boolean var3) {
        StringTokenizer var4 = new StringTokenizer(var0, var1);
        ArrayList var5 = new ArrayList();

        while(true) {
            String var6;
            do {
                if(!var4.hasMoreTokens()) {
                    return (String[])((String[])var5.toArray(new String[var5.size()]));
                }

                var6 = var4.nextToken();
                if(var2) {
                    var6 = var6.trim();
                }
            } while(var3 && var6.length() <= 0);

            var5.add(var6);
        }
    }

    public static String[] delimitedListToStringArray(String var0, String var1) {
        if(var0 == null) {
            return new String[0];
        } else if(var1 == null) {
            return new String[]{var0};
        } else {
            ArrayList var2 = new ArrayList();
            int var3 = 0;

            int var5;
            for(boolean var4 = false; (var5 = var0.indexOf(var1, var3)) != -1; var3 = var5 + var1.length()) {
                var2.add(var0.substring(var3, var5));
            }

            if(var0.length() > 0 && var3 <= var0.length()) {
                var2.add(var0.substring(var3));
            }

            return (String[])((String[])var2.toArray(new String[var2.size()]));
        }
    }

    public static String[] commaDelimitedListToStringArray(String var0) {
        return delimitedListToStringArray(var0, ",");
    }

    public static Set commaDelimitedListToSet(String var0) {
        TreeSet var1 = new TreeSet();
        String[] var2 = commaDelimitedListToStringArray(var0);

        for(int var3 = 0; var3 < var2.length; ++var3) {
            var1.add(var2[var3]);
        }

        return var1;
    }

    public static String arrayToDelimitedString(Object[] var0, String var1) {
        if(var0 == null) {
            return "";
        } else {
            StringBuffer var2 = new StringBuffer();

            for(int var3 = 0; var3 < var0.length; ++var3) {
                if(var3 > 0) {
                    var2.append(var1);
                }

                var2.append(var0[var3]);
            }

            return var2.toString();
        }
    }

    public static String collectionToDelimitedString(Collection var0, String var1, String var2, String var3) {
        if(var0 == null) {
            return "";
        } else {
            StringBuffer var4 = new StringBuffer();
            Iterator var5 = var0.iterator();

            for(int var6 = 0; var5.hasNext(); ++var6) {
                if(var6 > 0) {
                    var4.append(var1);
                }

                var4.append(var2).append(var5.next()).append(var3);
            }

            return var4.toString();
        }
    }

    public static String collectionToDelimitedString(Collection var0, String var1) {
        return collectionToDelimitedString(var0, var1, "", "");
    }

    public static String arrayToCommaDelimitedString(Object[] var0) {
        return arrayToDelimitedString(var0, ",");
    }

    public static String collectionToCommaDelimitedString(Collection var0) {
        return collectionToDelimitedString(var0, ",");
    }

    public static String encodePassword(String var0, String var1) {
        byte[] var2 = var0.getBytes();
        MessageDigest var3 = null;

        try {
            var3 = MessageDigest.getInstance(var1);
        } catch (Exception var7) {
            log.error("Exception: " + var7);
            return var0;
        }

        var3.reset();
        var3.update(var2);
        byte[] var4 = var3.digest();
        StringBuffer var5 = new StringBuffer();

        for(int var6 = 0; var6 < var4.length; ++var6) {
            if((var4[var6] & 255) < 16) {
                var5.append("0");
            }

            var5.append(Long.toString((long)(var4[var6] & 255), 16));
        }

        return var5.toString();
    }

    public static String encodeString(String var0) {
        Base64 var1 = new Base64();

        try {
            return ((String)var1.encode(var0)).trim();
        } catch (EncoderException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String decodeString(String var0) {
        Base64 var1 = new Base64();
        return new String(var1.decode(var0));
    }

    public static String toChinese(String var0) {
        try {
            if(var0 == null) {
                return null;
            } else {
                var0 = new String(var0.getBytes("ISO8859_1"), "GBK");
                return var0;
            }
        } catch (Exception var2) {
            return null;
        }
    }

    public static final int compareTo(String var0, String var1) {
        return var0.compareTo(var1);
    }

    public static final String rightGBKBytePad(String var0, int var1, char var2) {
        try {
            byte[] var3 = var0.getBytes("GBK");
            String var4 = new String(var3) + StringUtils.rightPad("", var1 - var3.length, var2);
            return var4;
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static final String leftGBKBytePad(String var0, int var1, char var2) {
        try {
            byte[] var3 = var0.getBytes("GBK");
            String var4 = StringUtils.leftPad("", var1 - var3.length, var2) + new String(var3);
            return var4;
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String getPYString(String var0) {
        StringBuffer var1 = new StringBuffer("");

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var3 = var0.charAt(var2);
            if(var3 >= 33 && var3 <= 126) {
                var1.append(String.valueOf(var3));
            } else {
                var1.append(getPYChar(String.valueOf(var3)));
            }
        }

        return var1.toString();
    }

    public static String getPYChar(String var0) {
        if(null != var0 && 0 != var0.trim().length()) {
            byte[] var1 = String.valueOf(var0).getBytes();
            if(2 > var1.length) {
                return var0;
            } else {
                int var2 = (short)(var1[0] - 0 + 256) * 256 + (short)(var1[1] - 0 + 256);
                return var2 < '낡'?"*":(var2 < '냅'?"a":(var2 < '닁'?"b":(var2 < '듮'?"c":(var2 < '뛪'?"d":(var2 < '랢'?"e":(var2 < '룁'?"f":(var2 < '맾'?"g":(var2 < '믷'?"h":(var2 < '뾦'?"j":(var2 < '사'?"k":(var2 < '싨'?"l":(var2 < '쓃'?"m":(var2 < '얶'?"n":(var2 < '얾'?"o":(var2 < '웚'?"p":(var2 < '좻'?"q":(var2 < '죶'?"r":(var2 < '쯺'?"s":(var2 < '췚'?"t":(var2 < '컴'?"w":(var2 < '톹'?"x":(var2 < '퓑'?"y":(var2 < 'ퟺ'?"z":"*")))))))))))))))))))))));
            }
        } else {
            return var0;
        }
    }

    public static String getRandomString(int var0) {
        String var1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random var2 = new Random();
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < var0; ++var4) {
            int var5 = var2.nextInt(var1.length());
            var3.append(var1.charAt(var5));
        }

        return var3.toString();
    }
}
