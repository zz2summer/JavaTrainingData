package mybook.book;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mydbc.dbc.DBC;
import java.sql.ResultSet;
import java.sql.*;
import java.awt.Toolkit;

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
//该类用于管理登陆
public class FrLogin extends JFrame {
    JPanel contentPane;
    JLabel lblWelcome = new JLabel();
    JLabel lblName = new JLabel();
    JLabel lblPwd = new JLabel();
    JTextField txtName = new JTextField();
    JPasswordField txtPwd = new JPasswordField();
    JButton btnLogin = new JButton();
    JButton btnExit = new JButton();
    public FrLogin() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(400, 300));
        setTitle("系统登陆");
        lblWelcome.setFont(new java.awt.Font("宋体", Font.BOLD | Font.ITALIC, 24));
        lblWelcome.setText("        图书管理系统");
        lblWelcome.setBounds(new Rectangle(25, 33, 500, 36));
        lblName.setFont(new java.awt.Font("宋体", Font.ITALIC, 16));
        lblName.setText("用户名：");
        lblName.setBounds(new Rectangle(69, 88, 64, 21));
        lblPwd.setFont(new java.awt.Font("宋体", Font.ITALIC, 16));
        lblPwd.setText("密码：");
        lblPwd.setBounds(new Rectangle(69, 120, 53, 15));
        txtName.setBounds(new Rectangle(154, 88, 132, 21));
        txtPwd.setBounds(new Rectangle(154, 117, 132, 21));
        btnLogin.setBounds(new Rectangle(94, 163, 83, 25));
        btnLogin.setText("登陆");
        btnLogin.addActionListener(new FrBook_btnLogin_actionAdapter(this));
        btnExit.setBounds(new Rectangle(214, 163, 83, 25));
        btnExit.setText("退出");
        btnExit.addActionListener(new FrBook_btnExit_actionAdapter(this));
        contentPane.add(lblWelcome);
        contentPane.add(lblName);
        contentPane.add(lblPwd);
        contentPane.add(txtName);
        contentPane.add(txtPwd);
        contentPane.add(btnLogin);
        contentPane.add(btnExit);
    }
    //该方法用于判断是否可以登陆
    public void btnLogin_actionPerformed(ActionEvent e) {
        //栏位判断
        if (txtName.getText().equals("") || txtPwd.getText().equals("")) {

            javax.swing.JOptionPane.showMessageDialog(this, "用户名及密码不能为空！请重新输入。");
            txtName.setText("");
            txtPwd.setText("");
        } else {
            boolean success = false;
            String name = "";
            String pwd = "";
            DBC dbc = DBC.getInstance();
            ResultSet rs = dbc.executeQuery("SELECT * FROM Admin");
            //查询帐号和密码是否匹配
            try {
                while (rs.next()) {
                    name = rs.getString("Aname");
                    pwd = rs.getString("Apwd");
                    if (txtName.getText().equals(name) &&
                        txtPwd.getText().equals(pwd)) {
                        success = true;
                        break;
                    }
                }
                //若匹配则登陆成功并进入主界面
                if (success) {
                    FrMain ufram = new FrMain();
                    this.setVisible(false);
                    Dimension screenSize = Toolkit.getDefaultToolkit().
                                           getScreenSize();
                    Dimension frameSize = ufram.getSize();
                    if (frameSize.height > screenSize.height) {
                        frameSize.height = screenSize.height;
                    }
                    if (frameSize.width > screenSize.width) {
                        frameSize.width = screenSize.width;
                    }
                    ufram.setLocation((screenSize.width - frameSize.width) /
                                      2,
                                      (screenSize.height - frameSize.height) /
                                      2);
                    ufram.setVisible(true);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "管理员名或密码错误！请重新输入。");
                    txtName.setText("");
                    txtPwd.setText("");
                }

            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        ex.getMessage().toString());
                return;
            }
        }
    }

    public void btnExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}


class FrBook_btnExit_actionAdapter implements ActionListener {
    private FrLogin adaptee;
    FrBook_btnExit_actionAdapter(FrLogin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnExit_actionPerformed(e);
    }
}


class FrBook_btnLogin_actionAdapter implements ActionListener {
    private FrLogin adaptee;
    FrBook_btnLogin_actionAdapter(FrLogin adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLogin_actionPerformed(e);
    }
}
