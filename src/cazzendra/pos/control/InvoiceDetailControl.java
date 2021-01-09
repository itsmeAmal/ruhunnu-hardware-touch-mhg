/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.control;

import cazzendra.pos.core.MethodStatus;
import cazzendra.pos.core.Validations;
import cazzendra.pos.daoImpl.InvoiceDetailDaoImpl;
import cazzendra.pos.model.InvoiceDetail;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class InvoiceDetailControl {

    public static MethodStatus addInvoiceDetail(int invoiceId, String itemId,
            String qty, String unit, String mrp, String sellingPrice, String discountRate, String purchasePrice) throws SQLException {
        InvoiceDetailDaoImpl detailDaoImpl = new InvoiceDetailDaoImpl();
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setId(detailDaoImpl.getNextInvoiceDetailId());
        invoiceDetail.setInvoiceId(invoiceId);
        invoiceDetail.setItemId(Validations.getIntOrZeroFromString(itemId));
        invoiceDetail.setItemQty(Validations.getBigDecimalOrZeroFromString(qty));
        invoiceDetail.setUnit(unit);
        invoiceDetail.setMRP(Validations.getBigDecimalOrZeroFromString(mrp));
        invoiceDetail.setSellingPrice(Validations.getBigDecimalOrZeroFromString(sellingPrice));
        invoiceDetail.setDiscountRate(Validations.getBigDecimalOrZeroFromString(discountRate));
        invoiceDetail.setPurchasePrice(Validations.getBigDecimalOrZeroFromString(purchasePrice));
        return detailDaoImpl.addInvoiceDetail(invoiceDetail);
    }

//    public static 
}
