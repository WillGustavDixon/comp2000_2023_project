import java.util.ArrayList;

public class Player {
    private String name;
    private Inventory inventory;
    private double carryWeightCapacity;
    private Inventory storageView;

    public Player(String playerName, double carryCapacity, Inventory sInventory) {
        name = playerName;
        carryWeightCapacity = carryCapacity;
        inventory = sInventory;
    }

    public void update(Inventory storage) {
        setStorageView(storage);
    }

    public void setStorageView(Inventory storageInventory) {
        storageView = storageInventory;
    }

    public Inventory getStorageView() {
        return storageView;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getCarryCapacity() {
        return carryWeightCapacity;
    }

    public double getCurrentWeight() {
        double carrying = 0;
        for (ItemInterface item : getInventory().searchItems("")) {
            carrying += item.getWeight();
        }
        return carrying;
    }

    public void store(ItemInterface item, Storage storage) throws ItemNotAvailableException {
        // Do we have the item we are trying to store
        if (!inventory.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        storage.store(inventory.remove(item));
    }

    public void retrieve(ItemInterface item, Storage storage) throws ItemNotAvailableException, ExceedWeightCapacity {
        // Does the Storage have the item we are trying to retrieve
        if (!storageView.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        if (getCurrentWeight() + item.getWeight() > getCarryCapacity()) {
            throw new ExceedWeightCapacity(this, item);
        }
        inventory.addOne(storage.retrieve(item));
    }
    
    // removes the first found instance of each crafting material used (using names)
    // then creates the crafted item object and adds it to the inventory
    public void craftItem(ItemDefinition crafted) throws ItemNotAvailableException {
        ItemInterface[] bases = crafted.returnBaseItems();
        CraftedItem craftItem = crafted.createCrafted();

        ArrayList<ItemInterface> inv = inventory.searchItems("");
        ArrayList<String> itemNames = new ArrayList<String>(inv.size());
        for (int i = 0; i < inv.size(); i++) {
            itemNames.add(inv.get(i).getName());
        }

        for (int i = 0; i < bases.length; i++) {
            if (!itemNames.contains(bases[i].getName())) {
                throw new ItemNotAvailableException(bases[i].getDefinition());
            }
        }
        for (int j = 0; j < bases.length; j++) {
            inventory.removeOne(bases[j].getDefinition());
        }

        inventory.addOne(craftItem);
    }

    // first it reads the definition of the crafted item to find the items its made of
    // and convert those to definitions.
    // from then on basically the inverse of the above function, removes the crafted item 
    // then creates and adds the base items

    public void uncraftItem(ItemInterface crafted) throws ItemNotAvailableException {
        ItemInterface[] components = crafted.getDefinition().returnBaseItems();
        int arrLen = components.length;
        ItemDefinition[] bases = new ItemDefinition[arrLen];
        for (int i = 0; i < components.length; i++) {
            bases[i] = components[i].getDefinition();
        }

        if (!inventory.searchItems("").contains(crafted)
            && crafted.getDefinition().isBaseItemDef()) {
            throw new ItemNotAvailableException(crafted.getDefinition());
        }
        inventory.remove(crafted);

        for (int i = 0; i < bases.length; i++) {
            Item item = bases[i].create();
            inventory.addOne(item);
        }
    }
}
