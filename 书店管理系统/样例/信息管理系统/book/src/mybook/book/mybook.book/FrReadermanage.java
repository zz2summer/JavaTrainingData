package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Rectangle;
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
//该类用于管理读者信息
public class FrReadermanage extends JFrame {
    public FrReadermanage() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(651, 588));
        this.setTitle("读者管理");
        lblFind.setText("按");
        lblFind.setBounds(new Rectangle(16, 15, 17, 15));
        cbWay.setBounds(new Rectangle(40, 10, 95, 23));
        lblFindit.setText("查找");
        lblFindit.setBounds(new Rectangle(141, 14, 29, 15));
        txtWhat.setBounds(new Rectangle(40, 45, 94, 21));
        btnFind.setBounds(new Rectangle(183, 12, 66, 53));
        btnFind.setText("查找");
        btnFind.addActionListener(new FrReadermanage_btnFind_actionAdapter(this));
        btnEdit.setBounds(new Rectangle(258, 12, 66, 53));
        btnEdit.setText("修改");
        btnEdit.addActionListener(new FrReadermanage_btnEdit_actionAdapter(this));
        btnDelete.setBounds(new Rectangle(333, 12, 66, 53));
        btnDelete.setToolTipText("");
        btnDelete.setText("删除");
        btnDelete.addActionListener(new FrReadermanage_btnDelete_actionAdapter(this));
        btnExit.setBounds(new Rectangle(482, 12, 66, 53));
        btnExit.setText("关闭");
        btnExit.addActionListener(new FrReadermanage_btnExit_actionAdapter(this));
        jScrollPane1.setBounds(new Rectangle(13, 83, 615, 464));
        btnFlush.setBounds(new Rectangle(407, 12, 66, 53));
        btnFlush.setText("刷新");
        btnFlush.addActionListener(new FrReadermanage_btnFlush_actionAdapter(this));
        btnKey.setBounds(new Rectangle(557, 12, 66, 53));
        btnKey.setText("解锁");
        btnKey.addActionListener(new FrReadermanage_btnKey_actionAdapter(this));
        this.getContentPane().add(lblFind);
        this.getContentPane().add(cbWay);
        this.getContentPane().add(lblFindit);
        this.getContentPane().add(txtWhat);
        this.getContentPane().add(btnFind);
        this.getContentPane().add(jScrollPane1);
        this.getContentPane().add(btnEdit);
        this.getContentPane().add(btnDelete);
        this.getContentPane().add(btnFlush);
        this.getContentPane().add(btnExit);
        this.getContentPane().add(btnKey);
        jScrollPane1.getViewport().add(jTable1);
        cbWay.addItem("编号");
        cbWay.addItem("姓名");
        cbWay.addItem("电话");
        this.Show();
    }

    JLabel lblFind = new JLabel();
    JComboBox cbWay = new JComboBox();
    JLabel lblFindit = new JLabel();
    JTextField txtWhat = new JTextField();
    JButton btnFind = new JButton();
    JButton btnEdit = new JButton();
    JButton btnDelete = new JButton();
    JButton btnExit = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    JButton btnFlush = new JButton();
    JButton btnKey = new JButton();
    //该方法用于退出当前Frame
    public void btnExit_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
    //该方法用于显示数据库中所有读者信息
    public void Show() {
        DBC dbc = DBC.getInstance();
        ResultSet rs1 = dbc.executeQuery("SELECT 总数=COUNT(*) FROM Users");
        ResultSet rs2 = dbc.executeQuery("SELECT * FROM Users");
        try {
            rs1.next();
            int count = rs1.getInt("总数");
            rs1.close();
            Object[][] cells = new Object[count][8];
            int i = 0;
            while (rs2.next()) {

                cells[i] = new Object[] {rs2.getInt("Uid"),
                           rs2.getString("Uname"), rs2.getString("Usex"),
                           rs2.getString("Uphone"), rs2.getString("Uaddress"),
                           rs2.getString("Uyue"), rs2.getString("Uregtime"),
                           rs2.getString("Ustate")};
                i++;
            }
            String[] colnames = {"编号", "姓名", "性别", "电话号码", "住址", "余额", "注册时间",
                                "是否锁定"};
            jTable1 = new JTable(cells, colnames);
            jScrollPane1.getViewport().add(jTable1);
        } catch (SQLException ex) {
        }
    }
    //该方法用于查找指定读者
    public void btnFind_actionPerformed(ActionEvent e) {
        //栏位判断是否有查询条件
        if (txtWhat.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "请输入查询信息！");
            return;
        }
        DBC dbc = DBC.getInstance();
        String sql = "";
        if (((String) cbWay.getSelectedItem()).equals("编号")) {
            try {
                //若条件为编号,判断是否为数字
                Integer.parseInt(txtWhat.getText());
            } catch (NumberFormatException ex2) {
                javax.swing.JOptionPane.showMessageDialog(this, "编号只能为数字！");
                return;
            }
            sql = "SELECT * FROM Users WHERE Uid = " +
                  Integer.parseInt(txtWhat.getText());
        } else if (((String) cbWay.getSelectedItem()).equals("姓名")) {
            sql = "SELECT * FROM Users WHERE Uname = '" + txtWhat.getText() +
                  "'";
        } else {
            sql = "SELECT * FROM Users WHERE Uphone = '" + txtWhat.getText() +
                  "'";
        }
        //开始查询
        ResultSet rs = dbc.executeQuery(sql);
        int count = 0;
        try {
            //count用于查询共有多少条包含指定条件的信息
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
                cells[i] = new Object[] {rs.getInt("Uid"),
                           rs.getString("Uname"), rs.getString("Usex"),
                           rs.getString("Uphone"), rs.getString("Uaddress"),
                           rs.getString("Uyue"), rs.getString("Uregtime"),
                           rs.getString("Ustate")};
                i++;
            }
            String[] colnames = {"编号", "姓名", "性别", "电话号码", "住址", "余额", "注册时间",
                                "是否锁定"};
            jTable1 = new JTable(cells, colnames);
            jScrollPane1.getViewport().add(jTable1);
            rs.close();

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex.getMessage().toString());
        }
    }
    //该方法用于调用Show()方法刷新记录
    public void btnFlush_actionPerformed(ActionEvent e) {
        this.Show();
    }
    //该方法用于删除操作
    public void btnDelete_actionPerformed(ActionEvent e) {
        DBC dbc = DBC.getInstance();
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "您还未选中任何信息!");
            return;
        }
        Object value = jTable1.getValueAt(row, 0);
        int id = Integer.parseInt(value.toString());
        if (dbc.executeUpdate("DELETE FROM Users WHERE Uid =" + id)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "编号为" + id + "的读者已成功被删除！请刷新！");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "删除失败！请再试。");
        }

    }
    //该方法用于编辑修改读者信息
    public void btnEdit_actionPerformed(ActionEvent e) {
        //获取选中行
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            //判断是否有选
            javax.swing.JOptionPane.showMessageDialog(this, "您还未选中任何信息!");
            return;
        }
        //获取表格内ID信息,并传给reader
        Object value = jTable1.getValueAt(row, 0);
        FrRedit reader = new FrRedit(Integer.parseInt(value.toString()));
        Dimension screenSize = Toolkit.getDefaultToolkit().
                               getScreenSize();
        Dimension frameSize = reader.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        reader.setLocation((screenSize.width - frameSize.width) /
                           2,
                           (screenSize.height - frameSize.height) /
                           2);
        reader.setVisible(true);

    }
    //该方法用于对指定读者解锁
    public void btnKey_actionPerformed(ActionEvent e) {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "您还未选中任何信息!");
            return;
        }
        Object value = jTable1.getValueAt(row, 0);
        int id = Integer.parseInt(value.toString());
        DBC dbc = DBC.getInstance();
        Object s = jTable1.getValueAt(row, 7);
        String state = s.toString();
        if (state.equals("否")) {
            javax.swing.JOptionPane.showMessageDialog(this, "该读者无需解锁！");
            return;
        }
        if (dbc.executeUpdate("UPDATE Users SET Ustate = '否' WHERE Uid ="+id)) {
            javax.swing.JOptionPane.showMessageDialog(this, "解锁成功！");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "解锁失败！请重试。");
        }
    }
}


class FrReadermanage_btnKey_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnKey_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnKey_actionPerformed(e);
    }
}


class FrReadermanage_btnEdit_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnEdit_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnEdit_actionPerformed(e);
    }
}


class FrReadermanage_btnDelete_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnDelete_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDelete_actionPerformed(e);
    }
}


class FrReadermanage_btnFlush_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnFlush_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnFlush_actionPerformed(e);
    }
}


class FrReadermanage_btnFind_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnFind_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnFind_actionPerformed(e);
    }
}


class FrReadermanage_btnExit_actionAdapter implements ActionListener {
    private FrReadermanage adaptee;
    FrReadermanage_btnExit_actionAdapter(FrReadermanage adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnExit_actionPerformed(e);
    }
}
