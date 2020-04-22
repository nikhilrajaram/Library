package com.example.Library.Util;

import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.Model.Book;
import com.example.Library.Model.Item;
import com.example.Library.Model.Movie;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ItemDataLoaderUtil {
    private static final Character COMMA_DELIMETER = ',';

    private static SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat yearMonthFormatter = new SimpleDateFormat("yyyy-mm");
    private static SimpleDateFormat yearMonthDayFormatter = new SimpleDateFormat("yyyy-mm-dd");

    private static Random randnGenerator = new Random(4448);
    private static CSVParser parser = new CSVParserBuilder()
            .withSeparator(COMMA_DELIMETER)
            .withIgnoreQuotations(false)
            .build();

    public static void readAndInsertBooks(String filepath, ItemDataDAOImpl itemDataDAO, BookDAOImpl bookDAO) throws FileNotFoundException {
        // credit for reading in csv code: https://www.baeldung.com/java-csv-file-array
        List<Item> items = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filepath))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Book bookToAdd = getBookItem(values);
                Item itemToAdd = getSemiRandomBookItem(bookToAdd.getItemId());
                books.add(bookToAdd);
                items.add(itemToAdd);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        itemDataDAO.batchAddItems(items);
        bookDAO.batchAddBooks(books);
    }

    public static void readAndInsertMovies(String filepath, ItemDataDAOImpl itemDataDAO, MovieDAOImpl movieDAO) throws FileNotFoundException {
        List<Item> items = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filepath))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Movie movieToAdd = getMovieItem(values);
                Item itemToAdd = getSemiRandomMovieItem(movieToAdd.getItemId());
                movies.add(movieToAdd);
                items.add(itemToAdd);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        itemDataDAO.batchAddItems(items);
        movieDAO.batchAddMovie(movies);
    }

    private static Item getSemiRandomMovieItem(Integer itemId) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setType("movie");
        item.setnAvailable((int) Math.round(5+randnGenerator.nextDouble()*3));
        item.setnCheckedOut(0);
        if (randnGenerator.nextDouble() <= 0.5) {
            item.setDigital(true);
        } else {
            item.setDigital(false);
        }
        return item;
    }

    private static Item getSemiRandomBookItem(Integer itemId) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setType("book");
        item.setnAvailable((int) Math.round(5+randnGenerator.nextDouble()*3));
        item.setnCheckedOut(0);
        if (randnGenerator.nextDouble() <= 0.5) {
            item.setDigital(true);
        } else {
            item.setDigital(false);
        }
        return item;
    }

    private static Book getBookItem(String[] values) {
        Book book = new Book();
        book.setItemId(parseInt(values[0]));
        book.setTitle(values[1]);
        book.setAuthor(values[2]);
        book.setPubDate(parseDate(values[3]));
        book.setGenre(values[4]);
        book.setSummary(values[5]);
        return book;
    }

    private static Movie getMovieItem(String[] values) {
        Movie movie = new Movie();
        movie.setItemId(parseInt(values[0]));
        movie.setTitle(values[1]);
        movie.setReleaseDate(parseDate(values[2]));
        movie.setRuntime(parseInt(values[3]));
        movie.setGenre(values[4]);
        movie.setSummary(values[5]);
        return movie;
    }

    private static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return (int) Math.floor(Double.parseDouble(s));
        }
    }

    private static java.sql.Date parseDate(String s) {
        int strlen = s.length();
        if (strlen == 4) {
            try {
                return new java.sql.Date(yearFormatter.parse(s).getTime());
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        } else if (strlen == 7) {
            try {
                return new java.sql.Date(yearMonthFormatter.parse(s).getTime());
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        } else if (strlen == 10) {
            try {
                return new java.sql.Date(yearMonthDayFormatter.parse(s).getTime());
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        }
        return fallbackParseDate(s);
    }

    private static java.sql.Date fallbackParseDate(String s) {
        try {
            return new java.sql.Date(yearFormatter.parse(s).getTime());
        } catch (ParseException i) {
            try {
                return new java.sql.Date(yearMonthFormatter.parse(s).getTime());
            } catch (ParseException j) {
                try {
                    return new java.sql.Date(yearMonthDayFormatter.parse(s).getTime());
                } catch (ParseException k) {
                    return null;
                }
            }
        }
    }
}
