package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Date;

public class Transfer extends JFrame implements ActionListener {
    String pin;
    JTextField tfAccount, tfAmount;
    JButton b1, b2;

    Transfer(String pin){
        this.pin = pin;

        setLayout(null);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel l1 = new JLabel("Receiver Account No:");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(430,220,200,35);
        l3.add(l1);

        tfAccount = new JTextField();
        tfAccount.setBackground(new Color(65,125,128));
        tfAccount.setForeground(Color.WHITE);
        tfAccount.setBounds(670,220,180,25);
        tfAccount.setFont(new Font("Raleway", Font.BOLD,14));
        l3.add(tfAccount);

        JLabel l2 = new JLabel("Enter Amount:");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(430,250,400,35);
        l3.add(l2);

        tfAmount = new JTextField();
        tfAmount.setBackground(new Color(65,125,128));
        tfAmount.setForeground(Color.WHITE);
        tfAmount.setBounds(670,255,180,25);
        tfAmount.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(tfAmount);

        b1 = new JButton("TRANSFER");
        b1.setBounds(700,362,150,35);
        b1.setBackground(new Color(65,125,128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700,406,150,35);
        b2.setBackground(new Color(65,125,128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        setSize(1550,1080);
        setLocation(0,0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource()==b1){
                String receiverAcc = tfAccount.getText();
                String amount = tfAmount.getText();

                if(receiverAcc.equals("") || amount.equals("")){
                    JOptionPane.showMessageDialog(null,"All fields are required");
                    return;
                }

                Con c = new Con();
                // ✅ Get sender account
                ResultSet rs = c.statement.executeQuery("select * from login where pin_no='"+pin+"'");
                String senderAcc = "";
                if(rs.next()) senderAcc = rs.getString("acc_no");

                // ✅ Check balance
                int balance = 0;
                ResultSet rs2 = c.statement.executeQuery("select * from bank where pin_no='"+pin+"'");
                while(rs2.next()){
                    if(rs2.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs2.getString("amount"));
                    }else{
                        balance -= Integer.parseInt(rs2.getString("amount"));
                    }
                }

                if(balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    return;
                }

                Date date = new Date();
                // Deduct from sender
                c.statement.executeUpdate("insert into bank values('"+pin+"','"+date+"','Transfer to "+receiverAcc+"','"+amount+"')");

                // Credit to receiver
                ResultSet rs3 = c.statement.executeQuery("select * from login where acc_no='"+receiverAcc+"'");
                if(rs3.next()){
                    String recvPin = rs3.getString("pin_no");
                    c.statement.executeUpdate("insert into bank values('"+recvPin+"','"+date+"','Received from "+senderAcc+"','"+amount+"')");
                } else {
                    JOptionPane.showMessageDialog(null,"Receiver Account not found!");
                    return;
                }

                JOptionPane.showMessageDialog(null,"Rs."+amount+" Transferred Successfully");
                setVisible(false);
                new Main_Class(pin);

            }else if(e.getSource()==b2){
                setVisible(false);
                new Main_Class(pin);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Transfer("");
    }
}
