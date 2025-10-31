package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.actors.creatures.boss.BedOfChaos;
import game.actors.creatures.GoldenBeetle;
import game.actors.creatures.OmenSheep;
import game.actors.creatures.SpiritGoat;
import game.actors.npcs.*;
import game.behaviours.NPCController;
import game.behaviours.RandomNPCController;
import game.behaviours.StandardNPCController;
import game.dialogue.DialogueManager;
import game.dialogue.JsonDialogueParser;
import game.grounds.*;
import game.grounds.plants.Bloodrose;
import game.grounds.plants.Inheritree;
import game.items.Talisman;
import game.items.Seed;
import game.llm.GeminiService;
import game.llm.LLMService;
import game.timemanagement.ServiceLocator;
import game.timemanagement.TimeTracker;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        TimeTracker timeTracker = new TimeTracker();
        ServiceLocator.registerTimeProvider(timeTracker);

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil(), new TeleportationCircle());

        List<String> map = Arrays.asList(
                "xxxx...x..............................xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xx.........xxxx.....xx",
                "...xxxxx...........x...........xx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....x.......xxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.######",
                "..xxxx...xxxxxxxxx...#___#....xx..#____#",
                "xxxxx..xx......xxx...##_##...xxx..#____#",
                "xxxxx......................xxxxx...____#",
                "xxxxx..xx........xx.......xxxxxx..######");

        List<String> limveldMapLayout = Arrays.asList(
                "xxxx...xxxxxxxxxxxx.......xxxxxx",
                "xxx.....xxxxxxx..xxx.....xxxxxxx",
                "..........xxxx....xx.......xxxxx",
                "....xxx...........xx........xxxx",
                "...xxxxx.....................xxx",
                "...xxxxxxxxxx.................xx",
                "....xxxxxxxxxx.................x",
                "....xxxxxxxxxxx................x",
                "....xxxxxxxxxxx................x",
                "...xxxx...xxxxxx...............x",
                "...xxx....xxxxxxx..............x",
                "..xxxx...xxxxxxxxx.............x",
                "xxxxx...xxxxxxxxxx.............x",
                "xxxxx..xxxxxxxxxxx.............x",
                "xxxxx..xxxxxxxxxxxx............x");

        GameMap highLevelMapForTimeTracker = new EldenThingGameMap("Map to track time", groundFactory, '.', 1, 1, timeTracker);
        GameMap gameMap = new EldenThingGameMap("Valley of the Inheritree", groundFactory, map);
        GameMap limveldMap = new EldenThingGameMap("Limveld", groundFactory, limveldMapLayout);

        world.addGameMap(highLevelMapForTimeTracker);
        world.addGameMap(gameMap);
        world.addGameMap(limveldMap);

        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // Please add your Gemini API Key as an Environment Variable!
        LLMService gemini = new GeminiService(System.getenv("GEMINI_API_KEY"));
        DialogueManager dialogueManager = new DialogueManager(gemini, new JsonDialogueParser());



        Player player = new Player("Farmer", '@', 100, 200);
        player.hurt(20);
        player.addItemToInventory(new Seed("Bloodrose Seed", new Bloodrose(), 75));
        player.addItemToInventory(new Seed("Inheritree Seed", new Inheritree(), 25));
        world.addPlayer(player, gameMap.at(23, 10));
        player.addBalance(20000);

        NPCController standardController = new StandardNPCController();
        RandomNPCController randomNPCController = new RandomNPCController();

        // Create teleportation circles
        TeleportationCircle valleyCircle = new TeleportationCircle();  // Main portal in Valley
        TeleportationCircle limveldCircle1 = new TeleportationCircle(); // First return portal
        //can we have multiple teleportation circles?

        // Set up Valley locations
        Location valleyPortalLoc = gameMap.at(23, 14);  // Where the portal sits in Valley
        Location valleyDestLoc = gameMap.at(19, 5);     // Valley destination

        // Set up Limveld locations
        Location limveldDestLoc1 = limveldMap.at(20, 9);  // Limveld destination 1
        Location limveldDestLoc2 = limveldMap.at(15, 9);  // Limveld destination 2

        // Set teleportation circle on Valley portal location
        valleyPortalLoc.setGround(valleyCircle);

        // Set teleportation circle on Limveld return locations
        limveldDestLoc1.setGround(limveldCircle1);

        // Add multiple destinations to the Valley portal
        valleyCircle.addDestination(new TeleportDestination(gameMap, valleyDestLoc));         // To another spot in Valley
        valleyCircle.addDestination(new TeleportDestination(limveldMap, limveldDestLoc1));    // To Limveld 1
        valleyCircle.addDestination(new TeleportDestination(limveldMap, limveldDestLoc2));    // To Limveld 2

        // Add return destinations to Limveld portals
        limveldCircle1.addDestination(new TeleportDestination(gameMap, valleyPortalLoc));     // Back to Valley portal

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());
        gameMap.at(20, 1).addActor(new SpiritGoat(standardController));
        gameMap.at(10, 5 ).addActor(new OmenSheep(standardController));
        gameMap.at(20, 14).addActor(new Sellen(standardController));
        gameMap.at(20, 6).addActor(new MerchantKale(standardController));
        gameMap.at(21,5).addActor(new Guts(standardController));
        gameMap.at(38, 13).addActor(new Shabiri(standardController, dialogueManager));
        gameMap.at(38, 11).addActor(new Explorer(standardController, dialogueManager));
        gameMap.at(35, 11).addActor(new Narrator(standardController, dialogueManager));

        limveldMap.at(20,10).addActor(new BedOfChaos(standardController));

        gameMap.at(8, 7).addActor(new OmenSheep(standardController));
        gameMap.at(20, 12).addActor(new GoldenBeetle(standardController));
        gameMap.at(10, 14).addActor(new GoldenBeetle((randomNPCController)));
        world.run();

        for (String line : FancyMessage.YOU_DIED.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
