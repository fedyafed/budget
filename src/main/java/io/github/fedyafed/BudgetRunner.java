package io.github.fedyafed;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import static io.github.fedyafed.BudgetRunner.FilePart.*;

/**
 * Created by fedyafed on 09.08.16.
 */
public class BudgetRunner {
    private static AtomicLong purchaseSeq = new AtomicLong(0);

    public static final String MAJOR_VERSION = "1.0";

    public static void main(String[] args) throws IOException, FileParseException {
        if (args.length != 1) {
            throw new FileParseException("Specify a file to scan as an argument.");
        }

        String filePath = args[0];
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            scanFile(reader);
        } catch (FileNotFoundException e) {
            throw new FileParseException(String.format("Specified file '%s' isn't exists.", filePath));
        }
    }

    enum FilePart {
        Header,
        Categories,
        Purchases
    }

    private static void scanFile(BufferedReader reader) throws IOException, FileParseException {
        FilePart state = Header;
        String s = reader.readLine();
        do {
            switch (state) {
                case Header:
                    if (checkHeader(s) && (s = reader.readLine()) != null && s.isEmpty()) {
                        state = Categories;
                    } else {
                        throw new FileParseException("File is not supported.");
                    }
                    break;
                case Categories:
                    if (addCategory(s) == null) {
                        state = Purchases;
                    }
                    break;
                case Purchases:
                    addPurchase(s);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown FilePart: " + state);
            }
        } while ((s = reader.readLine()) != null);
    }

    private static boolean checkHeader(String header) {
        return ("Budget v." + MAJOR_VERSION).equals(header);
    }

    private static Category addCategory(String s) throws FileParseException {
        if (s.isEmpty()) return null;

        String[] categoryParts = s.split(",");
        if (categoryParts.length != 3) {
            throw new FileParseException("Invalid category: " + s);
        }

        return new Category(
                Long.parseUnsignedLong(categoryParts[0]),
                categoryParts[1],
                Long.parseUnsignedLong(categoryParts[2])
        );
    }

    private static Purchase addPurchase(String s) throws FileParseException {
        if (s.isEmpty()) return null;

        String[] purchaseParts = s.split(",");
        if (purchaseParts.length != 4) {
            throw new FileParseException("Invalid purchase: " + s);
        }

        return new Purchase(
                purchaseSeq.incrementAndGet(),
                purchaseParts[0],
                Long.parseUnsignedLong(purchaseParts[1]),
                Double.parseDouble(purchaseParts[2]),
                Double.parseDouble(purchaseParts[3])
        );
    }
}
