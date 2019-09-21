import java.util.*;

public class Main {
    private static Map<Integer, Integer> snakesMap;
    private static Map<Integer, Integer> laddersMap;
    private static List<String> playersList;
    private static List<Integer> playersPositionList;
    private static int nPlayers;
    private static int winningPlayer = -1;
    //main class for driving the program
    public static void main(String[] args) {

        varInit();
        getUserInput();
        winningPlayer = startGame();

        System.out.println(playersList.get(winningPlayer-1)+" wins the game");
    }

    private static int startGame() {
        int mMin = 1, mMax = 6, mDiceValue;
        int mPlayerCurrentPosition , mPlayerNewPosition;
        while (winningPlayer == -1){ //while there is no winner keep on moving the game
            for(int i=1; i<=nPlayers; i++){
                Random random = new Random();
                mDiceValue = random.nextInt(mMax-mMin+1) + mMin; //generate random number b/w 1-6
                mPlayerCurrentPosition = playersPositionList.get(i-1);
                mPlayerNewPosition = mPlayerCurrentPosition + mDiceValue;
                mPlayerNewPosition = confirmNewPosition(mPlayerNewPosition);    //recursively find new position

                if(mPlayerNewPosition > 100){
                    mPlayerNewPosition = mPlayerCurrentPosition;
                }

                playersPositionList.set(i-1, mPlayerNewPosition);
                System.out.println(playersList.get(i-1)+" rolled a "+mDiceValue+" and moved from "
                        +mPlayerCurrentPosition+" to "+mPlayerNewPosition);

                if(mPlayerNewPosition == 100){
                    winningPlayer = i;
                    break;
                }

            }
        }

        return winningPlayer;
    }

    private static int confirmNewPosition(int mPlayerNewPosition) {
        if(!snakesMap.containsKey(mPlayerNewPosition) && !laddersMap.containsKey(mPlayerNewPosition)){
            return mPlayerNewPosition;
        }

        if(snakesMap.containsKey(mPlayerNewPosition)){
            mPlayerNewPosition = snakesMap.get(mPlayerNewPosition);
            return confirmNewPosition(mPlayerNewPosition);
        }

        mPlayerNewPosition = laddersMap.get(mPlayerNewPosition);
        return confirmNewPosition(mPlayerNewPosition);
    }

    private static void getUserInput() {
        Scanner sc = new Scanner(System.in);
        int nSnakes = sc.nextInt();
        for(int i = 1; i<= nSnakes; i++)
            snakesMap.put(sc.nextInt(), sc.nextInt());

        int nLadders = sc.nextInt();
        for(int i = 1; i<= nLadders; i++)
            laddersMap.put(sc.nextInt(), sc.nextInt());

        nPlayers = sc.nextInt();
        for(int i=1; i<=nPlayers; i++){
            playersList.add(sc.next());
            playersPositionList.add(0);
        }


    }

    private static void varInit() {
        snakesMap = new HashMap<>();
        laddersMap = new HashMap<>();
        playersList = new ArrayList<>();
        playersPositionList = new ArrayList<>();
    }
}
