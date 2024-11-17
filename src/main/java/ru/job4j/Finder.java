package ru.job4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Finder {

    public static void handle(ArgsName argsName) throws IOException {
        List<Path> list = find(argsName);
        try (PrintWriter writer = new PrintWriter(argsName.get("o"))) {
            for (Path l : list) {
                writer.println(l.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        validate(args);
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }

    public static List<Path> find(ArgsName argsName) throws IOException {
        SearchFiles searcher = new SearchFiles();
        Path root = Paths.get(argsName.get("d"));
        Files.walkFileTree(root, searcher);
        Predicate<Path> condition = new Predicate<>() {
            @Override
            public boolean test(Path path) {
                return false;
            }
        };
        Pattern pattern;
        switch (argsName.get("t")) {
            case ("mask") -> {
                pattern = Pattern.compile(argsName.get("n").replace("*", "\\"));
                condition = p -> pattern.matcher(p.toFile().getName()).find();
            }
            case ("name") -> {
                condition = p -> argsName.get("n").equals(p.toFile().getName());
            }
            case ("regexp") -> {
                pattern = Pattern.compile(argsName.get("n"));
                condition = p -> pattern.matcher(p.toFile().getName()).find();
            }
            default -> throw new IllegalStateException("Unexpected value: " + argsName.get("t"));
        }
        return searcher.getPaths().stream().filter(condition).toList();
    }

    private static void validate(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage ROOT_FOLDER.");
        }
        if (args.length < 4) {
            throw new IllegalArgumentException("Paramenetrs count must be equal 4.");
        }
        ArgsName jvm = ArgsName.of(args);
        if (!Paths.get(jvm.get("d")).toFile().exists()) {
            throw new IllegalArgumentException("The directory must exist.");
        }
        if ("".equals(jvm.get("n"))) {
            throw new IllegalArgumentException("The file name must be defined.");
        }
        if ("".equals(jvm.get("t"))) {
            throw new IllegalArgumentException("The search type must be defined.");
        }
        if (!List.of("mask", "name", "regexp").contains(jvm.get("t"))) {
            throw new IllegalArgumentException("The search type must have an correct value.");
        }
        if ("".equals(jvm.get("o"))) {
            throw new IllegalArgumentException("The ouput file name must be defined.");
        }
    }
}
