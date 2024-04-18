package user;
import mysql.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderManagementFrame extends JFrame {
    private JTable dataTable;
    private DefaultTableModel model;

    public OrderManagementFrame() {
        setTitle("��������");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // ���ݱ��
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // ɾ����ť
        JButton deleteButton = new JButton("ɾ��");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedData();
                JOptionPane.showMessageDialog(OrderManagementFrame.this, "ɾ���ɹ���", "��ʾ", JOptionPane.WARNING_MESSAGE);
            }
        });

        // ɾ����ť���
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(deleteButton);

        // ��ɾ����ť�����ӵ��������ϲ����ײ���
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // �������ģ��
        model = new DefaultTableModel();
        model.addColumn("��Ʒ����");
        model.addColumn("������");
        model.addColumn("�۸�");
        model.addColumn("ʱ��");
        dataTable.setModel(model);

        // ���ض�������
        loadOrderData();
    }

    private void loadOrderData() {
        // �������ݿ�
        try {
            //��ȡ���ݿ�����
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // ��ѯ��������
            String query = "SELECT productName, productCode, price, orderTime FROM t_order";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            // ��ձ������
            model.setRowCount(0);

            // �����ģ��
            while (result.next()) {
                String productName = result.getString("productName");
                String productCode = result.getString("productCode");
                double price = result.getDouble("price");
                String orderTime = result.getString("orderTime");
                model.addRow(new Object[]{productName, productCode, price , orderTime});
            }

            result.close();
            statement.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedData() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) { // �����ѡ����
            try {
                // ��ȡ���ݿ�����
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                // ��ȡѡ���е�����
                String productName = (String) model.getValueAt(selectedRow, 0);
                String productCode = (String) model.getValueAt(selectedRow, 1);

                // ����ɾ�����ݵ�SQL���
                String deleteQuery = "DELETE FROM t_order WHERE productName = ? AND productCode = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, productName);
                deleteStatement.setString(2, productCode);

                // ִ��ɾ������
                deleteStatement.executeUpdate();

                // ˢ�����ݱ��
                loadOrderData();

                // �ر����Ӻ����
                deleteStatement.close();
                dbConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ���������С�", "��ʾ", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OrderManagementFrame orderManagementFrame = new OrderManagementFrame();
                orderManagementFrame.setVisible(true);
            }
        });
    }
}
