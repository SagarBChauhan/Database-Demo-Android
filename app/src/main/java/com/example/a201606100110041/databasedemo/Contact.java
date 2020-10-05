package com.example.a201606100110041.databasedemo;

public class Contact {
    int ID;
    String Name;
    String ContactNo;

    public Contact()
    {

    }

    public Contact(String name,String contactNo)
    {
        this.Name = name;
        this.ContactNo = contactNo;
    }

    public Contact(int Id, String name, String contactNo)
    {
        this.ID =Id;
        this.Name = name;
        this.ContactNo = contactNo;
    }

    public Contact(int Id)
    {
        this.ID = Id;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int Id)
    {
        this.ID = Id;
    }

    public String getName()
    {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getContactNo() {
        return this.ContactNo;
    }

    public void setContactNo(String contactNo) {
        this.ContactNo = contactNo;
    }
}
