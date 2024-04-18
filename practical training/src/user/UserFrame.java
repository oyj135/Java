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
        setTitle("用户界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 下订单按钮
        JButton orderButton = new JButton("下订单");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = dataTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    String productName = (String) dataTable.getValueAt(selectedRowIndex, 0);
                    String productCode = (String) dataTable.getValueAt(selectedRowIndex, 1);
                    double productPrice = (double) dataTable.getValueAt(selectedRowIndex, 2);

                    // 执行下订单的逻辑
                    placeOrder(productName, productCode, productPrice);
                } else {
                    JOptionPane.showMessageDialog(UserFrame.this, "请先选择要下订单的商品！");
                }
            }
        });
        topPanel.add(orderButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 查询输入框和按钮
        searchField = new JTextField();
        searchButton = new JButton("查询");
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

        // 创建重置按钮
        resetButton = new JButton("重置");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        topPanel.add(resetButton);

        // 右上角菜单栏
        JComboBox<String> userMenu = new JComboBox<>();
        userMenu.addItem("修改密码");
        userMenu.addItem("退出登录");
        userMenu.addItem("退出系统");
        userMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMenu = (String) userMenu.getSelectedItem();
                if (selectedMenu.equals("修改密码")) {
                    dispose();
                    new ChangePassword().setVisible(true);
                } else if (selectedMenu.equals("退出登录")) {
                    dispose();
                    new Login().setVisible(true);
                } else if (selectedMenu.equals("退出系统")) {
                    dispose();
                }
            }
        });
        topPanel.add(userMenu);

        // 商品表格
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 分页导航
        JPanel paginationPanel = new JPanel();
        prevButton = new JButton("上一页");
        nextButton = new JButton("下一页");
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

        // 加载商品数据
        loadProductData();
    }

    private void loadProductData() {
        // 连接数据库
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // 查询总记录数
            String countQuery = "SELECT COUNT(*) AS total FROM t_goods";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            ResultSet countResult = countStatement.executeQuery();
            if (countResult.next()) {
                int totalCount = countResult.getInt("total");
                totalPage = (int) Math.ceil((double) totalCount / pageSize);
            }

            // 查询商品数据
            int offset = (currentPage - 1) * pageSize;
            String query = "SELECT g.goodsName, g.goodsCode, g.price, g.count, g.goodsTypeId, gt.goodsTypeName " +
                    "FROM t_goods g " +
                    "INNER JOIN t_goodsType gt ON g.goodsTypeId = gt.id " +
                    "LIMIT ?, ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, offset);
            statement.setInt(2, pageSize);
            ResultSet result = statement.executeQuery();

            // 创建表格模型
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("名称");
            model.addColumn("条形码");
            model.addColumn("价格");
            model.addColumn("数量");
            model.addColumn("分类编码");
            model.addColumn("分类名称");


            // 填充表格模型
            while (result.next()) {
                String name = result.getString("goodsName");
                String barcode = result.getString("goodsCode");
                double price = result.getDouble("price");
                int quantity = result.getInt("count");
                String categoryCode = result.getString("goodsTypeId");
                String goodsTypeName = result.getString("goodsTypeName");
                model.addRow(new Object[]{name, barcode, price, quantity, categoryCode, goodsTypeName});
            }

            // 设置表格模型
            dataTable.setModel(model);

            // 更新分页信息
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
        // 连接数据库
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // 查询商品总数
            String countQuery = "SELECT COUNT(*) AS total FROM t_goods WHERE goodsName LIKE ? OR goodsCode LIKE ?";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, "%" + keyword + "%");
            countStatement.setString(2, "%" + keyword + "%");
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int totalRecords = countResult.getInt("total");
            totalPage = (int) Math.ceil((double) totalRecords / pageSize);

            // 计算偏移量
            int offset = (currentPage - 1) * pageSize;

            // 查询商品数据
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

            // 创建表格模型
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("名称");
            model.addColumn("条形码");
            model.addColumn("价格");
            model.addColumn("数量");
            model.addColumn("分类编码");
            model.addColumn("分类名称");

            // 填充表格模型
            while (result.next()) {
                String name = result.getString("goodsName");
                String barcode = result.getString("goodsCode");
                double price = result.getDouble("price");
                int quantity = result.getInt("count");
                String categoryCode = result.getString("goodsTypeId");
                String goodsTypeName = result.getString("goodsTypeName");
                model.addRow(new Object[]{name, barcode, price, quantity, categoryCode, goodsTypeName});
            }

            // 设置表格模型
            dataTable.setModel(model);

            // 更新分页信息
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
        pageInfoLabel.setText("第 " + currentPage + " 页 / 共 " + totalPage + " 页");
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

            // 获取当前时间
            java.util.Date currentDate = new java.util.Date();
            java.sql.Timestamp orderTime = new java.sql.Timestamp(currentDate.getTime());

            // 插入订单数据
            String insertQuery = "INSERT INTO t_order (productName, productCode, Price, orderTime) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, productName);
            insertStatement.setString(2, productCode);
            insertStatement.setDouble(3, productPrice);
            insertStatement.setTimestamp(4, orderTime);
            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(UserFrame.this, "下订单成功！\n商品名称：" + productName +
                        "\n商品编码：" + productCode + "\n商品价格：" + productPrice);
            } else {
                JOptionPane.showMessageDialog(UserFrame.this, "订单插入失败！");
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
