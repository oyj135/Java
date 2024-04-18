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
        setTitle("管理员界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // 顶部菜单栏
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 左上角菜单栏
        JComboBox<String> managementMenu = new JComboBox<>();
        managementMenu.addItem("员工管理");
        managementMenu.addItem("订单管理");
        managementMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMenu = (String) managementMenu.getSelectedItem();
                if (selectedMenu.equals("员工管理")) {
                    dispose();
                    try {
                        new EmployeeManagementUI().setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selectedMenu.equals("订单管理")) {
                    new OrderManagementFrame().setVisible(true);
                }
            }
        });
        topPanel.add(managementMenu);

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

        // 商品查询面板
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // 查询文本框
        JLabel searchLabel = new JLabel("商品查询:");
        searchField = new JTextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // 查询按钮
        JButton searchButton = new JButton("查询");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                searchProduct(keyword);
            }
        });
        searchPanel.add(searchButton);

        // 创建重置按钮
        resetButton = new JButton("重置");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetData();
            }
        });
        searchPanel.add(resetButton);

        // 商品数据表格
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 底部分页栏
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 分页信息
        pageInfoLabel = new JLabel();
        bottomPanel.add(pageInfoLabel);

        // 上一页按钮
        prevButton = new JButton("上一页");
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

        // 下一页按钮
        nextButton = new JButton("下一页");
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

        // 添加商品按钮
        JButton addButton = new JButton("添加商品");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddGoodsFrame().setVisible(true);
            }
        });
        bottomPanel.add(addButton);

        // 删除商品按钮
        JButton deleteButton = new JButton("删除商品");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行删除商品的逻辑
                deleteSelectedProduct();
            }
        });
        bottomPanel.add(deleteButton);

        // 修改商品按钮
        JButton updateButton = new JButton("修改商品");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 执行修改商品的逻辑
                updateSelectedProduct();
            }
        });
        bottomPanel.add(updateButton);

        loadProductData();
    }

    // 加载商品数据
    public void loadProductData() {
        try {
            // 连接数据库并执行查询操作
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
            // 展示商品数据
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
                model.addRow(new Object[]{name, barcode, price, quantity, categoryCode,goodsTypeName});
            }

            dataTable.setModel(model);
            statement.close();
            dbConnection.closeConnection();
            updatePageInfo();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "发生数据库错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 更新分页按钮状态
    private void updatePageInfo() {
        pageInfoLabel.setText("第 " + currentPage + " 页 / 共 " + totalPage + " 页");
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPage);
    }

    // 根据关键字搜索商品
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

            //计算偏移量
            int offset = (currentPage - 1) * pageSize;

            //查询商品数据
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "发生数据库错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 重置数据
    private void resetData() {
        searchField.setText("");
        currentPage = 1;
        loadProductData();
    }

    // 删除商品
    private void deleteSelectedProduct() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的商品！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String productName = (String) dataTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除商品：" + productName + " 吗？", "确认删除",
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
                    JOptionPane.showMessageDialog(this, "商品删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    loadProductData();
                } else {
                    JOptionPane.showMessageDialog(this, "商品删除失败！", "失败", JOptionPane.ERROR_MESSAGE);
                }

                statement.close();
                dbConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "发生数据库错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 修改商品
    private void updateSelectedProduct() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要修改的商品", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String productName = (String) dataTable.getValueAt(selectedRow, 0);
        String productBarcode = (String) dataTable.getValueAt(selectedRow, 1);
        double productPrice = (double) dataTable.getValueAt(selectedRow, 2);
        int productQuantity = (int) dataTable.getValueAt(selectedRow, 3);
        String goodsTypeIdStr = (String) dataTable.getValueAt(selectedRow, 4);
        int goodsTypeId = Integer.parseInt(goodsTypeIdStr);

        String newProductName = JOptionPane.showInputDialog(this, "请输入新的商品名称", productName);
        if (newProductName == null || newProductName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "商品名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newProductBarcode;
        double newProductPrice;
        int newProductQuantity;
        String newGoodsTypeName;

        newProductBarcode = JOptionPane.showInputDialog(this, "请输入新的商品条形码", productBarcode);
        if (newProductBarcode == null || newProductBarcode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "商品条形码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        while (true) {
            try {
                String priceStr = JOptionPane.showInputDialog(this, "请输入新的商品价格", productPrice);
                if (priceStr == null || priceStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "商品价格不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                newProductPrice = Double.parseDouble(priceStr);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "商品价格必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

        while (true) {
            try {
                String quantityStr = JOptionPane.showInputDialog(this, "请输入新的商品数量", productQuantity);
                if (quantityStr == null || quantityStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "商品数量不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                newProductQuantity = Integer.parseInt(quantityStr);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "商品数量必须为整数", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

        // 创建下拉框选项
        String[] goodsTypes = {"饮料类", "图书类", "零食类", "水果类", "蔬菜类"};
        JComboBox<String> comboBox = new JComboBox<>(goodsTypes);

        // 显示包含下拉框的对话框
        int option = JOptionPane.showOptionDialog(
                this,                   // 父组件
                comboBox,               // 下拉框
                "请选择新的商品分类类型名称", // 对话框标题
                JOptionPane.OK_CANCEL_OPTION, // 选项类型
                JOptionPane.QUESTION_MESSAGE, // 对话框类型
                null, null, null);       // 其他参数

        // 用户点击了"确定"按钮
        if (option == JOptionPane.OK_OPTION) {
            newGoodsTypeName = (String) comboBox.getSelectedItem();
            if (newGoodsTypeName == null || newGoodsTypeName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "商品分类类型名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                // 更新商品分类类型名称
                String updateGoodsTypeQuery = "UPDATE t_goodsType SET goodsTypeName = ? WHERE id = ?";
                PreparedStatement updateGoodsTypeStatement = connection.prepareStatement(updateGoodsTypeQuery);
                updateGoodsTypeStatement.setString(1, newGoodsTypeName);
                updateGoodsTypeStatement.setInt(2, goodsTypeId);
                updateGoodsTypeStatement.executeUpdate();
                updateGoodsTypeStatement.close();

                // 更新商品信息
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

                JOptionPane.showMessageDialog(this, "商品修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "修改商品失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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
