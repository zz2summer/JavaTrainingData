package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mydbc.dbc.DBC;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

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
//该类用于对入库图书资料进行编辑修改
public class FrBedit extends JFrame {
    int id = 0;
    JLabel lblBedit = new JLabel();
    JLabel lblBid = new JLabel();
    JLabel lblBname = new JLabel();
    JLabel lblBauthor = new JLabel();
    JLabel lblSort = new JLabel();
    JLabel lblPublisher = new JLabel();
    JLabel lblPrice = new JLabel();
    JLabel lblSsum = new JLabel();
    JLabel lblPreview = new JLabel();
    JTextField txtBname = new JTextField();
    JTextField txtBid = new JTextField();
    JTextField txtPublisher = new JTextField();
    JTextField txtPrice = new JTextField();
    JTextField txtSort = new JTextField();
    JTextField txtBsum = new JTextField();
    JTextField txtBauthor = new JTextField();
    JButton btnSure = new JButton();
    JButton btnCancel = new JButton();
    JTextArea txtBpreview = new JTextArea();
    //使用构造方法接收图书管理界面传入的图书编号
    public FrBedit(int i) {
        id = i;
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(388, 519));
        this.setTitle("图书信息修改");
        lblBedit.setFont(new java.awt.Font("宋体", Font.BOLD, 16));
        lblBedit.setText("图书信息修改");
        lblBedit.setBounds(new Rectangle(140, 17, 108, 32));
        lblBid.setText("图书编号：");
        lblBid.setBounds(new Rectangle(63, 70, 60, 15));
        lblBname.setText("图书名：");
        lblBname.setBounds(new Rectangle(63, 111, 60, 15));
        lblBauthor.setText("作者：");
        lblBauthor.setBounds(new Rectangle(63, 152, 60, 15));
        lblSort.setText("类型：");
        lblSort.setBounds(new Rectangle(63, 193, 60, 15));
        lblPublisher.setText("出版社：");
        lblPublisher.setBounds(new Rectangle(63, 234, 60, 15));
        lblPrice.setText("书价：");
        lblPrice.setBounds(new Rectangle(63, 275, 60, 15));
        lblSsum.setText("库存量：");
        lblSsum.setBounds(new Rectangle(63, 316, 60, 15));
        lblPreview.setText("简介：");
        lblPreview.setBounds(new Rectangle(63, 357, 60, 15));
        txtBname.setBounds(new Rectangle(155, 105, 185, 21));
        txtBid.setEnabled(false);
        txtBid.setBounds(new Rectangle(155, 64, 72, 21));
        txtPublisher.setBounds(new Rectangle(155, 228, 72, 21));
        txtPrice.setBounds(new Rectangle(155, 269, 72, 21));
        txtSort.setBounds(new Rectangle(155, 187, 72, 21));
        txtBsum.setBounds(new Rectangle(155, 310, 72, 21));
        txtBauthor.setBounds(new Rectangle(155, 146, 72, 21));
        btnSure.setBounds(new Rectangle(64, 435, 83, 25));
        btnSure.setToolTipText("");
        btnSure.setText("确定");
        btnSure.addActionListener(new FrBedit_btnSure_actionAdapter(this));
        btnCancel.setBounds(new Rectangle(196, 435, 83, 25));
        btnCancel.setText("取消");
        btnCancel.addActionListener(new FrBedit_btnCancel_actionAdapter(this));
        txtBpreview.setBorder(BorderFactory.createLineBorder(Color.black));
        txtBpreview.setBounds(new Rectangle(155, 354, 185, 61));
        this.getContentPane().add(lblBedit);
        this.getContentPane().add(lblPreview);
        this.getContentPane().add(lblPublisher);
        this.getContentPane().add(lblSort);
        this.getContentPane().add(lblBauthor);
        this.getContentPane().add(lblBname);
        this.getContentPane().add(lblSsum);
        this.getContentPane().add(lblPrice);
        this.getContentPane().add(btnSure);
        this.getContentPane().add(txtBid);
        this.getContentPane().add(lblBid);
        this.getContentPane().add(txtBname);
        this.getContentPane().add(txtBsum);
        this.getContentPane().add(txtBauthor);
        this.getContentPane().add(txtSort);
        this.getContentPane().add(txtPublisher);
        this.getContentPane().add(txtPrice);
        this.getContentPane().add(txtBpreview);
        this.getContentPane().add(btnCancel);
        this.display();
    }
    //该方法用于初始化时显示选中的图书信息
    public void display() {
        DBC dbc = DBC.getInstance();
        ResultSet rs = dbc.executeQuery("SELECT * FROM Books WHERE Bid =" + id);
        try {
            rs.next();
            txtBid.setText(id + "");
            txtBname.setText(rs.getString(2));
            txtBpreview.setText(rs.getString(3));
            txtSort.setText(rs.getString(4));
            txtPublisher.setText(rs.getString(5));
            txtBsum.setText(rs.getInt(6) + "");
            txtPrice.setText(rs.getInt(7) + "");
            txtBauthor.setText(rs.getString(8));
            rs.close();
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex.getMessage().toString());
        }
    }
    //该方法用于退出修改编辑
    public void btnCancel_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
    //该方法用于提交修改
    public void btnSure_actionPerformed(ActionEvent e) {
        DBC dbc = DBC.getInstance();

        try {
            if (Integer.parseInt(txtPrice.getText()) < 0 ||
                Integer.parseInt(txtBsum.getText()) < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "不能有负数！");
                return;
            }
        } catch (HeadlessException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,ex.getMessage().toCharArray());
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,"格式错误！");
            return;
        }

        if (dbc.executeUpdate("UPDATE Books SET Bname ='" + txtBname.getText() +
                              "',Bpreview ='" + txtBpreview.getText() +
                              "',Bsort ='" + txtSort.getText() +
                              "',Bpublisher ='" + txtPublisher.getText() +
                              "',Bsum = " +Integer.parseInt(txtBsum.getText())  +
                              ",Bprice =" + Integer.parseInt(txtPrice.getText()) +
                              ",Bauthor ='" +
                              txtBauthor.getText() + "' WHERE Bid =" + id)) {
            javax.swing.JOptionPane.showMessageDialog(this, "信息修改成功！");
            this.setVisible(false);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "修改失败！请重试。");
        }
    }
}


class FrBedit_btnSure_actionAdapter implements ActionListener {
    private FrBedit adaptee;
    FrBedit_btnSure_actionAdapter(FrBedit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSure_actionPerformed(e);
    }
}


class FrBedit_btnCancel_actionAdapter implements ActionListener {
    private FrBedit adaptee;
    FrBedit_btnCancel_actionAdapter(FrBedit adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCancel_actionPerformed(e);
    }
}
