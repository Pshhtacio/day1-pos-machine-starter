package pos.machine;

public class ReceiptItem {
    private final String name;
    private final int quantity;
    private final int unitPrice;
    private int subTotal;

    public ReceiptItem(String name, int unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubtotal(int subtotal) {
        this.subTotal = subtotal;
    }
}