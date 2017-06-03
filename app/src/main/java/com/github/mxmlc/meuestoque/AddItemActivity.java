package com.github.mxmlc.meuestoque;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mxmlc.meuestoque.dao.ItemDAO;
import com.github.mxmlc.meuestoque.fragment.DatePickerFragment;
import com.github.mxmlc.meuestoque.helper.ItemHelper;
import com.github.mxmlc.meuestoque.model.Item;

/**
 * Atividade para adicionar um item ao estoque.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class AddItemActivity extends AppCompatActivity {

    private ItemHelper helper;
    private Item item;

    private int itemQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setTitle(R.string.add_item);

        helper = new ItemHelper(this);

        item = (Item) getIntent().getSerializableExtra(Item.TABLE_NAME);
        if (item != null) {
            helper.fillForm(item);
        }
    }

    /**
     * Direciona a aplicação para a tela de leitura de código de barras.
     *
     * @param v
     */
    public void callBarcodeScanner(View v) {
        Intent intent = new Intent(AddItemActivity.this, BarcodeScannerActivity.class);
        startActivity(intent);
    }

    /**
     * Apresenta a caixa de diálogo de seleção de data.
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setEditText(v);
        fragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * Incrementa a quantidade de itens.
     *
     * @param v
     */
    public void increment(View v) {
        TextView qtt = (TextView) findViewById(R.id.text_item_quantity);
        qtt.setText(String.valueOf(++itemQuantity));
    }

    /**
     * Decrementa a quantidade de itens.
     *
     * @param v
     */
    public void decrement(View v) {
        TextView qtt = (TextView) findViewById(R.id.text_item_quantity);
        qtt.setText(String.valueOf(--itemQuantity));
    }

    /**
     * Adiciona o item à tabela.
     *
     * @param v
     */
    public void addItem(View v) {
        ItemDAO dao = new ItemDAO(AddItemActivity.this);
        if (item == null) {
            dao.persist(helper.getItem());
        } else {
            Item localItem = helper.getItem();
            localItem.setId(item.getId());
            dao.update(localItem);
        }
        dao.close();
        Toast.makeText(AddItemActivity.this, "Item " + item.getName() + " cadastrado com sucesso!", Toast.LENGTH_SHORT);
        finish();
    }

}
