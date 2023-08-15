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

        return renderReceipt(receipt);
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

        return new Receipt(receiptItems, totalPrice);
    }

    public String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt);
        return generateReceipt(itemsReceipt, receipt.getTotalPrice());
    }

    public List<ReceiptItem> calculateItemsCost(List<ReceiptItem> receiptItems) {
        for (ReceiptItem item : receiptItems) {
            item.setSubtotal(item.getQuantity() * item.getUnitPrice());
        }

        return receiptItems;
    }

    public int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .mapToInt(ReceiptItem::getSubTotal)
                .sum();
    }


    public String generateItemsReceipt(Receipt receipt) {
        StringBuilder itemReceiptBuilder = new StringBuilder();

        List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        for (ReceiptItem item : receiptItems) {
            String itemName = item.getName();
            int quantity = item.getQuantity();
            int unitPrice = item.getUnitPrice();
            int subTotal = item.getSubTotal();

            String itemInfo = String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    itemName, quantity, unitPrice, subTotal);

            itemReceiptBuilder.append(itemInfo);
        }

        return itemReceiptBuilder.toString();
    }

    public String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***\n" +
                itemsReceipt +
                "----------------------\n" +
                "Total: " + totalPrice + " (yuan)\n" +
                "**********************";
    }



}
