package com.awiserk.kundalias.demo2.utils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;
/**
 * Created by akundalia on 4/30/2017.
 */

public class NumberTextWatcherForThousand implements TextWatcher {

    EditText editText;
    int cursorPosition;


    public NumberTextWatcherForThousand(EditText editText) {
        this.editText = editText;
        this.cursorPosition = editText.getVerticalScrollbarPosition();


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();


            if (value != null && !value.equals(""))
            {

                if(value.startsWith(".")){
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    editText.setText("");

                }


                String str = editText.getText().toString().replaceAll(",", "");
                if (!value.equals(""))
                    editText.setText(getDecimalFormattedString(str, editText));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getDecimalFormattedString(String value, EditText editText)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }

        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if(str3.length() > 13)
                {
                    //Limit decimal to be 10 digit only + 3 commas
                    str3 = str3.substring(0, Math.min(str3.length(), 13));
                }
                if (str2.length() > 0)
                {
                    if(str2.length() > 2)
                    {
                        //Limit decimal to be 2 digit only
                        str2 = str2.substring(0, Math.min(str2.length(), 2));
                        editText.setFilters(new InputFilter[] {
                                        new InputFilter.LengthFilter(19)});
                    }
                    str3 = str3 + "." + str2;
                }

                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }
    }

    public static String trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return string.replace(",","");}
        else {
            return string;
        }

    }
}