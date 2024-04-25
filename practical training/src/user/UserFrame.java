package user;

import mysql.DatabaseConnection;
import resandlogin.ChangePassword;
import resandlogin.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFrame extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTable dataTable;
    private int currentPage = 1;
    private int pageSize = 10;
    private int totalPage = 0;
    private JLabel pageInfoLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton resetButton;

    public UserFrame() {
        setTitle("�û�����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // �¶�����ť
        JButton orderButton = new JButton("�¶���");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = dataTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    String productName = (String) dataTable.getValueAt(selectedRowIndex, 0);
                    String productCode = (String) dataTable.getValueAt(selectedRowIndex, 1);
                    double productPrice = (double) dataTable.getValueAt(selectedRowIndex, 2);

                    // ִ���¶������߼�
                    placeOrder(productName, productCode, productPrice);
                } else {
                    JOptionPane.showMessageDialog(UserFrame.this, "����ѡ��Ҫ�¶�������Ʒ��");
                }
            }
        });
        topPanel.add(orderButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ��ѯ�����Ͱ�ť
        searchField = new JTextField();
        searchButton = new JButton("��ѯ");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                searchProduct(keyword);
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // �������ð�ť
        resetButton = new JButton("����");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        topPanel.add(resetButton);

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

        // ��Ʒ���
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // ��ҳ����
        JPanel paginationPanel = new JPanel();
        prevButton = new JButton("��һҳ");
        nextButton = new JButton("��һҳ");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    loadProductData();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < totalPage) {
                    currentPage++;
                    loadProductData();
                }
            }
        });
        pageInfoLabel = new JLabel();
        paginationPanel.add(prevButton);
        paginationPanel.add(pageInfoLabel);
        paginationPanel.add(nextButton);
        mainPanel.add(paginationPanel, BorderLayout.SOUTH);

        // ������Ʒ����
        loadProductData();
    }

    private void loadProductData() {
        // �������ݿ�
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ��ѯ�ܼ�¼��
            String countQuery = "SELECT COUNT(*) AS total FROM t_goods";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            ResultSet countResult = countStatement.executeQuery();
            if (countResult.next()) {
                int totalCount = countResult.getInt("total");
                totalPage = (int) Math.ceil((double) totalCount / pageSize);
            }

            // ��ѯ��Ʒ����
            int offset = (currentPage - 1) * pageSize;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchProduct(String keyword) {
        // �������ݿ�
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ��ѯ��Ʒ����
            String countQuery = "SELECT COUNT(*) AS total FROM t_goods WHERE goodsName LIKE ? OR goodsCode LIKE ?";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, "%" + keyword + "%");
            countStatement.setString(2, "%" + keyword + "%");
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int totalRecords = countResult.getInt("total");
            totalPage = (int) Math.ceil((double) totalRecords / pageSize);

            // ����ƫ����
            int offset = (currentPage - 1) * pageSize;

            // ��ѯ��Ʒ����
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePageInfo() {
        pageInfoLabel.setText("�� " + currentPage + " ҳ / �� " + totalPage + " ҳ");
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPage);
    }

    private void resetData() {
        loadProductData();
    }

    private void placeOrder(String productName, String productCode, double productPrice) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ��ȡ��ǰʱ��
            java.util.Date currentDate = new java.util.Date();
            java.sql.Timestamp orderTime = new java.sql.Timestamp(currentDate.getTime());

            // ���붩������
            String insertQuery = "INSERT INTO t_order (productName, productCode, Price, orderTime) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, productName);
            insertStatement.setString(2, productCode);
            insertStatement.setDouble(3, productPrice);
            insertStatement.setTimestamp(4, orderTime);
            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(UserFrame.this, "�¶����ɹ���\n��Ʒ���ƣ�" + productName +
                        "\n��Ʒ���룺" + productCode + "\n��Ʒ�۸�" + productPrice);
            } else {
                JOptionPane.showMessageDialog(UserFrame.this, "��������ʧ�ܣ�");
            }

            insertStatement.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserFrame userFrame = new UserFrame();
                userFrame.setVisible(true);
            }
        });
    }
}
