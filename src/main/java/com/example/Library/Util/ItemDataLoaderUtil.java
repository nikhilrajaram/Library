package com.example.Library.Util;

import com.example.Library.DAO.BookDAO;
import com.example.Library.DAO.ItemDataDAO;
import com.example.Library.Model.Book;
import com.example.Library.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ItemDataLoaderUtil {
    private final String COMMA_DELIMETER = ",";

    @Autowired
    ItemDataDAO itemDataDAO;

    @Autowired
    BookDAO bookDAO;

    private SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private SimpleDateFormat yearMonthFormatter = new SimpleDateFormat("yyyy-mm");
    private SimpleDateFormat yearMonthDayFormatter = new SimpleDateFormat("yyyy-mm-dd");

    private Random randnGenerator;

    public ItemDataLoaderUtil() {
        this.randnGenerator = new Random(4448);
    }

    public void readAndInsertBooks(String filepath) throws FileNotFoundException  {
        Boolean isFirstLine = true;

        List<Item> items = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filepath));) {
            while (scanner.hasNextLine()) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                Book bookToAdd = getBookItemFromLine(scanner.next());
                Item semiRandomBookItem = getSemiRandomBookItem(bookToAdd.getItemId());
                books.add(bookToAdd);
                items.add(semiRandomBookItem);
            }
        }

        itemDataDAO.batchAddItems(items);
        bookDAO.batchAddBooks(books);
    }

    private Item getSemiRandomBookItem(Integer itemId) {
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

    private Book getBookItemFromLine(String line) {
        Book book = new Book();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMETER);
            book.setItemId(rowScanner.nextInt());
            book.setTitle(rowScanner.next());
            book.setAuthor(rowScanner.next());
            book.setPubDate(parseDate(rowScanner.next()));
            book.setGenre(rowScanner.next());
            book.setSummary(rowScanner.next());
        }
        return book;
    }

    private Date parseDate(String s) {
        int strlen = s.length();
        if (strlen == 4) {
            try {
                return yearFormatter.parse(s);
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        } else if (strlen == 7) {
            try {
                return yearMonthFormatter.parse(s);
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        } else if (strlen == 10) {
            try {
                return yearMonthDayFormatter.parse(s);
            } catch (ParseException e) {
                return fallbackParseDate(s);
            }
        }
        return fallbackParseDate(s);
    }

    private Date fallbackParseDate(String s) {
        try {
            return yearFormatter.parse(s);
        } catch (ParseException i) {
            try {
                return yearMonthFormatter.parse(s);
            } catch (ParseException j) {
                try {
                    return yearMonthDayFormatter.parse(s);
                } catch (ParseException k) {
                    return null;
                }
            }
        }
    }
}
