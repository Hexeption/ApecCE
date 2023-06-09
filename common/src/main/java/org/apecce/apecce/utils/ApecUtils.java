package org.apecce.apecce.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.FormattedText;

public class ApecUtils {

    public static boolean isContainedIn(String s1, String s2) {
        char[] targetChars = s2.toCharArray();
        char[] sourceChars = s1.toCharArray();

        int targetIndex = 0;
        for (char sourceChar : sourceChars) {
            if (sourceChar == targetChars[targetIndex]) {
                targetIndex++;
                if (targetIndex == targetChars.length) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containedByCharSequence(String str, String substr) {
        if (substr.length() > str.length()) {
            return false;
        }
        int j = 0;
        for (char c : str.toCharArray()) {
            if (c == substr.charAt(j)) {
                j++;
                if (j == substr.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String removeFirstSpaces(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        int i = 0;
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }

        return s.substring(i);
    }

    public enum SegmentationOptions {

        TOTALLY_EXCLUSIVE,
        TOTALLY_INCLUSIVE,
        ALL_INSTANCES_RIGHT,
        ALL_INSTANCES_LEFT

    }

    public static String segmentString(String string, String symbol, char leftChar, char rightChar, int allowedInstancesL, int allowedInstancesR, SegmentationOptions... options) {
        boolean totallyExclusive = false, totallyInclusive = false, allInstancesR = false, allInstancesL = false;
        for (SegmentationOptions option : options) {
            if (option == SegmentationOptions.TOTALLY_EXCLUSIVE) totallyExclusive = true;
            if (option == SegmentationOptions.TOTALLY_INCLUSIVE) totallyInclusive = true;
            if (option == SegmentationOptions.ALL_INSTANCES_RIGHT) allInstancesR = true;
            if (option == SegmentationOptions.ALL_INSTANCES_LEFT) allInstancesL = true;
        }
        return segmentString(string, symbol, leftChar, rightChar, allowedInstancesL, allowedInstancesR, totallyExclusive, totallyInclusive, allInstancesR, allInstancesL);
    }

    /**
     * @param string            = The string you want to extract data from
     * @param symbol            = A string that will act as a pivot
     * @param leftChar          = It will copy all the character from the left of the pivot until it encounters this character
     * @param rightChar         = It will copy all the character from the right of the pivot until it encounters this character
     * @param allowedInstancesL = How many times can it encounter the left char before it stops copying the characters
     * @param allowedInstancesR = How many times can it encounter the right char before it stops copying the characters
     * @param totallyExclusive  = Makes so that the substring wont include the character from the left index
     * @return Returns the string that is defined by the bounds of leftChar and rightChar encountered allowedInstacesL  respectively allowedInctancesR - 1 within it
     * allowedInsracesL only if totallyExclusive = false else allowedInstacesL - 1
     */

    public static String segmentString(String string, String symbol, char leftChar, char rightChar, int allowedInstancesL, int allowedInstancesR, boolean totallyExclusive, boolean totallyInclusive, boolean allInstancesR, boolean allInstancesL) {

        int leftIdx = 0, rightIdx = 0;

        if (string.contains(symbol)) {

            int symbolIdx = string.indexOf(symbol);

            for (int i = 0; symbolIdx - i > -1; i++) {
                leftIdx = symbolIdx - i;
                if (string.charAt(symbolIdx - i) == leftChar) allowedInstancesL--;
                if (allowedInstancesL == 0) {
                    break;
                }
            }

            symbolIdx += symbol.length() - 1;

            for (int i = 0; symbolIdx + i < string.length(); i++) {
                rightIdx = symbolIdx + i;
                if (string.charAt(symbolIdx + i) == rightChar) allowedInstancesR--;
                if (allowedInstancesR == 0) {
                    break;
                }
            }

            if (allowedInstancesL != 0 && allInstancesL) return null;
            if (allowedInstancesR != 0 && allInstancesR) return null;
            return string.substring(leftIdx + (totallyExclusive ? 1 : 0), rightIdx + (totallyInclusive ? 1 : 0));
        } else {
            return null;
        }

    }

    public static String removeAllColourCodes(String s) {
        while (s.contains("§")) {
            s = s.replace("§" + s.charAt(s.indexOf("§") + 1), "");
        }
        return s;
    }

    public static void drawOutlineText(Minecraft mc, PoseStack poseStack, String text, int x, int y, int colour) {
        String noColorText = removeAllColourCodes(text);
        mc.font.draw(poseStack, noColorText, x + 1, y, (colour >> 24) << 24);
        mc.font.draw(poseStack, noColorText, x - 1, y, (colour >> 24) << 24);
        mc.font.draw(poseStack, noColorText, x, y + 1, (colour >> 24) << 24);
        mc.font.draw(poseStack, noColorText, x, y - 1, (colour >> 24) << 24);
        mc.font.draw(poseStack, text, x, y, colour);
    }

    public static void drawOutlineWrappedText(Minecraft mc, PoseStack poseStack, String text, int x, int y, int wordWrap, int colour) {
        FormattedText formattedText = FormattedText.of(text);
        mc.font.drawWordWrap(poseStack, formattedText, x + 1, y, wordWrap, (colour >> 24) << 24);
        mc.font.drawWordWrap(poseStack, formattedText, x - 1, y, wordWrap, (colour >> 24) << 24);
        mc.font.drawWordWrap(poseStack, formattedText, x, y + 1, wordWrap, (colour >> 24) << 24);
        mc.font.drawWordWrap(poseStack, formattedText, x, y - 1, wordWrap, (colour >> 24) << 24);
        mc.font.drawWordWrap(poseStack, formattedText, x, y, wordWrap, colour);
    }

}
