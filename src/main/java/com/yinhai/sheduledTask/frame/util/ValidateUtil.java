package com.yinhai.sheduledTask.frame.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by zrc on 2016/11/28.
 */
public class ValidateUtil {
    public static final String module = ValidateUtil.class.getName();
    public static final boolean defaultEmptyOK = true;
    public static final String digits = "0123456789";
    public static final String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    public static final String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String whitespace = " \t\n\r";
    public static final String decimalPointDelimiter = ".";
    public static final String phoneNumberDelimiters = "()- ";
    public static final String validUSPhoneChars = "0123456789()- ";
    public static final String validWorldPhoneChars = "0123456789()- +";
    public static final String SSNDelimiters = "- ";
    public static final String validSSNChars = "0123456789- ";
    public static final int digitsInSocialSecurityNumber = 9;
    public static final int digitsInUSPhoneNumber = 10;
    public static final int digitsInUSPhoneAreaCode = 3;
    public static final int digitsInUSPhoneMainNumber = 7;
    public static final String ZipCodeDelimiters = "-";
    public static final String ZipCodeDelimeter = "-";
    public static final String validZipCodeChars = "0123456789-";
    public static final int digitsInZipCode1 = 5;
    public static final int digitsInZipCode2 = 9;
    public static final String creditCardDelimiters = " -";
    public static final int[] daysInMonth = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String USStateCodeDelimiter = "|";
    public static final String USStateCodes = "AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY|AE|AA|AE|AE|AP";
    public static final String ContiguousUSStateCodes = "AL|AZ|AR|CA|CO|CT|DE|DC|FL|GA|ID|IL|IN|IA|KS|KY|LA|ME|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VT|VA|WA|WV|WI|WY";

    public ValidateUtil() {
    }

    public static boolean areEqual(Object var0, Object var1) {
        return var0 == null?var1 == null:var0.equals(var1);
    }

    public static boolean areEqualIgnoreCase(String var0, String var1) {
        return var0 == null?var1 == null:var0.equalsIgnoreCase(var1);
    }

    public static boolean isEmpty(Object var0) {
        if(var0 == null) {
            return true;
        } else {
            if(var0 instanceof String) {
                if(((String)var0).length() == 0) {
                    return true;
                }
            } else if(var0 instanceof Collection) {
                if(((Collection)var0).size() == 0) {
                    return true;
                }
            } else if(var0 instanceof Map && ((Map)var0).size() == 0) {
                return true;
            }

            return false;
        }
    }

    public static boolean isEmpty(String var0) {
        return var0 == null || var0.length() == 0;
    }

    public static boolean isEmpty(Collection var0) {
        return var0 == null || var0.size() == 0;
    }

    public static boolean isNotEmpty(String var0) {
        return var0 != null && var0.length() > 0;
    }

    public static boolean isNotEmpty(Collection var0) {
        return var0 != null && var0.size() > 0;
    }

    public static boolean isString(Object var0) {
        return var0 != null && var0 instanceof String;
    }

    public static boolean isWhitespace(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            for(int var1 = 0; var1 < var0.length(); ++var1) {
                char var2 = var0.charAt(var1);
                if(" \t\n\r".indexOf(var2) == -1) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String stripCharsInBag(String var0, String var1) {
        StringBuffer var3 = new StringBuffer("");

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var4 = var0.charAt(var2);
            if(var1.indexOf(var4) == -1) {
                var3.append(var4);
            }
        }

        return var3.toString();
    }

    public static String stripCharsNotInBag(String var0, String var1) {
        StringBuffer var3 = new StringBuffer("");

        for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var4 = var0.charAt(var2);
            if(var1.indexOf(var4) != -1) {
                var3.append(var4);
            }
        }

        return var3.toString();
    }

    public static String stripWhitespace(String var0) {
        return stripCharsInBag(var0, " \t\n\r");
    }

    public static boolean charInString(char var0, String var1) {
        return var1.indexOf(var0) != -1;
    }

