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
        setTitle("�����Ʒ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // ��Ʒ����
        JLabel nameLabel = new JLabel("����:");
        nameField = new JTextField();
        nameField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(nameLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(nameField, constraints);

        // ��Ʒ������
        JLabel barcodeLabel = new JLabel("������:");
        barcodeField = new JTextField();
        barcodeField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(barcodeLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(barcodeField, constraints);

        // ��Ʒ�۸�
        JLabel priceLabel = new JLabel("�۸�:");
        priceField = new JTextField();
        priceField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(priceLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(priceField, constraints);

        // ��Ʒ����
        JLabel quantityLabel = new JLabel("����:");
        quantityField = new JTextField();
        quantityField.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(quantityLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(quantityField, constraints);

        // ��Ʒ��������
        JLabel categoryLabel = new JLabel("��������:");
        String[] typeName = {"������", "ͼ����", "��ʳ��", "�߲���", "ˮ����"};
        JComboBox<String> categoryComboBox = new JComboBox<>(typeName);
        constraints.gridx = 0;
        constraints.gridy = 4;
        mainPanel.add(categoryLabel, constraints);
        constraints.gridx = 1;
        mainPanel.add(categoryComboBox, constraints);

        // ��Ӱ�ť
        JButton addButton = new JButton("���");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String barcode = barcodeField.getText();
                String priceText = priceField.getText();
                String quantityText = quantityField.getText();
                String typeName = (String) categoryComboBox.getSelectedItem();

                if (name.isEmpty() && barcode.isEmpty() && priceText.isEmpty() && quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "����д��Ʒȫ����Ϣ��");
                    return;
                }else if ( name.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "����д��Ʒ���ƣ�");
                    return;
                }else if ( barcode.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "����д��Ʒ�����룡");
                    return;
                }else if ( priceText.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "����д��Ʒ�۸�");
                    return;
                }else if ( quantityText.isEmpty() ){
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "����д��Ʒ������");
                    return;
                }

                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "��Ʒ�۸��ʽ����ȷ��");
                    return;
                }

                try {
                    quantity = Integer.parseInt(quantityText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddGoodsFrame.this, "��Ʒ������ʽ����ȷ��");
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

        JButton backButton = new JButton("����");
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

    //�����Ʒ
    private void addGoods(String name, String barcode, double price, int quantity, String typeName) {
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ��ѯ��Ʒ�������
            String typeQuery = "SELECT id FROM t_goodsType WHERE goodsTypeName = ?";
            PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
            typeStatement.setString(1, typeName);
            ResultSet typeResult = typeStatement.executeQuery();

            if (typeResult.next()) {
                int typeId = typeResult.getInt("id");

                // �����Ʒ��Ϣ�� t_goods ��
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

            JOptionPane.showMessageDialog(this, "��Ʒ��ӳɹ���");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "��Ʒ���ʧ�ܣ�");
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
