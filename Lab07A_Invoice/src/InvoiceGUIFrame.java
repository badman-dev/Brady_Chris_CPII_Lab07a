import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InvoiceGUIFrame extends JFrame {
    JPanel centerPnl;
    JPanel eastPnl;
    JPanel titlePnl;
    JPanel inputPnl;
    JPanel logPnl;
    JPanel displayPnl;

    JLabel titleLbl;

    JLabel addressLbl;
    JTextField addressTxt;
    JLabel productLbl;
    JLabel productNameLbl;
    JTextField productNameTxt;
    JLabel productUnitPriceLbl;
    JTextField productUnitPriceTxt;
    JLabel lineItemQuantityLbl;
    JTextField lineItemQuantityTxt;
    JTextArea lineItemListArea;
    JScrollPane lineItemListScroll;
    JButton addLineItemBtn;
    JButton invoiceBtn;
    JButton clearBtn;

    JTextArea invoiceArea;
    JScrollPane invoiceScroll;

    ArrayList<LineItem> itemList = new ArrayList<LineItem>();

    public InvoiceGUIFrame() {
        createCenterPanel();
        createEastPanel();
        createTitlePanel();
        createInputPanel();
        createLogPanel();
        createDisplayPanel();

        setTitle("Invoice");
        setSize(1100, 735);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createCenterPanel() {
        centerPnl = new JPanel(new BorderLayout());

        add(centerPnl, BorderLayout.CENTER);
    }

    private void createEastPanel() {
        eastPnl = new JPanel(new BorderLayout());

        add(eastPnl, BorderLayout.EAST);
    }

    private void createTitlePanel() {
        titlePnl = new JPanel();
        titleLbl = new JLabel("Invoice");

        titlePnl.add(titleLbl);
        centerPnl.add(titlePnl, BorderLayout.NORTH);
    }

    private void createInputPanel() {
        inputPnl = new JPanel(new GridLayout(5, 2));
        addressLbl = new JLabel("Customer Address:");
        addressTxt = new JTextField(40);
        productNameLbl = new JLabel("Name:");
        productNameTxt = new JTextField(20);
        productUnitPriceLbl = new JLabel("Unit Price:");
        productUnitPriceTxt = new JTextField(6);
        lineItemQuantityLbl = new JLabel("Quantity");
        lineItemQuantityTxt = new JTextField(6);
        addLineItemBtn = new JButton("Add Product");

        addLineItemBtn.addActionListener((ActionEvent ae) -> addLineItem());

        inputPnl.add(addressLbl);
        inputPnl.add(addressTxt);
        inputPnl.add(productNameLbl);
        inputPnl.add(productNameTxt);
        inputPnl.add(productUnitPriceLbl);
        inputPnl.add(productUnitPriceTxt);
        inputPnl.add(lineItemQuantityLbl);
        inputPnl.add(lineItemQuantityTxt);
        inputPnl.add(addLineItemBtn);
        centerPnl.add(inputPnl, BorderLayout.CENTER);
    }

    private void createLogPanel() {
        logPnl = new JPanel();
        lineItemListArea = new JTextArea(20, 40);
        lineItemListScroll = new JScrollPane(lineItemListArea);
        clearBtn = new JButton("Clear");
        invoiceBtn = new JButton("Create Invoice");

        clearBtn.addActionListener((ActionEvent ae) -> clearAllInput());
        invoiceBtn.addActionListener((ActionEvent ae) -> createInvoice());

        logPnl.add(lineItemListScroll);
        logPnl.add(clearBtn);
        logPnl.add(invoiceBtn);
        centerPnl.add(logPnl, BorderLayout.SOUTH);
    }

    private void createDisplayPanel() {
        displayPnl = new JPanel();
        invoiceArea = new JTextArea(40, 60);
        invoiceArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        invoiceScroll = new JScrollPane(invoiceArea);

        displayPnl.add(invoiceScroll);
        eastPnl.add(displayPnl, BorderLayout.CENTER);
    }

    private void createInvoice() {
        String customerAddress = addressTxt.getText();
        Invoice inv = new Invoice(customerAddress, itemList);
        ArrayList<LineItem> items = inv.getLineItems();

        invoiceArea.setText("- INVOICE -\n\n" + inv.getCustomerAddress() + "\n\n");
        String format = "%-15s %-8s %-8s %-8s %n";
        String invoiceTable = String.format(format, "Name", "Quantity", "Price", "Total");
        for (LineItem item : items) {
            Product product = item.getProduct();
            invoiceTable += String.format(format, product.getName(), item.getQuantity(), "$" + product.getUnitPrice(), "$" + item.getTotalPrice());
        }
        invoiceArea.append(invoiceTable);
        invoiceArea.append("\nAMOUNT DUE: $" + inv.getAmountDue());
    }

    private void addLineItem() {
        String productName = productNameTxt.getText();
        double productUnitPrice = Double.parseDouble(productUnitPriceTxt.getText());
        int lineItemQuantity = Integer.parseInt(lineItemQuantityTxt.getText());

        Product newProduct = new Product(productName, productUnitPrice);
        LineItem newLineItem = new LineItem(newProduct, lineItemQuantity);
        itemList.add(newLineItem);

        lineItemListArea.append(productName + ", $" + productUnitPrice + "/unit, " + lineItemQuantity + ", $" + newLineItem.getTotalPrice() + "\n");

        clearProductInput();
    }

    private void clearAllInput() {
        invoiceArea.setText("");
        addressTxt.setText("");
        lineItemListArea.setText("");
        clearProductInput();
    }

    private void clearProductInput() {
        productNameTxt.setText("");
        productUnitPriceTxt.setText("");
        lineItemQuantityTxt.setText("");
    }
}
