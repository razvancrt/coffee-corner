package coffeecorner.service;

import coffeecorner.data.Type;

import java.util.List;

import static coffeecorner.data.Type.BEVERAGE;
import static coffeecorner.data.Type.EXTRA;
import static coffeecorner.data.Type.SNACK;

public class PriceTypeService {

    private static final List<String> prodNames = List.of("small coffee", "medium coffee",
            "large coffee", "bacon roll", "orange juice", "extra milk", "foamed milk", "special roast coffee");
    private static final List<Type> types = List.of(BEVERAGE, BEVERAGE, BEVERAGE, SNACK, BEVERAGE, EXTRA, EXTRA, EXTRA);
    private static final List<Double> prices = List.of(2.50, 3.00, 3.50, 4.50, 3.95, 0.30, 0.50, 0.90);

    public static double getPriceForProductName(String productName) {
        return prices.get(prodNames.indexOf(productName)).doubleValue();
    }

    public static Type getTypeForProductName(String productName) {
        return types.get(prodNames.indexOf(productName));
    }
}
