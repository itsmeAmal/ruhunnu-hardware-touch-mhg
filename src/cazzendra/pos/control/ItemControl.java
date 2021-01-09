/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.ItemDaoImpl;
import cazzendra.pos.model.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class ItemControl {

    public static MethodStatus addItem(String itemName, String itemCode,
            String itemBarcode, String reorderLevel, String sellingPrice,
            String category, String subCategory) throws SQLException {

        Item item = new Item();
        ItemDaoImpl daoImpl = new ItemDaoImpl();
        item.setItemId(daoImpl.getNextItemId());
        item.setItemName(itemName);
        item.setItemCode(itemCode);
        item.setBarcode(itemBarcode);
        item.setReOrderLevel(Validations.getIntOrZeroFromString(reorderLevel));
        item.setSellingPrice(Validations.getBigDecimalOrZeroFromString(sellingPrice));
        item.setCategory(category);
        item.setSubCategory(subCategory);
        return daoImpl.addItem(item);
    }

    public static ResultSet getAllItems() throws SQLException {
        return new ItemDaoImpl().getAllItems();
    }

    public static ResultSet getItemByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new ItemDaoImpl().getItemByOneAttribute(attribute, condition, value);
    }

    public static Item getItemByItemId(int id) throws SQLException {
        Item item = null;
        ResultSet rset = getItemByOneAttribute("item_id", CommonConstants.sql.LIKE, Integer.toString(id));
        while (rset.next()) {
            item = new Item();
            item.setItemId(rset.getInt("item_id"));
            item.setItemName(rset.getString("item_name"));
            item.setBarcode(rset.getString("item_barcode"));
            item.setCategory(rset.getString("item_category"));
            item.setSubCategory(rset.getString("item_sub_category"));
            item.setItemCode(rset.getString("item_code"));
            item.setReOrderLevel(rset.getInt("item_reorder_level"));
            item.setSellingPrice(rset.getBigDecimal("item_selling_price"));
            item.setPurchasePrice(rset.getBigDecimal("item_purchasing_price"));
        }
        return item;
    }

    public static Item getItemByItemBarcodeOrItemCode(String searchAttribute) throws SQLException {
        Item item = null;

        ArrayList<String[]> attributeConditionValueList = new ArrayList<>();

        String[] acv1 = {"item_barcode", CommonConstants.sql.EQUAL, searchAttribute};
        attributeConditionValueList.add(acv1);

        String[] acv2 = {"item_code", CommonConstants.sql.EQUAL, searchAttribute};
        attributeConditionValueList.add(acv2);

        ResultSet rset = getItemsByMoreAttributes(attributeConditionValueList, CommonConstants.sql.OR);

        if (rset.next()) {
            item = new Item();
            item.setItemId(rset.getInt("item_id"));
            item.setItemName(rset.getString("item_name"));
            item.setBarcode(rset.getString("item_barcode"));
            item.setCategory(rset.getString("item_category"));
            item.setSubCategory(rset.getString("item_sub_category"));
            item.setItemCode(rset.getString("item_code"));
            item.setReOrderLevel(rset.getInt("item_reorder_level"));
            item.setSellingPrice(rset.getBigDecimal("item_selling_price"));
            item.setPurchasePrice(rset.getBigDecimal("item_purchasing_price"));
        }
        return item;
    }

    public static Item getItemByResultset(ResultSet rset) throws SQLException {
        Item item = null;
        if (rset.next()) {
            item = new Item();
            item.setItemId(rset.getInt("item_id"));
            item.setItemName(rset.getString("item_name"));
            item.setBarcode(rset.getString("item_barcode"));
            item.setCategory(rset.getString("item_category"));
            item.setSubCategory(rset.getString("item_sub_category"));
            item.setItemCode(rset.getString("item_code"));
            item.setReOrderLevel(rset.getInt("item_reorder_level"));
            item.setSellingPrice(rset.getBigDecimal("item_selling_price"));
            item.setPurchasePrice(rset.getBigDecimal("item_purchasing_price"));
        }
        return item;
    }

    public static void addRowToInvoiceTable(JTable table, String itemId,
            String itemName, String itemCode, String retailPrice,
            String sellingPrice, String qty, String unit, String total,
            String discountRate, String mrp) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        Object[] obj = {itemId, itemCode, itemName, retailPrice, discountRate, sellingPrice, qty, unit, total, mrp};
        dtm.addRow(obj);
    }

    public static ResultSet searchItem(String attribute, String searchValue) throws SQLException {
        return getItemByOneAttribute(attribute, CommonConstants.sql.LIKE, searchValue + "%");
    }

    public static MethodStatus updateItem(int itemId, String itemName, String genericName, String reOrderLevel, String CategoryName) throws SQLException {
        Item item = new Item();
        item.setItemId(itemId);
        item.setBarcode(itemName);
        item.setItemName(genericName);
        item.setReOrderLevel(Validations.getIntOrZeroFromString(reOrderLevel));
        item.setCategory(CategoryName); 
        return new ItemDaoImpl().updateItem(item);
    }

    public static boolean isExistingItemCode(String itemCode) throws SQLException {
        boolean status = false;
        ResultSet rset = getItemByOneAttribute("item_code", CommonConstants.sql.EQUAL, itemCode);
        if (!rset.next()) {
            status = true;
        }
        return status;
    }

    public static MethodStatus updateItemPrice(String retailPrice, int itemId) throws SQLException {
        return new ItemDaoImpl().updateItemPrice(retailPrice, itemId);
    }

    public static ResultSet getItemsByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new ItemDaoImpl().getItemsByMoreAttributes(attributeConditionValueList, operator);
    }
}
