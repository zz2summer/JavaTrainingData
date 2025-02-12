package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//该类用于显示本程序相关介绍
public class FrAbout extends JFrame {
    public FrAbout() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        setSize(new Dimension(340, 244));
        this.setTitle("关于");
        lblSysnam.setFont(new java.awt.Font("宋体", Font.BOLD, 20));
        lblSysnam.setText("图书管理系统");
        lblSysnam.setBounds(new Rectangle(103, 9, 135, 37));
        btnSure.addActionListener(new FrAbout_btnSure_actionAdapter(this));
        this.getContentPane().add(lblSysnam);
        lblTools.setText("开发人员: 姜2星星,顾2超,找妹");
        lblTools.setBounds(new Rectangle(25, 14, 205, 33));
        this.getContentPane().add(palShow);
        /*lblRun.setText("运行环境：JVM+MSSQL 2000+Win32");
        lblRun.setBounds(new Rectangle(25, 60, 181, 27));*/
        btnSure.setBounds(new Rectangle(129, 174, 83, 25));
        btnSure.setText("确定");
        palShow.add(lblTools);
        palShow.add(lblRun);
        this.getContentPane().add(btnSure);
        palShow.setBorder(BorderFactory.createEtchedBorder());
        palShow.setBounds(new Rectangle(51, 44, 238, 119));
        palShow.setLayout(null);
    }

    JLabel lblSysnam = new JLabel();
    JPanel palShow = new JPanel();
    JLabel lblTools = new JLabel();
    JLabel lblRun = new JLabel();
    JButton btnSure = new JButton();
    public void btnSure_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}


class FrAbout_btnSure_actionAdapter implements ActionListener {
    private FrAbout adaptee;
    FrAbout_btnSure_actionAdapter(FrAbout adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSure_actionPerformed(e);
    }
}
