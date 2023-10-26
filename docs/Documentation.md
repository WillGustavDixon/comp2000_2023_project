# Task 1
Behavioural pattern - Options: *Strategy* or *Observer* pattern.
You chose: Strategy Pattern - Item Searching

## Itemise changes made (which class files were modified)
1. Inventory.java 
2. App.java

# Task 2
Structural pattern - *Composite* pattern.

## Itemise changes made (which class files were modified)
1. CraftedItem.java (file created)
2. App.java
3. Player.java
4. ItemDefinition.java

# Task 3

## Itemised changes or new files

### 1. Observer design was added to the storage system. (TOPIC: OBSERVER DESIGN PATTERN)

#### What was changed
1. An update method was added to the Player.java class which updates the storage view when called.
2. 3 methods were added to Storage.java: 
   1. addPlayer: adds a player to a list of players that can interact with the storage
   2. removePlayer: removes a player from that same list
   3. setStorage: runs the update method of every observing player whenever something is stored or retrieved.
3. App was modified to use the addPlayer and setStorage methods

#### Why it was changed
1. This was changed to facilitate potential multiplayer interactions in the future and prevent problems from arising concerning the sharing of resources. The game does not have multiplayer functions as of yet, so the addPlayer and removePlayer functions are quite simplistic as they are just meant to represent the abilities of using an observer design.

#### The benefits of the change
1. This will allow players to retrieve and store resources for crafting without any bugs relating to resources disappearing or duplicating, as each player is updated immediately when something is changed.
2. Pre-emptively implementing systems that facilitate multiplayer interactions could limit the amount of restructuring that must be done in the future.

### 2. Added searching by weight to the searching system. (TOPIC: EXCEPTIONS)

#### What was changed
1. Inventory.java was modified to have 2 more values in the searchTypes enumerator, and its searchBy function was added to with these new enum values to search for all items with a weight either greater than/equal to or lower than the input weight. 
2. App.java was modified to add 2 more buttons to the inventory page corresponding to these new search choices.

#### Why it was changed
1. This was changed to allow convenience in finding certain items. Since no sorting function is supported as of yet, allowing users to find items based on their weight is an adequate solution. 

#### The benefits of the change
1. This will allow players to look through their inventories to find items that are either taking up too much space or aren't worth carrying around and can be put in storage.
2. When converting the search term to a double for use when deciding what to display, there is a try/catch statement that clears all items from the display if the user inputs something invalid, such as using letter characters. This is to signal to the player that their input was invalid. 
 