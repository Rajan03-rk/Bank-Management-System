package bank.management.system;

import javax.swing.*;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Signup3 extends JFrame implements ActionListener {

    JRadioButton r1, r2, r3, r4;
    JCheckBox c1, c2, c3, c4, c5, c6;
    JButton s, cancel;
    String formno;

    Signup3(String formno) {
        this.formno = formno;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(150, 5, 100, 100);
        add(image);

        JLabel l1 = new JLabel("Page 3: Account Details");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setBounds(280, 40, 400, 40);
        add(l1);

        JLabel l2 = new JLabel("Account Type:");
        l2.setFont(new Font("Arial", Font.BOLD, 18));
        l2.setBounds(100, 140, 200, 30);
        add(l2);

        r1 = new JRadioButton("Saving Account");
        r2 = new JRadioButton("Fixed Deposit Account");
        r3 = new JRadioButton("Current Account");
        r4 = new JRadioButton("Recurring Deposit Account");

        JRadioButton[] radios = {r1, r2, r3, r4};
        int y = 180;
        for (int i = 0; i < radios.length; i++) {
            radios[i].setFont(new Font("Arial", Font.BOLD, 16));
            radios[i].setBackground(new Color(215, 252, 252));
            radios[i].setBounds((i % 2 == 0 ? 100 : 350), y, 250, 20);
            add(radios[i]);
            if (i % 2 == 1) y += 40;
        }

        ButtonGroup group = new ButtonGroup();
        for (JRadioButton rb : radios) group.add(rb);

        JLabel l3 = new JLabel("Account Number:");
        l3.setFont(new Font("Arial", Font.BOLD, 18));
        l3.setBounds(100, 300, 200, 30);
        add(l3);

        JLabel l4 = new JLabel("(Your 16-digit Account Number)");
        l4.setFont(new Font("Arial", Font.PLAIN, 12));
        l4.setBounds(100, 330, 300, 20);
        add(l4);

        JLabel l5 = new JLabel("XXXX-XXXX-XXXX-XXXX");
        l5.setFont(new Font("Arial", Font.BOLD, 18));
        l5.setBounds(330, 300, 300, 30);
        add(l5);

        JLabel l10 = new JLabel("Services Required:");
        l10.setFont(new Font("Arial", Font.BOLD, 18));
        l10.setBounds(100, 400, 200, 30);
        add(l10);

        c1 = new JCheckBox("ATM CARD");
        c2 = new JCheckBox("Internet Banking");
        c3 = new JCheckBox("Mobile Banking");
        c4 = new JCheckBox("EMAIL Alerts");
        c5 = new JCheckBox("Cheque Book");
        c6 = new JCheckBox("E-Statement");

        JCheckBox[] checks = {c1, c2, c3, c4, c5, c6};
        int y2 = 450;
        for (int i = 0; i < checks.length; i++) {
            checks[i].setFont(new Font("Arial", Font.BOLD, 16));
            checks[i].setBackground(new Color(215, 252, 252));
            checks[i].setBounds((i % 2 == 0 ? 100 : 350), y2, 200, 30);
            add(checks[i]);
            if (i % 2 == 1) y2 += 50;
        }

        s = new JButton("Submit");
        s.setFont(new Font("Arial", Font.BOLD, 16));
        s.setBackground(Color.BLACK);
        s.setForeground(Color.WHITE);
        s.setBounds(250, 650, 100, 30);
        s.addActionListener(this);
        add(s);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.BOLD, 16));
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(420, 650, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        getContentPane().setBackground(new Color(215, 252, 252));
        setSize(850, 750);
        setLayout(null);
        setLocation(350, 30);
        setVisible(true);
    }

    // ✅ Generate unique account number (prefix + last 4 digits)
    private String generateUniqueAccNo(Con c) throws Exception {
        Random rand = new Random();
        String prefix = "225580014563"; // fixed first 12 digits
        String suffix;

        while (true) {
            int num = rand.nextInt(10000); // 0000–9999
            suffix = String.format("%04d", num);

            // check if last 4 already exists
            String checkQuery = "SELECT acc_no FROM signupthree WHERE RIGHT(acc_no, 4) = '" + suffix + "'";
            ResultSet rs = c.statement.executeQuery(checkQuery);

            if (!rs.next()) {
                break; // unique suffix found
            }
        }

        return prefix + suffix; // full 16-digit acc_no
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String atype = null;
        if (r1.isSelected()) atype = "Saving Account";
        else if (r2.isSelected()) atype = "Fixed Deposit Account";
        else if (r3.isSelected()) atype = "Current Account";
        else if (r4.isSelected()) atype = "Recurring Deposit Account";

        try {
            if (e.getSource() == s) {
                if (atype == null) {
                    JOptionPane.showMessageDialog(null, "Please select Account Type");
                    return;
                }

                Con c = new Con();

                // ✅ Step 1: Generate Account No
                String accNo = generateUniqueAccNo(c);
                String accNoDisplay = accNo.substring(0, 4) + "-" +
                        accNo.substring(4, 8) + "-" +
                        accNo.substring(8, 12) + "-" +
                        accNo.substring(12);

                // ✅ Show account no to user first
                JOptionPane.showMessageDialog(null,
                        "Your Account Number is:\n" + accNoDisplay +
                                "\n\nPlease remember this for login.");

                // ✅ Step 2: Ask user to create PIN
                String pin = JOptionPane.showInputDialog(this, "Now create a 4-digit numeric PIN:");
                if (pin == null) return; // cancelled
                pin = pin.trim();
                if (!pin.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(this, "PIN must be exactly 4 digits.");
                    return;
                }

                // ✅ Collect facilities
                String fac = "";
                if (c1.isSelected()) fac += "ATM CARD ";
                if (c2.isSelected()) fac += "Internet Banking ";
                if (c3.isSelected()) fac += "Mobile Banking ";
                if (c4.isSelected()) fac += "EMAIL Alerts ";
                if (c5.isSelected()) fac += "Cheque Book ";
                if (c6.isSelected()) fac += "E-Statement ";

                // ✅ Insert into DB
                String q1 = "insert into signupthree values('" + formno + "', '" + atype + "','" + accNo + "','" + pin + "','" + fac + "')";
                String q2 = "insert into login values('" + formno + "','" + accNo + "','" + pin + "')";
                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);

                // ✅ Final success message
                JOptionPane.showMessageDialog(null,
                        "Account Created Successfully!\nAccount Number : " + accNoDisplay +
                                "\nPIN : " + pin);

                new Deposit(pin);
                setVisible(false);

            } else if (e.getSource() == cancel) {
                System.exit(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Signup3("1001"); // test with dummy formno
    }
}
