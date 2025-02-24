package org.example;

public class Human {
    public String name;
    public int age;

    public Human(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Umur tidak boleh negatif.");
        }
        this.name = name;
        this.age = age;
    }
}
