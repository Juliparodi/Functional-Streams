package com.example.demo.functional;

import com.example.demo.domain.City;
import com.example.demo.domain.User;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@AllArgsConstructor
public class FunctionalStream {

    public static void run() {
        	/*
		4 PILLARS OF FUNCTIONAL PROGRAMMING:
		Supplier
		Consumer
		Predicate
		Function
		 */

        //FUNCTIONAL AND ANONIMOUSE CLASSES ARE NOT THE SAME, functional is better in performance

        Supplier<String> supplier = () -> {
            System.out.println("I am inside the supplier");
            return "Hello!";
        };

        String string = supplier.get();
        System.out.println("string = " + string);

        Consumer<String> consumer = (String s) -> {
            System.out.println("I am inside the Consumer");
            System.out.println(s);

        };

        consumer.accept("Hello");
        //SpringApplication.run(InterfaceApplication.class, args);

        //PREDICATE IS FOR FILTERING
        //FUNCTION IS FOR MAPPING

        List<String> strings =
                new ArrayList<>(List.of("one", "two", "three", "four", "five"));

        Predicate<String> filter = (s) -> s.startsWith("t");
        strings.removeIf(filter);

        Consumer<String> action = (s) -> System.out.println(s);
        strings.forEach(action);

        mappingFunction();
        specializedSupplierAndFunction();
        chainingLambdas();
        comparators();
        compareByMultipleProperties();
        streams();
        streamsTextFile();
        streamsArray();
        streamsRegex();
        streamLetters();

    }

    private static void streamLetters() {
        String sentence = "Hey i'm Julian and i just wanna practice streams";
        Predicate<String> equalsH = (s) -> s.equals("H");

        sentence.chars()
                .mapToObj(codePoint -> Character.toString(codePoint))
                .filter(equalsH)
                .distinct()
                .sorted()
                .forEach(letter -> System.out.println(letter));
    }

    private static void streamsRegex() {
        String sentence = "Hey i'm Julian and i just wanna practice streams";

        Pattern pattern = Pattern.compile(" ");
        long count = pattern.splitAsStream(sentence).count();
        System.out.println("count of stream using regex: " + count);
    }

    private static void streamsArray() {
        String sentence = "Hey i'm Julian and i just wanna practice streams";
        String[] sentenceArray = sentence.split(" ");

        Stream<String> sentenceStream = Arrays.stream(sentenceArray);
        long count = sentenceStream.count();
        System.out.println("count of stream array: " + count);
    }

    private static void streamsTextFile()  {

        // Convert the URI to a Path
        Path path = Path.of("src/main/resources/static/first-names.txt");

        try (Stream<String> lines = Files.lines(path)) {
            long count = lines.count();
            System.out.println("count = " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File Path: " + path);
    }

    private static void streams() {
        User sarah = new User("1", "Sarah");
        User james = new User("2", "James");
        User james2 = new User("5", "James");
        User mary = new User("3", "Mary");
        User john = new User("4", "John");

        List<User> listOfUsers = new ArrayList<>(List.of(sarah, james, mary, john, james2));
        List<User> listOfUsers2 = new ArrayList<>(List.of(sarah, james, mary, john, james2));

        Predicate<User> filter = (s) -> s.getName().contains("t");
        listOfUsers.removeIf(filter);

        City newYork = new City("newYork", listOfUsers);

        Predicate<User> filter2 = (s) -> s.getName().contains("s");
        listOfUsers2.removeIf(filter2);

        City BA = new City("BA", listOfUsers2);

        List<City> cities = List.of(newYork, BA);

        cities.stream()
                .flatMap(x -> x.getUserList().stream())
                .map(User::getName)
                .forEach(System.out::println);


    }

    private static void compareByMultipleProperties() {
        User sarah = new User("1", "Sarah");
        User james = new User("2", "James");
        User james2 = new User("5", "James");
        User mary = new User("3", "Mary");
        User john = new User("4", "John");

        List<User> listOfUsers = new ArrayList<>(List.of(sarah, james, mary, john, james2));

        //IF YOU WANT TO SORT BY NAME AND THEN BY ID IF THERE IS TWO USERS WITH SAME NAME
        Comparator<User> cmpName = Comparator.comparing(user -> user.getName());
        Comparator<User> cmpId = Comparator.comparing(user -> user.getId());
        Comparator<User> comparator = cmpName.thenComparing(cmpId);

        //IF YOU WANT TO SORT IN REVERSED ORDER
        Comparator<User> comparatorReversed =comparator.reversed();

        listOfUsers.sort(comparatorReversed);
        listOfUsers.forEach(System.out::println);

    }

    private static void comparators() {
        List<String> strings = new ArrayList<>(
                List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
        Comparator<String> cmp = (s1, s2) -> s1.compareTo(s2);
        strings.sort(cmp);
        System.out.println(strings);

        ToIntFunction<String> toLengthInt = s -> s.length();
        Function<String, Integer> toLength = s -> s.length();

        Comparator<String> cmp2 =
                Comparator.comparing(toLength);
        Comparator<String> cmpInt =
                Comparator.comparingInt(toLengthInt);
        strings.sort(cmp2);
        System.out.println(strings);
    }

    private static void chainingLambdas() {

        Consumer<String> c1 = s -> System.out.println("c1 consumes: " + s);
        Consumer<String> c2 = s -> System.out.println("c2 consumes: " + s);

        Consumer<String> c3 = s -> {
            c1.accept("Hello");
            c2.accept("hello");
        };

        c3.accept("Hello");

        Predicate<String> isNull = s -> s == null;
        System.out.println("For null = " + isNull.test(null));
        System.out.println("For hello = " + isNull.test("Hello"));

        Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println("For empty = " + isEmpty.test(""));
        System.out.println("For hello = " + isEmpty.test("Hello"));

        Predicate<String> p = isNull.negate().and(isEmpty.negate());
        System.out.println("For null = " + p.test(null));
        System.out.println("For empty = " + p.test(""));
        System.out.println("For hello = " + p.test("Hello"));


    }

    private static void specializedSupplierAndFunction() {

        IntSupplier supplier = () -> 10;
        int i = supplier.getAsInt();
        System.out.println("i = " + i);

        DoubleToIntFunction function = (double value) -> (int) Math.floor(value);
        int pi = function.applyAsInt(Math.PI);
        System.out.println("Pi = " + pi);

    }
    private static void mappingFunction() {

        User sarah = new User("1", "Sarah");
        User james = new User("2", "James");
        User mary = new User("3", "Mary");
        User john = new User("4", "John");

        List<User> listOfUsers = List.of(sarah, james, mary, john);
        List<String> names = new ArrayList<>();
        Function<User, String> toName = User::getName;

        listOfUsers.forEach(s -> {
            String name = toName.apply(s);
            names.add(name);
        });

        listOfUsers.forEach(s -> System.out.println(s));
        names.forEach(n -> System.out.println(n));

    }


}
