package goods;

import mysql.DatabaseConnection;
import user.AdminFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddGoodsFrame extends JFrame {
    private JTextField nameField;
    private JTextField barcodeField;
    private JTextField priceField;
    private JTextField quantityField;

    public AddGoodsFrame() {
        setTitle("添加商品");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // 商品名称
        JLabel nameLabel = new JLabel("名称:");
        nameField = new JTextField();
        nameField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(nameLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(nameField, constraints);

        // 商品条形码
        JLabel barcodeLabel = new JLabel("条形码:");
        barcodeField = new JTextField();
        barcodeField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(barcodeLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(barcodeField, constraints);

        // 商品价格
        JLabel priceLabel = new JLabel("价格:");
        priceField = new JTextField();
        priceField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(priceLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(priceField, constraints);

        // 商品数量
        JLabel quantityLabel = new JLabel("数量:");
        quantityField = new JTextField();
        quantityField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(quantityLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(quantityField, constraints);

        // 商品分类类型
        JLabel categoryLabel = new JLabel("分类类型:");
        String[] typeName = {"饮料类", "图书类", "零食类", "蔬菜类", "水果类"};
        JComboBox<String> categoryComboBox = new JComboBox<>(typeName);
        constraints.gridx = 0;
        constraints.gridy = 4;
        mainPanel.add(categoryLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(categoryComboBox, constraints);

        // 添加按钮
        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String barcode = barcodeField.getText();
                String priceText = priceField.getText();
                String quantityText = quantityField.getText();
                String typeName = (String) categoryComboBox.getSelectedItem();

                if (name.isEmpty() && barcode.isEmpty() && priceText.isEmpty() && quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "请填写商品全部信息！");
                    return;
                }else if ( name.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "请填写商品名称！");
                    return;
                }else if ( barcode.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "请填写商品条形码！");
                    return;
                }else if ( priceText.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "请填写商品价格！");
                    return;
                }else if ( quantityText.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "请填写商品数量！");
                    return;
                }

                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "商品价格格式不正确！");
                    return;
                }

                try {
                    quantity = Integer.parseInt(quantityText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "商品数量格式不正确！");
                    return;
                }

                addGoods(name, barcode, price, quantity, typeName);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addButton, constraints);

        setVisible(true);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminFrame().setVisible(true);
            }
        });
        constraints.gridx = 2;
        mainPanel.add(backButton, constraints);

        setVisible(true);
    }

    //添加商品
    private void addGoods(String name, String barcode, double price, int quantity, String typeName) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // 查询商品分类编码
            String typeQuery = "SELECT id FROM t_goodsType WHERE goodsTypeName = ?";
            PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
            typeStatement.setString(1, typeName);
            ResultSet typeResult = typeStatement.executeQuery();

            if (typeResult.next()) {
                int typeId = typeResult.getInt("id");

                // 添加商品信息到 t_goods 表
                String goodsQuery = "INSERT INTO t_goods (goodsName, goodsCode, price, count, goodsTypeId) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement goodsStatement = connection.prepareStatement(goodsQuery);
                goodsStatement.setString(1, name);
                goodsStatement.setString(2, barcode);
                goodsStatement.setDouble(3, price);
                goodsStatement.setInt(4, quantity);
                goodsStatement.setInt(5, typeId);
                goodsStatement.executeUpdate();

                goodsStatement.close();
            }

            typeResult.close();
            typeStatement.close();
            dbConnection.closeConnection();

            JOptionPane.showMessageDialog(this, "商品添加成功！");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "商品添加失败！");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddGoodsFrame();
            }
        });
    }
}
