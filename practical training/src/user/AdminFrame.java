package user;

import employee.EmployeeManagementUI;
import goods.AddGoodsFrame;
import mysql.DatabaseConnection;
import resandlogin.ChangePassword;
import resandlogin.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminFrame extends JFrame {
    private JTextField searchField;
    private JTable dataTable;
    private int currentPage = 1;
    private int pageSize = 10;
    private int totalPage = 0;
    private JLabel pageInfoLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton resetButton;

    public AdminFrame() {
        setTitle("����Ա����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // �����˵���
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ���Ͻǲ˵���
        JComboBox<String> managementMenu = new JComboBox<>();
        managementMenu.addItem("Ա������");
        managementMenu.addItem("��������");
        managementMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMenu = (String) managementMenu.getSelectedItem();
                if (selectedMenu.equals("Ա������")) {
                    dispose();
                    try {
                        new EmployeeManagementUI().setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selectedMenu.equals("��������")) {
                    new OrderManagementFrame().setVisible(true);
                }
            }
        });
        topPanel.add(managementMenu);

        // ���Ͻǲ˵���
        JComboBox<String> userMenu = new JComboBox<>();
        userMenu.addItem("�޸�����");
        userMenu.addItem("�˳���¼");
        userMenu.addItem("�˳�ϵͳ");
        userMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMenu = (String) userMenu.getSelectedItem();
                if (selectedMenu.equals("�޸�����")) {
                    dispose();
                    new ChangePassword().setVisible(true);
                } else if (selectedMenu.equals("�˳���¼")) {
                    dispose();
                    new Login().setVisible(true);
                } else if (selectedMenu.equals("�˳�ϵͳ")) {
                    dispose();
                }
            }
        });
        topPanel.add(userMenu);

        // ��Ʒ��ѯ���
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // ��ѯ�ı���
        JLabel searchLabel = new JLabel("��Ʒ��ѯ:");
        searchField = new JTextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // ��ѯ��ť
        JButton searchButton = new JButton("��ѯ");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                searchProduct(keyword);
            }
        });
        searchPanel.add(searchButton);

        // �������ð�ť
        resetButton = new JButton("����");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        searchPanel.add(resetButton);

        // ��Ʒ���ݱ��
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // �ײ���ҳ��
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ��ҳ��Ϣ
        pageInfoLabel = new JLabel();
        bottomPanel.add(pageInfoLabel);

        // ��һҳ��ť
        prevButton = new JButton("��һҳ");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    loadProductData();
                }
            }
        });
        bottomPanel.add(prevButton);

        // ��һҳ��ť
        nextButton = new JButton("��һҳ");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < totalPage) {
                    currentPage++;
                    loadProductData();
                }
            }
        });
        bottomPanel.add(nextButton);

        // �����Ʒ��ť
        JButton addButton = new JButton("�����Ʒ");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddGoodsFrame().setVisible(true);
            }
        });
        bottomPanel.add(addButton);

        // ɾ����Ʒ��ť
        JButton deleteButton = new JButton("ɾ����Ʒ");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ִ��ɾ����Ʒ���߼�
                deleteSelectedProduct();
            }
        });
        bottomPanel.add(deleteButton);

        // �޸���Ʒ��ť
        JButton updateButton = new JButton("�޸���Ʒ");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ִ���޸���Ʒ���߼�
                updateSelectedProduct();
            }
        });
        bottomPanel.add(updateButton);

        loadProductData();
    }

    // ������Ʒ����
    public void loadProductData() {
        try {
            // �������ݿⲢִ�в�ѯ����
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            String countQuery = "SELECT COUNT(*) FROM t_goods";
            Statement countStatement = connection.createStatement();
            ResultSet countResult = countStatement.executeQuery(countQuery);
            if (countResult.next()) {
                int totalCount = countResult.getInt(1);
                totalPage = (int) Math.ceil((double) totalCount / pageSize);
            }
            countStatement.close();

            int offset = (currentPage - 1) * pageSize;
            // չʾ��Ʒ����
            String query = "SELECT g.goodsName, g.goodsCode, g.price, g.count, g.goodsTypeId, gt.goodsTypeName " +
                    "FROM t_goods g " +
                    "INNER JOIN t_goodsType gt ON g.goodsTypeId = gt.id " +
                    "LIMIT ?, ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, offset);
            statement.setInt(2, pageSize);
            ResultSet result = statement.executeQuery();

            // �������ģ��
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("����");
            model.addColumn("������");
            model.addColumn("�۸�");
            model.addColumn("����");
            model.addColumn("�������");
            model.addColumn("��������");

            // �����ģ��
            while (result.next()) {
                String name = result.getString("goodsName");
                String barcode = result.getString("goodsCode");
                double price = result.getDouble("price");
                int quantity = result.getInt("count");
                String categoryCode = result.getString("goodsTypeId");
                String goodsTypeName = result.getString("goodsTypeName");
                model.addRow(new Object[]{name, barcode, price, quantity, categoryCode,goodsTypeName});
            }

            dataTable.setModel(model);
            statement.close();
            dbConnection.closeConnection();
            updatePageInfo();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "�������ݿ����", "����", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ���·�ҳ��ť״̬
    private void updatePageInfo() {
        pageInfoLabel.setText("�� " + currentPage + " ҳ / �� " + totalPage + " ҳ");
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPage);
    }

    // ���ݹؼ���������Ʒ
    private void searchProduct(String keyword) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            String countQuery = "SELECT COUNT(*) FROM t_goods WHERE goodsName LIKE ?";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, "%" + keyword + "%");
            ResultSet countResult = countStatement.executeQuery();
            if (countResult.next()) {
                int totalCount = countResult.getInt(1);
                totalPage = (int) Math.ceil((double) totalCount / pageSize);
            }
            countStatement.close();

            //����ƫ����
            int offset = (currentPage - 1) * pageSize;

            //��ѯ��Ʒ����
            String query = "SELECT g.goodsName, g.goodsCode, g.price, g.count, g.goodsTypeId, gt.goodsTypeName " +
                    "FROM t_goods g " +
                    "INNER JOIN t_goodsType gt ON g.goodsTypeId = gt.id " +
                    "WHERE g.goodsName LIKE ? OR g.goodsCode LIKE ? " +
                    "LIMIT ?, ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setInt(3, offset);
            statement.setInt(4, pageSize);
            ResultSet result = statement.executeQuery();

            // �������ģ��
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("����");
            model.addColumn("������");
            model.addColumn("�۸�");
            model.addColumn("����");
            model.addColumn("�������");
            model.addColumn("��������");


            // �����ģ��
            while (result.next()) {
                String name = result.getString("goodsName");
                String barcode = result.getString("goodsCode");
                double price = result.getDouble("price");
                int quantity = result.getInt("count");
                String categoryCode = result.getString("goodsTypeId");
                String goodsTypeName = result.getString("goodsTypeName");
                model.addRow(new Object[]{name, barcode, price, quantity, categoryCode, goodsTypeName});
            }

            // ���ñ��ģ��
            dataTable.setModel(model);

            // ���·�ҳ��Ϣ
            updatePageInfo();

            countResult.close();
            countStatement.close();
            result.close();
            statement.close();
            dbConnection.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "�������ݿ����", "����", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ��������
    private void resetData() {
        searchField.setText("");
        currentPage = 1;
        loadProductData();
    }

    // ɾ����Ʒ
    private void deleteSelectedProduct() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "����ѡ��Ҫɾ������Ʒ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String productName = (String) dataTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ����Ʒ��" + productName + " ��", "ȷ��ɾ��",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                String deleteQuery = "DELETE FROM t_goods WHERE goodsName = ?";
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setString(1, productName);
                int result = statement.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "��Ʒɾ���ɹ���", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
                    loadProductData();
                } else {
                    JOptionPane.showMessageDialog(this, "��Ʒɾ��ʧ�ܣ�", "ʧ��", JOptionPane.ERROR_MESSAGE);
                }

                statement.close();
                dbConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "�������ݿ����", "����", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // �޸���Ʒ
    private void updateSelectedProduct() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "����ѡ��Ҫ�޸ĵ���Ʒ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String productName = (String) dataTable.getValueAt(selectedRow, 0);
        String productBarcode = (String) dataTable.getValueAt(selectedRow, 1);
        double productPrice = (double) dataTable.getValueAt(selectedRow, 2);
        int productQuantity = (int) dataTable.getValueAt(selectedRow, 3);
        String goodsTypeIdStr = (String) dataTable.getValueAt(selectedRow, 4);
        int goodsTypeId = Integer.parseInt(goodsTypeIdStr);

        String newProductName = JOptionPane.showInputDialog(this, "�������µ���Ʒ����", productName);
        if (newProductName == null || newProductName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "��Ʒ���Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newProductBarcode;
        double newProductPrice;
        int newProductQuantity;
        String newGoodsTypeName;

        newProductBarcode = JOptionPane.showInputDialog(this, "�������µ���Ʒ������", productBarcode);
        if (newProductBarcode == null || newProductBarcode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "��Ʒ�����벻��Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        while (true) {
            try {
                String priceStr = JOptionPane.showInputDialog(this, "�������µ���Ʒ�۸�", productPrice);
                if (priceStr == null || priceStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "��Ʒ�۸���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                newProductPrice = Double.parseDouble(priceStr);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "��Ʒ�۸����Ϊ����", "����", JOptionPane.ERROR_MESSAGE);
            }
        }

        while (true) {
            try {
                String quantityStr = JOptionPane.showInputDialog(this, "�������µ���Ʒ����", productQuantity);
                if (quantityStr == null || quantityStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "��Ʒ��������Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                newProductQuantity = Integer.parseInt(quantityStr);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "��Ʒ��������Ϊ����", "����", JOptionPane.ERROR_MESSAGE);
            }
        }

        // ����������ѡ��
        String[] goodsTypes = {"������", "ͼ����", "��ʳ��", "ˮ����", "�߲���"};
        JComboBox<String> comboBox = new JComboBox<>(goodsTypes);

        // ��ʾ����������ĶԻ���
        int option = JOptionPane.showOptionDialog(
                this,                   // �����
                comboBox,               // ������
                "��ѡ���µ���Ʒ������������", // �Ի������
                JOptionPane.OK_CANCEL_OPTION, // ѡ������
                JOptionPane.QUESTION_MESSAGE, // �Ի�������
                null, null, null);       // ��������

        // �û������"ȷ��"��ť
        if (option == JOptionPane.OK_OPTION) {
            newGoodsTypeName = (String) comboBox.getSelectedItem();
            if (newGoodsTypeName == null || newGoodsTypeName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "��Ʒ�����������Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                // ������Ʒ������������
                String updateGoodsTypeQuery = "UPDATE t_goodsType SET goodsTypeName = ? WHERE id = ?";
                PreparedStatement updateGoodsTypeStatement = connection.prepareStatement(updateGoodsTypeQuery);
                updateGoodsTypeStatement.setString(1, newGoodsTypeName);
                updateGoodsTypeStatement.setInt(2, goodsTypeId);
                updateGoodsTypeStatement.executeUpdate();
                updateGoodsTypeStatement.close();

                // ������Ʒ��Ϣ
                String updateQuery = "UPDATE t_goods SET goodsName = ?, goodsCode = ?, price = ?, count = ?  WHERE goodsName = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newProductName);
                updateStatement.setString(2, newProductBarcode);
                updateStatement.setDouble(3, newProductPrice);
                updateStatement.setInt(4, newProductQuantity);
                updateStatement.setString(5, productName);
                updateStatement.executeUpdate();
                updateStatement.close();

                connection.close();

                loadProductData();

                JOptionPane.showMessageDialog(this, "��Ʒ�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "�޸���Ʒʧ�ܣ�" + e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }
}
