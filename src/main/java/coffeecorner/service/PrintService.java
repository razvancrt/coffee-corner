package coffeecorner.service;

import coffeecorner.data.Product;

import java.util.List;

public class PrintService {

    public static String printReceipt(List<Product> products) {
        // print the complete list of products first, then print the reductions/offers
        StringBuilder receipt = new StringBuilder();
        products.forEach(p -> {
            receipt.append(p.getName() + "....................." + p.getPrice());
            receipt.append("\n");
            List<Product> extras = p.getExtras();
            if (!extras.isEmpty()) {
                receipt.append("with ");
                extras.forEach(x -> {
                    receipt.append(x.getName() + "....................." + x.getPrice());
                    receipt.append("\n");
                    receipt.append(" and ");
                });
                receipt.replace(receipt.length()-6, receipt.length()-1, ""); //remove the last "and"
                receipt.append("\n");
            }
        });

        //prepare printing reductions/offers:
        double totalGrossPrice = BonusService.grossPrice(products);
        double totalReducedPrice = BonusService.computePriceWithReductions(products);

        if (totalReducedPrice < totalGrossPrice) {
            receipt.append("Bonuses/reductions" + "............." + (totalGrossPrice-totalReducedPrice) + "\n");
        }
        receipt.append("-------------------------------------------\n");
        receipt.append("TOTAL" + "........................" + totalReducedPrice);

        return receipt.toString();
    }
}
