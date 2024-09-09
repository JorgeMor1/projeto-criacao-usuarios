package br.com.syonet.model;

public class Studant {
    private long id;
    private String name;
    private int age;
    private String email;

    public Studant(long id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Studant(String name, int age, String email) {
        this(0, name, age, email);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) { 
            this.age = age;
        } else {
            throw new IllegalArgumentException("Idade não pode ser negativa.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("O e-mail deve ser um endereço de e-mail válido");
        }
    }

    @Override
    public String toString() {
        return String.format("Studant{id=%d, name='%s', age=%d, email='%s'}", id, name, age, email);
    }
}
