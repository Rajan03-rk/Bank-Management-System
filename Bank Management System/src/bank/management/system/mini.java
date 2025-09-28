package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton button;
    JTable table;
    JLabel label3, label4;

    mini(String pin) {
        this.pin = pin;
        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(600, 500);
        setLocation(50, 50);
        setLayout(null);

        JLabel label2 = new JLabel("Banking Management System");
        label2.setFont(new Font("System", Font.BOLD, 18));
        label2.setBounds(180, 10, 300, 30);
        add(label2);

        label3 = new JLabel();
        label3.setBounds(20, 50, 500, 20);
        add(label3);

        label4 = new JLabel();
        label4.setBounds(20, 400, 400, 20);
        add(label4);

        // Table for transactions
        String[] columnNames = {"Date", "Type", "Amount"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // 🎨 Table style
        table.setBackground(new Color(255, 204, 204));   // pink background
        table.setGridColor(Color.BLACK);                 // black grid lines
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(200, 200, 200)); // highlight row color
        table.setFont(new Font("Arial", Font.PLAIN, 10));

        // Table header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 550, 300);

        // 🖤 Add black border to table area
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        add(scrollPane);

        try {
            // ✅ Fetch Account Number
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("select * from login where pin_no = '" + pin + "'");
            while (resultSet.next()) {
                String accNo = resultSet.getString("acc_no");
                if (accNo != null && accNo.length() == 16) {
                    label3.setText("Account Number:  " +
                            accNo.substring(0, 4) + "XXXXXXXX" + accNo.substring(12));
                } else {
                    label3.setText("Account Number:  " + accNo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // ✅ Transactions
            int balance = 0;
            Con c = new Con();
            ResultSet resultSet = c.statement.executeQuery("select * from bank where pin_no = '" + pin + "'");
            DefaultTableModel model1 = (DefaultTableModel) table.getModel();

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String type = resultSet.getString("type");
                String amount = resultSet.getString("amount");

                model1.addRow(new Object[]{date, type, amount});

                if (type.equalsIgnoreCase("Deposit") || type.startsWith("Received from")) {
                    balance += Integer.parseInt(amount);
                } else {
                    balance -= Integer.parseInt(amount);
                }
            }
            label4.setText("Your Total Balance is Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(450, 400, 100, 30);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("");
    }
}
