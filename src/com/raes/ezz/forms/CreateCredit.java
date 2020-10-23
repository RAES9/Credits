package com.raes.ezz.forms;

import com.raes.ezz.helpers.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateCredit extends JFrame{
    private JTextField number;
    private JComboBox comboAmount;
    private JComboBox comboCompany;
    private JScrollPane table1;
    private JButton filtrarButton;
    private JButton mostrarTodasButton;
    private JComboBox comboFilterCustom;
    private JButton busquedaPorNumeroButton;
    private JPanel frame;
    private JTextField idEmploye;
    private JButton recargarButton;
    private JComboBox comboFilterType;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private ArrayList<String> idsCompanies = new ArrayList<>();

    private void getCompanies(){
        ResultSet rs = Singleton.getInstance().db.selectWithTableName("Companies");
        try{
            while (rs.next()){
                comboCompany.addItem(rs.getString("Name"));
                comboFilterType.addItem(rs.getString("Name"));
                idsCompanies.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Número");
        model.addColumn("Monto");
        model.addColumn("ID Empresa");
        model.addColumn("ID Empleado");
        ResultSet rs = Singleton.getInstance().db.selectWithTableName("Credits");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Name")
                        ,rs.getString("Amount")
                        ,rs.getString("ConpanieId")
                        ,rs.getString("EmployeId")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table = new JTable();
        table.setModel(model);
        table1.getViewport ().add (table);
    }

    private void loadFilterTable(String column, String value){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Número");
        model.addColumn("Monto");
        model.addColumn("ID Empresa");
        model.addColumn("ID Empleado");
        ResultSet rs = Singleton.getInstance().db.searchCustomOnTable("Credits",value,column);
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Name")
                        ,rs.getString("Amount")
                        ,rs.getString("ConpanieId")
                        ,rs.getString("EmployeId")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table = new JTable();
        table.setModel(model);
        table1.getViewport ().add (table);
    }

    public CreateCredit() {
        setBounds(0, 0, 500, 500);
        add(frame);
        getCompanies();
        loadTable();
        recargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",number.getText());
                parameters.put("Amount",comboAmount.getSelectedItem().toString());
                parameters.put("ConpanieId",idsCompanies.get(comboCompany.getSelectedIndex()));
                parameters.put("EmployeId",idEmploye.getText());
                String responseName = "";
                ResultSet rs = Singleton.getInstance().db.searchOnTableWithId("Employees",Integer.parseInt(idEmploye.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    Singleton.getInstance().db.insertWithTableName(parameters, "Credits");
                    number.setText("");
                    idEmploye.setText("");
                }else{
                    idEmploye.setText("");
                    JOptionPane.showMessageDialog(null,"El ID del empleado no existe");
                }
                loadTable();
            }
        });
        mostrarTodasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        busquedaPorNumeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = JOptionPane.showInputDialog("Ingrese número que desea buscar");
                loadFilterTable("Name",num);
            }
        });
        filtrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton2.isSelected()){
                    loadFilterTable("Amount",comboFilterCustom.getSelectedItem().toString());
                }else{
                    loadFilterTable("ConpanieId",idsCompanies.get(comboFilterType.getSelectedIndex()));
                }
            }
        });
    }
}
