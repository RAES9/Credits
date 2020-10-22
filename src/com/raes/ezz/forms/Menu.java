package com.raes.ezz.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JButton agenciasButton;
    private JButton empleadosButton;
    private JButton recargasButton;
    private JPanel frame;

    public Menu() {
        setBounds(0, 0, 450, 250);
        add(frame);
        agenciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAgencies c = new CreateAgencies();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
            }
        });
        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateEmploye c = new CreateEmploye();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
            }
        });
        recargasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
