package PRACTISE;

import java.util.Objects;

public class TestEqua {

    String name;
    int rollNo;
    String section;

    @Override
    public boolean equals(Object obj) {
        TestEqua o=(TestEqua) obj;
        return rollNo==o.rollNo && name.equalsIgnoreCase(o.name) && section.equalsIgnoreCase(o.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rollNo, section);
    }

    public static void main(String[] args) {
    TestEqua   testEqua=new TestEqua();
    testEqua.section="A";
    testEqua.name="mah";
    testEqua.rollNo=1;

        TestEqua   testEqua1=new TestEqua();
        testEqua1.section="B";
        testEqua1.name="mah";
        testEqua1.rollNo=1;

        System.out.println(testEqua.equals(testEqua1));

    }
}
