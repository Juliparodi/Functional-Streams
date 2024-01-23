package com.example.demo.async;

import com.example.demo.domain.Quotation;
import com.example.demo.domain.Weather;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Async {

    static Random random = new Random();

    public static void run() throws InterruptedException {

        /*
        Patterns to create an Async task:
        //allOf Completes when all complete d
        //anyOf Completes on one of the fastest
         */

        /*
        Model tasks:
        Runnable: Does not taking any argyument and does not provide return
        Consumer: Take one argumento but not produce anything
        Function: Takes something and returns something
         */

        /*
        Chain tasks:
        thenRun() Runnable
        thenAccept() Consumer
        thenApply() Function
        thenCompose() Other BiFunction
        runAfterBoth() Runnable
        thenAcceptBoth() BiConsumer<T,U>
        thenCombine() BiFunction<T,U,V>
        runAfterEither() Runnable
        acceptEither() Consumer<T>
        applyToEither() Function<T,V>

        Exception Handling:
        exceptionally() Function<Throwable, T>
        handle() BiFunction<T, Throwable, U>
        whenComplete() BiConsumer<T, Throwable>
         */

        var quotationTasks = buildQuotationTasks();

        List<CompletableFuture<Quotation>> quotationCFS = new ArrayList<>();
        for (Supplier<Quotation> task : quotationTasks) {

            CompletableFuture<Quotation> future = CompletableFuture.supplyAsync(task);
            quotationCFS.add(future);
        }


        CompletableFuture<Void> allOf =
                CompletableFuture.allOf(quotationCFS.toArray(CompletableFuture[]::new));

        Quotation bestQuotation = allOf.thenApply( v->
            quotationCFS.stream()
                    .map(CompletableFuture::join)
                    .min(Comparator.comparing(Quotation::amount))
                    .orElseThrow()
        ).join();
        System.out.println("bestQuotation = " + bestQuotation);


        Instant begin = Instant.now();

        List<CompletableFuture<Quotation>> futures = new ArrayList<>();
        for (Supplier<Quotation> task : quotationTasks) {

            CompletableFuture<Quotation> quotationCompletableFuture =
            CompletableFuture.supplyAsync(task);
            futures.add(quotationCompletableFuture);
        }


        List<Quotation> quotations = new ArrayList<>();
        List<CompletableFuture<Void>> voids = new ArrayList<>();

        for (CompletableFuture<Quotation> future : futures) {
            future.thenAccept(System.out::println);
            CompletableFuture<Void> accept = future.thenAccept(quotations::add);
            voids.add(accept);
            future.thenAccept(quotations::add);
        }

        for (CompletableFuture<Void> v : voids) {
            v.join();
        }

        System.out.println("quotations = " + quotations);

        Thread.sleep(500);

        Quotation bestQuotation1 =
        quotations.stream()
                .min(Comparator.comparing(Quotation::amount))
                .orElseThrow();

        Instant end = Instant.now();
        Duration duration = Duration.between(begin, end);
        System.out.println("Best quotation [ASYNC] = " + bestQuotation1 + " (" + duration.toMillis() + "ms)");

        Random random1 = new Random();
        List<Supplier<Weather>> weatherTasks = buildWeatherTasks(random1);
        List<CompletableFuture<Weather>> futures2 = new ArrayList<>();
        for (Supplier<Weather> task : weatherTasks) {
            CompletableFuture<Weather> future = CompletableFuture.supplyAsync(task);
            futures2.add(future);
        }

        CompletableFuture<Object> future =
                CompletableFuture.anyOf(futures2.toArray(CompletableFuture[]::new));

        future.thenAccept(System.out::println).join();


    }

    private static List<Supplier<Quotation>> buildQuotationTasks() {
        Supplier<Quotation> fetchQuotationA =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Quotation("Server A", random.nextInt(40, 80));
                };

        Supplier<Quotation> fetchQuotationB =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Quotation("Server B", random.nextInt(30, 70));
                };

        Supplier<Quotation> fetchQuotationC =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Quotation("Server C", random.nextInt(30, 70));
                };

        return List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);

    }

    private static List<Supplier<Weather>> buildWeatherTasks(Random random1) {

        Supplier<Weather> fetchWeatherA =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Weather("Weather A","cloudy");
                };

        Supplier<Weather> fetchWeatherB =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Weather("Weather B","sunny");
                };

        Supplier<Weather> fetchWeatherC =
                () -> {
                    try {
                        Thread.sleep(random.nextInt(80, 120));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Weather("Weather C","cloudy");
                };

        return List.of(fetchWeatherA, fetchWeatherB, fetchWeatherC);

    }
}
