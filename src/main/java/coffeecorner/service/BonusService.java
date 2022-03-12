package coffeecorner.service;

import coffeecorner.data.Product;
import coffeecorner.data.Type;

import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;

public class BonusService {

    public static double computePriceWithReductions(List<Product> products) {
        //fail fast
        if (products==null || products.size()==0) {
            return 0L;
        }

        DoubleAdder price = new DoubleAdder();
        price.add(grossPrice(products));
        int beveragesCount = getNrOfBeverages(products);
        int groupsOfFive = beveragesCount / 5;
        //apply the reduction for every 5th beverage:
        price.add((-3d)*groupsOfFive); //assumption: offer a free medium beverage to keep it simple (3 CHF) because for example
        // ... in case of 3 smalls and 2 large I don't know what should we reduce (1 large or 1 small? Which is the fifth beverage?) So I kept it simple

        //check if there are pairs of beverage+snack and make free one extra for each pair
        //assumption: for simplicity apply the offer for the first extra (even if there can be more extras)
        double snacksCount = getNrOfSnacks(products);
        double extraOffers = Math.min(beveragesCount, snacksCount);

        // for simplicity apply the "extra" offer for the first beverages
        products.stream()
                .filter(p -> Type.BEVERAGE==p.getType())
                .limit((long)extraOffers)
                .forEach(p -> price.add((-1)*p.getExtras().get(0).getPrice()));

        return price.doubleValue();
    }


    private static int getNrOfBeverages(List<Product> products) {
        return (int)products.stream().filter(p -> Type.BEVERAGE==p.getType()).count();
    }

    private static int getNrOfSnacks(List<Product> products) {
        return (int)products.stream().filter(p -> Type.SNACK==p.getType()).count();
    }


    /**
     * This method computes the total price without any reduction/offer
     * @param products
     * @return
     */
    public static double grossPrice(List<Product> products) {
        DoubleAdder grossPrice = new DoubleAdder();
        products.forEach(p -> {
            grossPrice.add(p.getPrice());
            p.getExtras().forEach(x -> grossPrice.add(x.getPrice()));
        });
        return grossPrice.doubleValue();
    }



}
