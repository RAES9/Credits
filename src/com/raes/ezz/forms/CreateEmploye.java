package com.raes.ezz.forms;
import com.raes.ezz.helpers.Singleton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateEmploye extends JFrame{
    private JTextField surname;
    private JTextField name;
    private JComboBox comboAgencies;
    private JTextField idT;
    private JButton buscarButton;
    private JButton borrarButton;
    private JScrollPane table1;
    private JPanel frame;
    private JLabel resultName;
    private JLabel resultSurname;
    private JLabel resultAgnecie;
    private JButton agregarButton;
    private ArrayList<String> idsAgencies = new ArrayList<>();

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Agencia");
        ResultSet rs = Singleton.getInstance().db.selectWithTableName("Employees");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Name")
                        ,rs.getString("Surname")
                        ,rs.getString("AgencyId")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table = new JTable();
        table.setModel(model);
        table1.getViewport ().add (table);
    }

    private void getAgencies(){
        ResultSet rs = Singleton.getInstance().db.selectWithTableName("Agencies");
        try{
            while (rs.next()){
                comboAgencies.addItem(rs.getString("Name"));
                idsAgencies.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }

    public CreateEmploye() {
        setBounds(0, 0, 550, 450);
        add(frame);
        loadTable();
        getAgencies();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",name.getText());
                parameters.put("Surname",surname.getText());
                parameters.put("AgencyId",idsAgencies.get(comboAgencies.getSelectedIndex()));
                Singleton.getInstance().db.insertWithTableName(parameters, "Employees");
                name.setText("");
                surname.setText("");
                loadTable();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "", responseSurname = "", responseAgencie = "";
                ResultSet rs = Singleton.getInstance().db.searchOnTableWithId("Employees",Integer.parseInt(idT.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                        responseSurname = rs.getString("Surname");
                        ResultSet rsA = Singleton.getInstance().db.searchOnTableWithId("Agencies",Integer.parseInt(rs.getString("AgencyId")));
                        while (rsA.next()){
                            responseAgencie = rsA.getString("Name");
                        }
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultName.setText(responseName);
                    resultSurname.setText(responseSurname);
                    resultAgnecie.setText(responseAgencie);
                }else{
                    resultName.setText("");
                    resultSurname.setText("");
                    resultAgnecie.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Singleton.getInstance().db.deleteOnTableWithId("Employees", Integer.parseInt(idT.getText()));
                loadTable();
            }
        });
    }
}