    public static String stripInitialWhitespace(String var0) {
        int var1;
        for(var1 = 0; var1 < var0.length() && charInString(var0.charAt(var1), " \t\n\r"); ++var1) {
            ;
        }

        return var0.substring(var1);
    }

    public static boolean isLetter(char var0) {
        return Character.isLetter(var0);
    }

    public static boolean isDigit(char var0) {
        return Character.isDigit(var0);
    }

    public static boolean isLetterOrDigit(char var0) {
        return Character.isLetterOrDigit(var0);
    }

    public static boolean isInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            for(int var1 = 0; var1 < var0.length(); ++var1) {
                char var2 = var0.charAt(var1);
                if(!isDigit(var2)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isSignedInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                Integer.parseInt(var0);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isSignedLong(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                Long.parseLong(var0);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isPositiveInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                long var1 = Long.parseLong(var0);
                return var1 > 0L;
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public static boolean isNonnegativeInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                int var1 = Integer.parseInt(var0);
                return var1 >= 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isNegativeInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                int var1 = Integer.parseInt(var0);
                return var1 < 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isNonpositiveInteger(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                int var1 = Integer.parseInt(var0);
                return var1 <= 0;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isFloat(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            boolean var1 = false;
            if(var0.startsWith(".")) {
                return false;
            } else {
                for(int var2 = 0; var2 < var0.length(); ++var2) {
                    char var3 = var0.charAt(var2);
                    if(var3 == ".".charAt(0)) {
                        if(var1) {
                            return false;
                        }

                        var1 = true;
                    } else if(!isDigit(var3)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static boolean isSignedFloat(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                float var1 = Float.parseFloat(var0);
                return var1 <= 0.0F;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isSignedDouble(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            try {
                Double.parseDouble(var0);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isAlphabetic(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            for(int var1 = 0; var1 < var0.length(); ++var1) {
                char var2 = var0.charAt(var1);
                if(!isLetter(var2)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isAlphanumeric(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            for(int var1 = 0; var1 < var0.length(); ++var1) {
                char var2 = var0.charAt(var1);
                if(!isLetterOrDigit(var2)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isSSN(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "- ");
            return isInteger(var1) && var1.length() == 9;
        }
    }

    public static boolean isUSPhoneNumber(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "()- ");
            return isInteger(var1) && var1.length() == 10;
        }
    }

    public static boolean isUSPhoneAreaCode(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "()- ");
            return isInteger(var1) && var1.length() == 3;
        }
    }

    public static boolean isUSPhoneMainNumber(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "()- ");
            return isInteger(var1) && var1.length() == 7;
        }
    }

    public static boolean isInternationalPhoneNumber(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "()- ");
            return isPositiveInteger(var1);
        }
    }

    public static boolean isZipCode(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, "-");
            return isInteger(var1) && (var1.length() == 5 || var1.length() == 9);
        }
    }

    public static boolean isContiguousZipCode(String var0) {
        boolean var1 = false;
        if(isZipCode(var0)) {
            if(isEmpty(var0)) {
                var1 = true;
            } else {
                String var2 = var0.substring(0, 5);
                int var3 = Integer.parseInt(var2);
                if((var3 < 96701 || var3 > 96898) && (var3 < 99501 || var3 > 99950)) {
                    var1 = true;
                } else {
                    var1 = false;
                }
            }
        }

        return var1;
    }

    public static boolean isStateCode(String var0) {
        return isEmpty(var0)?true:"AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|GA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|UT|VT|VI|VA|WA|WV|WI|WY|AE|AA|AE|AE|AP".indexOf(var0) != -1 && var0.indexOf("|") == -1;
    }

    public static boolean isContiguousStateCode(String var0) {
        return isEmpty(var0)?true:"AL|AZ|AR|CA|CO|CT|DE|DC|FL|GA|ID|IL|IN|IA|KS|KY|LA|ME|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VT|VA|WA|WV|WI|WY".indexOf(var0) != -1 && var0.indexOf("|") == -1;
    }

    public static boolean isEmail(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else if(isWhitespace(var0)) {
            return false;
        } else {
            int var1 = 1;

            int var2;
            for(var2 = var0.length(); var1 < var2 && var0.charAt(var1) != 64; ++var1) {
                ;
            }

            return var1 < var2 - 1 && var0.charAt(var1) == 64;
        }
    }

    public static boolean isUrl(String var0) {
        return isEmpty(var0)?true:var0.indexOf("://") != -1;
    }

    public static boolean isYear(String var0) {
        return isEmpty(var0)?true:(!isNonnegativeInteger(var0)?false:var0.length() == 2 || var0.length() == 4);
    }

    public static boolean isIntegerInRange(String var0, int var1, int var2) {
        if(isEmpty(var0)) {
            return true;
        } else if(!isSignedInteger(var0)) {
            return false;
        } else {
            int var3 = Integer.parseInt(var0);
            return var3 >= var1 && var3 <= var2;
        }
    }

    public static boolean isMonth(String var0) {
        return isEmpty(var0)?true:isIntegerInRange(var0, 1, 12);
    }

    public static boolean isDay(String var0) {
        return isEmpty(var0)?true:isIntegerInRange(var0, 1, 31);
    }

    public static int daysInFebruary(int var0) {
        return var0 % 4 != 0 || var0 % 100 == 0 && var0 % 400 != 0?28:29;
    }

    public static boolean isHour(String var0) {
        return isEmpty(var0)?true:isIntegerInRange(var0, 0, 23);
    }

    public static boolean isMinute(String var0) {
        return isEmpty(var0)?true:isIntegerInRange(var0, 0, 59);
    }

    public static boolean isSecond(String var0) {
        return isEmpty(var0)?true:isIntegerInRange(var0, 0, 59);
    }

    public static boolean isDate(String var0, String var1, String var2) {
        if(isYear(var0) && isMonth(var1) && isDay(var2)) {
            int var3 = Integer.parseInt(var0);
            int var4 = Integer.parseInt(var1);
            int var5 = Integer.parseInt(var2);
            return var5 > daysInMonth[var4 - 1]?false:var4 != 2 || var5 <= daysInFebruary(var3);
        } else {
            return false;
        }
    }

    public static boolean isDate(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            int var4 = var0.indexOf("-");
            int var5 = var0.lastIndexOf("-");
            if(var4 > 0 && var4 != var5) {
                String var1 = var0.substring(0, var4);
                String var2 = var0.substring(var4 + 1, var5);
                String var3 = var0.substring(var5 + 1);
                return isDate(var1, var2, var3);
            } else {
                return false;
            }
        }
    }

    public static boolean isDateAfterToday(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            int var1 = var0.indexOf("-");
            int var2 = var0.lastIndexOf("-");
            if(var1 <= 0) {
                return false;
            } else {
                Date var3 = null;
                String var4;
                String var5;
                String var6;
                if(var1 == var2) {
                    var4 = var0.substring(0, var1);
                    var5 = "28";
                    var6 = var0.substring(var1 + 1);
                    if(!isDate(var4, var6, var5)) {
                        return false;
                    }

                    try {
                        int var7 = Integer.parseInt(var6);
                        int var8 = Integer.parseInt(var4);
                        Calendar var9 = Calendar.getInstance();
                        var9.set(var8, var7 - 1, 0, 0, 0, 0);
                        var9.add(2, 1);
                        var3 = new Date(var9.getTime().getTime());
                    } catch (Exception var10) {
                        var3 = null;
                    }
                } else {
                    var4 = var0.substring(0, var1);
                    var5 = var0.substring(var1 + 1, var2);
                    var6 = var0.substring(var2 + 1);
                    if(!isDate(var4, var5, var6)) {
                        return false;
                    }

                    var3 = DateUtil.toDate(var5, var6, var4, "0", "0", "0");
                }

                Date var11 = DateUtil.nowDate();
                return var3 != null?var3.after(var11):false;
            }
        }
    }

    public static boolean isDateAfterDate(String var0, java.sql.Date var1) {
        if(isEmpty(var0)) {
            return true;
        } else {
            int var2 = var0.indexOf("-");
            int var3 = var0.lastIndexOf("-");
            if(var2 <= 0) {
                return false;
            } else {
                Object var4 = null;
                String var5;
                String var6;
                String var7;
                if(var2 == var3) {
                    var5 = var0.substring(0, var2);
                    var6 = "28";
                    var7 = var0.substring(var2 + 1);
                    if(!isDate(var5, var7, var6)) {
                        return false;
                    }

                    try {
                        int var8 = Integer.parseInt(var7);
                        int var9 = Integer.parseInt(var5);
                        Calendar var10 = Calendar.getInstance();
                        var10.set(var9, var8 - 1, 0, 0, 0, 0);
                        var10.add(2, 1);
                        var4 = new Date(var10.getTime().getTime());
                    } catch (Exception var11) {
                        var4 = null;
                    }
                } else {
                    var5 = var0.substring(0, var2);
                    var6 = var0.substring(var2 + 1, var3);
                    var7 = var0.substring(var3 + 1);
                    if(!isDate(var5, var6, var7)) {
                        return false;
                    }

                    var4 = DateUtil.getDate(var5, var6, var7);
                }

                return var4 != null?((Date)var4).after(var1):false;
            }
        }
    }

    public static boolean isTime(String var0, String var1, String var2) {
        return isHour(var0) && isMinute(var1) && isSecond(var2);
    }

    public static boolean isTime(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            int var4 = var0.indexOf(":");
            int var5 = var0.lastIndexOf(":");
            if(var4 <= 0) {
                return false;
            } else {
                String var1 = var0.substring(0, var4);
                String var2;
                String var3;
                if(var4 == var5) {
                    var2 = var0.substring(var4 + 1);
                    var3 = "0";
                } else {
                    var2 = var0.substring(var4 + 1, var5);
                    var3 = var0.substring(var5 + 1);
                }

                return isTime(var1, var2, var3);
            }
        }
    }

    public static boolean isValueLinkCard(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, " -");
            return var1.length() == 16 && (var1.startsWith("7") || var1.startsWith("6"));
        }
    }

