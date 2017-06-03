package com.github.mxmlc.meuestoque.helper;

import android.widget.EditText;
import android.widget.TextView;

import com.github.mxmlc.meuestoque.AddItemActivity;
import com.github.mxmlc.meuestoque.R;
import com.github.mxmlc.meuestoque.model.Item;
import com.github.mxmlc.meuestoque.util.DateUtil;

/**
 * Classe auxiliar para manipular os dados em tela.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class ItemHelper {

    private EditText name;
    private EditText description;
    private EditText manufactureDate;
    private EditText validThru;
    private TextView quantity;

    private Item item;

    /**
     * Instancia o helper com base na activity.
     *
     * @param activity
     */
    public ItemHelper(AddItemActivity activity) {
        name = (EditText) activity.findViewById(R.id.input_name);
        description = (EditText) activity.findViewById(R.id.input_desc);
        manufactureDate = (EditText) activity.findViewById(R.id.input_manufacture_date);
        validThru = (EditText) activity.findViewById(R.id.input_valid_thru);
        quantity = (TextView) activity.findViewById(R.id.text_item_quantity);
        item = new Item();
    }

    /**
     * Retorna uma instância de item baseado nas informações da tela.
     *
     * @return
     */
    public Item getItem() {
        item.setName(name.getText().toString());
        item.setDescription(description.getText().toString());
        item.setManufactureDate(DateUtil.parseDate(manufactureDate.getText().toString()));
        item.setValidThru(DateUtil.parseDate(validThru.getText().toString()));
        item.setQuantity(Integer.valueOf(quantity.getText().toString()));
        return item;
    }

    /**
     * Preenche os campos da tela com base no item informado.
     *
     * @param item
     */
    public void fillForm(Item item) {
        name.setText(item.getName());
        description.setText(item.getDescription());
        manufactureDate.setText(DateUtil.formatDate(item.getManufactureDate()));
        validThru.setText(DateUtil.formatDate(item.getValidThru()));
        quantity.setText("" + item.getQuantity());
    }

}
