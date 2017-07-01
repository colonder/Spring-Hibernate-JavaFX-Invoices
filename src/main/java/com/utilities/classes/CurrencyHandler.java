package com.utilities.classes;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

import java.math.BigDecimal;

/*
 * Using ICU4J libraries for converting numbers to words, more can be found at
 * Documentation: http://icu-project.org/apiref/icu4j/
 * I highly recommend using this libraries for any kind of project (especially engineering, language and banking ones,
 * ICU has created very good stuff supporting those issues in Java)
 */
public class CurrencyHandler
{
    // can be easily changed to almost any language that is supported by ICU
    private static ULocale locale = new ULocale("pl_PL");
    //private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private static NumberFormat spellOutFormatter = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);

    public static String convertToWords(BigDecimal value)
    {

        // SO EASY! It even supports BigDecimal right out of the box!
        // Need a little bit of modification
        return spellOutFormatter.format(value.intValue());

        //TODO: get back to this later
    }
}