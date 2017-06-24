package com.utilities.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumbersToWords
{
    private static HashMap<String, HashMap<String, String>> map;
    private static HashMap<String, HashMap<String, String>> currency;

    public NumbersToWords()
    {
        Type type = new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType();
        JsonReader reader;
        try
        {
            reader = new JsonReader(new FileReader("numbersInWords.json"));
            map = new Gson().fromJson(reader, type);
            reader = new JsonReader(new FileReader("currencyStrings.json"));
            currency = new Gson().fromJson(reader, type);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static String convert(BigDecimal value)
    {
        StringBuilder builder = new StringBuilder();

        String parts[] = value.toPlainString().split("\\.");
        int len = parts[0].length();

        if(len > 9)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wartość zbyt duża");
            alert.setContentText("Wartość jest zbyt duża, by automatycznie wygenerować zapis słowny. Należność zosanie" +
                    "obliczona, jednak bez zapisu słownego.");

            return "#NAN";
        }

        List<String> digits = new ArrayList<>();
        for(int i = len; i > 0; i -= 3)
        {
            digits.add(parts[0].substring(Math.max(0, i - 3), i)); //split every three digits from the back
        }

        digits.forEach(System.out::println);

        //builder.append(parts[1]).append("/100");

        //TODO: work in progress

        return builder.toString();
    }
}