package game.memory;

import game.template.elements.BucketHeadZombie;
import game.template.elements.ConeHeadZombie;
import game.template.elements.LawnMower;
import game.template.elements.NormalZombie;
import game.template.bufferstrategy.GameState;
import utils.FileUtils;

import java.util.Map;

/**
 * This class merges all information of game that should be saved and
 * save them in the folder of users as a text file with username of player.
 * @author Tanya Djavaherpour, Elaheh Akbari
 * @version 1.0 2021
 */
public class Save {
    private GameState state;
    //normal / hard
    private String type;
    //day / night
    private String timeType;
    //player's username
    private String userName;
    //start time of the game
    private long startTime;
    //current time tha game has been saved
    private long currentTime;
    //number of saved suns
    private int sunNumber;
    //string of given information that should be written in the file
    private String cards;
    private String info;
    private String lifeInfo;
    private String normalZombies;
    private String coneHead;
    private String bucketHead;
    private String lawnMowers;
    private String sunFlowerTime;
    private String sunFlowerState;
    private String mushroomTime;
    private String mushroomState;
    private String sun;
    //final text
    private String text;
    //game over or end of game
    private String finishState;
    //number of the saved game
    private String gameNum;
    private static final String PATH = ".\\users\\";

    /**
     * Saves game
     * @param state as gameState
     * @param userName as name of the player
     * @param finishState , gameOver / endOfGame / notFinished
     * @param gameNum as number of saved game
     */
    public Save(GameState state, String userName, String finishState, String gameNum)
    {
        this.state = state;
        this.userName = userName;
        this.finishState = finishState;
        this.gameNum = gameNum;
        text = "";
        setInformation();
    }

    /**
     * Sets all necessary information to save them in a text file.
     */
    public void setInformation()
    {
        this.type = state.getType();
        this.timeType = state.getTimeType();
        this.startTime = state.getStartTime();
        this.currentTime = System.currentTimeMillis();
        this.sunNumber = state.getSunNumber();
        setCards();
        setPlaygroundInfo();
        setZombiesState();
        setLawMower();
        setSun();
        text = toString();
    }
    /**
     * Saves information as a text file in defined path.
     * @return the number of this game
     */
    public String saveInformation()
    {
        FileUtils.makeFolder(PATH);
        if(gameNum == null)
            gameNum = FileUtils.gamesWriter(text, PATH+userName+"\\");
        else
            FileUtils.fileWriterByFileName(text, gameNum, PATH+userName+"\\");
        return gameNum;
    }
    /**
     * Sets information which is necessary to show a card.
     */
    public void setCards()
    {
        cards = "";
        //CherryBomb
        cards = "cherryBomb " + state.getCherry().getCard() + " "
                + state.getCherry().getFlowerTime() + " " +state.getCherryBombState();
        //peaShooter
        cards = cards + "\npeaShooter " + state.getPea().getCard() + " " + state.getPea().getFlowerTime();
        //freezePea
        cards = cards + "\nfreezePeaShooter " + state.getFreezePea().getCard() + " " + state.getFreezePea().getFlowerTime();
        //squash
        cards = cards + "\nsquash " + state.getSquash().getCard() + " " + state.getSquash().getFlowerTime();
        //sunFlower
        cards = cards + "\nsunFlower " + state.getSunFlower().getCard() + " " + state.getSunFlower().getFlowerTime();
        //wallNut
        cards = cards + "\nwallNut " + state.getWallNut().getCard() + " " + state.getWallNut().getFlowerTime();
        //mushroom
        if(timeType.equals("night"))
            cards = cards + "\nmushroom " + state.getMushroom().getCard() + " " + state.getMushroom().getFlowerTime();
    }
    /**
     * Sets flowers state and their life state, by getting their hashmap from game state.
     * Also sets information of sunFlowers
     */
    public void setPlaygroundInfo()
    {
        info = "";
        lifeInfo = "";
        sunFlowerTime = "";
        sunFlowerState = "";
        mushroomState = "";
        mushroomTime = "";
        for (int j = 1; j <= 5; j++){
            for (int i = 1; i <= 9; i++){
                int loc = j * 10 + i;
                //playground info
                info = info + state.getInfo().get(loc) + " ";
                lifeInfo = lifeInfo + state.getLifeInfo().get(loc) + " ";
                //sunFlowers info
                sunFlowerTime = sunFlowerTime + state.getSunFlower().getSunFlowerSunTime().get(loc) + " ";
                sunFlowerState = sunFlowerState + state.getSunFlower().getSunFlowerState().get(loc) + " ";
                //mushroom info
                if(timeType.equals("night"))
                {
                    mushroomTime = mushroomTime + state.getMushroom().getSunFlowerSunTime().get(loc) + " ";
                    mushroomState = mushroomState + state.getMushroom().getSunFlowerState().get(loc) + " ";
                }
            }
            info = info + ("\n");
            lifeInfo = lifeInfo + "\n";
            sunFlowerTime = sunFlowerTime + ("\n");
            sunFlowerState = sunFlowerState + "\n";
            mushroomTime = mushroomTime + "\n";
            mushroomState = mushroomState + "\n";
        }
    }

    /**
     * Sets x  coordinate and row of all zombies which are in the playground.
     */
    public void setZombiesState()
    {
        normalZombies = "";
        coneHead = "";
        bucketHead = "";
        //normal zombies
        for (Map.Entry<Integer, NormalZombie> info: state.getZombie().getNormalInfo().entrySet())
            if (info.getValue().getX() > 0)
                normalZombies = normalZombies + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
        //Cone head zombies
        for (Map.Entry<Integer, ConeHeadZombie> info: state.getZombie().getConeInfo().entrySet())
            if (info.getValue().getX() > 0)
                coneHead = coneHead + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
        //Bucket head zombies
        for (Map.Entry<Integer, BucketHeadZombie> info: state.getZombie().getBucketInfo().entrySet())
            if (info.getValue().getX() > 0)
                bucketHead = bucketHead + info.getValue().getX() + " " +
                        info.getValue().getRow() + " " + info.getValue().getLife() + "\n";
    }

    /**
     * Sets lawn makers state based on their row and x coordinate.
     */
    public void setLawMower()
    {
        lawnMowers = "";
        for(LawnMower lawnMower: state.getLawnMowers())
            lawnMowers = lawnMowers + lawnMower.getX() + " " + lawnMower.getRow() + "\n";
    }

    /**
     * Sets dropping time, x and y coordinate of sun
     */
    public void setSun()
    {
        sun = state.getSunDropping() + " " + state.sunX + " " + state.sunY;
    }

    /**
     * Appends all information and make a string that should be saved in file.
     * @return text that should be saved.
     */
    @Override
    public String toString()
    {
        String res = finishState + "\ntype " + type + "\ntimeType " + timeType
                + "\nstartTime " + startTime + "\ncurrentTime " + currentTime +"\nsunNumber " + sunNumber
                + "\nsun " + sun
                + "\ninfo\n" + info + "lifeInfo\n" + lifeInfo
                + "sunFlowerTime\n" + sunFlowerTime + "sunFlowerState\n" + sunFlowerState
                + "normalZombies\n" + normalZombies + "coneHeadZombies\n" + coneHead + "bucketHeadZombies\n" + bucketHead
                + "lawnMowers\n" + lawnMowers + "cards\n" + cards;
        if(timeType.equals("night"))
            res = res + "\nmushroomTime\n" + mushroomTime + "mushroomState\n" + mushroomState;
        return res;
    }

}

