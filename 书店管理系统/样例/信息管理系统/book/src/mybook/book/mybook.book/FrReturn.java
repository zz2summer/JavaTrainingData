package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Dimension;
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
//该类用于还书登记
public class FrReturn extends JFrame {
    public FrReturn() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(295, 263));
        this.setTitle("还书登记");
        lblBid.setText("所借书编号：");
        lblBid.setBounds(new Rectangle(46, 91, 83, 15));
        lblReturn.setFont(new java.awt.Font("宋体", Font.BOLD, 20));
        lblReturn.setText("还书登记");
        lblReturn.setBounds(new Rectangle(90, 21, 114, 49));
        btnSubmit.setBounds(new Rectangle(51, 196, 81, 23));
        btnSubmit.setText("提交");
        btnSubmit.addActionListener(new FrReturn_btnSubmit_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(159, 196, 81, 23));
        btnCancel.setText("取消");
        btnCancel.addActionListener(new FrReturn_jButton2_actionAdapter(this));
        this.getContentPane().add(lblBid);
        txtUid.setBounds(new Rectangle(137, 136, 76, 20));
        txtBid.setBounds(new Rectangle(137, 87, 76, 20));
        this.getContentPane().add(txtBid);
        this.getContentPane().add(txtUid);
        this.getContentPane().add(lblUid);
        this.getContentPane().add(lblReturn);
        this.getContentPane().add(btnSubmit);
        this.getContentPane().add(btnCancel);
        lblUid.setText("借书人编号：");
        lblUid.setBounds(new Rectangle(46, 138, 86, 15));
    }

    JLabel lblBid = new JLabel();
    JLabel lblUid = new JLabel();
    JTextField txtBid = new JTextField();
    JTextField txtUid = new JTextField();
    JLabel lblReturn = new JLabel();
    JButton btnSubmit = new JButton();
    JButton btnCancel = new JButton();
    //该方法用于退出当前Frame
    public void jButton2_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
    //该方法用于还书
    public void btnSubmit_actionPerformed(ActionEvent e) {
        //栏位判断
        if (txtUid.getText().equals("") || txtBid.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "各栏位不能为空！请重新输入。");
            return;
        } else {
            //判断是否为数字型
            try {
                Integer.parseInt(txtUid.getText());
                Integer.parseInt(txtBid.getText());
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "编号只能为数字！请重新输入。");
                return;
            }
            //判断数据库中是否有该读者的结束记录
            DBC dbc = DBC.getInstance();
            String state = "";
            String time = "";
            ResultSet rs = dbc.executeQuery(
                    "SELECT TOP 1 * FROM OutBooks WHERE Obid = " +
                    Integer.parseInt(txtBid.getText()) + "AND Ouid=" +
                    Integer.parseInt(txtUid.getText()) +
                    "ORDER BY Obotime DESC");
            try {
                rs.next();
                time = rs.getString("Obotime");
                state = rs.getString("Obstate");
            } catch (SQLException ex1) {
                javax.swing.JOptionPane.showMessageDialog(this, "该读者未租借过此书！");
                return;
            }
            if (state.equals("是")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "还书失败，该读者已经还过此书！");
                return;
            } else {
                //开始还书操作
                if (dbc.executeUpdate(
                        "UPDATE OutBooks SET Obstate='是',Obkeep='否' WHERE Obid = " +
                        Integer.parseInt(txtBid.getText()) + " AND Ouid = " +
                        Integer.parseInt(txtUid.getText()) + "AND Obotime = '" +
                        time + "'") &&
                    dbc.
                    executeUpdate(
                            "UPDATE Users SET Uyue=Uyue+(SELECT Bprice FROM Books WHERE Bid = " +
                            Integer.parseInt(txtBid.getText()) +
                            ") WHERE Uid = " +
                            Integer.parseInt(txtUid.getText())) &&
                    dbc.
                    executeUpdate(
                            "UPDATE Books SET Bsum=Bsum+1 WHERE Bid =" +
                            Integer.parseInt(txtBid.getText()))) {
                    javax.swing.JOptionPane.showMessageDialog(this, "还书成功！");

                } else {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "还书失败！请重试。");
                }
            }
        }
    }
}


class FrReturn_btnSubmit_actionAdapter implements ActionListener {
    private FrReturn adaptee;
    FrReturn_btnSubmit_actionAdapter(FrReturn adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSubmit_actionPerformed(e);
    }
}


class FrReturn_jButton2_actionAdapter implements ActionListener {
    private FrReturn adaptee;
    FrReturn_jButton2_actionAdapter(FrReturn adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
