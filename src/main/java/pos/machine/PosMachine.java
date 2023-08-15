package pos.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static pos.machine.ItemsLoader.loadAllItems;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> items = decodeToItem(barcodes);
        Receipt receipt = calculateCost(items);
        String renderReceipt = renderReceipt(receipt);

        return renderReceipt;
    }

    public List<ReceiptItem> decodeToItem(List<String> barcodes) {
        List<Item> items = loadAllItems();
        Map<String, Long> barcodeCountMap = barcodes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<ReceiptItem> receiptItems = new ArrayList<>();

        for (Item item : items) {
            String barcode = item.getBarcode();
            Long count = barcodeCountMap.get(barcode);

            if (count != null && count > 0) {
                receiptItems.add(new ReceiptItem(item.getName(), item.getPrice(), count.intValue()));
            }
        }

        return receiptItems;
    }

    public Receipt calculateCost(List<ReceiptItem> receiptItem) {
        List<ReceiptItem> receiptItems = calculateItemsCost(receiptItem);
        int totalPrice = calculateTotalPrice(receiptItem);
        Receipt receipt = new Receipt(receiptItems, totalPrice);

        return receipt;
    }

    public String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt);
        String generatedReceipt = generateReceipt(itemsReceipt, receipt.getTotalPrice());
        return generatedReceipt;
    }

    public List<ReceiptItem> calculateItemsCost(List<ReceiptItem> receiptItems) {
        for (ReceiptItem item : receiptItems) {
            item.setSubtotal(item.getQuantity() * item.getUnitPrice());
        }

        return receiptItems;
    }

    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        int total = 0;
        for (ReceiptItem item : receiptItems) {
            total += item.getsubTotal();
        }
        return total;
    }

    public String generateItemsReceipt(Receipt receipt) {
        String itemReceipt = "";

        List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        for (ReceiptItem item : receiptItems) {
            String itemName = item.getName();
            int quantity = item.getQuantity();
            int unitPrice = item.getUnitPrice();
            int subTotal = item.getsubTotal();
            itemReceipt += "Name: " + itemName
                    + ", Quantity: " + quantity
                    + ", Unit price: " + unitPrice
                    + " (yuan), Subtotal: " + subTotal
                    + " (yuan)\n";
        }

        return itemReceipt;
    }

    public String generateReceipt(String itemsReceipt, int totalPrice) {
        String receipt = "***<store earning no money>Receipt***\n" +
                itemsReceipt + "----------------------\n" +
                "Total: " + totalPrice + " (yuan)\n" +
                "**********************";
        return receipt;
    }


}
