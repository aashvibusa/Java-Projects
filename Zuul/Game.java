import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */
class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room entrance_hall, oak_hall, parlour, east_hall, smoking_room, spare_room,
            kitchen, dining_room, pantry, cellar, atrium, storage, study, bedroom,
            west_hall, back_hall, guest_room, back_room;
    ArrayList<Item> inventory = new ArrayList<Item>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Create the rooms
        entrance_hall = new Room("in the entrance hall of Bilbo's house at Bag End");
        oak_hall = new Room("in oak hall");
        parlour = new Room("in the parlour. The fireplace is on. Nice and toasty");
        east_hall = new Room("in east hall");
        smoking_room = new Room("in the smoking room. What a fine collection of cigars");
        spare_room = new Room("in the spare room");
        kitchen = new Room("in the kitchen. Something smells delicious");
        dining_room = new Room("in the dining room");
        pantry = new Room("in the pantry. What an enormous amount of food for a single Hobbit");
        cellar = new Room("in the cellar");
        atrium = new Room("in the atrium. That couch looks too comfortable");
        storage = new Room("in the storage. Wow, there is a lot of stuff in here");
        study = new Room("in the study. The desk is very messy, someone must have been busy");
        bedroom = new Room("in the bedroom");
        west_hall = new Room("in west hall");
        back_hall = new Room("in the back hall. It looks like the back door is locked");
        guest_room = new Room("in the guest room. You can certainly fit 13 dwarves and a wizard here");
        back_room = new Room("in the back room");

        // Initialize the room exits
        entrance_hall.setExit("north", oak_hall);
        entrance_hall.setExit("west", parlour);

        oak_hall.setExit("north", smoking_room);
        oak_hall.setExit("south", entrance_hall);
        oak_hall.setExit("east", spare_room);
        oak_hall.setExit("west", east_hall);

        parlour.setExit("north", east_hall);
        parlour.setExit("east", entrance_hall);
        parlour.setExit("west", kitchen);

        east_hall.setExit("south", parlour);
        east_hall.setExit("east", oak_hall);
        east_hall.setExit("west", atrium);

        smoking_room.setExit("south", oak_hall);
        smoking_room.setExit("west", pantry);

        spare_room.setExit("west", oak_hall);

        kitchen.setExit("east", parlour);
        kitchen.setExit("west", dining_room);

        dining_room.setExit("east", kitchen);
        dining_room.setExit("north", atrium);

        pantry.setExit("north", cellar);
        pantry.setExit("south", atrium);
        pantry.setExit("east", smoking_room);
        pantry.setExit("west", storage);

        cellar.setExit("south", pantry);
        cellar.setExit("west", storage);

        atrium.setExit("north", pantry);
        atrium.setExit("south", dining_room);
        atrium.setExit("east", east_hall);
        atrium.setExit("west", west_hall);

        storage.setExit("north", cellar);
        storage.setExit("east", pantry);
        storage.setExit("west", back_room);

        study.setExit("west", bedroom);

        bedroom.setExit("east", study);
        bedroom.setExit("west", back_hall);

        west_hall.setExit("north", back_room);
        west_hall.setExit("south", back_hall);
        west_hall.setExit("east", atrium);
        west_hall.setExit("west", guest_room);

        back_hall.setExit("north", west_hall);
        back_hall.setExit("east", bedroom);

        guest_room.setExit("east", west_hall);

        back_room.setExit("south", west_hall);
        back_room.setExit("east", storage);

        currentRoom = entrance_hall;  // start game in the entrance hall

        // Initialize the items
        smoking_room.setItem(new Item("pipe"));
        parlour.setItem(new Item("ring"));
        study.setItem(new Item("book"));
        pantry.setItem(new Item("seedcake"));
        back_room.setItem(new Item("handkerchief"));
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the Shire!");
        System.out.println("Find all the items to get ready for an adventure.");
        System.out.println("Type 'help' if you need help.");
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("inventory")) {
            printInventory();
        } else if (commandWord.equals("get")) {
            wantToQuit = getItem(command);
        } else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost and in need of assistance.");
        System.out.print("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }

    // Prints inventory to the console
    private void printInventory() {
        String output = "";
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("You are carrying: ");
        System.out.println(output);
    }

    // Adds item to inventory
    private boolean getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to get...
            System.out.println("Get what?");
            return false;
        }

        String item = command.getSecondWord();
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("That item is not here!");
        } else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up: " + item);
            if (inventory.size() == 5) {    // Win condition: collect all five items
                System.out.println("\r\n" + "Great job you found all the items! You are now ready to go on an adventure.");
                return true;
            }
        }
        return false;
    }

    // Removes item from inventory
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = null;
        int index = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getDescription().equals(item)) {
                newItem = inventory.get(i);
                index = i;
            }
        }

        if (newItem == null)
            System.out.println("That item is not in your inventory!");
        else {
            inventory.remove(index);
            currentRoom.setItem(new Item(item));
            System.out.println("Dropped: " + item);
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.play();
    }
}
