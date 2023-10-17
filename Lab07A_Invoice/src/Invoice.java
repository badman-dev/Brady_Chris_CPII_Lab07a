import java.util.ArrayList;

public class Invoice {
    String customerAddress;
    double amountDue;
    ArrayList<LineItem> lineItems;

    public Invoice(String customerAddress, double amountDue, ArrayList<LineItem> lineItems) {
        this.customerAddress = customerAddress;
        this.amountDue = amountDue;
        this.lineItems = lineItems;
    }

    public Invoice(String customerAddress, ArrayList<LineItem> lineItems) {
        this.customerAddress = customerAddress;
        this.lineItems = lineItems;
        double tempAmountDue = 0;
        for (LineItem item : lineItems) {
            tempAmountDue += item.getTotalPrice();
        }
        this.amountDue = tempAmountDue;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(LineItem item) {
        this.lineItems.add(item);
    }
}
