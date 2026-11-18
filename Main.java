//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

    import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

    public static class ProductApp extends JFrame {

        JTextField txtCustomerName;
        JComboBox<String> cmbProduct;
        JTextField txtPrice;
        JTextArea txtOutput;
        JButton btnProcess;

        public ProductApp() {
            setTitle("Product App");
            setSize(500, 450);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);

            //  MENU BAR
            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenu toolsMenu = new JMenu("Tools");
            JMenu editMenu = new JMenu("Edit");

            JMenuItem exitItem = new JMenuItem("Exit");
            JMenuItem processItem = new JMenuItem("Process");
            JMenuItem clearItem = new JMenuItem("Clear");
            JMenuItem clearItem2 = new JMenuItem("Clear");

            fileMenu.add(exitItem);
            toolsMenu.add(processItem);
            toolsMenu.add(clearItem);
            editMenu.add(clearItem2);

            menuBar.add(fileMenu);
            menuBar.add(toolsMenu);
            menuBar.add(editMenu);
            setJMenuBar(menuBar);

            // Customer Name
            JLabel lblCustomer = new JLabel("Customer Name:");
            lblCustomer.setBounds(20, 20, 150, 25);
            add(lblCustomer);

            txtCustomerName = new JTextField();
            txtCustomerName.setBounds(180, 20, 250, 25);
            add(txtCustomerName);

            //  Select Product
            JLabel lblProduct = new JLabel("Select Product:");
            lblProduct.setBounds(20, 60, 150, 25);
            add(lblProduct);

            String[] products = {
                    "Cozy Nights Monarch",
                    "Restonic Barbados",
                    "Sealy Phuket"
            };
            cmbProduct = new JComboBox<>(products);
            cmbProduct.setBounds(180, 60, 250, 25);
            add(cmbProduct);

            //  Enter Price
            JLabel lblPrice = new JLabel("Enter Price:");
            lblPrice.setBounds(20, 100, 150, 25);
            add(lblPrice);

            txtPrice = new JTextField();
            txtPrice.setBounds(180, 100, 250, 25);
            add(txtPrice);

            // PROCESS Button
            btnProcess = new JButton("PROCESS");
            btnProcess.setBounds(20, 150, 120, 35);
            add(btnProcess);

            //  Output Area
            txtOutput = new JTextArea();
            txtOutput.setEditable(false);
            JScrollPane scroll = new JScrollPane(txtOutput);
            scroll.setBounds(20, 200, 440, 180);
            add(scroll);

            //  Shared PROCESS Logic
            // Used by PROCESS button AND Tools > Process menu - same logic
            ActionListener processAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String customerName = txtCustomerName.getText();
                        String productName = (String) cmbProduct.getSelectedItem();
                        double price = Double.parseDouble(txtPrice.getText());

                        // Use ProductCalculations class
                        ProductCalculations calc = new ProductCalculations(price);
                        double vat = calc.GetVat();
                        double commission = calc.GetCommission();
                        double total = calc.GetTotal();

                        // Show in Text Area
                        String report = "Customer Name: " + customerName + "\n" +
                                "Product: " + productName + "\n" +
                                "VAT: R" + vat + "\n" +
                                "COMMISSION: R" + commission + "\n" +
                                "TOTAL: R" + total;
                        txtOutput.setText(report);

                        // Show in Message Dialog
                        JOptionPane.showMessageDialog(ProductApp.this, report, "Message", JOptionPane.INFORMATION_MESSAGE);

                        //  Save to data.txt
                        try {
                            FileWriter writer = new FileWriter("data.txt");
                            writer.write("DATA LOG\n");
                            writer.write("************************\n");
                            writer.write("Customer Name: " + customerName + "\n");
                            writer.write("Product: " + productName + "\n");
                            writer.write("VAT: R" + vat + "\n");
                            writer.write("COMMISSION: R" + commission + "\n");
                            writer.write("TOTAL: R" + total + "\n");
                            writer.write("************************");
                            writer.close();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(ProductApp.this, "Error saving file: " + ex.getMessage());
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ProductApp.this, "Please enter a valid price");
                    }
                }
            };

            btnProcess.addActionListener(processAction);
            processItem.addActionListener(processAction);

            // Clear Logic
            // Used by Tools > Clear AND Edit > Clear - same logic
            ActionListener clearAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txtCustomerName.setText("");
                    cmbProduct.setSelectedIndex(0);
                    txtPrice.setText("");
                    txtOutput.setText("");
                }
            };
            clearItem.addActionListener(clearAction);
            clearItem2.addActionListener(clearAction);

            //  Exit Logic
            exitItem.addActionListener(e -> System.exit(0));

            setVisible(true);
        }

        public static void main(String[] args) {
            new ProductApp();
        }
    }

    public class ProductCalculationsTest {

        @Test
        public void GetVat_WithValidData_ReturnsCorrectValue() {
            ProductCalculations calc = new ProductCalculations(3500);
            assertEquals(525, calc.GetVat(), 0.001);
        }

        @Test
        public void GetCommission_WithValidData_ReturnsCorrectValue() {
            ProductCalculations calc = new ProductCalculations(3500);
            assertEquals(175, calc.GetCommission(), 0.001);
        }

        @Test
        public void GetTotal_WithValidData_ReturnsCorrectValue() {
            ProductCalculations calc = new ProductCalculations(3500);
            assertEquals(4200, calc.GetTotal(), 0.001);
        }
    }
