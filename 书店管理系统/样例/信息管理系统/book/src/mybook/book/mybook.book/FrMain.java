package mybook.book;

import java.awt.*;

import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.io.IOException;
import mydbc.dbc.DBC;
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
//该类为本程序主界面
public class FrMain extends JFrame {
    ImageIcon borrow = new ImageIcon("img/borrow.jpg");
    ImageIcon breturn = new ImageIcon("img/return.jpg");
    ImageIcon usermanage = new ImageIcon("img/usermanage.jpg");
    ImageIcon bookmanage = new ImageIcon("img/bookmanage.jpg");
    ImageIcon about = new ImageIcon("img/about.jpg");
    ImageIcon bexit = new ImageIcon("img/exit.jpg");
    ImageIcon back = new ImageIcon("img/bookbg.jpg");
    public FrMain() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(null);
        this.setJMenuBar(jMenuBar1);
        setSize(new Dimension(700, 600));
        this.setTitle("图书管理系统");
        menuSysManage.setText("【系统管理】");
        miExit.setText("退出");
        miExit.addActionListener(new FrMain_miExit_actionAdapter(this));
        menuBasic.setText("【基本操作】");
        miAddReader.setText("添加新读者");
        miAddReader.addActionListener(new FrMain_miAddReader_actionAdapter(this));
        miAddBook.setText("新书入库");
        miAddBook.addActionListener(new FrMain_miAddBook_actionAdapter(this));
        miBorrowBook.setText("借书");
        miBorrowBook.addActionListener(new FrMain_miBorrowBook_actionAdapter(this));
        miReturnBook.setActionCommand("还书");
        miReturnBook.setText("还书");
        miReturnBook.addActionListener(new FrMain_miReturnBook_actionAdapter(this));
        miGoon.setText("续借");
        miGoon.addActionListener(new FrMain_miGoon_actionAdapter(this));
        miReaderManage.setText("读者管理");
        miReaderManage.addActionListener(new
                                         FrMain_miReaderManage_actionAdapter(this));
        miBookManage.setText("图书管理");
        miBookManage.addActionListener(new FrMain_miBookManage_actionAdapter(this));
        menuHelp.setText("【帮助】");
        miAbout.setText("关于");
        miAbout.addActionListener(new FrMain_miAbout_actionAdapter(this));
        palMenu.setBorder(BorderFactory.createRaisedBevelBorder());
        palMenu.setBounds(new Rectangle( -1, 0, 700, 78));
        palMenu.setLayout(null);
        btnBorrow.setBounds(new Rectangle(5, 3, 75, 70));
        btnBorrow.setIcon(borrow);
        btnBorrow.addActionListener(new FrMain_btnBorrow_actionAdapter(this));
        btnReturn.setBounds(new Rectangle(83, 3, 75, 70));
        btnReturn.setIcon(breturn);
        btnReturn.addActionListener(new FrMain_btnReturn_actionAdapter(this));
        btnBookManage.setBounds(new Rectangle(160, 3, 75, 70));
        btnBookManage.setIcon(bookmanage);
        btnBookManage.addActionListener(new FrMain_btnBookManage_actionAdapter(this));
        btnUserManage.setBounds(new Rectangle(238, 3, 75, 70));
        btnUserManage.setIcon(usermanage);
        btnUserManage.addActionListener(new FrMain_btnUserManage_actionAdapter(this));
        btnAbout.setBounds(new Rectangle(315, 3, 75, 70));
        btnAbout.setIcon(about);
        btnAbout.addActionListener(new FrMain_btnAbout_actionAdapter(this));
        btnExit.setBounds(new Rectangle(393, 3, 75, 70));
        btnExit.setIcon(bexit);
        btnExit.addActionListener(new FrMain_btnExit_actionAdapter(this));
        palState.setBorder(BorderFactory.createRaisedBevelBorder());
        palState.setBounds(new Rectangle(0, 507, 700, 38));
        palState.setLayout(null);
        lblSysName.setBorder(BorderFactory.createLoweredBevelBorder());
        lblSysName.setText("图书管理系统");
        lblSysName.setBounds(new Rectangle(3, 3, 78, 31));
        lblShow.setBorder(BorderFactory.createLoweredBevelBorder());
        lblShow.setText("技术支持：9920317");
        lblShow.setBounds(new Rectangle(86, 3, 120, 31));
        miChangePwd.setText("修改密码");
        miChangePwd.addActionListener(new FrMain_miChangePwd_actionAdapter(this));
        miAddManager.setText("添加/删除管理员");
        miAddManager.addActionListener(new FrMain_miAddManager_actionAdapter(this));
        lblMainbg.setIcon(back);
        lblMainbg.setBounds(new Rectangle(0, 77, 699, 431));
        lblNowtime.setBorder(BorderFactory.createLoweredBevelBorder());
        lblNowtime.setBounds(new Rectangle(514, 3, 179, 31));
        lblTime.setText("当前时间：");
        lblTime.setBounds(new Rectangle(441, 3, 63, 31));
        menuTools.setToolTipText("");
        menuTools.setText("【小工具】");
        miNotepad.setText("记事本");
        miNotepad.addActionListener(new FrMain_miNotepad_actionAdapter(this));
        miCalc.setText("计算器");
        miCalc.addActionListener(new FrMain_miCalc_actionAdapter(this));
        lblWarnnig.setText("欢迎访问图书管理系统");
        lblWarnnig.setBounds(new Rectangle(214, 3, 222, 31));
        jMenuBar1.add(menuSysManage);
        jMenuBar1.add(menuBasic);
        jMenuBar1.add(menuTools);
        jMenuBar1.add(menuHelp);
        menuSysManage.add(miAddManager);
        menuSysManage.add(miChangePwd);
        menuSysManage.addSeparator();
        menuSysManage.add(miExit);
        menuBasic.add(miAddReader);
        menuBasic.add(miAddBook);
        menuBasic.addSeparator();
        menuBasic.add(miBorrowBook);
        menuBasic.add(miReturnBook);
        menuBasic.add(miGoon);
        menuBasic.addSeparator();
        menuBasic.add(miReaderManage);
        menuBasic.add(miBookManage);
        menuHelp.add(miAbout);
        this.getContentPane().add(palMenu);
        palMenu.add(btnBorrow);
        palMenu.add(btnReturn);
        palMenu.add(btnBookManage);
        palMenu.add(btnUserManage);
        palMenu.add(btnAbout);
        palMenu.add(btnExit);
        this.getContentPane().add(palState);
        this.getContentPane().add(lblMainbg);
        palState.add(lblSysName);
        palState.add(lblShow);
        palState.add(lblNowtime);
        palState.add(lblTime);
        palState.add(lblWarnnig);
        menuTools.add(miNotepad);
        menuTools.add(miCalc);
        Time time = new Time();
        timOut tout = new timOut();
        time.start();
        tout.start();
    }

    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu menuSysManage = new JMenu();
    JMenuItem miExit = new JMenuItem();
    JMenu menuBasic = new JMenu();
    JMenuItem miAddReader = new JMenuItem();
    JMenuItem miAddBook = new JMenuItem();
    JMenuItem miBorrowBook = new JMenuItem();
    JMenuItem miReturnBook = new JMenuItem();
    JMenuItem miGoon = new JMenuItem();
    JMenuItem miReaderManage = new JMenuItem();
    JMenuItem miBookManage = new JMenuItem();
    JMenu menuHelp = new JMenu();
    JMenuItem miAbout = new JMenuItem();
    JPanel palMenu = new JPanel();
    JButton btnBorrow = new JButton();
    JButton btnReturn = new JButton();
    JButton btnBookManage = new JButton();
    JButton btnUserManage = new JButton();
    JButton btnAbout = new JButton();
    JButton btnExit = new JButton();
    JPanel palState = new JPanel();
    JLabel lblSysName = new JLabel();
    JLabel lblShow = new JLabel();
    JMenuItem miChangePwd = new JMenuItem();
    JMenuItem miAddManager = new JMenuItem();
    JLabel lblMainbg = new JLabel();
    JLabel lblNowtime = new JLabel();
    JLabel lblTime = new JLabel();
    JMenu menuTools = new JMenu();
    JMenuItem miNotepad = new JMenuItem();
    JMenuItem miCalc = new JMenuItem();
    JLabel lblWarnnig = new JLabel();
    //该方法用于退出程序
    public void btnExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    //该方法用于被调用并初始化一个新Frame
    public void NewFram(Frame x) {
        Dimension screenSize = Toolkit.getDefaultToolkit().
                               getScreenSize();
        Dimension frameSize = x.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        x.setLocation((screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);
        x.setVisible(true);

    }

    class timOut extends Thread {

        public void run() {
            DBC dbc = DBC.getInstance();
            ResultSet rs;
            int bid = 0;
            int uid = 0;
            int balance = 0;
            int time = 1;

            while (true) {
                rs = dbc.executeQuery(
                        "SELECT balance=datediff(S,Obotime,getdate()),Ouid,ObRenttime FROM Outbooks");

                try {
                    while (rs.next()) {
                        //获取读者ID和当前时间和借书时时间差
                        uid = rs.getInt("balance");
                        balance = rs.getInt("Ouid");
                        //获取借书期限
                        time = rs.getInt("ObRenttime");
                        //若时间差大于期限则锁定该读者
                        if (balance >= time) {
                            dbc.executeUpdate(
                                    "UPDATE Users SET Ustate = '是' WHERE Uid = " +
                                    uid);
                        }


                    }
                    rs.close();
                    //每30分钟执行一次
                    Thread.sleep(1000 * 60 * 30);
                } catch (InterruptedException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            ex.getMessage().toString());
                } catch (SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            ex.getMessage().toString());
                }

            }

        }
    }


    class Time extends Thread {

        public void run() {
            //以下用于同步显示时间
            Calendar cal;

            while (true) {
                try {
                    cal = Calendar.getInstance();
                    String now = cal.get(Calendar.YEAR) + "年" +
                                 (cal.get(Calendar.MONTH) + 1) +
                                 "月" + cal.get(Calendar.DATE) + "日 " +
                                 cal.get(Calendar.HOUR) +
                                 "时" + cal.get(Calendar.MINUTE) + "分" +
                                 cal.get(Calendar.SECOND) +
                                 "秒";

                    lblNowtime.setText(now);

                    //每一秒钟循环一次
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }


    public void btnAbout_actionPerformed(ActionEvent e) {
        FrAbout about = new FrAbout();
        NewFram(about);

    }

    public void miAbout_actionPerformed(ActionEvent e) {
        FrAbout about = new FrAbout();
        NewFram(about);

    }

    public void miAddManager_actionPerformed(ActionEvent e) {
        FrAddmanager addmanager = new FrAddmanager();
        NewFram(addmanager);
    }

    public void miChangePwd_actionPerformed(ActionEvent e) {
        FrChange change = new FrChange();
        NewFram(change);

    }

    public void miExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    public void miAddReader_actionPerformed(ActionEvent e) {
        FrAddreader addreader = new FrAddreader();
        NewFram(addreader);

    }

    public void miAddBook_actionPerformed(ActionEvent e) {
        FrAddbook addbook = new FrAddbook();
        NewFram(addbook);

    }

    public void miBorrowBook_actionPerformed(ActionEvent e) {
        FrBorrow borrow = new FrBorrow();
        NewFram(borrow);

    }

    public void miReturnBook_actionPerformed(ActionEvent e) {
        FrReturn returnbook = new FrReturn();
        NewFram(returnbook);

    }

    public void miGoon_actionPerformed(ActionEvent e) {
        FrKeep keep = new FrKeep();
        NewFram(keep);

    }

    public void miReaderManage_actionPerformed(ActionEvent e) {
        FrReadermanage rmanage = new FrReadermanage();
        NewFram(rmanage);

    }

    public void miBookManage_actionPerformed(ActionEvent e) {
        FrBookmanage bmanage = new FrBookmanage();
        NewFram(bmanage);

    }

    public void btnBorrow_actionPerformed(ActionEvent e) {
        FrBorrow borrow = new FrBorrow();
        NewFram(borrow);

    }

    public void btnReturn_actionPerformed(ActionEvent e) {
        FrReturn returnbook = new FrReturn();
        NewFram(returnbook);

    }

    public void btnBookManage_actionPerformed(ActionEvent e) {
        FrBookmanage bmanage = new FrBookmanage();
        NewFram(bmanage);

    }

    public void btnUserManage_actionPerformed(ActionEvent e) {
        FrReadermanage rmanage = new FrReadermanage();
        NewFram(rmanage);

    }

    public void miNotepad_actionPerformed(ActionEvent e) {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException ex) {
        }
    }

    public void miCalc_actionPerformed(ActionEvent e) {
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (IOException ex) {
        }
    }
}


class FrMain_miCalc_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miCalc_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miCalc_actionPerformed(e);
    }
}


