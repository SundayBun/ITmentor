import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Дан массив объектов Person. Класс Person характеризуется полями age (возраст, целое число 0-100),
 * sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 * Создать два класса, методы которых будут реализовывать сортировку объектов.
 * Предусмотреть единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
 * первые идут мужчины
 * выше в списке тот, кто более старший
 * имена сортируются по алфавиту
 * Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 * Предусмотреть генерацию исходного массива (10000 элементов и более). Если имена людей и возраст совпадают,
 * выбрасывать в программе пользовательское исключение.
 */
class Person {

    private int age;
    static private final String SEXW = "Women";
    static private final String SEXM = "Men";
    private String sex;
    private String name;

    Person(String name, String sex, int age) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    static public Person[] randomPersonArray(String[] names, int size) {
        Person[] newArray = new Person[size];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = new Person(randomName(names), randomSex(), randomAge());
            if (chekForDuplicate(newArray, i)) {
                i--;
            }
        }
        return newArray;
    }

    static public String randomName(String[] names) {
        return names[(int) (Math.random() * names.length)];
    }

    static public int randomAge() {
        return (int) (Math.random() * 100 + 1);
    }

    static public String randomSex() {
        int randomDigit = (int) (Math.random() * 2 + 1);
        return randomDigit == 1 ? SEXM : SEXW;
    }

    public static boolean chekForDuplicate(Person[] personArray, int index) {
        boolean isDuplicate = false;
        for (int i = 0; i < index; i++) {
            try {
                if (personArray[i].equals(personArray[index])) {
                    isDuplicate = true;
                    throw new MyException("The duplicate element at index" + index);
                }

            } catch (MyException myException) {
                System.out.println(myException.getMessage());
            }
        }
        return isDuplicate;
    }


    @Override
    public String toString() {
        return "Name: " + getName() + ", sex: " + getSex() + ", age: " + getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(sex, person.sex) && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, sex, name);
    }
}

class MyException extends Exception {
    MyException(String message) {
        super(message);
    }
}

interface ISorting {
    void sorting();
}

class SortingBySex implements ISorting {
    Person[] person;

    SortingBySex(Person[] arrayPerson) {
        this.person = arrayPerson;
    }

    @Override
    public void sorting() {
        Arrays.sort(person, Comparator.comparing(Person::getSex));
    }
}

class SortingByName implements ISorting {
    Person[] person;

    SortingByName(Person[] arrayPerson) {
        this.person = arrayPerson;
    }

    @Override
    public void sorting() {
        Arrays.sort(person, Comparator.comparing(Person::getName));
    }
}

class SortingByAge implements ISorting {
    Person[] person;

    SortingByAge(Person[] arrayPerson) {
        this.person = arrayPerson;
    }

    @Override
    public void sorting() {
        Arrays.sort(person, Comparator.comparing(Person::getAge));
    }
}

public class Exception2ndLesson2 {
    public static void main(String[] args) {
        String[] names = new String[]{"Alex", "Lilla", "Jessica", "Tom", "Jake", "John", "Kevin"};
        Person[] personRandom = Person.randomPersonArray(names, 100);

        ISorting sortingByName = new SortingByName(personRandom);
        ISorting sortingByAge = new SortingByAge(personRandom);
        ISorting sortingBySex = new SortingBySex(personRandom);

        System.out.println("-----By name-----");
        measureTime(sortingByName::sorting);
        System.out.println("By name " + Arrays.toString(personRandom));

        System.out.println("\n-----By age-----");
        measureTime(sortingByAge::sorting);
        System.out.println("By age " + Arrays.toString(personRandom));

        System.out.println("\n-----By sex-----");
        measureTime(sortingBySex::sorting);
        System.out.println("By sex " + Arrays.toString(personRandom));

    }

    public static void measureTime(Runnable thread) {
        long startTime = System.currentTimeMillis();
        thread.run();
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("Затраченное время: " + elapsed + " ms");

    }
}
