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
        setTitle("订单管理");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // 数据表格
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 删除按钮
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedData();
                JOptionPane.showMessageDialog(OrderManagementFrame.this, "删除成功！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 删除按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(deleteButton);

        // 将删除按钮面板添加到主面板的南部（底部）
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 创建表格模型
        model = new DefaultTableModel();
        model.addColumn("商品名称");
        model.addColumn("条形码");
        model.addColumn("价格");
        model.addColumn("时间");
        dataTable.setModel(model);

        // 加载订单数据
        loadOrderData();
    }

    private void loadOrderData() {
        // 连接数据库
        try {
            //获取数据库链接
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // 查询订单数据
            String query = "SELECT productName, productCode, price, orderTime FROM t_order";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            // 清空表格数据
            model.setRowCount(0);

            // 填充表格模型
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
        if (selectedRow != -1) { // 如果有选择行
            try {
                // 获取数据库连接
                DatabaseConnection dbConnection = new DatabaseConnection();
                Connection connection = dbConnection.getConnection();

                // 获取选中行的数据
                String productName = (String) model.getValueAt(selectedRow, 0);
                String productCode = (String) model.getValueAt(selectedRow, 1);

                // 构建删除数据的SQL语句
                String deleteQuery = "DELETE FROM t_order WHERE productName = ? AND productCode = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, productName);
                deleteStatement.setString(2, productCode);

                // 执行删除操作
                deleteStatement.executeUpdate();

                // 刷新数据表格
                loadOrderData();

                // 关闭连接和语句
                deleteStatement.close();
                dbConnection.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请选择要删除的数据行。", "提示", JOptionPane.WARNING_MESSAGE);
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
