package com.example.kalkulatorandroid;

public class ItemList {
    private final String input1;
    private final String input2;
    private final String operasi;
    private final String hasil;

    // Constructor
    public ItemList(String Minput1, String Moperasi, String Minput2, String Mhasil)
    {
        input1 = Minput1;
        operasi = Moperasi;
        input2 = Minput2;
        hasil = Mhasil;
    }

    public String getInput1()
    {
        return input1;
    }
    public String getInput2()
    {
        return input2;
    }
    public String getOperasi()
    {
        return operasi;
    }
    public String getHasil()
    {
        return hasil;
    }

}
