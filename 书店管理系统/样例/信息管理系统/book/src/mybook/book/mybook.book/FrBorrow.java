package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mydbc.dbc.DBC;
import java.sql.ResultSet;
import java.sql.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
//该类用于借书
public class FrBorrow extends JFrame {
    public FrBorrow() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(323, 334));
        this.setTitle("借书登记");
        lblBid.setText("图书编号：");
        lblBid.setBounds(new Rectangle(64, 82, 63, 15));
        jLabel8.setFont(new java.awt.Font("宋体", Font.BOLD, 20));
        jLabel8.setText("借书登记");
        jLabel8.setBounds(new Rectangle(111, 16, 101, 40));
        btnSure.setBounds(new Rectangle(49, 235, 80, 30));
        btnSure.setText("确定");
        btnSure.addActionListener(new FrBorrow_btnSure_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(179, 235, 80, 30));
        btnCancel.setText("取消");
        btnCancel.addActionListener(new FrBorrow_btnCancel_actionAdapter(this));
        txtRenttime.setBounds(new Rectangle(139, 184, 65, 20));
        txtUid.setBounds(new Rectangle(139, 131, 66, 20));
        txtBid.setBounds(new Rectangle(139, 77, 66, 20));
        lblRenttime.setText("借书期限：");
        lblRenttime.setBounds(new Rectangle(64, 182, 73, 15));
        this.getContentPane().add(txtBid);
        this.getContentPane().add(txtUid);
        this.getContentPane().add(lblBid);
        this.getContentPane().add(lblUid);
        this.getContentPane().add(lblRenttime);
        this.getContentPane().add(txtRenttime);
        this.getContentPane().add(btnSure);
        this.getContentPane().add(btnCancel);
        this.getContentPane().add(jLabel8);
        lblUid.setText("借书人编号：");
        lblUid.setBounds(new Rectangle(64, 132, 74, 15));
    }

    JTable jTable1 = new JTable();
    JLabel lblBid = new JLabel();
    JLabel lblUid = new JLabel();
    JLabel lblRenttime = new JLabel();
    JTextField txtBid = new JTextField();
    JTextField txtUid = new JTextField();
    JTextField txtRenttime = new JTextField();
    JLabel jLabel8 = new JLabel();
    JButton btnSure = new JButton();
    JButton btnCancel = new JButton();
    //该方法用于退出
    public void btnCancel_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
    //该方法用于提交借书信息
    public void btnSure_actionPerformed(ActionEvent e) {
        //栏位判断
        if (txtBid.getText().equals("") || txtUid.getText().equals("") ||
            txtRenttime.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "各栏位不能为空！");
        } else {
            try {
                //是否为数字型
                Integer.parseInt(txtBid.getText());
                Integer.parseInt(txtUid.getText());
                Integer.parseInt(txtRenttime.getText());
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "各栏位必须均为数字！");
                return;
            }
            DBC dbc = DBC.getInstance();
            ResultSet rs1 = dbc.executeQuery("SELECT * FROM Books WHERE Bid = " +
                                             Integer.parseInt(txtBid.getText()));
            ResultSet rs2 = dbc.executeQuery("SELECT * FROM Users WHERE Uid = " +
                                             Integer.parseInt(txtUid.getText()));
            int uyue = 0;
            int bprice = 0;
            //查询是否有此书信息
            try {
                rs1.next();
                rs1.getInt("Bprice");
                rs1.close();
            } catch (SQLException ex1) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "库中无此书信息！请重新确认。");
                return;
            }
            //查询是否有该读者信息
            try {
                rs2.next();
                uyue = rs2.getInt("Uyue");
                rs2.close();
            } catch (SQLException ex2) {
                javax.swing.JOptionPane.showMessageDialog(this, "无此读者信息！请重新确认。");
                return;
            }
            //判断余额是否容许租借本书
            if (uyue < bprice) {
                javax.swing.JOptionPane.showMessageDialog(this, "该读者余额不足以租借此书！");
                return;
            } else {
                //更新相关数据并将该书库存量减1
                if(Integer.parseInt(txtRenttime.getText()) < 0){
                    javax.swing.JOptionPane.showMessageDialog(this,"时间不能为负数！");
                    return;
                }
                if (dbc.executeUpdate("INSERT OutBooks VALUES (" +
                                      Integer.parseInt(txtBid.getText()) +
                                      "," +
                                      Integer.parseInt(txtUid.getText()) +
                                      "," +
                                      bprice +
                                      ",DEFAULT," +
                                      Integer.parseInt(txtRenttime.getText()) +
                                      ",DEFAULT,DEFAULT)") &&
                    dbc.executeUpdate(
                        "UPDATE Books SET Bsum = Bsum-1 WHERE Bid =" +
                        Integer.parseInt(txtBid.getText()))) {
                    javax.swing.JOptionPane.showMessageDialog(this, "借书成功！");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "借书失败！请重试。");
                }

            }
        }
    }
}


class FrBorrow_btnSure_actionAdapter implements ActionListener {
    private FrBorrow adaptee;
    FrBorrow_btnSure_actionAdapter(FrBorrow adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSure_actionPerformed(e);
    }
}


class FrBorrow_btnCancel_actionAdapter implements ActionListener {
    private FrBorrow adaptee;
    FrBorrow_btnCancel_actionAdapter(FrBorrow adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
