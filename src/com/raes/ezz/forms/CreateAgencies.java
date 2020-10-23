package com.raes.ezz.forms;
import com.raes.ezz.helpers.Singleton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class CreateAgencies extends JFrame{
    private JPanel frame;
    private JTextField direction;
    private JTextField name;
    private JButton agregarButton;
    private JButton buscarButton;
    private JTextField idT;
    private JLabel resultName;
    private JLabel resultDirection;
    private JScrollPane table1;
    private JButton borrarButton;

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Dirección");
        ResultSet rs = Singleton.getInstance().db.selectWithTableName("Agencies");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Name")
                        ,rs.getString("Direccion")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table = new JTable();
        table.setModel(model);
        table1.getViewport ().add (table);
    }

    public CreateAgencies(){
        setBounds(0,0,500,400);
        add(frame);
        loadTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",name.getText());
                parameters.put("Direccion",direction.getText());
                Singleton.getInstance().db.insertWithTableName(parameters, "Agencies");
                name.setText("");
                direction.setText("");
                loadTable();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "", responseDirection = "";
                ResultSet rs = Singleton.getInstance().db.searchOnTableWithId("Agencies",Integer.parseInt(idT.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                        responseDirection = rs.getString("Direccion");
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultName.setText(responseName);
                    resultDirection.setText(responseDirection);
                }else{
                    resultName.setText("");
                    resultDirection.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Singleton.getInstance().db.deleteOnTableWithId("Agencies", Integer.parseInt(idT.getText()));
                loadTable();
            }
        });
    }
}
