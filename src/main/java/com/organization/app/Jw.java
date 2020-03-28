package com.organization.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @ClassName: Jw
 * @Author： yuanyixiong
 * @Date： 2020/3/26 下午8:09
 * @Description： TODO
 * @Version： 1.0
 */
public class Jw extends JWindow {

    public Jw(){
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        super.setBounds(0,0,500,500);
        super.setVisible(true);
    }

    public static void main(String[] args) {
        new Jw();
    }
}
