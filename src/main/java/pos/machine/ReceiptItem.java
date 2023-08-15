package pos.machine;

public class ReceiptItem {
    private String name;
    private int quantity;
    private int unitPrice;
    private int subTotal;

    public ReceiptItem(String name, int unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getsubTotal() {
        return subTotal;
    }

    public void setSubtotal(int subtotal) {
        this.subTotal = subtotal;
    }
}