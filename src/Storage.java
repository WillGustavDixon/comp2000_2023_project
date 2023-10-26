import java.util.ArrayList;

public class Storage {
    private String storageName;
    private Inventory items;
    private ArrayList<Player> players = new ArrayList<>();

    public Storage(String name, Inventory startingInventory) {
        storageName = name;
        items = startingInventory;
    }

    // if a player clicked on the storage chest, they would be added to the
    // observer list
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    // once they clicked out of it, they would be removed from the list.
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // whenever a change is made to the storage box (something is stored or
    // retrieved) update all players about it.
    public void setStorage() {
        for (Player player : players) {
            player.update(items);
        }
    } 

    public Inventory getInventory() {
        return items;
    }

    public String getName() {
        return storageName;
    }
    
    public void store(ItemInterface item) {
        items.addOne(item);
        setStorage();
    }

    public ItemInterface retrieve(ItemInterface item) throws ItemNotAvailableException {
        ItemInterface removed = items.remove(item);
        setStorage();
        return removed;
    }
    
}
