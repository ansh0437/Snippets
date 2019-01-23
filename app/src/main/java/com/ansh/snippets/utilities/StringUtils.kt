package com.ansh.snippets.utilities

object StringUtils {

    fun isBlank(cs: CharSequence?): Boolean {
        if (cs == null || cs.isEmpty()) {
            return true
        }
        for (i in 0 until cs.length) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }

    fun isNotBlank(cs: CharSequence): Boolean {
        return !isBlank(cs)
    }

    fun containsIgnoreCase(str: CharSequence?, searchStr: CharSequence?): Boolean {
        if (str == null || searchStr == null) {
            return false
        }
        val len = searchStr.length
        val max = str.length - len
        for (i in 0..max) {
            if (regionMatches(str, true, i, searchStr, 0, len)) {
                return true
            }
        }
        return false
    }

    private fun regionMatches(
        cs: CharSequence, ignoreCase: Boolean, thisStart: Int,
        substring: CharSequence, start: Int, length: Int
    ): Boolean {
        if (cs is String && substring is String) {
            return cs.regionMatches(thisStart, substring, start, length, ignoreCase = ignoreCase)
        }
        var index1 = thisStart
        var index2 = start
        var tmpLen = length
        while (tmpLen-- > 0) {
            val c1 = cs[index1++]
            val c2 = substring[index2++]
            if (c1 == c2) {
                continue
            }
            if (!ignoreCase) {
                return false
            }
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(
                    c2
                )
            ) {
                return false
            }
        }
        return true
    }
}