class FrMain_miNotepad_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miNotepad_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miNotepad_actionPerformed(e);
    }
}


class FrMain_miReturnBook_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miReturnBook_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miReturnBook_actionPerformed(e);
    }
}


class FrMain_miGoon_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miGoon_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miGoon_actionPerformed(e);
    }
}


class FrMain_miReaderManage_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miReaderManage_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miReaderManage_actionPerformed(e);
    }
}


class FrMain_miBookManage_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miBookManage_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miBookManage_actionPerformed(e);
    }
}


class FrMain_btnBorrow_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnBorrow_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnBorrow_actionPerformed(e);
    }
}


class FrMain_btnReturn_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnReturn_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnReturn_actionPerformed(e);
    }
}


class FrMain_btnBookManage_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnBookManage_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnBookManage_actionPerformed(e);
    }
}


class FrMain_btnUserManage_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnUserManage_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnUserManage_actionPerformed(e);
    }
}


class FrMain_miAddBook_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miAddBook_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miAddBook_actionPerformed(e);
    }
}


class FrMain_miBorrowBook_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miBorrowBook_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miBorrowBook_actionPerformed(e);
    }
}


class FrMain_miAddReader_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miAddReader_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miAddReader_actionPerformed(e);
    }
}


class FrMain_miChangePwd_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miChangePwd_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miChangePwd_actionPerformed(e);
    }
}


class FrMain_miExit_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miExit_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miExit_actionPerformed(e);
    }
}


class FrMain_miAddManager_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miAddManager_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miAddManager_actionPerformed(e);
    }
}


class FrMain_miAbout_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_miAbout_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.miAbout_actionPerformed(e);
    }
}


class FrMain_btnAbout_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnAbout_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnAbout_actionPerformed(e);
    }
}


class FrMain_btnExit_actionAdapter implements ActionListener {
    private FrMain adaptee;
    FrMain_btnExit_actionAdapter(FrMain adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnExit_actionPerformed(e);
    }
}
