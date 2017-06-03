package com.github.mxmlc.meuestoque.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.github.mxmlc.meuestoque.model.Item;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para controle dos itens cadastrados.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class ItemDAO extends SQLiteOpenHelper {

    public ItemDAO(Context context) {
        super(context, "inventory.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ")
                .append(Item.TABLE_NAME).append("(")
                .append(Item.ID_COLUMN).append(" INTEGER PRIMARY KEY, ")
                .append(Item.NAME_COLUMN).append(" TEXT NOT NULL, ")
                .append(Item.DESC_COLUMN).append(" TEXT, ")
                .append(Item.MANUFACTURE_COLUMN).append(" INTEGER NOT NULL, ")
                .append(Item.VALID_THRU_COLUMN).append(" INTEGER NOT NULL, ")
                .append(Item.QUANTITY_COLUMN).append(" INTEGER);");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + Item.TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * Persiste o item no banco.
     *
     * @param item
     */
    public void persist(Item item) {
        getWritableDatabase().insert(Item.TABLE_NAME, null, getItemData(item));
    }

    /**
     * Realiza a busca de todos os itens.
     *
     * @return
     */
    public List<Item> fetchAll() {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Item.TABLE_NAME)
                .append(" ORDER BY ").append(Item.VALID_THRU_COLUMN);
        return fetch(sql.toString());
    }

    /**
     * Realiza a busca somente dos itens vencidos.
     *
     * @return
     */
    public List<Item> fetchItemsOverdue() {
        Calendar cal = Calendar.getInstance();
        long start = cal.getTimeInMillis();
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Item.TABLE_NAME)
                .append(" WHERE ").append(Item.VALID_THRU_COLUMN).append(" < ").append(start)
                .append(" ORDER BY ").append(Item.VALID_THRU_COLUMN);
        return fetch(sql.toString());
    }

    /**
     * Realiza a busca somente dos itens a vencer nos próximos 30 dias.
     *
     * @return
     */
    public List<Item> fetchItemsCloseOverdue() {
        Calendar cal = Calendar.getInstance();
        long start = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, 1);
        long end = cal.getTimeInMillis();
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Item.TABLE_NAME)
                .append(" WHERE ").append(Item.VALID_THRU_COLUMN)
                .append(" BETWEEN ").append(start)
                .append(" AND ").append(end)
                .append(" ORDER BY ").append(Item.VALID_THRU_COLUMN);
        return fetch(sql.toString());
    }

    /**
     * Realiza a busca dos itens de acordo com o SQL.
     *
     * @param sql
     * @return
     */
    private List<Item> fetch(String sql) {
        Cursor c = getWritableDatabase().rawQuery(sql, null);
        List<Item> items = new ArrayList<>();
        while (c.moveToNext()) {
            Item item = new Item();
            item.setId(c.getLong(c.getColumnIndex(Item.ID_COLUMN)));
            item.setName(c.getString(c.getColumnIndex(Item.NAME_COLUMN)));
            item.setDescription(c.getString(c.getColumnIndex(Item.DESC_COLUMN)));
            item.setManufactureDate(new Date(c.getLong(c.getColumnIndex(Item.MANUFACTURE_COLUMN))));
            item.setValidThru(new Date(c.getLong(c.getColumnIndex(Item.VALID_THRU_COLUMN))));
            item.setQuantity(c.getInt(c.getColumnIndex(Item.QUANTITY_COLUMN)));
            items.add(item);
        }
        return items;
    }

    /**
     * Atualiza um item cadastrado.
     *
     * @param item
     */
    public void update(Item item) {
        String[] params = { item.getId().toString() };
        getWritableDatabase().update(Item.TABLE_NAME, getItemData(item), "id = ?", params);
    }

    /**
     * Remove um item.
     *
     * @param item
     */
    public void remove(Item item) {
        String[] params = { item.getId().toString() };
        getWritableDatabase().delete(Item.TABLE_NAME, "id = ?", params);
    }

    /**
     * Retorna um {@link ContentValues} baseado no item informado.
     *
     * @param item
     * @return
     */
    @NonNull
    private ContentValues getItemData(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(Item.NAME_COLUMN, item.getName());
        cv.put(Item.DESC_COLUMN, item.getDescription());
        cv.put(Item.MANUFACTURE_COLUMN, item.getManufactureDate().getTime());
        cv.put(Item.VALID_THRU_COLUMN, item.getValidThru().getTime());
        cv.put(Item.QUANTITY_COLUMN, item.getQuantity());
        return cv;
    }
}
