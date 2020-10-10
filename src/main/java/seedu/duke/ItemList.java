package seedu.duke;

import java.util.ArrayList;

public abstract class ItemList {

    private ArrayList<Object> items;

    /**
     * Constructs a itemList object with an empty ArrayList to store item objects.
     */
    public ItemList() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructs the itemList object containing an ArrayList to store item objects.
     * This constructor is used when loading items from a text file.
     *
     * @param itemStrings the list of strings of items.
     */
    public ItemList(ArrayList<String> itemStrings) {
        this.items = new ArrayList<>();
        // loadItemList(itemStrings);
    }

    /**
     * Returns the number of items in the list.
     *
     * @return the size of the item list.
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Returns item based on the index.
     *
     * @param index The index of the item in the list.
     * @return The item with the corresponding index in the list.
     */
    public Object getItem(int index) {
        return items.get(index);
    }

    /**
     * Returns the item list.
     *
     * @return The item list.
     */
    public ArrayList<Object> getItemList() {
        return items;
    }

    /**
     * This method deletes the item from the list.
     *
     * @param item The item to be deleted.
     */
    public void deleteItem(Object item) {
        items.remove(item);
    }   
}
