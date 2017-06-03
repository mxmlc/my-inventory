package com.github.mxmlc.meuestoque;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mxmlc.meuestoque.dao.ItemDAO;
import com.github.mxmlc.meuestoque.model.Item;

import java.util.Date;
import java.util.List;

/**
 * Atividade de listagem do estoque de itens.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class ListInventoryActivity extends AppCompatActivity {

    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inventory);
        addToolbar();
        addFloatingButton();
        addActions();
    }

    /**
     * Adiciona a toolbar à tela.
     */
    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Adiciona o botão flutuante.
     */
    private void addFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListInventoryActivity.this, AddItemActivity.class);
                startActivity(intent);

                // if you want to create a list of actions, use the code below
                //Snackbar.make(view, R.string.action_add_item, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
    }

    /**
     * Adiciona as ações à tela.
     */
    private void addActions() {
        itemsList = (ListView) findViewById(R.id.items_list);
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) itemsList.getItemAtPosition(position);
                Intent intent = new Intent(ListInventoryActivity.this, AddItemActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra(Item.TABLE_NAME, item);
                startActivity(intent);
            }
        });
    }

    /**
     * Realiza a busca dos itens cadastrados.
     *
     * @param overdue Indica que tipo de busca deve ser realizada:
     *                - Item.OVERDUE.INDIFERENT: Todos os itens;
     *                - Item.OVERDUE.OVERDUE: Itens vencidos;
     *                - Item.OVERDUE.CLOSE_OVERDUE: Itens a vencer em 60 dias.
     */
    private void fetchItems(Item.OVERDUE_IN overdue) {
        ItemDAO dao = new ItemDAO(this);
        List<Item> items;
        if (overdue == Item.OVERDUE_IN.OVERDUE) {
            items = dao.fetchItemsOverdue();
        } else if (overdue == Item.OVERDUE_IN.CLOSE_OVERDUE) {
            items = dao.fetchItemsCloseOverdue();
        } else {
            items = dao.fetchAll();
        }
        dao.close();
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(ListInventoryActivity.this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchItems(Item.OVERDUE_IN.INDIFERENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_inventory, menu);
        return true;
    }

    /**
     * Ao selecionar um item no menu suspenso, realiza o filtro de acordo.
     *
     * @param item Item selecionado no menu suspenso.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_all_items:
                fetchItems(Item.OVERDUE_IN.INDIFERENT);
                return true;
            case R.id.action_overdue_items:
                fetchItems(Item.OVERDUE_IN.OVERDUE);
                return true;
            case R.id.action_close_overdue_items:
                fetchItems(Item.OVERDUE_IN.CLOSE_OVERDUE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
