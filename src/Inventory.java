import java.util.ArrayList;
import java.util.Optional;

public class Inventory {
    private ArrayList<ItemInterface> stock;
    public enum searchTypes {
        ALL,
        NAME,
        DESC,
        WGHTASC,
        WGHTDESC
    }
    private searchTypes searchBy;

    public Inventory() {
        stock = new ArrayList<>();
        searchBy = searchTypes.ALL;
    }

    public Inventory(ArrayList<ItemInterface> startingStock) {
        stock = startingStock;
        searchBy = searchTypes.ALL;
    }

    /**
     * Removes and returns the first Item instance that matches the
     * provided 'itemDefinition'.
     * Throws an ItemNotAvailableException if the `item` is not present in the inventory.
     * @param itemDefinition
     * @return Item instance matching `item` parameter definition
     * @throws ItemNotAvailableException
     */
    public ItemInterface removeOne(ItemDefinition itemDefinition) throws ItemNotAvailableException {
        Optional<Integer> removeFromIdx = indexOfItemByName(itemDefinition);
        if (removeFromIdx.isEmpty()) {
            throw new ItemNotAvailableException(itemDefinition);
        }

        return stock.remove((int) removeFromIdx.get());
    }

    public ItemInterface remove(ItemInterface item) throws ItemNotAvailableException {
        // Check if the provided item exists in the players inventory
        Optional<Integer> removeFromIdx = Optional.empty();
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i) == item) {
                removeFromIdx = Optional.of(i);
                break;
            }
        }
        if (removeFromIdx.isEmpty()) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        return stock.remove(removeFromIdx.get().intValue());
    }

    /**
     * Adds an Item instance to the inventories stock.
     * Sort is called using the current/existing sort strategy.
     * @param item - actual instance
     */
    public void addOne(ItemInterface item) {
        stock.add(item);
    }

    /**
     * Search for `item` in the inventory stock.
     * @param item definition
     * @return index of `item` or empty optional if `item` not in stock
     */
    private Optional<Integer> indexOfItemByName(ItemDefinition item) {
        for (int i = 0; i < stock.size(); i++) {
            ItemInterface cur = stock.get(i);
            if (cur.getName().equals(item.getName())) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void setSearch(searchTypes search) {
        // changed to use the enum search criteria
        searchBy = search;
    }

    /**
     * Search for items using the current search criteria in the Inventory.
     * An instance copy is made, such that the items that the inventory is not
     * lost when removed from the resulting ArrayList.
     * @param searchTerm - Text from the UIs textfield
     * @return a filtered instance copy of the items arraylist
     * 
     * Modified to use an enumerator to store all the different search
     * criteria. App.java has been modified accordingly.
     */
    public ArrayList<ItemInterface> searchItems(String searchTerm) {
        String term = searchTerm.toLowerCase();
        ArrayList<ItemInterface> result = new ArrayList<>(stock);  // ArrayList copy
        switch (searchBy) { // select which method to sort with based on the enumerator
            case ALL:
                for (int i = 0; i < result.size(); i++) {
                    ItemInterface curItem = result.get(i);
                    if (!curItem.getName().contains(term) && !curItem.getDescription().contains(term)) {
                        result.remove(i);
                        i--;  // Go back to revisit current index on next run of loop
                    }
                }
                break;
            case NAME:
                for (int i = 0; i < result.size(); i++) {
                    ItemInterface curItem = result.get(i);
                    if (!curItem.getName().contains(term)) {
                        result.remove(i);
                        i--;  // Go back to revisit current index on next run of loop
                    }
                }
                break;
            case DESC:
                for (int i = 0; i < result.size(); i++) {
                    ItemInterface curItem = result.get(i);
                    if (!curItem.getDescription().contains(term)) {
                        result.remove(i);
                        i--;  // Go back to revisit current index on next run of loop
                    }
                }
                break;
            case WGHTASC:
                if (!term.isEmpty()) { // if its not just an empty string or spaces
                    double wght = 0;
                    try { // try and get the weight from the term
                        wght = Double.parseDouble(term); 
                    }  // if it doesn't work display no results
                    catch (NumberFormatException e) {
                        result.clear();
                        break;
                    }
                    for (int i = 0; i < result.size(); i++) {
                        ItemInterface curItem = result.get(i);
                        if (curItem.getWeight() < wght) {
                            result.remove(i); // remove the item if its less than the specified weight
                            i--;  
                        }
                    }
                }
                break;
            case WGHTDESC:
                if (!term.isEmpty()) { 
                    double wght = 0;
                    try {
                        wght = Double.parseDouble(term);
                    } // this is all the same as before
                    catch (NumberFormatException e) {
                        result.clear();
                        break;
                    }
                    for (int i = 0; i < result.size(); i++) {
                        ItemInterface curItem = result.get(i);
                        if (curItem.getWeight() >= wght) {
                            result.remove(i); // remove if its greater than or equal to the given weight
                            i--;  
                        }
                    }
                }
                break;
            default:
                break; // if no enum selected (impossible) do nothing
        }
        return result;
    }

    public int qtyOf(ItemDefinition def) {
        int qty = 0;
        for (ItemInterface item : stock) {
            if (item.getName().equals(def.getName())) {
                qty++;
            }
        }
        return qty;
    }

    @Override
    public String toString() {
        String str = "";
        for (ItemInterface item : stock) {
            str += item.toString() + "\n\n";
        }
        return str;
    }
}
