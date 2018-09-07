package com.utilities;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
 * Using ICU4J libraries for converting numbers to words, more can be found at
 * Documentation: http://icu-project.org/apiref/icu4j/
 * I highly recommend using this libraries for any kind of project (especially engineering, language and banking ones,
 * ICU has created very good stuff supporting those issues in Java)
 */
@Component
public class CurrencyHandler {

    private static ULocale locale = new ULocale("pl_PL");
    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private static NumberFormat spellOutFormatter = new RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT);
    private static Currency currency = Currency.getInstance(locale);

    static {
        currencyFormatter.setCurrency(currency);
    }

    public String formatToCurrency(BigDecimal value) {
        return currencyFormatter.format(value);
    }

    public String convertSumToWords(BigDecimal value) {
        String cents = value.toString();
        cents = cents.substring(cents.indexOf(".") + 1);
        String digits = String.valueOf(value.intValue());
        char character = digits.charAt(digits.length() - 1);
        String currencySymbolSpellOut;
        if (character == '1' && digits.length() < 2) {
            currencySymbolSpellOut = "złoty";
        } else if (character == '2' || character == '3' || character == '4') {
            currencySymbolSpellOut = "złote";
        } else {
            currencySymbolSpellOut = "złotych";
        }

        return spellOutFormatter.format(value.intValue()) + " " + currencySymbolSpellOut + " " + cents + "/100";
    }


}