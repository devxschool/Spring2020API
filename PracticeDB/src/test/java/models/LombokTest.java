package models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LombokTest {
    int id;
    private String name;
    private String lastName;

    public void setName(String name) {
        this.name = "Hello";
    }

    public static void main(String[] args) {
        LombokTest lt = new LombokTest();
        lt.setId(1);
        lt.setLastName("Bond");
        lt.setName("James");

        LombokTest lt2 = new LombokTest();
        lt2.setId(1);
        lt2.setLastName("Bond");
        lt2.setName("James");
        System.out.println(lt.equals(lt2));

        LombokTest lt3 = new LombokTest(1, "John", "Doe");

        System.out.println(lt);
        System.out.println(lt2);
    }
}