    public static boolean isGiftCard(String var0) {
        return isValueLinkCard(var0);
    }

    public static int getLuhnSum(String var0) {
        var0 = var0.replaceAll("\\D", "");
        int var1 = var0.length();
        int var2 = 0;
        int var3 = 1;

        for(int var4 = var1 - 1; var4 >= 0; --var4) {
            int var5 = Character.digit(var0.charAt(var4), 10);
            var5 *= var3 == 1?var3++:var3--;
            var2 += var5 >= 10?var5 % 10 + 1:var5;
        }

        return var2;
    }

    public static int getLuhnCheckDigit(String var0) {
        int var1 = getLuhnSum(var0);
        int var2 = ((var1 / 10 + 1) * 10 - var1) % 10;
        return 10 - var2;
    }

    public static boolean sumIsMod10(int var0) {
        return var0 % 10 == 0;
    }

    public static String appendCheckDigit(String var0) {
        String var1 = Integer.valueOf(getLuhnCheckDigit(var0)).toString();
        return var0 + var1;
    }

    public static boolean isCreditCard(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, " -");
            return var1.length() > 19?false:sumIsMod10(getLuhnSum(var1));
        }
    }

    public static boolean isVisa(String var0) {
        return (var0.length() == 16 || var0.length() == 13) && var0.substring(0, 1).equals("4")?isCreditCard(var0):false;
    }

    public static boolean isMasterCard(String var0) {
        int var1 = Integer.parseInt(var0.substring(0, 1));
        int var2 = Integer.parseInt(var0.substring(1, 2));
        return var0.length() == 16 && var1 == 5 && var2 >= 1 && var2 <= 5?isCreditCard(var0):false;
    }

    public static boolean isAmericanExpress(String var0) {
        int var1 = Integer.parseInt(var0.substring(0, 1));
        int var2 = Integer.parseInt(var0.substring(1, 2));
        return var0.length() != 15 || var1 != 3 || var2 != 4 && var2 != 7?false:isCreditCard(var0);
    }

    public static boolean isDinersClub(String var0) {
        int var1 = Integer.parseInt(var0.substring(0, 1));
        int var2 = Integer.parseInt(var0.substring(1, 2));
        return var0.length() != 14 || var1 != 3 || var2 != 0 && var2 != 6 && var2 != 8?false:isCreditCard(var0);
    }

    public static boolean isCarteBlanche(String var0) {
        return isDinersClub(var0);
    }

    public static boolean isDiscover(String var0) {
        String var1 = var0.substring(0, 4);
        return var0.length() == 16 && var1.equals("6011")?isCreditCard(var0):false;
    }

    public static boolean isEnRoute(String var0) {
        String var1 = var0.substring(0, 4);
        return var0.length() != 15 || !var1.equals("2014") && !var1.equals("2149")?false:isCreditCard(var0);
    }

    public static boolean isJCB(String var0) {
        String var1 = var0.substring(0, 4);
        return var0.length() != 16 || !var1.equals("3088") && !var1.equals("3096") && !var1.equals("3112") && !var1.equals("3158") && !var1.equals("3337") && !var1.equals("3528")?false:isCreditCard(var0);
    }

    public static boolean isAnyCard(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = stripCharsInBag(var0, " -");
            return !isCreditCard(var1)?false:isMasterCard(var1) || isVisa(var1) || isAmericanExpress(var1) || isDinersClub(var1) || isDiscover(var1) || isEnRoute(var1) || isJCB(var1);
        }
    }

    public static String getCardType(String var0) {
        if(isEmpty(var0)) {
            return "Unknown";
        } else {
            String var1 = stripCharsInBag(var0, " -");
            return !isCreditCard(var1)?"Unknown":(isMasterCard(var1)?"MasterCard":(isVisa(var1)?"Visa":(isAmericanExpress(var1)?"AmericanExpress":(isDinersClub(var1)?"DinersClub":(isDiscover(var1)?"Discover":(isEnRoute(var1)?"EnRoute":(isJCB(var1)?"JCB":"Unknown")))))));
        }
    }

    public static boolean isCardMatch(String var0, String var1) {
        if(isEmpty(var0)) {
            return true;
        } else if(isEmpty(var1)) {
            return true;
        } else {
            String var2 = stripCharsInBag(var1, " -");
            return var0.equalsIgnoreCase("VISA") && isVisa(var2)?true:(var0.equalsIgnoreCase("MASTERCARD") && isMasterCard(var2)?true:((var0.equalsIgnoreCase("AMERICANEXPRESS") || var0.equalsIgnoreCase("AMEX")) && isAmericanExpress(var2)?true:(var0.equalsIgnoreCase("DISCOVER") && isDiscover(var2)?true:(var0.equalsIgnoreCase("JCB") && isJCB(var2)?true:((var0.equalsIgnoreCase("DINERSCLUB") || var0.equalsIgnoreCase("DINERS")) && isDinersClub(var2)?true:(var0.equalsIgnoreCase("CARTEBLANCHE") && isCarteBlanche(var2)?true:var0.equalsIgnoreCase("ENROUTE") && isEnRoute(var2)))))));
        }
    }

    public static boolean isNotPoBox(String var0) {
        if(isEmpty(var0)) {
            return true;
        } else {
            String var1 = var0.toLowerCase();
            return var1.indexOf("p.o. b") != -1?false:(var1.indexOf("p.o.b") != -1?false:(var1.indexOf("p.o b") != -1?false:(var1.indexOf("p o b") != -1?false:(var1.indexOf("po b") != -1?false:(var1.indexOf("pobox") != -1?false:(var1.indexOf("po#") != -1?false:(var1.indexOf("po #") != -1?false:(var1.indexOf("p.0. b") != -1?false:(var1.indexOf("p.0.b") != -1?false:(var1.indexOf("p.0 b") != -1?false:(var1.indexOf("p 0 b") != -1?false:(var1.indexOf("p0 b") != -1?false:(var1.indexOf("p0box") != -1?false:(var1.indexOf("p0#") != -1?false:var1.indexOf("p0 #") == -1))))))))))))));
        }
    }
}
