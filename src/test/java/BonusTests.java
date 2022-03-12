import coffeecorner.data.Product;
import coffeecorner.service.BonusService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static coffeecorner.service.PrintService.printReceipt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BonusTests {

    private static final double DELTA = Double.valueOf(0.00000001);

    @Test
    void testNoOffer() {
        Product smallCoffeeWithFoamedMilk = new Product("small coffee");
        smallCoffeeWithFoamedMilk.setExtras(List.of(new Product("foamed milk")));
        List<Product> underTest = List.of(smallCoffeeWithFoamedMilk);

        assertEquals(BonusService.grossPrice(underTest), BonusService.computePriceWithReductions(underTest));
        System.out.println(printReceipt(underTest));
    }


    @Test
    void testStampCardSimple_oneReduction() {
        Product smallCoffee = new Product("small coffee");
        List<Product> underTest = List.of(smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - 3); //price of medium (assumption: reduction for 5th is with the price of a medium, for simplicity)
        System.out.println(printReceipt(underTest));
    }

    @Test
    void testStampCard_oneReduction_difficult() {
        Product smallCoffee = new Product("small coffee");
        List<Product> underTest = List.of(smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee,
                smallCoffee, smallCoffee);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - 3); //price of medium (assumption: reduction for 5th is with the price of a medium, for simplicity)
        System.out.println(printReceipt(underTest));
    }


    @Test
    void testStampCardSimple_twoReductions() {
        Product smallCoffee = new Product("small coffee");
        Product mediumCoffee = new Product("medium coffee");
        List<Product> underTest = List.of(smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee,
                mediumCoffee, mediumCoffee, mediumCoffee, mediumCoffee, mediumCoffee);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - 3*2); //price of medium (assumption: reduction for 5th is with the price of a medium, for simplicity)
        System.out.println(printReceipt(underTest));
    }

    @Test
    void testStampCardSimple_twoReductionsDifficult() {
        Product smallCoffee = new Product("small coffee");
        Product mediumCoffee = new Product("medium coffee");
        List<Product> underTest = List.of(smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee,
                mediumCoffee, mediumCoffee, mediumCoffee, mediumCoffee, mediumCoffee, mediumCoffee);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - 3*2); //price of medium (assumption: reduction for 5th is with the price of a medium, for simplicity)
        System.out.println(printReceipt(underTest));
    }


    @Test
    void testStampCard_oneReduction_complex() {
        // add some extras
        Product smallCoffee = new Product("small coffee");
        smallCoffee.setExtras(List.of(new Product("extra milk"), new Product("special roast coffee")));
        List<Product> underTest = List.of(smallCoffee, smallCoffee, smallCoffee, smallCoffee, smallCoffee,
                smallCoffee, smallCoffee);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - 3); //price of medium (assumption: reduction for 5th is with the price of a medium, for simplicity)
        System.out.println(printReceipt(underTest));
    }


    @Test
    void testBeverageAndSnack_simple() {
        Product smallCoffeeWith2Extras = new Product("small coffee");
        Product extraMilk = new Product("extra milk");
        smallCoffeeWith2Extras.setExtras(List.of(extraMilk, new Product("special roast coffee")));
        Product snack = new Product("bacon roll");

        List<Product> underTest = List.of(smallCoffeeWith2Extras, smallCoffeeWith2Extras, smallCoffeeWith2Extras, snack);

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest); //3.7*3 + 4.5 = 15.6 CHF
        assertNotEquals(reducedPrice, grossPrice);
        assertEquals(reducedPrice, grossPrice - extraMilk.getPrice(), DELTA);
        System.out.println(printReceipt(underTest));
    }


    @Test
    void testMoreBeveragesAndSnacks_complex() {
        Product smallCoffeeWith2Extras = new Product("small coffee");
        Product extraMilk = new Product("extra milk");
        Product specialRoast = new Product("special roast coffee");
        smallCoffeeWith2Extras.setExtras(List.of(specialRoast, extraMilk));
        Product largeCoffeeWithExtraFoam = new Product("large coffee");

        Product extraFoamedMilk = new Product("foamed milk");
        largeCoffeeWithExtraFoam.setExtras(List.of(extraFoamedMilk));
        Product snack = new Product("bacon roll");

        List<Product> underTest = List.of(smallCoffeeWith2Extras, smallCoffeeWith2Extras, smallCoffeeWith2Extras,
                largeCoffeeWithExtraFoam, largeCoffeeWithExtraFoam,
                snack, snack);
        // reductions applied should be:
        // 3 CHF (price of a medium beverage) for the 5th beverage
        // two extras free because of 2 snacks combined with 2 beverages (0.9 CHF + 0.9 CHF)
        // Total reductions should be 4.8 CHF

        double reducedPrice = BonusService.computePriceWithReductions(underTest);
        double grossPrice = BonusService.grossPrice(underTest);
        assertNotEquals(reducedPrice, grossPrice);
        double totalReductions = grossPrice-reducedPrice;
        assertEquals(totalReductions, 3 + 2*specialRoast.getPrice(), DELTA); //the assumption made in the code was that ...
        // ... the reduced extra is the first one from each snack, in our case special roast
        assertEquals(4.8, totalReductions, DELTA);
        assertEquals(reducedPrice, grossPrice - totalReductions, DELTA);
        System.out.println(printReceipt(underTest));
    }

}
