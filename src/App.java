import javax.swing.JFrame;

public class App {
    private Player player;
    private Storage storage;
    private JFrame frame;
    private PageManager manager;

    public App(Player p, Storage s) {
        player = p;
        storage = s;
        storage.addPlayer(p);
        storage.setStorage();

        manager = new PageManager(player, storage);

        // Setup of sorting
        setupSearching((InventoryPage) manager.findPage("Player Inventory"));
        setupSearching((InventoryPage) manager.findPage("Storage"));

        // Setup of craftng
        setupCrafting((ItemCraftPage) manager.findPage("Item Crafting"), player);
        setupUncrafting((ProductPage) manager.findPage("Product Page"), player);

        // Window creation
        manager.refresh();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(manager.getJPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Task 1: Defining what each button in the UI will do.
    void setupSearching(InventoryPage page) {
        page.addSearchByButton(new SearchByButton("All", () -> {
            player.getInventory().setSearch(Inventory.searchTypes.ALL);
            player.getStorageView().setSearch(Inventory.searchTypes.ALL);
        }));

        page.addSearchByButton(new SearchByButton("Name", () -> {
            player.getInventory().setSearch(Inventory.searchTypes.NAME);
            player.getStorageView().setSearch(Inventory.searchTypes.NAME);
        }));

        page.addSearchByButton(new SearchByButton("Description", () -> {
            player.getInventory().setSearch(Inventory.searchTypes.DESC);
            player.getStorageView().setSearch(Inventory.searchTypes.DESC);
        }));

        page.addSearchByButton(new SearchByButton("Weight (Greater than/equal to)", () -> {
            player.getInventory().setSearch(Inventory.searchTypes.WGHTASC);
            player.getStorageView().setSearch(Inventory.searchTypes.WGHTASC);
        }));

        page.addSearchByButton(new SearchByButton("Weight (Less than)", () -> {
            player.getInventory().setSearch(Inventory.searchTypes.WGHTDESC);
            player.getStorageView().setSearch(Inventory.searchTypes.WGHTDESC);
        }));
    }

    void setupCrafting(ItemCraftPage page, Player player) {
        page.setCraftAction((def) -> player.craftItem(def));
    }

    void setupUncrafting(ProductPage page, Player player) {
        page.setUncraftAction((item) -> player.uncraftItem(item));
    }
}
