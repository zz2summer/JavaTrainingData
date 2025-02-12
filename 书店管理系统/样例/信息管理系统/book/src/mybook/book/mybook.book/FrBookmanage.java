package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import mydbc.dbc.DBC;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.io.*;

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
//该类用于图书管理,该类中方法类似于读者管理类,故不再做注释
public class FrBookmanage extends JFrame {
    public FrBookmanage() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(651, 588));
        this.setTitle("图书管理");
        lblFind.setText("按");
        lblFind.setBounds(new Rectangle(18, 16, 18, 15));
        cbWay.setBounds(new Rectangle(38, 12, 77, 23));
        lblFindit.setText("查找");
        lblFindit.setBounds(new Rectangle(120, 15, 28, 15));
        btnFind.setBounds(new Rectangle(156, 13, 66, 53));
        btnFind.setText("查找");
        btnFind.addActionListener(new FrBookmanage_btnFind_actionAdapter(this));
        txtWhat.setBounds(new Rectangle(38, 46, 77, 21));
        btnDelete.setBounds(new Rectangle(341, 13, 66, 53));
        btnDelete.setText("删除");
        btnDelete.addActionListener(new FrBookmanage_btnDelete_actionAdapter(this));
        btnFlush.setBounds(new Rectangle(433, 13, 66, 53));
        btnFlush.setText("刷新");
        btnFlush.addActionListener(new FrBookmanage_btnFlush_actionAdapter(this));
        btnExit.setBounds(new Rectangle(525, 13, 66, 53));
        btnExit.setText("关闭");
        btnExit.addActionListener(new FrBookmanage_btnExit_actionAdapter(this));
        btnEdit.setBounds(new Rectangle(248, 13, 66, 53));
        btnEdit.setText("修改");
        btnEdit.addActionListener(new FrBookmanage_btnEdit_actionAdapter(this));
        jScrollPane1.setBounds(new Rectangle(14, 84, 623, 467));
        this.getContentPane().add(lblFind);
        this.getContentPane().add(cbWay);
        this.getContentPane().add(lblFindit);
        this.getContentPane().add(txtWhat);
        this.getContentPane().add(btnFind);
        this.getContentPane().add(btnEdit);
        this.getContentPane().add(btnDelete);
        this.getContentPane().add(btnFlush);
        this.getContentPane().add(btnExit);
        this.getContentPane().add(jScrollPane1);
        jScrollPane1.getViewport().add(jTable1);
        cbWay.addItem("编号");
        cbWay.addItem("书名");
        cbWay.addItem("类别");
        cbWay.addItem("作者");
        cbWay.addItem("价格");
        cbWay.addItem("出版社");
        this.Show();
    }

    JLabel lblFind = new JLabel();
    JComboBox cbWay = new JComboBox();
    JLabel lblFindit = new JLabel();
    JButton btnFind = new JButton();
    JTextField txtWhat = new JTextField();
    JButton btnDelete = new JButton();
    JButton btnFlush = new JButton();
    JButton btnExit = new JButton();
    JButton btnEdit = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    //该方法用于退出
    public void btnExit_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
    public void Show() {
        DBC dbc = DBC.getInstance();
        int count = 0;
        ResultSet rs = dbc.executeQuery("SELECT * FROM Books");
        try {
            while (rs.next()) {
                count++;
            }
            rs.close();
            Object[][] cells = new Object[count][8];
            int i = 0;
            rs = dbc.executeQuery("SELECT * FROM Books");
            while (rs.next()) {

                cells[i] = new Object[] {rs.getInt(1),
                           rs.getString(2), rs.getString(3),
                           rs.getString(4), rs.getString(5),
                           rs.getString(6), rs.getString(7), rs.getString(8)};
                i++;
            }
            String[] colnames = {"编号", "书名", "简介", "类型", "出版社", "库存量", "单价",
                                "作者"};
            jTable1 = new JTable(cells, colnames);
            jScrollPane1.getViewport().add(jTable1);

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex.getMessage().toString());
        }
    }

    public void btnFind_actionPerformed(ActionEvent e) {
        if (txtWhat.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "请输入查询信息！");
            return;
        }
        DBC dbc = DBC.getInstance();
        String sql = "";
        if (((String) cbWay.getSelectedItem()).equals("编号")) {
            try {
                Integer.parseInt(txtWhat.getText());
            } catch (NumberFormatException ex2) {
                javax.swing.JOptionPane.showMessageDialog(this, "编号只能为数字！");
                return;
            }
            sql = "SELECT * FROM Books WHERE Bid = " +
                  Integer.parseInt(txtWhat.getText());
        } else if (((String) cbWay.getSelectedItem()).equals("书价")) {
            try {
                Integer.parseInt(txtWhat.getText());
            } catch (NumberFormatException ex2) {
                javax.swing.JOptionPane.showMessageDialog(this, "书价只能为数字！");
                return;
            }
            sql = "SELECT * FROM Books WHERE Bprice = " +
                  Integer.parseInt(txtWhat.getText());

        } else if (((String) cbWay.getSelectedItem()).equals("书名")) {
            sql = "SELECT * FROM Books WHERE Bname = '" + txtWhat.getText() +
                  "'";
        } else if (((String) cbWay.getSelectedItem()).equals("类别")) {
            sql = "SELECT * FROM Books WHERE Bsort = '" + txtWhat.getText() +
                  "'";
        } else if (((String) cbWay.getSelectedItem()).equals("作者")) {
            sql = "SELECT * FROM Books WHERE Bauthor = '" + txtWhat.getText() +
                  "'";
        } else {
            sql = "SELECT * FROM Books WHERE Bpublisher = '" + txtWhat.getText() +
                  "'";
        }
        ResultSet rs = dbc.executeQuery(sql);
        int count = 0;
        try {
            while (rs.next()) {
                count++;
            }
            rs.close();
        } catch (SQLException ex1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex1.getMessage().toString());
        }
        Object[][] cells = new Object[count][8];
        rs = dbc.executeQuery(sql);
        try {
            int i = 0;
            while (rs.next()) {
                cells[i] = new Object[] {rs.getInt(1),
                           rs.getString(2), rs.getString(3),
                           rs.getString(4), rs.getString(5),
                           rs.getString(6), rs.getString(7), rs.getString(8)};
                i++;
            }
            String[] colnames = {"编号", "书名", "简介", "类型", "出版社", "库存量", "单价",
                                "作者"};

            jTable1 = new JTable(cells, colnames);
            jScrollPane1.getViewport().add(jTable1);
            rs.close();

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex.getMessage().toString());
        }
    }

    public void btnDelete_actionPerformed(ActionEvent e) {
        DBC dbc = DBC.getInstance();
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "您还未选中任何信息!");
            return;
        }
        Object value = jTable1.getValueAt(row, 0);
        int id = Integer.parseInt(value.toString());
        if (dbc.executeUpdate("DELETE FROM Books WHERE Bid =" + id)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "编号为" + id + "的图书已成功被删除！请刷新！");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "删除失败！请再试。");
        }

    }

    public void btnFlush_actionPerformed(ActionEvent e) {
        this.Show();
    }

    public void btnEdit_actionPerformed(ActionEvent e) {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "您还未选中任何信息!");
            return;
        }
        Object value = jTable1.getValueAt(row, 0);
        FrBedit book = new FrBedit(Integer.parseInt(value.toString()));
        Dimension screenSize = Toolkit.getDefaultToolkit().
                               getScreenSize();
        Dimension frameSize = book.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        book.setLocation((screenSize.width - frameSize.width) /
                         2,
                         (screenSize.height - frameSize.height) /
                         2);
        book.setVisible(true);

    }


    class FrBookmanage_btnExit_actionAdapter implements ActionListener {
        private FrBookmanage adaptee;
        FrBookmanage_btnExit_actionAdapter(FrBookmanage adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnExit_actionPerformed(e);
        }
    }
}


class FrBookmanage_btnEdit_actionAdapter implements ActionListener {
    private FrBookmanage adaptee;
    FrBookmanage_btnEdit_actionAdapter(FrBookmanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnEdit_actionPerformed(e);
    }
}


class FrBookmanage_btnFlush_actionAdapter implements ActionListener {
    private FrBookmanage adaptee;
    FrBookmanage_btnFlush_actionAdapter(FrBookmanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnFlush_actionPerformed(e);
    }
}


class FrBookmanage_btnDelete_actionAdapter implements ActionListener {
    private FrBookmanage adaptee;
    FrBookmanage_btnDelete_actionAdapter(FrBookmanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDelete_actionPerformed(e);
    }
}


class FrBookmanage_btnFind_actionAdapter implements ActionListener {
    private FrBookmanage adaptee;
    FrBookmanage_btnFind_actionAdapter(FrBookmanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnFind_actionPerformed(e);
    }
}
